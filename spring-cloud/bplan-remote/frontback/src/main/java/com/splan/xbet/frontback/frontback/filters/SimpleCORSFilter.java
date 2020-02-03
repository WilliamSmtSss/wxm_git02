//package com.splan.xbet.frontback.frontback.filters;
//
//import org.springframework.stereotype.Component;
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class SimpleCORSFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//      try{
//        HttpServletResponse resp = (HttpServletResponse) response;
//        HttpServletRequest req = (HttpServletRequest)request;
////        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
//        resp.setHeader("Access-Control-Allow-Origin", "*");
//        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
//        resp.setHeader("Access-Control-Max-Age", "3600");
//        resp.setHeader("Access-Control-Allow-Headers", "x-requested-with, content-type, authorization");
//        resp.setHeader("Access-Control-Allow-Credentials", "true");
//        chain.doFilter(request, response);}catch(Exception e){e.printStackTrace();}
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
