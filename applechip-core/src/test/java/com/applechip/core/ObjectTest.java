package com.applechip.core;

import org.junit.Test;

import com.applechip.core.entity.member.Role;
import com.applechip.core.entity.member.User;
import com.applechip.core.vo.ResponseVO;

public class ObjectTest {
  @Test
  public void test() {
    User dto1 = new User();
    dto1.setId("123");
    User dto2 = new User();
    dto2.setId("1234");
    System.out.println("dto1.equals(dto2)" + dto1.equals(dto2)); // False (if equals is not overridden)
    System.out.println(dto1 == dto2); // False
    
    Role dto3 = new Role();
    dto3.setId("123");
    Role dto4 = new Role();
    dto4.setId("1234");
    System.out.println("dto3.equals(dto4)" + dto3.equals(dto4)); // False (if equals is not overridden)
    System.out.println(dto3 == dto4); // False

    ResponseVO vo1 = ResponseVO.getResponseVO();
    vo1.setMessage("123");
    ResponseVO vo2 = ResponseVO.getResponseVO();
    vo2.setMessage("1234");
    System.out.println("vo1.equals(vo2)" + vo1.equals(vo2)); // True
    System.out.println(vo1 == vo2); // True
    
    VO vo3 = VO.getInstance(10);
    VO vo4 = VO.getInstance(10);
    System.out.println("vo3.equals(vo4)" + vo3.equals(vo4)); // True
    System.out.println(vo3 == vo4); // True
  }
}
