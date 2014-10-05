package com.applechip.core.util;

import java.security.GeneralSecurityException;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import lombok.Getter;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.applechip.core.entity.member.User;
import com.applechip.core.exception.SystemException;

public class SecurityUtil {
  private SecurityUtil() {}

  public static String getCurrentUserId() {
    String id = null;
    User user = getCurrentUser();
    if (user != null) {
      id = user.getId();
    }
    return id;
  }

  public static String getCurrentRemoteAddr() {
    HttpServletRequest request = WebUtil.getCurrentRequest();
    String remoteAddr = request.getHeader("X-Forwarded-For");
    if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
      remoteAddr = request.getHeader("Proxy-Client-IP");
    }
    if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
      remoteAddr = request.getHeader("WL-Proxy-Client-IP");
    }
    if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
      remoteAddr = request.getHeader("HTTP_CLIENT_IP");
    }
    if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
      remoteAddr = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (remoteAddr == null || remoteAddr.length() == 0 || "unknown".equalsIgnoreCase(remoteAddr)) {
      remoteAddr = request.getRemoteAddr();
    }
    return StringUtil.defaultString(remoteAddr);
  }

  public static User getCurrentUser() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();
    if (authentication == null || new AuthenticationTrustResolverImpl().isAnonymous(authentication)) {
      return null;
    }
    return getCurrentUser(authentication);
  }

  private static User getCurrentUser(Authentication authentication) {
    User user = null;
    if (authentication.getPrincipal() instanceof UserDetails) {
      user = (User) authentication.getPrincipal();
    } else if (authentication.getDetails() instanceof UserDetails) {
      user = (User) authentication.getDetails();
    }
    return user;
  }

  public static String getSecurityKey() {
    UUID uuid = UUID.nameUUIDFromBytes(StringUtil.getBytesUtf8("applechip"));
    return StringUtil.rightPad(Long.toString(uuid.getMostSignificantBits(), Character.MAX_RADIX), 16, '-');
  }

  public static int identityHashCode(Class<?> clazz) {
    return System.identityHashCode(clazz);
  }

  public enum Digest {
    SHA_256("SHA-256"), SHA_512("SHA-512"), MD5("MD5");
    private String name;

    private Digest(String name) {
      this.name = name;
    }

    public boolean equals(String name) {
      return (name == null) ? false : this.name.equals(name);
    }

    public String toString() {
      return this.name;
    }
  }

  public static String encodeHexStringByDigest(String string, Digest digest) {
    String result = "";
    try {
      switch (digest) {
        case SHA_256:
          result = DigestUtils.sha256Hex(string);
        case SHA_512:
          result = DigestUtils.sha512Hex(string);
        case MD5:
          result = DigestUtils.md5Hex(string);
      }
    } catch (Exception e) {
      throw new SystemException(String.format("encodeHexStringByDigest fail.. %s", e.getMessage()), e);
    }
    return result;
  }

  public static String encrypt(String decryptText) {
    Crypto crypto = Crypto.getInstance();
    Cipher cipher = crypto.getCipher();
    String decrypt = null;
    try {
      cipher.init(Cipher.ENCRYPT_MODE, crypto.getKeySpec(), crypto.getIvSpec());
      decrypt = StringUtil.newStringUtf8(Base64.encodeBase64(cipher.doFinal(StringUtil.getBytesUtf8(decryptText))));
    } catch (GeneralSecurityException e) {
      throw new SystemException(String.format("encrypt fail.. %s", e.getMessage()), e);
    } catch (IllegalArgumentException e) {
      throw new SystemException(String.format("encrypt fail.. %s", e.getMessage()), e);
    } catch (NullPointerException e) {
      throw new SystemException(String.format("encrypt fail.. %s", e.getMessage()), e);
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
      decrypt = StringUtil.newStringUtf8(cipher.doFinal(Base64.decodeBase64(StringUtil.getBytesUtf8(encryptText))));
    } catch (GeneralSecurityException e) {
      throw new SystemException(String.format("decrypt fail.. %s", e.getMessage()), e);
    } catch (IllegalArgumentException e) {
      throw new SystemException(String.format("decrypt fail.. %s", e.getMessage()), e);
    } catch (NullPointerException e) {
      throw new SystemException(String.format("decrypt fail.. %s", e.getMessage()), e);
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
      byte[] key = StringUtil.getBytesUtf8(applechip);
      byte[] iv = StringUtil.getBytesUtf8(StringUtil.reverse(applechip));
      this.keySpec = new SecretKeySpec(key, 0, 16, "AES");
      this.ivSpec = new IvParameterSpec(iv);
      try {
        this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      } catch (GeneralSecurityException e) {
        throw new SystemException(String.format("Crypto fail.. %s", e.getMessage()), e);
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
