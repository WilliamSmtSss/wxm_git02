package com.splan.xbet.admin.back.Interceptors;

import com.alibaba.fastjson.JSONObject;
import com.splan.base.enums.ResultStatus;
import com.splan.xbet.admin.back.utils.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BussinessUnloginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.err.println(handler.toString());
        JSONObject sysUser = (JSONObject) SecurityUtils.getSubject().getPrincipal();
        if(null==sysUser){
            ResultUtil.sendJsonMessage(response,ResultUtil.returnError(ResultStatus.LOGIN_EXPIRED));
            return false;
        }
        return true;
    }


}
