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
    String result = "";
    SecurityContext securityContext = SecurityContextHolder.getContext();
    if (securityContext.getAuthentication() != null) {
      Authentication authentication = securityContext.getAuthentication();
      AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
      if (!authenticationTrustResolver.isAnonymous(authentication)) {
        if (authentication.getPrincipal() instanceof UserDetails) {
          result = ((User) authentication.getPrincipal()).getId();
        } else if (authentication.getDetails() instanceof UserDetails) {
          result = ((User) authentication.getDetails()).getId();
        }
      }
    }
    return result;
  }

  public static String getSecurityKey() {
    UUID uuid = UUID.nameUUIDFromBytes(org.apache.commons.codec.binary.StringUtils.getBytesUtf8("applechip"));
    return StringUtils.rightPad(Long.toString(uuid.getMostSignificantBits(), Character.MAX_RADIX), 16, '-');
  }
}
