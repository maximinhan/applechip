package com.applechip.core.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.applechip.core.constant.SystemConstant;


public class ExcelToDataUtil extends ExcelConvertUtil {

  public static void convertForData(String srcFile, String destFile, boolean ignoreSampleData) throws Exception {
    Workbook workbook = WorkbookFactory.create(new FileInputStream(srcFile));
    int sheetCount = workbook.getNumberOfSheets();
    if (sheetCount == 0)
      return;

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(String.format("%s%s%s", "<?xml version=\"1.0\" encoding=\"", SystemConstant.CHARSET.toString(), "\"?>"));
    for (int i = 0; i < sheetCount; i++) {
      String tableData = getTableData(workbook, i, ignoreSampleData);
      stringBuilder.append(tableData);
    }
    stringBuilder.append(String.format("%s%s", SystemConstant.LINE_SEPARATOR, "</dataset>"));

    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(destFile), SystemConstant.CHARSET));
    printWriter.println(stringBuilder.toString());
    printWriter.close();
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
        if (cell == null || isIgnoreSampleData(cell))
          return stringBuilder.toString();
      }
      columnName = getCellValue(null, cell);
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
    StringBuffer sb = new StringBuffer();
    for (int rowCount = 1; rowCount < physicalNumberOfRows; rowCount++) {
      Row row = sheet.getRow(rowCount);
      if (row == null)
        break;
      StringBuffer rowTag = new StringBuffer("\n    <row>");
      boolean isValidRow = false;
      String value = "";

      for (int k = 0; k < columns.size(); k++) {
        Cell cell = row.getCell(k);
        if (k == 0 && ignoreTestData) {
          if (cell == null) {
            isValidRow = false;
            break;
          }
          try {
            isIgnoreSampleData(cell);
          } catch (Exception ex) {
            isValidRow = false;
            break;
          }
        }
        value = getCellValue(null, cell);
        if (k == 0 && StringUtil.isBlank(value)) {
          isValidRow = false;
          break;
        }
        if (value == null || "NULL".equalsIgnoreCase(value)) {
          rowTag.append("\n         ").append("<null />");
          continue;
        }

        if (!value.startsWith("<![CDATA[") || !value.endsWith("]]>"))
          value = StringEscapeUtil.escapeXml(value);
        rowTag.append("\n         ").append("<value description=\"").append(columns.get(k)).append("\">").append(value).append("</value>");
        isValidRow = true;
      }
      rowTag.append("\n    </row>");
      if (isValidRow)
        sb.append(rowTag);
    }
    return sb.toString();
  }
}
