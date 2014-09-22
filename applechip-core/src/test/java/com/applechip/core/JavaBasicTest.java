package com.applechip.core;

import org.junit.Test;

import com.applechip.core.configurer.CustomAspectJConfigurer;
import com.applechip.core.entity.User;

public class JavaBasicTest {
  @Test
  public void test(){
//    User user = new User();
//    user.setStatus(15);
//    System.out.println(user.isAccountNonExpired());
//    System.out.println(user.isEnabled());
//    System.out.println(user.isCredentialsNonExpired());
//    System.out.println(user.isAccountNonLocked());
	  System.out.println(CustomAspectJConfigurer.class.getCanonicalName());
  }

}
