package com.applechip.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;

import org.junit.Test;

import com.applechip.core.configurer.AspectJConfigurer;

public class JavaBasicTest {
  @Test
  public void test() throws IOException{
//    User user = new User();
//    user.setStatus(15);
//    System.out.println(user.isAccountNonExpired());
//    System.out.println(user.isEnabled());
//    System.out.println(user.isCredentialsNonExpired());
//    System.out.println(user.isAccountNonLocked());
	  System.out.println(AspectJConfigurer.class.getCanonicalName());
//	  org.gradle.internal.nativeplatform.filesystem.FileSystem.DEFAULT_FILE_MODE
//	  System.out.println(new File("D:/dev/workspace-applechip/applechip/storage/resource/GeoLite2-City.mmdb").getAbsolutePath()Path());
  }

}
