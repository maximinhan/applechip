package com.applechip.core.util;

import org.junit.Ignore;
import org.junit.Test;

public class ExcelConvertUtilTest {
  @Test
  @Ignore
  public void convertExcelToData() throws Exception {
    ExcelToDataUtil.convertExcelToData("src/test/resources/resource_data.xls", "src/test/resources/resource_sample.xml", false);
    ExcelToDataUtil.convertExcelToData("src/test/resources/resource_data.xls", "src/test/resources/resource_default.xml", true);
  }

  @Test
  @Ignore
  public void testGenerateAll() throws Exception {
    ExcelToPropertiesUtil.convertExcelToProperties("src/test/resources/resource_message.xls", "src/main/resources/META-INF/message");
  }
}
