package com.applechip.core;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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

  @Test
  public void test1() {
    // List<String> myList = new ArrayList<String>();
    List<String> myList = new CopyOnWriteArrayList<String>();

    myList.add("1");
    myList.add("2");
    myList.add("3");
    myList.add("4");
    myList.add("5");

    Iterator<String> it = myList.iterator();
    while (it.hasNext()) {
      String value = it.next();
      System.out.println("List Value:" + value);
      if (value.equals("3"))
        myList.remove(value);
    }

    // Map<String,String> myMap = new ConcurrentHashMap<String,String>();
    Map<String, String> myMap = new HashMap<String, String>();
    myMap.put("1", "1");
    myMap.put("2", "2");
    myMap.put("3", "3");

    Iterator<String> it1 = myMap.keySet().iterator();
    while (it1.hasNext()) {
      String key = it1.next();
      System.out.println("Map Value:" + myMap.get(key));
      if (key.equals("2")) {
        myMap.put("1", "4");
        myMap.put("4", "4");
      }
    }
  }
}
