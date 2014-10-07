package com.applechip.core.util;


public class ExcelToDataUtil extends ExcelConvertUtil {

//  public static void convertForData(String srcFile, String destFile, boolean ignoreTestData) throws Exception {
//    Workbook workbook = WorkbookFactory.create(new FileInputStream(srcFile));
//    StringBuffer sb = new StringBuffer();
//    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//    sb.append("\n<dataset>");
//    int sheetCount = workbook.getNumberOfSheets();
//    if (sheetCount == 0)
//      return;
//
//    for (int i = 0; i < sheetCount; i++) {
//      String tableXml = getTableXml(workbook, i, ignoreTestData);
//      sb.append(tableXml);
//    }
//    sb.append("\n</dataset>");
//
//    PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(destFile), "utf-8"));
//    out.println(sb.toString());
//    out.close();
//  }
//
//  private static String getTableXml(Workbook wb, int i, boolean ignoreTestData) {
//    Sheet sheet = null;
//    String tableName;
//    tableName = wb.getSheetName(i);
//    sheet = wb.getSheetAt(i);
//    int rowCount = sheet.getPhysicalNumberOfRows();
//    if (rowCount == 0)
//      return "";
//
//    String tableDataXml = getTableDataXml(sheet, ignoreTestData);
//    if (StringUtils.isBlank(tableDataXml))
//      return "";
//
//    StringBuffer sb = new StringBuffer();
//    sb.append("\n<table name=\"").append(tableName).append("\">");
//    sb.append(tableDataXml);
//    sb.append("\n</table>");
//    return sb.toString();
//  }
//
//  private static String getTableDataXml(Sheet sheet, boolean ignoreTestData) {
//    List<String> columns = new ArrayList<String>();
//
//    String columnString = getColumnXml(sheet, columns, ignoreTestData);
//    if (StringUtils.isBlank(columnString))
//      return "";
//
//    String rowString = getRowXml(sheet, columns, ignoreTestData);
//    if (StringUtils.isBlank(rowString))
//      return "";
//
//    StringBuffer sb = new StringBuffer();
//    sb.append(columnString).append(rowString);
//    return sb.toString();
//  }
//
//  private static String getColumnXml(Sheet sheet, List<String> columns, boolean ignoreTestData) {
//    String columnName = "";
//    Row headerRow = sheet.getRow(0);
//    int cellCount = headerRow.getPhysicalNumberOfCells();
//
//    StringBuffer sb = new StringBuffer();
//    for (int ii = 0; ii < cellCount; ii++) {
//      Cell cell = headerRow.getCell(ii);
//      if (ii == 0 && ignoreTestData) {
//        if (cell == null)
//          return "";
//        try {
//          if (ii == 0 && ignoreTestData)
//            checkIgnoreTestData(cell);
//        } catch (Exception ex) {
//          return "";
//        }
//      }
//      columnName = getStringCellValue(null, cell);
//      if (StringUtils.isBlank(columnName))
//        continue;
//      columns.add(columnName);
//      sb.append("\n    ").append("<column>").append(columnName).append("</column>");
//    }
//    return sb.toString();
//  }
//
//  private static void checkIgnoreTestData(Cell cell) {
//    short color = cell.getCellStyle().getFillForegroundColor();
//    short targetColor = 13;
//    if (color != targetColor) {
//      throw new SystemException();
//    }
//  }
//
//  private static String getRowXml(Sheet sheet, List<String> columns, boolean ignoreTestData) {
//    int rowCount = sheet.getPhysicalNumberOfRows();
//    StringBuffer sb = new StringBuffer();
//    for (int j = 1; j < rowCount; j++) {
//      Row row = sheet.getRow(j);
//      if (row == null)
//        break;
//      StringBuffer rowTag = new StringBuffer("\n    <row>");
//      boolean isValidRow = false;
//      String value = "";
//
//      for (int k = 0; k < columns.size(); k++) {
//        Cell cell = row.getCell(k);
//        if (k == 0 && ignoreTestData) {
//          if (cell == null) {
//            isValidRow = false;
//            break;
//          }
//          try {
//            checkIgnoreTestData(cell);
//          } catch (Exception ex) {
//            isValidRow = false;
//            break;
//          }
//        }
//        value = getStringCellValue(null, cell);
//        if (k == 0 && StringUtils.isBlank(value)) {
//          isValidRow = false;
//          break;
//        }
//        if (value == null || "NULL".equalsIgnoreCase(value)) {
//          rowTag.append("\n         ").append("<null />");
//          continue;
//        }
//
//        if (!value.startsWith("<![CDATA[") || !value.endsWith("]]>"))
//          value = StringEscapeUtils.escapeXml(value);
//        rowTag.append("\n         ").append("<value description=\"").append(columns.get(k)).append("\">").append(value).append("</value>");
//        isValidRow = true;
//      }
//      rowTag.append("\n    </row>");
//      if (isValidRow)
//        sb.append(rowTag);
//    }
//    return sb.toString();
//  }

}
