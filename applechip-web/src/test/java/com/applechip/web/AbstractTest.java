package com.applechip.web;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.FlashAttributeResultMatchers;
import org.springframework.test.web.servlet.result.ModelResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ApplicationContextTest.class})
@TransactionConfiguration(defaultRollback = true)
@Slf4j
public abstract class AbstractTest {
  @Autowired
  private WebApplicationContext webApplicationContext;

  @Rule
  public TestName testName = new TestName();

  protected MockMvc mockMvc;

  protected MockRestServiceServer mockServer;

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    // this.mockServer = MockRestServiceServer.createServer(restTemplate);
  }

  @Rule
  public TestRule watchman = new TestWatcher() {
    @Override
    protected void starting(Description description) {
      String methodName = StringUtils.defaultIfBlank(description.getMethodName(), "before... ");
      String className = StringUtils.substringAfterLast(description.getClassName(), ".");
      log.debug("starting... test className: {}, methodName: {}", className, methodName);
    }
  };

  public ResultHandler print() {
    return org.springframework.test.web.servlet.result.MockMvcResultHandlers.print();
  }

  public StatusResultMatchers status() {
    return org.springframework.test.web.servlet.result.MockMvcResultMatchers.status();
  }

  public ContentResultMatchers content() {
    return org.springframework.test.web.servlet.result.MockMvcResultMatchers.content();
  }

  public ModelResultMatchers model() {
    return org.springframework.test.web.servlet.result.MockMvcResultMatchers.model();
  }

  public FlashAttributeResultMatchers flash() {
    return org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash();
  }

  public MockHttpServletRequestBuilder get(String urlTemplate, Object... urlVariables) {
    return org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(urlTemplate, urlVariables);
  }

  public ResultMatcher forwardedUrl(String expectedUrl) {
    return org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl(expectedUrl);
  }

  public ResultMatcher redirectedUrl(String expectedUrl) {
    return org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl(expectedUrl);
  }

  public <T> Matcher<T> anything(String description) {
    return org.hamcrest.CoreMatchers.anything(description);
  }

  /*
   * @RunWith(SpringJUnit4ClassRunner.class)
   * 
   * @ContextConfiguration(locations = {"/spring.xml"}) public class RegisterBean {
   * 
   * @Autowired private ApplicationContext context;
   * 
   * 
   * @Test public void registBean() {
   * 
   * // get BeanFactory from ApplicationContext DefaultListableBeanFactory factory =
   * (DefaultListableBeanFactory) ((ConfigurableApplicationContext) context).getBeanFactory();
   * 
   * // 멤버 필드 값 셋팅 MutablePropertyValues propertyValues = new MutablePropertyValues();
   * propertyValues.addPropertyValue("prop1", "prop1"); propertyValues.addPropertyValue("prop2",
   * "prop2");
   * 
   * // bean 생성 GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
   * beanDefinition.setBeanClass(Test1.class);
   * beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
   * beanDefinition.setPropertyValues(propertyValues);
   * 
   * // factory에 bean 등록 factory.registerBeanDefinition("test1", beanDefinition);
   * 
   * Test1 test = context.getBean(Test1.class); assertThat("method1", is(test.method1()));
   * assertThat("method2", is(test.method2()));
   * 
   * String namkyu = context.getBean("namkyu", String.class); assertThat("test_namkyu", is(namkyu));
   * } }
   * 
   * @Data class Test1 {
   * 
   * private String prop1; private String prop2;
   * 
   * public String method1() { return "method1"; }
   * 
   * public String method2() { return "method2"; } }
   */
  public MockHttpServletRequest newPost(String url) {
    return new MockHttpServletRequest(HttpMethod.POST.name(), url);
  }

  public MockHttpServletRequest newGet(String url) {
    return new MockHttpServletRequest(HttpMethod.GET.name(), url);
  }

  public MockHttpServletResponse newResponse() {
    return new MockHttpServletResponse();
  }

  public MockMultipartHttpServletRequest newMultipartRequest() {
    return new MockMultipartHttpServletRequest();
  }
}
