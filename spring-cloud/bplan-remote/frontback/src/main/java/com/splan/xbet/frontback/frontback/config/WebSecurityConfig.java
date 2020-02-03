package com.splan.xbet.frontback.frontback.config;

import com.splan.xbet.frontback.frontback.Interceptors.BussinessUnloginInterceptor;
import com.splan.xbet.frontback.frontback.Interceptors.CurrentSysUserMethodArgumentResolver;
import com.splan.xbet.frontback.frontback.Interceptors.DelayInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
//public class WebSecurityConfig extends WebMvcConfigurerAdapter {
public class WebSecurityConfig implements WebMvcConfigurer {

    @Autowired
    private BussinessUnloginInterceptor bussinessUnloginInterceptor;

    @Autowired
    private CurrentSysUserMethodArgumentResolver currentSysUserMethodArgumentResolver;

    @Autowired
    private DelayInterceptor delayInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //未登陆拦截
        List<String> stringList=new ArrayList<>();
        stringList.add("/frontBack/ServiceManager/**");
        stringList.add("/frontBack/ServiceOrder/**");
        stringList.add("/frontBack/ServiceTrial/**");
        stringList.add("/frontBack/login/getInfo");
        registry.addInterceptor(bussinessUnloginInterceptor).addPathPatterns(stringList);


    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        argumentResolvers.add(currentUserMethodArgumentResolver);
        argumentResolvers.add(currentSysUserMethodArgumentResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/back/excelTemp/**").addResourceLocations("file:"+Myconfig.getProfile());
    }

}
