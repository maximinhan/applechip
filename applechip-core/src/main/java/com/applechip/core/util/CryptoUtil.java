package com.applechip.core.util;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.Getter;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

import com.applechip.core.exception.SystemException;

public class CryptoUtil {

  public enum Digest {

    SHA_256("SHA-256"), SHA_384("SHA-384"), SHA_512("SHA-512"), MD5("MD5");

    private String name;

    private Digest(String name) {
      this.name = name;
    }

    public boolean equals(String name) {
      return (name == null) ? false : this.name.equals(name);
    }

    public String toString() {
      return name;
    }
  }

  public static String toHexStringByDigest(String string, Digest digest) {
    String result = "";
    try {
      switch (digest) {
        case SHA_256:
          result = DigestUtils.sha256Hex(string);
        case SHA_384:
          result = DigestUtils.sha512Hex(string);
        case SHA_512:
          result = DigestUtils.sha512Hex(string);
        case MD5:
          result = DigestUtils.md5Hex(string);
      }
    } catch (Exception e) {
      throw new SystemException(String.format("cryto fail.. %s", e));
    }
    return result;
  }

  public static String encrypt(String decryptText) {
    Crypto crypto = Crypto.getInstance();
    Cipher cipher = crypto.getCipher();
    String decrypt = null;
    try {
      cipher.init(Cipher.ENCRYPT_MODE, crypto.getKeySpec(), crypto.getIvSpec());
      decrypt = StringUtils.newStringUtf8(Base64.encodeBase64(cipher.doFinal(StringUtils.getBytesUtf8(decryptText))));
    } catch (GeneralSecurityException e) {
      throw new SystemException(String.format("cryto fail.. %s", e.getMessage()), e);
    } catch (IllegalArgumentException e) {
      throw new SystemException(String.format("cryto fail.. %s", e.getMessage()), e);
    } catch (NullPointerException e) {
      throw new SystemException(String.format("cryto fail.. %s", e.getMessage()), e);
    }
    return decrypt;
  }

  public static String decrypt(String encryptText) {
    Crypto crypto = Crypto.getInstance();
    Cipher cipher = crypto.getCipher();
    String decrypt = null;
    try {
      if (encryptText.length() < 2) {
        throw new IllegalArgumentException("The field must not be length() < 2");
      }
      cipher.init(Cipher.DECRYPT_MODE, crypto.getKeySpec(), crypto.getIvSpec());
      decrypt = StringUtils.newStringUtf8(cipher.doFinal(Base64.decodeBase64(StringUtils.getBytesUtf8(encryptText))));
    } catch (GeneralSecurityException e) {
      throw new SystemException(String.format("cryto fail.. %s", e.getMessage()), e);
    } catch (IllegalArgumentException e) {
      throw new SystemException(String.format("cryto fail.. %s", e.getMessage()), e);
    } catch (NullPointerException e) {
      throw new SystemException(String.format("cryto fail.. %s", e.getMessage()), e);
    }
    return decrypt;
  }

  public static String forceDecrypt(String str) {
    try {
      return decrypt(str);
    } catch (SystemException e) {
      return str;
    }
  }

  public static String encrypt(String decryptText, Digest digest) {
    try {
      MessageDigest md = MessageDigest.getInstance(digest.toString());
      md.update(StringUtils.getBytesUtf8(decryptText));
      return toHexString(md.digest());
    } catch (NoSuchAlgorithmException e) {
      throw new SystemException(String.format("cryto fail.. %s", e.getMessage()), e);
    }
  }

  private static String toHexString(byte[] bytes) {
    StringBuffer result = new StringBuffer();
    for (byte b : bytes) {
      result.append(Integer.toString((b & 0xF0) >> 4, 16));
      result.append(Integer.toString(b & 0x0F, 16));
    }
    return result.toString();
  }

