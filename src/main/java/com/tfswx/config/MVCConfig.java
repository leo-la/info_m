package com.tfswx.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * mvc配置
 */
@Configuration
public class MVCConfig implements WebMvcConfigurer {

    @Value("${web.upload-word-path}")
    private String staticWordFilePath;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/toLogin").setViewName("login");
//        registry.addViewController("/route/toIndex").setViewName("login");
//        registry.addViewController("/route/permission/users").setViewName("login");
//        registry.addViewController("/route/permission/managers").setViewName("login");
//        registry.addViewController("/route/permission/roles").setViewName("login");

    }

    /**
     * 配置国际化转换器
     * @return
     */
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }

    /**
     * word文档上传地址
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploadWord/**").addResourceLocations("file:"+staticWordFilePath);
    }


}
