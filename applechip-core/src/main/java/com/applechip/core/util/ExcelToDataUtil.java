package com.applechip.core.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.applechip.core.constant.SystemConstant;


public class ExcelToDataUtil extends ExcelConvertUtil {

  public static void convertExcelToData(String srcFile, String destFile, boolean ignoreSampleData) {
    Workbook workbook = getWorkbook(srcFile);
    int sheetCount = workbook.getNumberOfSheets();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(String.format("%s%s%s", "<?xml version=\"1.0\" encoding=\"", SystemConstant.CHARSET.toString(), "\"?>"));
    stringBuilder.append(String.format("%s%s", SystemConstant.LINE_SEPARATOR, "<dataset>"));
    for (int i = 0; i < sheetCount; i++) {
      String tableData = getTableData(workbook, i, ignoreSampleData);
      stringBuilder.append(tableData);
    }
    stringBuilder.append(String.format("%s%s", SystemConstant.LINE_SEPARATOR, "</dataset>"));

    FileOutputStream fileOutputStream = null;
    PrintWriter printWriter = null;
    try {
      fileOutputStream = new FileOutputStream(destFile);
      printWriter = new PrintWriter(new OutputStreamWriter(fileOutputStream, SystemConstant.CHARSET));
      printWriter.println(stringBuilder.toString());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {
      IOUtil.closeQuietly(Arrays.asList(printWriter, fileOutputStream));
    }
  }

  private static String getTableData(Workbook workbook, int i, boolean ignoreSampleData) {
    Sheet sheet = workbook.getSheetAt(i);
    int rowCount = sheet.getPhysicalNumberOfRows();
    if (rowCount == 0)
      return "";

    String tableDataXml = getTableDataXml(sheet, ignoreSampleData);
    if (StringUtil.isBlank(tableDataXml))
      return "";

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(String.format("%s%s%s%s", SystemConstant.LINE_SEPARATOR, "<table name=\"", workbook.getSheetName(i), "\">"));
    stringBuilder.append(tableDataXml);
    stringBuilder.append(String.format("%s%s", SystemConstant.LINE_SEPARATOR, "</table>"));
    return stringBuilder.toString();
  }

  private static String getTableDataXml(Sheet sheet, boolean ignoreSampleData) {
    List<String> columns = new ArrayList<String>();

    String columnData = getColumnData(sheet, columns, ignoreSampleData);
    if (StringUtil.isBlank(columnData))
      return "";

    String rowData = getRowData(sheet, columns, ignoreSampleData);
    if (StringUtil.isBlank(rowData))
      return "";

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(String.format("%s%s", columnData, rowData));
    return stringBuilder.toString();
  }

  private static String getColumnData(Sheet sheet, List<String> columns, boolean ignoreSampleData) {
    String columnName = "";
    Row headerRow = sheet.getRow(0);
    int physicalNumberOfCells = headerRow.getPhysicalNumberOfCells();

    StringBuilder stringBuilder = new StringBuilder();
    for (int cellCount = 0; cellCount < physicalNumberOfCells; cellCount++) {
      Cell cell = headerRow.getCell(cellCount);
      if (cellCount == 0 && ignoreSampleData) {
        if (cell == null) {
          return stringBuilder.toString();
        }
        if (isIgnoreSampleData(cell)) {
          return stringBuilder.toString();
        }
      }
      columnName = getCellValue(cell);
      if (StringUtil.isBlank(columnName))
        continue;
      columns.add(columnName);
      stringBuilder.append(String.format("%s%s%s%s%s", SystemConstant.LINE_SEPARATOR, SystemConstant.TAB_SEPARATOR, "<column>", columnName, "</column>"));
    }
    return stringBuilder.toString();
  }

  private static boolean isIgnoreSampleData(Cell cell) {
    return cell.getCellStyle().getFillForegroundColor() != 13;
  }

  private static String getRowData(Sheet sheet, List<String> columns, boolean ignoreTestData) {
    int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
    StringBuilder stringBuilder = new StringBuilder();
    for (int rowCount = 1; rowCount < physicalNumberOfRows; rowCount++) {
      Row row = sheet.getRow(rowCount);
      if (row == null)
        break;
      StringBuilder sb = new StringBuilder(String.format("%s%s%s", SystemConstant.LINE_SEPARATOR, SystemConstant.TAB_SEPARATOR, "<row>"));
      boolean isValidRow = false;
      String value = "";
      int physicalNumberOfCells = columns.size();
      for (int cellCount = 0; cellCount < physicalNumberOfCells; cellCount++) {
        Cell cell = row.getCell(cellCount);
        if (cellCount == 0 && ignoreTestData) {
          if (cell == null) {
            isValidRow = false;
            break;
          }
          if (isIgnoreSampleData(cell)) {
            isValidRow = false;
            break;
          }
        }
        value = getCellValue(cell);
        if (cellCount == 0 && StringUtil.isBlank(value)) {
          isValidRow = false;
          break;
        }
        if (StringUtil.isBlank(value) || StringUtil.equalsIgnoreCase("null", value)) {
          sb.append(String.format("%s%s%s%s", SystemConstant.LINE_SEPARATOR, SystemConstant.TAB_SEPARATOR, SystemConstant.TAB_SEPARATOR, "<null />"));
          continue;
        }

        if (!value.startsWith("<![CDATA[") || !value.endsWith("]]>")) {
          value = StringEscapeUtil.escapeXml(value);
        }
        sb.append(String.format("%s%s%s%s%s%s%s%s", SystemConstant.LINE_SEPARATOR, SystemConstant.TAB_SEPARATOR, SystemConstant.TAB_SEPARATOR, "<value description=\"", columns.get(cellCount), "\">",
            value, "</value>"));
        isValidRow = true;
      }
      sb.append(String.format("%s%s%s", SystemConstant.LINE_SEPARATOR, SystemConstant.TAB_SEPARATOR, "</row>"));
      if (isValidRow) {
        stringBuilder.append(sb);
      }
    }
    return stringBuilder.toString();
  }
}
