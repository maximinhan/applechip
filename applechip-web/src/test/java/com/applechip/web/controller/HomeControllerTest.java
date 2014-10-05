package com.applechip.web.controller;

import org.junit.Test;

import com.applechip.web.AbstractTest;


public class HomeControllerTest extends AbstractTest {

  @Test
  // @Ignore
  public void getHome() throws Exception {
    this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk()).andExpect(forwardedUrl("/WEB-INF/views/index.jsp"));
  }

  @Test
  // @Ignore
  public void getFile() throws Exception {
    this.mockMvc.perform(get("/exe.dile")).andDo(print()).andExpect(status().isOk()).andExpect(forwardedUrl("/WEB-INF/views/index.jsp"));
  }
}
