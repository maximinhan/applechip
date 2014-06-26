package com.applechip.core.util;

public class BitwisePermissions {

  // Check Any Permission
  public static boolean isAnyPermitted(long myPermissions, long... permissionToChecks) {
    for (long permissionToCheck : permissionToChecks) {
      if (isPermitted(myPermissions, permissionToCheck))
        return true;
    }
    return false;
  }

  // Check All Permission
  public static boolean isAllPermitted(long myPermissions, long... permissionToChecks) {
    for (long permissionToCheck : permissionToChecks) {
      if (!isPermitted(myPermissions, permissionToCheck))
        return false;
    }
    return true;
  }

  // Check Permission(s)
  public static boolean isPermitted(long myPermissions, long permissionToCheck) {
    return ((myPermissions & permissionToCheck) == permissionToCheck);
  }

  // Add Permission
  public static long addPermission(long myPermissions, long permissionToAdd) {
    return addPermissions(myPermissions, new long[] {permissionToAdd});

  }

  public static long addPermissions(long myPermissions, long... permissionsToAdd) {
    for (long myPermission : permissionsToAdd) {
      myPermissions |= myPermission;
    }
    return myPermissions;
  }

  // Delete Permission(s)
  public static long deletePermission(long myPermissions, long permissionToDelete) {
    return deletePermissions(myPermissions, new long[] {permissionToDelete});
  }

  public static long deletePermissions(long myPermissions, long... permissionsToDelete) {
    for (long myPermission : permissionsToDelete) {
      myPermissions &= ~myPermission;
    }
    return myPermissions;
  }
}
