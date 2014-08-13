package com.applechip.core.util;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import com.applechip.core.exception.SystemException;

@Component
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

  private SecretKeySpec keySpec = null;
  private IvParameterSpec ivSpec = null;
  private Cipher cipher = null;

  public CryptoUtil() {
    generateKey(SecurityUtil.getSecurityKey());
  }

  public void generateKey(String string) {
    byte[] key = StringUtils.getBytesUtf8(string);
    byte[] iv = StringUtils.getBytesUtf8(org.apache.commons.lang.StringUtils.reverse(string));
    this.keySpec = new SecretKeySpec(key, 0, 16, "AES");
    this.ivSpec = new IvParameterSpec(iv);
    try {
      this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    } catch (Exception e) {
      throw new SystemException(String.format("cryto fail.. %s", e));
    }
  }


  public String toHexStringByDigest(String string, Digest digest) {
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

  public String encrypt(String decryptText) {
    try {
      this.cipher.init(Cipher.ENCRYPT_MODE, this.keySpec, this.ivSpec);
      byte[] encrypt = cipher.doFinal(StringUtils.getBytesUtf8(decryptText));
      return StringUtils.newStringUtf8(Base64.encodeBase64(encrypt));
    } catch (Exception e) {
      throw new SystemException(String.format("cryto fail.. %s", e));
    }
  }

  public String decrypt(String encryptText) {
    try {
      this.cipher.init(Cipher.DECRYPT_MODE, this.keySpec, this.ivSpec);
      byte[] plain = cipher.doFinal(Base64.decodeBase64(StringUtils.getBytesUtf8(encryptText)));
      return StringUtils.newStringUtf8(plain);
    } catch (Exception e) {
      throw new SystemException(String.format("cryto fail.. %s", e));
    }
  }

  public String encrypt(String decryptText, Digest digest) {
    try {
      MessageDigest md = MessageDigest.getInstance(digest.toString());
      md.update(StringUtils.getBytesUtf8(decryptText));
      return toHexString(md.digest());
    } catch (Exception e) {
      throw new SystemException(String.format("cryto fail.. %s", e));
    }
  }

  private String toHexString(byte[] bytes) {
    StringBuffer result = new StringBuffer();
    for (byte b : bytes) {
      result.append(Integer.toString((b & 0xF0) >> 4, 16));
      result.append(Integer.toString(b & 0x0F, 16));
    }
    return result.toString();
  }

  public static void main(String[] args) {

    CryptoUtil a = new CryptoUtil();
    CryptoUtil b = new CryptoUtil();

    System.out.println("a.equals(b):" + a.equals(b));
    System.out.println("a==b" + (a == b));

    CryptoUtil cryptUtil = new CryptoUtil();
    System.out.println(cryptUtil.toHexStringByDigest("test", Digest.MD5));
    System.out.println(cryptUtil.toHexStringByDigest("test", Digest.SHA_256));
    System.out.println(cryptUtil.encrypt("password"));
    System.out.println(cryptUtil.decrypt("iaLQS4uk5SrS0oV+mm7s/g=="));
  }
}
