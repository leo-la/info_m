package com.tfswx.config;

import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {



    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        //获取请求参数
        String language = httpServletRequest.getParameter("l");
        Locale locale = Locale.getDefault();//获取默认解析器，没有传入参数就是用默认解析器
        if(!StringUtils.isEmpty(language)){
            String[] split = language.split("_");
            locale = new Locale(split[0], split[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
