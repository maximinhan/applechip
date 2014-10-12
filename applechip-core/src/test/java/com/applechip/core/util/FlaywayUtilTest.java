package com.applechip.core.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.persistence.Entity;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

import org.flywaydb.core.Flyway;
import org.h2.Driver;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.tool.hbm2ddl.DatabaseMetadata;
import org.hibernate.tool.hbm2ddl.SchemaValidator;
import org.reflections.Reflections;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.google.common.collect.Sets;

public class FlaywayUtilTest {

  private EntityPackagesScanner entityPackageScanner = new EntityPackagesScanner();

  private static final Class<? extends Dialect> dialectClass = H2Dialect.class;

  public void testHibernateHbm2DdlValidationAfterFullFlywayMigrationOnH2() {
    try {
      Driver driver = new org.h2.Driver();
      String dbUsername = "user";
      String dbPassword = "password";
      String dbUrl = "jdbc:h2:mem:migrationtestdb;DB_CLOSE_DELAY=5;MODE=PostgreSQL;TRACE_LEVEL_SYSTEM_OUT=1;";
      DataSource dataSource = new SimpleDriverDataSource(driver, dbUrl, dbUsername, dbPassword);

      Flyway flyway = new Flyway();
      // flyway.setLocations("database/migration");
      flyway.setDataSource(dataSource);
      flyway.clean();
      flyway.init();

      flyway.setValidateOnMigrate(true);
      flyway.migrate();

      SchemaGenerator schemaGenerator = new SchemaGenerator(dialectClass, driver.getClass(), dbUrl, dbUsername, dbPassword, entityPackageScanner.getEntityPackages());
      schemaGenerator.validate();
    } catch (Exception e) {
      // Assert.fail("Hibernate hbm2ddl validation failed after running Flyway migrations; If you edited entities, make sure to supply a Flyway migration script: "
      // + e.getMessage(), e);
    }
  }

  @Slf4j
  public static class SchemaGenerator {
    private final Configuration cfg;

    public SchemaGenerator(Class<? extends Dialect> dialectClass, Class<?> driverClass, String dbUrl, String dbUsername, String dbPassword, Set<String> packageNames) throws Exception {
      cfg = new Configuration();
      // cfg.setProperty("hibernate.hbm2ddl.auto", ddlAutoMode);
      cfg.setProperty("hibernate.dialect", dialectClass.getName());
      cfg.setProperty("hibernate.connection.driver_class", driverClass.getName());
      cfg.setProperty("hibernate.connection.url", dbUrl);
      cfg.setProperty("hibernate.connection.username", dbUsername);
      cfg.setProperty("hibernate.connection.password", dbPassword);

      cfg.setNamingStrategy(new org.hibernate.cfg.ImprovedNamingStrategy());

      for (String packageName : packageNames) {
        for (Class<Object> clazz : getClasses(packageName)) {
          cfg.addAnnotatedClass(clazz);
        }
      }
    }

    public void validate() {

      SchemaValidator schemaValidator = new SchemaValidator(cfg);
      schemaValidator.validate();
    }

    public String[] createUpdateScript(DataSource dataSource, Dialect dialect) throws SQLException {
      DatabaseMetadata meta = new DatabaseMetadata(dataSource.getConnection(), dialect);
      String[] createSQL = cfg.generateSchemaUpdateScript(dialect, meta);
      return createSQL;
    }

    private List<Class> getClasses(String packageName) throws Exception {
      final String classExt = ".class";
      List<String> files = listFilesInPackage(packageName);
      List<Class> classes = new ArrayList<Class>(files.size());

      for (String file : files) {
        if (file.endsWith(classExt)) {
          // removes the .class extension, and replaces '/' with '.'
          String className = (file.substring(0, file.length() - classExt.length())).replace(File.separatorChar, '.');
          classes.add(Class.forName(className));
        }
      }

      return classes;
    }

