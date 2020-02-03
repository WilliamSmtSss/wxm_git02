package com.splan.bplan.interceptor;

import com.splan.bplan.annotation.Authorization;
import com.splan.bplan.constants.Constants;
import com.splan.base.enums.ResultStatus;
import com.splan.bplan.service.TokenManager;
import com.splan.bplan.token.TokenModel;
import com.splan.bplan.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenManager manager;

    public boolean preHandle (HttpServletRequest request,
                              HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod ();
        // 从 header 中得到 token
        String authorization = request.getHeader (Constants.AUTHORIZATION);
        // 验证 token
        TokenModel model = manager.getToken (authorization);
        if (manager.checkToken (model)) {
            // 如果 token 验证成功，将 token 对应的用户 id 存在 request 中，便于之后注入
            request.setAttribute (Constants.CURRENT_USER_ID, model.getUserId ());
            return true;
        }
        // 如果验证 token 失败，并且方法注明了 Authorization，返回 401 错误
        if (method.getAnnotation (Authorization.class) != null) {
            //response.setStatus (HttpServletResponse.SC_UNAUTHORIZED);
            ResultUtil.sendJsonMessage(response,ResultUtil.returnError(ResultStatus.SC_UNAUTHORIZED));
            return false;
        }
        return true;
    }
}
