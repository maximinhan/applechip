package com.applechip.core.util;

public class BitwisePermissions {

  // Check Any Permission
  public static boolean isAnyPermitted(long originPermission, long... comparePermission) {
    for (long permission : comparePermission) {
      if (isPermitted(originPermission, permission)) {
        return true;
      }
    }
    return false;
  }

  // Check All Permission
  public static boolean isAllPermitted(long originPermission, long... comparePermission) {
    for (long permission : comparePermission) {
      if (!isPermitted(originPermission, permission)) {
        return false;
      }
    }
    return true;
  }

  // Check Permission(s)
  public static boolean isPermitted(long originPermission, long permission) {
    return ((originPermission & permission) == permission);
  }

  public static long addPermissions(long originPermission, long... permissionsToAdd) {
    for (long myPermission : permissionsToAdd) {
      originPermission |= myPermission;
    }
    return originPermission;
  }

  public static long deletePermissions(long originPermission, long... permissionsToDelete) {
    for (long myPermission : permissionsToDelete) {
      originPermission &= ~myPermission;
    }
    return originPermission;
  }
}
