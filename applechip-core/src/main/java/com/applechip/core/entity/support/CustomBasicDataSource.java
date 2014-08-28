package com.applechip.core.entity.support;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import com.applechip.core.exception.SystemException;
import com.applechip.core.util.CryptoUtil;

public class CustomBasicDataSource extends BasicDataSource {

  public void setPassword(String password) {
    super.setPassword(decrypt(password));
  }

  public void setUsername(String username) {
    super.setUsername(decrypt(username));
  }

  public Connection getConnection(String username, String password) throws SQLException {
    return super.getConnection();
  }

  private String decrypt(String str) {
    try {
      return CryptoUtil.decrypt(str);
    } catch (SystemException e) {
      return str;
    }
  }

}
