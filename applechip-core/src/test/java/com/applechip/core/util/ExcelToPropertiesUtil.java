package com.applechip.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.applechip.core.AbstractObject;
import com.applechip.core.constant.SystemConstant;

@Slf4j
public class ExcelToPropertiesUtil extends ExcelConvertUtil {

  private static final String DEFAULT_LANGUAGE = "en";
  private static final String KEY = "key";

  public static void convertForMessage(String srcFile, String destDirPath) {
    Workbook workbook = getWorkbook(srcFile);
    int numberOfSheets = workbook.getNumberOfSheets();
    if (numberOfSheets == 0)
      return;

    for (int sheetCount = 0; sheetCount < numberOfSheets; sheetCount++) {
      generateResources(workbook.getSheetAt(sheetCount), destDirPath);
    }
  }

  private static void generateResources(Sheet sheet, String destDirPath) {
    String sheetName = sheet.getSheetName();
    List<Resource> resources = getResourceList(sheet);
    generateResource(sheetName, resources, destDirPath);
  }

  private static List<Resource> getResourceList(Sheet sheet) {
    List<Resource> resources = createResourceLanguageList(sheet.getRow(0));
    int languageCount = resources.size() + 1;
    for (Resource resource : resources) {
      Row row = sheet.getRow(resource.getNumber());
      String key = null;
      for (int cellCount = 0; cellCount < languageCount; cellCount++) {
        Cell cell = row.getCell(cellCount);
        if (cellCount == 0) {
          key = getCellValue(cell);
          continue;
        }
        String value = getCellValue(key, cell);
        try {
          resources.get(cellCount - 1).getDataMap().put(key, value);
        } catch (NullPointerException ex) {
          log.error("sheetName={}, key={}, value={}, cellCount={}", sheet.getSheetName(), key, value, cellCount);
        }
      }
    }
    return resources;
  }

  private static List<Resource> createResourceLanguageList(Row row) {
    int physicalNumberOfCells = row.getPhysicalNumberOfCells();
    List<Resource> resources = new ArrayList<Resource>();
    for (int cellCount = 0; cellCount < physicalNumberOfCells; cellCount++) {
      Cell cell = row.getCell(cellCount);
      String cellValue = getCellValue(cell);
      if (StringUtil.equals(KEY, cellValue)) {
        continue;
      }
      resources.add(new Resource(cellValue, cellCount, new TreeMap<String, String>()));
    }
    return resources;
  }

  private static void generateResource(String sheetName, List<Resource> resources, String destDirPath) {
    Resource defaultResource = null;
    for (Resource resource : resources) {
      if (DEFAULT_LANGUAGE.equals(resource.getLanguage())) {
        defaultResource = resource;
      }
      generatePropertiesResource(sheetName, resource, destDirPath, resource.getLanguage());
    }
    if (defaultResource == null) {
      defaultResource = resources.get(0);
    }
    generatePropertiesResource(sheetName, defaultResource, destDirPath, "");
  }

  private static void generatePropertiesResource(String sheetName, Resource resource, String destDirPath, String language) {
    String resourceFilePath = getResourceFilePath(sheetName, language, destDirPath);
    PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
    propertiesConfiguration.setDelimiterParsingDisabled(true);
    propertiesConfiguration.setEncoding(SystemConstant.CHARSET.toString());
    if (resource.getDataMap() == null) {
      return;
    }
    for (Entry<String, String> entry : resource.getDataMap().entrySet()) {
      if (entry != null && entry.getKey() != null) {
        propertiesConfiguration.addProperty(entry.getKey(), entry.getValue());
      }
    }
    try {
      propertiesConfiguration.save(resourceFilePath);
    } catch (ConfigurationException e) {
      e.printStackTrace();
    }
  }

  private static String getResourceFilePath(String sheetName, String language, String targetDirPath) {
    String resourceFilePath = String.format("%s%s%s", targetDirPath, SystemConstant.FILE_SEPARATOR, sheetName);
    if (StringUtil.isNotBlank(language)) {
      resourceFilePath = String.format("%s_%s", resourceFilePath, language);
    }
    return String.format("%s.properties", resourceFilePath);
  }

  @Getter
  @Setter
  @AllArgsConstructor
  public static class Resource extends AbstractObject {

    private static final long serialVersionUID = -1025857826795867605L;

    private String language;

    private int number;

    private SortedMap<String, String> dataMap;
  }
}
