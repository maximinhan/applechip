package com.applechip.core.util;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.applechip.core.entity.User;

public class SecurityUtil {

  public static String getCurrentUserId() {
    User user = null;
    try {
      user = getCurrentUser();
    } catch (AccessDeniedException e) {

    }
    if (user == null) {
      return "";
    } else {
      return user.getId();
    }
  }

  public static User getCurrentUser() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    User user = null;
    if (securityContext.getAuthentication() != null) {
      Authentication authentication = securityContext.getAuthentication();
      AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
      if (!authenticationTrustResolver.isAnonymous(authentication)) {
        if (authentication.getPrincipal() instanceof UserDetails) {
          user = (User) authentication.getPrincipal();
        } else if (authentication.getDetails() instanceof UserDetails) {
          user = (User) authentication.getDetails();
        } else {
          throw new AccessDeniedException("User not properly authenticated.");
        }
      }
    }
    return user;
  }

  public static String getSecurityKey() {
    UUID uuid = UUID.nameUUIDFromBytes(org.apache.commons.codec.binary.StringUtils.getBytesUtf8("applechip"));
    return StringUtils.rightPad(Long.toString(uuid.getMostSignificantBits(), Character.MAX_RADIX), 16, '-');
  }
}
