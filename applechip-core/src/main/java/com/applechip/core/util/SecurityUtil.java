package com.applechip.core.util;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.applechip.core.entity.User;

public class SecurityUtil {

  public static String getCurrentUserId() {
    User user = getCurrentUser();
    if (user == null) {
      return "";
    } else {
      return user.getId();
    }
  }

  public static User getCurrentUser() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
    Authentication authentication = securityContext.getAuthentication();
    if (authentication == null || authenticationTrustResolver.isAnonymous(authentication)) {
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
    UUID uuid = UUID.nameUUIDFromBytes(org.apache.commons.codec.binary.StringUtils.getBytesUtf8("applechip"));
    return StringUtils.rightPad(Long.toString(uuid.getMostSignificantBits(), Character.MAX_RADIX), 16, '-');
  }
}
