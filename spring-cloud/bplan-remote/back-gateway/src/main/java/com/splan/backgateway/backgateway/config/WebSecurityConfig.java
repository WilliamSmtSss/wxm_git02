//package com.splan.backgateway.backgateway.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
////    @Bean
////    PasswordEncoder passwordEncoder() {
////        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
////    }
////
////    /**
////     * 注入AuthenticationManager接口，启用OAuth2密码模式
////     *
////     * @return
////     * @throws Exception
////     */
////    @Bean
////    @Override
////    public AuthenticationManager authenticationManagerBean() throws Exception {
////        AuthenticationManager manager = super.authenticationManagerBean();
////        return manager;
////    }
//
//    /**
//     * 通过HttpSecurity实现Security的自定义过滤配置
//     *
//     * @param httpSecurity
//     * @throws Exception
//     */
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//
//        httpSecurity
//                .requestMatchers().anyRequest()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/**").permitAll();
//    }
//
//}
