package com.applechip.core.util;

import java.util.HashMap;
import java.util.Map;
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

  public static void convertExcelToProperties(String srcFile, String destDirPath) {
    Workbook workbook = getWorkbook(srcFile);
    int numberOfSheets = workbook.getNumberOfSheets();
    for (int sheetCount = 0; sheetCount < numberOfSheets; sheetCount++) {
      Sheet sheet = workbook.getSheetAt(sheetCount);
      Map<Integer, Resource> resourceMap = getResourceMap(sheet);
      generateResource(sheet.getSheetName(), resourceMap, destDirPath);
    }
  }

  private static Map<Integer, Resource> getResourceMap(Sheet sheet) {
    Map<Integer, Resource> resourceMap = getResourceMap(sheet.getRow(0));
    int languageCount = resourceMap.size();
    int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
    for (int rowCount = 1; rowCount < physicalNumberOfRows; rowCount++) {
      Row row = sheet.getRow(rowCount);
      String key = null;
      for (int cellCount = 0; cellCount <= languageCount; cellCount++) {
        Cell cell = row.getCell(cellCount);
        if (cellCount == 0) {
          key = getCellValue(cell);
          continue;
        }
        String value = getCellValue(key, cell);
        try {
          resourceMap.get(cellCount).getResourceMap().put(key, value);
        } catch (NullPointerException ex) {
          log.error("sheetName={}, key={}, value={}, cellCount={}", sheet.getSheetName(), key, value, cellCount);
        }
      }
    }
    return resourceMap;
  }

  private static Map<Integer, Resource> getResourceMap(Row row) {
    Map<Integer, Resource> resourceMap = new HashMap<Integer, ExcelToPropertiesUtil.Resource>();
    int physicalNumberOfCells = row.getPhysicalNumberOfCells();
    for (int cellCount = 1; cellCount < physicalNumberOfCells; cellCount++) {
      String cellValue = getCellValue(row.getCell(cellCount));
      if (StringUtil.equals(KEY, cellValue)) {
        continue;
      }
      resourceMap.put(cellCount, new Resource(cellValue, new TreeMap<String, String>()));
    }
    return resourceMap;
  }


  private static void generateResource(String sheetName, Map<Integer, Resource> resourceMap, String destDirPath) {
    Resource defaultResource = null;
    for (Entry<Integer, Resource> entry : resourceMap.entrySet()) {
      if (StringUtil.equals(DEFAULT_LANGUAGE, entry.getValue().getLanguage())) {
        defaultResource = entry.getValue();
      }
      generatePropertiesResource(sheetName, entry.getValue(), destDirPath, entry.getValue().getLanguage());
    }
    if (defaultResource == null) {
      defaultResource = resourceMap.get(0);
    }
    generatePropertiesResource(sheetName, defaultResource, destDirPath, "");
  }

  private static void generatePropertiesResource(String sheetName, Resource resource, String destDirPath, String language) {
    String resourceFilePath = getResourceFilePath(sheetName, language, destDirPath);
    PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
    propertiesConfiguration.setDelimiterParsingDisabled(true);
    propertiesConfiguration.setEncoding(SystemConstant.CHARSET.toString());
    if (resource.getResourceMap() == null) {
      return;
    }
    for (Entry<String, String> entry : resource.getResourceMap().entrySet()) {
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
    private SortedMap<String, String> resourceMap;
  }
}
