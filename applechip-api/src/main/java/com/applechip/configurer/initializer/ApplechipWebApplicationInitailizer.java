package com.applechip.configurer.initializer;

import com.applechip.configurer.AppleChipApiApplicationContextConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

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
        return new Class[] {AppleChipApiApplicationContextConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