  public static void main(String[] args) {

    // CryptoUtil a = new CryptoUtil();
    // CryptoUtil b = new CryptoUtil();
    // Crypto a = Crypto.getInstance();
    // Crypto b = Crypto.getInstance();
    //
    // System.out.println("a.equals(b):" + a.equals(b));
    // System.out.println("a==b" + (a == b));
    //
    // System.out.println(CryptoUtil.toHexStringByDigest("test", Digest.MD5));
    // System.out.println(CryptoUtil.toHexStringByDigest("test", Digest.SHA_256));
    // System.out.println(CryptoUtil.encrypt("password"));
    // // System.out.println(CryptoUtil.decrypt("password"));
    // System.out.println(CryptoUtil.decrypt("iaLQS4uk5SrS0oV+mm7s/g=="));
    //
    // System.out.println(CryptoUtil.decrypt("iaLQS4uk5SrS0oV+mm7s/g=="));
    // System.out.println(SecurityUtil.getSecurityKey());
    // System.out.println(SecurityUtil.getSecurityKey());
    System.out.println(CryptoUtil.decrypt("43mtVG+B6SrIXd2BLH0DDQ=="));

    // Field[] fields = BasicDataSourceFactory.class.getDeclaredFields();
    // for (Field field : fields) {
    // if(String.class.equals(field.getType())){
    // field.setAccessible(true);
    // try {
    // System.out.println(field.get(field.getName()).toString());
    // } catch (IllegalArgumentException e) {
    // // TODO Auto-generated catch block
    // // e.printStackTrace();
    // } catch (IllegalAccessException e) {
    // // TODO Auto-generated catch block
    // // e.printStackTrace();
    // }
    // // clazz.get
    // }
    // }


    // Field[] fieldsCfg = org.hibernate.cfg.AvailableSettings.class.getDeclaredFields();
    // Field[] fieldsjpa = org.hibernate.jpa.AvailableSettings.class.getDeclaredFields();
    // Field[] result = Arrays.copyOf(fieldsCfg, fieldsCfg.length + fieldsjpa.length);
    // System.arraycopy(fieldsjpa, 0, result, fieldsCfg.length, fieldsjpa.length);
    // for (Field field : result) {
    // if (String.class.equals(field.getType())) {
    // if (!field.isAccessible()) {
    // field.setAccessible(true);
    // }
    // try {
    // System.out.println(field.get(field.getName()).toString());
    // } catch (IllegalArgumentException e) {
    // } catch (IllegalAccessException e) {
    // }
    // }
    // }


    // for (Field field : BasicDataSourceFactory.class.getDeclaredFields()) {
    // Object object = ReflectUtil.readField(field, String.class);
    // if (object != null) {
    // System.out.println(object.toString());
    // }
    // // System.out.println(ReflectUtil.readField(field, String.class));
    // }

    // for (Field field : BasicDataSourceFactory.class.getDeclaredFields()) {
    // System.out.println(ReflectUtil.readField(field, String.class));
    // }


    // for (int i = 0; i < fields.length; i++) {
    // if (!Modifier.isStatic(fields[i].getModifiers())) {
    // if (categoryMap.get(fields[i].getName()) != null) {
    // String optionCodeId = categoryMap.get(fields[i].getName());
    // String optionValue = null;
    // try {
    // optionValue = String.valueOf(fields[i].get(userCategoryVO));
    // if (fields[i].getType().equals(boolean.class)) {
    // optionValue = optionValue.equals("true") ? "1" : "0";
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // UserCategory userCategory = new UserCategory(userId, optionCodeId,
    // BooleanUtils.toBoolean(optionValue));
    // userCategories.add(userCategory);
    // }
    // }
    // }

    // for (Field field : org.hibernate.cfg.AvailableSettings.class.getDeclaredFields()) {
    // System.out.println(field.getName());
    // // System.out.println(field.getName());
    // }
    // org.hibernate.cfg.AvailableSettings.class.getDeclaredFields()[0].getName();
  }

  @Getter
  @SuppressWarnings(value = {"PMD.UnusedPrivateField", "PMD.SingularField"})
  private static class Crypto {
    private SecretKeySpec keySpec = null;
    private IvParameterSpec ivSpec = null;
    private Cipher cipher = null;

    private static Crypto crypto;

    private Crypto() {
      super();
      String applechip = SecurityUtil.getSecurityKey();
      byte[] key = StringUtils.getBytesUtf8(applechip);
      byte[] iv = StringUtils.getBytesUtf8(org.apache.commons.lang.StringUtils.reverse(applechip));
      this.keySpec = new SecretKeySpec(key, 0, 16, "AES");
      this.ivSpec = new IvParameterSpec(iv);
      try {
        this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      } catch (GeneralSecurityException e) {
        throw new SystemException(String.format("cryto fail.. %s", e), e);
      }
    }

    public static Crypto getInstance() {
      if (crypto == null) {
        crypto = new Crypto();
      }
      return crypto;
    }
  }
}
