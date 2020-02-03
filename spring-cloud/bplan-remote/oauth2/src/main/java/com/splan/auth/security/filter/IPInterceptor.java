package com.splan.auth.security.filter;

import com.splan.auth.service.ClientSecretService;
import com.splan.auth.utils.IpUtil;
import com.splan.base.bean.OauthClientDetailsBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class IPInterceptor implements HandlerInterceptor {

    @Autowired
    private ClientSecretService clientSecretService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*log.info(request.getPathInfo());
        log.info(request.getRequestURI());
        log.info(request.getRequestURI().matches("/oauth/*")+"");*/
        if (!request.getRequestURI().contains("/oauth/")){
            return true;
        }
        String clientId = request.getParameter("client_id");
        if (clientId==null){
            return true;
        }
        String ipAddress = IpUtil.getIpAddr(request);
        if(!StringUtils.isNotBlank(ipAddress)) {
            return false;
        }
        if (!StringUtils.isNotBlank(clientId)){
            return false;
        }
        OauthClientDetailsBean clientSecret = clientSecretService.getWhiteList(clientId);
        if (clientSecret==null){
            return false;
        }
        if (StringUtils.isBlank(clientSecret.getIpWhitelist())){
            return true;
        }
        if (!clientSecret.getIpWhitelist().contains(ipAddress)){
            //ResultUtil.sendJsonMessage(response,"1005","非法IP");
            throw new OAuth2Exception("非法IP");
            //return false;
        }
        return true;
    }

    /*public static void main(String[] args) {
        String url = "/oauth/check_token";
        System.out.println(url.matches("/oauth/(*)"));
    }*/

}
