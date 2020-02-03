package com.splan.bplan.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.splan.bplan.constants.BussinessContants;
import com.splan.bplan.constants.Constants;
import com.splan.base.enums.ResultStatus;
import com.splan.bplan.utils.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

@Component
public class BussinessOperationInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.err.println(handler.toString());
        JSONObject sysUser = (JSONObject) SecurityUtils.getSubject().getPrincipal();
        if(null==sysUser){
            ResultUtil.sendJsonMessage(response,ResultUtil.returnError(ResultStatus.SC_UNAUTHORIZED));
            return false;
        }

        //获取登陆对象的菜单列表
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject jsonObject=(JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        Set<String> meunCodes=(Set<String>) jsonObject.get("menuList");
        Set<String> mustHaveCodes=new HashSet<>();
        mustHaveCodes.add(BussinessContants.BUSINESSBACK_JURISDICTION);
        mustHaveCodes.add(BussinessContants.BUSINESSBACK_MODPASSWORD);
        if(!meunCodes.containsAll(mustHaveCodes)){
            ResultUtil.sendJsonMessage(response,ResultUtil.returnError(ResultStatus.OPERATIONFAIL));
            return false;
        }
        return true;
    }
}
