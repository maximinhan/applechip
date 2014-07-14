package com.applechip.configurer.initializer;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.applechip.configurer.ApplechipApiApplicationContextConfig;

/**
 * Created with IntelliJ IDEA.
 * User: Yawn
 * Date: 2014. 7. 8.
 * Time: 오후 7:15
 */
public class ApplechipWebApplicationInitailizer extends AbstractAnnotationConfigDispatcherServletInitializer {

//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
//        webApplicationContext.register(AppleChipApiApplicationContextConfig.class);
//        servletContext.addListener(new ContextLoaderListener(webApplicationContext));
//
//        this.addEcodingFilter(servletContext);
//        this.addHttpMethodFilter(servletContext);
//    }

//    private void addEcodingFilter(ServletContext servletContext) {
//        FilterRegistration encodingFilter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
//        encodingFilter.setInitParameter("encoding", "UTF-8");
//        encodingFilter.setInitParameter("forceEncoding", "true");
//        encodingFilter.addMappingForUrlPatterns(null, false, "/*");
//    }
//
//    private void addHttpMethodFilter(ServletContext servletContext) {
//        FilterRegistration httpMethodFilter = servletContext.addFilter("httpMethodFilter", HiddenHttpMethodFilter.class);
//        httpMethodFilter.addMappingForUrlPatterns(null, false, "/*");
//    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return new Filter[] {characterEncodingFilter};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        // return new Class[] { CustomWebSecurityConfigurer.class };
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {ApplechipApiApplicationContextConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
