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
    if (org.apache.commons.lang.StringUtils.isBlank(decryptText)) {
      return "";
    }
    try {
      Crypto crypto = Crypto.getInstance();
      Cipher cipher = crypto.getCipher();
      cipher.init(Cipher.ENCRYPT_MODE, crypto.getKeySpec(), crypto.getIvSpec());
      byte[] encrypt = cipher.doFinal(StringUtils.getBytesUtf8(decryptText));
      return StringUtils.newStringUtf8(Base64.encodeBase64(encrypt));
    } catch (GeneralSecurityException e) {
      throw new SystemException(String.format("cryto fail.. %s", e.getMessage()), e);
    }
  }

  public static String decrypt(String encryptText) {
    if (org.apache.commons.lang.StringUtils.isBlank(encryptText)) {
      return "";
    }
    try {
      Crypto crypto = Crypto.getInstance();
      Cipher cipher = crypto.getCipher();
      cipher.init(Cipher.DECRYPT_MODE, crypto.getKeySpec(), crypto.getIvSpec());
      byte[] plain = cipher.doFinal(Base64.decodeBase64(StringUtils.getBytesUtf8(encryptText)));
      return StringUtils.newStringUtf8(plain);
    } catch (GeneralSecurityException e) {
      throw new SystemException(String.format("cryto fail.. %s", e.getMessage()), e);
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
    Crypto a = Crypto.getInstance();
    Crypto b = Crypto.getInstance();

    System.out.println("a.equals(b):" + a.equals(b));
    System.out.println("a==b" + (a == b));

    System.out.println(CryptoUtil.toHexStringByDigest("test", Digest.MD5));
    System.out.println(CryptoUtil.toHexStringByDigest("test", Digest.SHA_256));
    System.out.println(CryptoUtil.encrypt("password"));
    // System.out.println(CryptoUtil.decrypt("password"));
    System.out.println(CryptoUtil.decrypt("iaLQS4uk5SrS0oV+mm7s/g=="));

    System.out.println(CryptoUtil.decrypt("iaLQS4uk5SrS0oV+mm7s/g=="));
    System.out.println(SecurityUtil.getSecurityKey());
    System.out.println(SecurityUtil.getSecurityKey());
    System.out.println(SecurityUtil.getSecurityKey());
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
