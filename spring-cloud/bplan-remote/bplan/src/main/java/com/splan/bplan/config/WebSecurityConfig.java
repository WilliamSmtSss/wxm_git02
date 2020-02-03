package com.splan.bplan.config;

import com.splan.bplan.interceptor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
//public class WebSecurityConfig extends WebMvcConfigurerAdapter {
public class WebSecurityConfig implements WebMvcConfigurer {

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Autowired
    private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

    @Autowired
    private CurrentSysUserMethodArgumentResolver currentSysUserMethodArgumentResolver;

    @Autowired
    private BussinessOperationInterceptor bussinessOperationInterceptor;

    @Autowired
    private BussinessUnloginInterceptor bussinessUnloginInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor);
        //商户后台权限管理/修改密码请求拦截
        registry.addInterceptor(bussinessOperationInterceptor).addPathPatterns("/businessBack/operation/**");
        //商户后台未登陆拦截
        List<String> stringList=new ArrayList<>();
        stringList.add("/businessBack/**");
        stringList.add("/back/login/getInfo");
        registry.addInterceptor(bussinessUnloginInterceptor).addPathPatterns(stringList);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver);
        argumentResolvers.add(currentSysUserMethodArgumentResolver);
    }


}
