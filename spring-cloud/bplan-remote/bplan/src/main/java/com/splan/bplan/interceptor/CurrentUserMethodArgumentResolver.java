package com.splan.bplan.interceptor;

import com.splan.bplan.annotation.CurrentUser;
import com.splan.base.bean.UserBean;
import com.splan.bplan.constants.Constants;
import com.splan.bplan.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean supportsParameter (MethodParameter parameter) {
        // 如果参数类型是 User 并且有 CurrentUser 注解则支持
        if (parameter.getParameterType ().isAssignableFrom (UserBean.class) &&
                parameter.hasParameterAnnotation (CurrentUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument (MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 取出鉴权时存入的登录用户 Id
        Long currentUserId = (Long) webRequest.getAttribute (Constants.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);
        if (currentUserId != null) {
            // 从数据库中查询并返回
            return userMapper.selectById (currentUserId);
        }
        throw new MissingServletRequestPartException(Constants.CURRENT_USER_ID);
    }
}
