package com.applechip.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelConvertUtil {
  protected static String getCellValue(String key, Cell cell) {
    String result = null;
    if (cell == null) {
      if (StringUtil.isNotBlank(key)) {
        result = key;
      }
      return StringUtil.defaultString(result);
    }
    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
      result = String.valueOf((double) cell.getNumericCellValue());
    } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
      result = cell.getStringCellValue();
    } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
      result = String.valueOf((int) cell.getNumericCellValue());
    } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
      result = String.valueOf(cell.getBooleanCellValue());
    }
    return StringUtil.defaultString(result);
  }

  protected static String getCellValue(Cell cell) {
    return getCellValue("", cell);
  }

  protected static Workbook getWorkbook(String srcFile) {
    Workbook workbook = null;
    FileInputStream fileInputStream = null;
    try {
      fileInputStream = new FileInputStream(srcFile);
      workbook = WorkbookFactory.create(fileInputStream);
    } catch (InvalidFormatException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtil.closeQuietly(fileInputStream);
    }
    return workbook;
  }
}
