package com.applechip.web;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration(classes = { CoreConfig.class, WebConfig.class })
public class ApplicationTest {
	private static final String STANDARD = "Yummy Noodles";

	private static final String CHEF_SPECIAL = "Special Yummy Noodles";

	private static final String LOW_CAL = "Low cal Yummy Noodles";

	private MockMvc mockMvc;

	// @Autowired
	// WebApplicationContext webApplicationContext;
	//
	// @Before
	// public void setup() {
	// mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	// }

	@Test
	public void thatTextReturned() throws Exception {
		// mockMvc.perform(get("/")).andDo(print()).andExpect(content().string(containsString(STANDARD)))
		// .andExpect(content().string(containsString(CHEF_SPECIAL)))
		// .andExpect(content().string(containsString(LOW_CAL)));

	}

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
 * // get BeanFactory from ApplicationContext DefaultListableBeanFactory factory = (DefaultListableBeanFactory)
 * ((ConfigurableApplicationContext) context).getBeanFactory();
 * 
 * // 멤버 필드 값 셋팅 MutablePropertyValues propertyValues = new MutablePropertyValues();
 * propertyValues.addPropertyValue("prop1", "prop1"); propertyValues.addPropertyValue("prop2", "prop2");
 * 
 * // bean 생성 GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
 * beanDefinition.setBeanClass(Test1.class); beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
 * beanDefinition.setPropertyValues(propertyValues);
 * 
 * // factory에 bean 등록 factory.registerBeanDefinition("test1", beanDefinition);
 * 
 * Test1 test = context.getBean(Test1.class); assertThat("method1", is(test.method1())); assertThat("method2",
 * is(test.method2()));
 * 
 * String namkyu = context.getBean("namkyu", String.class); assertThat("test_namkyu", is(namkyu)); } }
 * 
 * @Data class Test1 {
 * 
 * private String prop1; private String prop2;
 * 
 * public String method1() { return "method1"; }
 * 
 * public String method2() { return "method2"; } }
 */
