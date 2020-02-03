package com.splan.bplan.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.splan.bplan.annotation.CurrentSysUser;
import com.splan.bplan.annotation.CurrentUser;
import com.splan.base.bean.SysUser;
import com.splan.base.bean.UserBean;
import com.splan.bplan.constants.Constants;
import com.splan.bplan.mappers.SysUserMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@Component
public class CurrentSysUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    SysUserMapper sysUserMapper;
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if (methodParameter.getParameterType ().isAssignableFrom (SysUser.class) &&
                methodParameter.hasParameterAnnotation (CurrentSysUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        JSONObject sysUser = (JSONObject) SecurityUtils.getSubject().getPrincipal();
        Integer userId=sysUser.getInteger("userId");
        if(userId!=0){
            return sysUserMapper.selectById(userId);
        }
        throw new MissingServletRequestPartException(Constants.CURRENT_USER_ID);
    }
}
