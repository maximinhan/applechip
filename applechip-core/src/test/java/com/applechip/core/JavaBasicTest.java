package com.applechip.core;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

import com.applechip.core.configurer.AspectJConfigurer;

public class JavaBasicTest {
  @Test
  public void test() throws IOException {
    // User user = new User();
    // user.setStatus(15);
    // System.out.println(user.isAccountNonExpired());
    // System.out.println(user.isEnabled());
    // System.out.println(user.isCredentialsNonExpired());
    // System.out.println(user.isAccountNonLocked());
    System.out.println(AspectJConfigurer.class.getCanonicalName());
    // org.gradle.internal.nativeplatform.filesystem.FileSystem.DEFAULT_FILE_MODE
    // System.out.println(new
    // File("D:/dev/workspace-applechip/applechip/storage/resource/GeoLite2-City.mmdb").getAbsolutePath()Path());

    ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
    map.putIfAbsent("", "");
    Map<String, String> m = Collections.synchronizedMap(new HashMap<String, String>());
    m.put("", "");
  }
}
