package com.applechip.core.entity.support;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.applechip.core.exception.SystemException;
import com.applechip.core.util.CryptoUtil;

public class CustomDriverManagerDataSource extends DriverManagerDataSource {

  public void setPassword(String password) {
    super.setPassword(decrypt(password));
  }

  public void setUsername(String username) {
    super.setUsername(decrypt(username));
  }

  private String decrypt(String str) {
    try {
      return CryptoUtil.decrypt(str);
    } catch (SystemException e) {
      return str;
    }
  }

}
