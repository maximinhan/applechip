package com.applechip.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.applechip.web.configurer.WebWebMvcConfigurer;

@Configuration
@Import(value = {WebConfig.class, WebWebMvcConfigurer.class})
public class ApplicationContextTest {
}
