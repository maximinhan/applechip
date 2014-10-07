package com.applechip.core.util;


public class ExcelToPropertiesUtil extends ExcelConvertUtil {

  private static final String DEFAULT_LANGUAGE = "en";

//  public static void convertForMessage(String srcFile, String destDirPath) throws Exception {
//    Workbook workbook = WorkbookFactory.create(new FileInputStream(srcFile));
//    int sheetCount = workbook.getNumberOfSheets();
//    if (sheetCount == 0)
//      return;
//
//    for (int i = 0; i < sheetCount; i++) {
//      generateResources(workbook.getSheetAt(i), destDirPath);
//    }
//  }
//
//  private static void generateResources(Sheet sheet, String targetDirPath) throws Exception {
//    String sheetName = sheet.getSheetName();
//    List<Resource> resourceVOs = getResourceList(sheet);
//    generateResource(sheetName, resourceVOs, targetDirPath);
//  }
//
//  private static List<Resource> getResourceList(Sheet sheet) {
//    int rowCount = sheet.getPhysicalNumberOfRows();
//    Row header = sheet.getRow(0);
//    List<Resource> resourceList = createResourceList(header);
//    int languageCount = resourceList.size();
//    for (int i = 1; i < rowCount; i++) {
//      Row row = sheet.getRow(i);
//      String key = "";
//      for (int j = 0; j < languageCount + 1; j++) {
//        if (row == null)
//          break;
//        Cell cell = row.getCell(j);
//        if (j == 0) {
//          key = getStringCellValue(null, cell);
//          if (StringUtils.isBlank(key))
//            break;
//        } else {
//          String value = getStringCellValue(key, cell);
//          if (StringUtils.isBlank(resourceList.get(j - 1).getLanguage()))
//            continue;
//          resourceList.get(j - 1).getDataMap().put(key, value);
//        }
//      }
//    }
//    return resourceList;
//  }
//
//  private static List<Resource> createResourceList(Row header) {
//    int cellCount = header.getPhysicalNumberOfCells();
//    List<Resource> resourceList = new ArrayList<Resource>();
//    for (int i = 1; i < cellCount; i++) {
//      Resource vo = new Resource();
//      Cell cell = header.getCell(i);
//      vo.setLanguage(getStringCellValue(null, cell));
//      resourceList.add(vo);
//    }
//    return resourceList;
//  }
//
//  private static void generateResource(String sheetName, List<Resource> resourceList, String targetDirPath) throws Exception {
//    for (Resource vo : resourceList) {
//      generatePropertiesResource(sheetName, vo, targetDirPath);
//    }
//
//    Resource defaultResourcesVO = getDefaultResourceVO(resourceList);
//    generatePropertiesResource(sheetName, defaultResourcesVO, targetDirPath, "");
//  }
//
//  private static Resource getDefaultResourceVO(List<Resource> resourceList) {
//    for (Resource vo : resourceList) {
//      if (DEFAULT_LANGUAGE.equals(vo.getLanguage())) {
//        return vo;
//      }
//    }
//    return null;
//  }
//
//  private static void generatePropertiesResource(String sheetName, Resource resource, String targetDirPath) throws Exception {
//    if (StringUtils.isBlank(resource.getLanguage()))
//      return;
//    generatePropertiesResource(sheetName, resource, targetDirPath, resource.getLanguage());
//  }
//
//  private static void generatePropertiesResource(String sheetName, Resource vo, String targetDirPath, String targetLanguage) throws Exception {
//    String resourceFilePath = getResourceFilePath(sheetName, targetLanguage, targetDirPath);
//    PropertiesConfiguration config = new PropertiesConfiguration();
//    config.setEncoding("utf-8");
//    for (Map.Entry<String, String> entry : vo.getDataMap().entrySet()) {
//      if (entry != null && entry.getKey() != null)
//        config.addProperty(entry.getKey(), entry.getValue());
//    }
//    config.save(resourceFilePath);
//  }
//
//  private static String getResourceFilePath(String sheetName, String language, String targetDirPath) {
//    if (StringUtils.isBlank(language)) {
//      return String.format("%s/%s.properties", targetDirPath, sheetName);
//    } else {
//      return String.format("%s/%s_%s.properties", targetDirPath, sheetName, language);
//    }
//  }
//
//  public static class Resource extends AbstractObject {
//
//    private static final long serialVersionUID = -1025857826795867605L;
//
//    private String language;
//
//    private SortedMap<String, String> dataMap = new TreeMap<String, String>();
//
//    public String getLanguage() {
//      return language;
//    }
//
//    public void setLanguage(String language) {
//      this.language = language;
//    }
//
//    public SortedMap<String, String> getDataMap() {
//      return dataMap;
//    }
//
//    public void setDataMap(SortedMap<String, String> dataMap) {
//      this.dataMap = dataMap;
//    }
//  }

}
