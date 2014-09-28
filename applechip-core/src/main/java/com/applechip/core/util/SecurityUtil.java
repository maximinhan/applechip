package com.applechip.core.util;

import java.util.UUID;

import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.applechip.core.entity.User;

public class SecurityUtil {

	public static String getCurrentUserId() {
		String id = null;
		User user = getCurrentUser();
		if (user != null) {
			id = user.getId();
		}
		return id;
	}

	public static String getCurrentRemoteAddr() {
		String remoteAddr = WebUtil.getCurrentRequest().getRemoteAddr();
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
		}
		else if (authentication.getDetails() instanceof UserDetails) {
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
}
