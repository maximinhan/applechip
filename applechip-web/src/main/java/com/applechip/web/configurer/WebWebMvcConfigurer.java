package com.applechip.web.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.applechip.web.WebConfig;
import com.applechip.web.interceptor.CorsInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebMvc
// @EnableWebSocket
// @EnableWebSocketMessageBroker
@ComponentScan(basePackageClasses = WebConfig.class, includeFilters = @Filter(Controller.class), useDefaultFilters = false)
public class WebWebMvcConfigurer extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("index");
  }
  
//  @Bean
//  public SimpleMappingExceptionResolver exceptionResolver() {
//      SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
//
//      Properties exceptionMappings = new Properties();
//
//      exceptionMappings.put("java.lang.Exception", "error/error");
//      exceptionMappings.put("java.lang.RuntimeException", "error/error");
//
//      exceptionResolver.setExceptionMappings(exceptionMappings);
//
//      Properties statusCodes = new Properties();
//
//      statusCodes.put("error/404", "404");
//      statusCodes.put("error/error", "500");
//
//      exceptionResolver.setStatusCodes(statusCodes);
//
//      return exceptionResolver;
//  }


  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    localeChangeInterceptor.setParamName("language");
    registry.addInterceptor(localeChangeInterceptor);
    registry.addInterceptor(new CorsInterceptor());
  }

  @Bean
  public LocaleResolver localeResolver() {
    CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
    cookieLocaleResolver.setDefaultLocale(StringUtils.parseLocaleString("en"));
    return cookieLocaleResolver;
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.setOrder(-10).addResourceHandler("version.txt").addResourceLocations("version.txt", "classpath:/META-INF/version.txt");
    registry.setOrder(-10).addResourceHandler("favicon.ico").addResourceLocations("favicon.ico");
    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(31556926);
    // registry.addResourceHandler("/resources/**").addResourceLocations("/resources/", "/",
    // "classpath:/META-INF/web-resources/");
    // registry.addResourceHandler("/favicon.ico").addResourceLocations("/");
    // registry.addResourceHandler("/robots.txt").addResourceLocations("/");
  }

  /*
   * favicon
   * 
   * @Controller static class FaviconController {
   * 
   * @RequestMapping("favicon.ico") String favicon() { return
   * "forward:/resources/images/favicon.ico"; } }
   * 
   * @Controller class FaviconController {
   * 
   * @RequestMapping("favicon.ico")
   * 
   * @ResponseBody void favicon() {} }
   */

  private void test() {
    MappingJackson2JsonView mappingJackson2JsonView = new MappingJackson2JsonView();
    mappingJackson2JsonView.setModelKey("");
    mappingJackson2JsonView.setExtractValueFromSingleKeyModel(Boolean.TRUE);
    mappingJackson2JsonView.setObjectMapper(new ObjectMapper());
  }

  /*
   * tiles
   * 
   * https://tiles.apache.org/framework/tutorial/advanced/preparer.html
   * 
   * <definition name="defaultLayout" template="/WEB-INF/view/common/layout/default/layout.jsp">
   * <put-attribute name="header" value="/WEB-INF/view/common/layout/default/header.jsp" />
   * <put-attribute name="left" value="/WEB-INF/view/common/layout/default/left.jsp" />
   * <put-attribute name="body" value="/WEB-INF/view/common/layout/default/blank.jsp" />
   * <put-attribute name="footer" value="/WEB-INF/view/common/layout/default/footer.jsp" />
   * </definition>
   * 
   * 
   * <definition name="main" extends="defaultLayout"> <put-attribute name="body"
   * value="/WEB-INF/view/main/main.jsp" /> <put-attribute name="left"
   * value="/WEB-INF/view/main/leftMain.jsp" /> </definition>
   * 
   * 
   * attributeContext.putAttribute("left", new Attribute("/WEB-INF/view/" + layoutId + "/test.jsp"))
   * 
   * tiles XML setting definition element에 preparer="ViewPreparer impl class"
   * 
   * <definition name="preparer.definition"
   * preparer="org.apache.tiles.test.preparer.TestViewPreparer"> <put-attribute name="foo"
   * value="/bar/foo.jsp" /> </definition>
   * 
   * 
   * public class TestViewPreparer implements ViewPreparer {
   * 
   * public void execute(Request tilesRequest, AttributeContext attributeContext) throws
   * PreparerException { attributeContext.putAttribute( "body", new
   * Attribute("This is the value added by the ViewPreparer")); } }
   */

  @Bean
  public InternalResourceViewResolver jspViewResolver() {
    InternalResourceViewResolver bean = new InternalResourceViewResolver();
    bean.setPrefix("/WEB-INF/views");
    bean.setSuffix(".jsp");
    bean.setViewClass(JstlView.class);
    bean.setOrder(0);
    return bean;
  }

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
    webSocketHandlerRegistry.addHandler(webSocketHandler(), "/echo");
    webSocketHandlerRegistry.addHandler(webSocketHandler(), "/socketjs/echo").withSockJS();
  }

  @Bean
  public WebSocketHandler webSocketHandler() {
    return new CustomTextWebSocketHandlerAdapter();
  }

  // @Override
  // public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
  // configurer.enable();
  // }
}