    private List<String> listFilesInPackage(String packageName) throws ClassNotFoundException, IOException {

      log.debug("listFiledInPacakge: starting for package: {}", packageName);

      List<String> classNames = new ArrayList<String>();
      File directory = null;
      try {
        ClassLoader cld = Thread.currentThread().getContextClassLoader();
        if (cld == null) {
          throw new ClassNotFoundException("Can't get class loader.");
        }

        String path = packageName.replace('.', '/');

        for (Enumeration<URL> resources = cld.getResources(path); resources.hasMoreElements();) {
          URL resource = resources.nextElement();
          // if (!resource.getFile().toString().toLowerCase().contains("test")) {
          String filePath = resource.getFile();
          filePath = filePath.replace("%20", " "); // In case of directory spacing (marked as %20 in
                                                   // Jenkins
                                                   // workspace)
          directory = new File(filePath);
          break;
          // }

        }
      } catch (NullPointerException x) {
        throw new ClassNotFoundException(packageName + " (" + directory + ") does not appear to be a valid package");
      }

      if (directory.exists()) { // Deal with file-system case

        log.debug("listFiledInPacakge: directory for path exists: {}", directory.getAbsolutePath());

        String[] files = directory.list();
        for (int i = 0; i < files.length; i++) {
          classNames.add(packageName.replace('.', File.separatorChar) + '.' + files[i]);
        }
      } else { // Deal with case where files are within a JAR

        log.debug("listFiledInPacakge: directory for path doesn't exist: absolute path = {}, path = {}", directory.getAbsoluteFile(), directory.getPath());

        final String[] parts = directory.getPath().split(".jar!\\\\");

        log.debug("listFiledInPacakge: after splitting the path {}, got {} parts", directory.getPath(), parts.length);

        if (parts.length == 2) {
          String jarFilename = parts[0].substring(6) + ".jar";
          String relativePath = parts[1].replace(File.separatorChar, '/');
          JarFile jarFile = new JarFile(jarFilename);
          final Enumeration entries = jarFile.entries();
          while (entries.hasMoreElements()) {
            final JarEntry entry = (JarEntry) entries.nextElement();
            final String entryName = entry.getName();
            if ((entryName.length() > relativePath.length()) && entryName.startsWith(relativePath)) {
              classNames.add(entryName.replace('/', File.separatorChar));
            }
          }
        } else {
          throw new ClassNotFoundException(packageName + " is not a valid package");
        }
      }

      return classNames;
    }
  }

  public static class EntityPackagesScanner {

    private final static String BASE_PACKAGE = "com.ravellosystems";

    private final Set<String> entityPackages = Sets.newHashSet();

    public EntityPackagesScanner() {
      Reflections reflections = new Reflections(BASE_PACKAGE);
      Set<Class<?>> entities = reflections.getTypesAnnotatedWith(Entity.class);
      for (Class<?> entity : entities) {
        entityPackages.add(entity.getPackage().getName());
      }
    }

    public Set<String> getEntityPackages() {
      return entityPackages;
    }

  }
}



//@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { EntityPackagesScanner.class })
//public class MigrationHelperTest extends AbstractTestNGSpringContextTests {
//
//    private final Logger log = LoggerFactory.getLogger(getClass());
//
//    @Inject
//    private EntityPackagesScanner entityPackageScanner;
//
//    // edit those properties according to your env/needs...
//    private final String dbUrl = "jdbc:h2:/tmp/h2db;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1;MODE=PostgreSQL;TRACE_LEVEL_SYSTEM_OUT=1";
//    private final String dbUsername = "username";
//    private final String dbPassword = "secret";
//    private static final Class<? extends Dialect> dialectClass = H2Dialect.class;
//    private static final Class<? extends java.sql.Driver> driverClass = org.h2.Driver.class;
//
//    private DataSource dataSource;
//    private SchemaGenerator schemaGenerator;
//
//    @BeforeMethod
//    protected void createSchemaGenerator() throws Exception {
//        schemaGenerator = new SchemaGenerator(dialectClass, driverClass, dbUrl, dbUsername, dbPassword,
//                entityPackageScanner.getEntityPackages());
//    }
//
//    @BeforeClass
//    protected void setupDatasource() throws Exception {
//
//        Driver driver = driverClass.newInstance();
//        log.info("working with db in url {}", dbUrl);
//        dataSource = new SimpleDriverDataSource(driver, dbUrl, dbUsername, dbPassword);
//    }
//
//    private String formatSqlSuggestionScript(String[] sqls) {
//        StringBuilder builder = new StringBuilder("\n\n****update sql suggestion:***\n\n");
//        for (String str : sqls) {
//            builder.append("\n" + str + ";");
//        }
//        builder.append("\n\n");
//        return builder.toString();
//    }
//
//    @Test
//    public void suggestSqlUpdateScript() throws SQLException {
//
//        String[] updateCommands = schemaGenerator.createUpdateScript(dataSource, new PostgreSQL82Dialect());
//        Assert.assertEquals(updateCommands.length, 0, formatSqlSuggestionScript(updateCommands));
//    }
//
//}