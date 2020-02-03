package com.splan.auth.security;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by keets on 2017/8/5.
 */
public class CustomTokenEnhancer extends JwtAccessTokenConverter implements Serializable {
    private static int authenticateCodeExpiresTime = 10 * 60;

    private static final String TOKEN_SEG_USER_ID = "Storm-UserId";
    private static final String TOKEN_SEG_CLIENT = "Storm-ClientId";
    private static final String REFRESH_TOKEN = "refresh_token";

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
                                     OAuth2Authentication authentication) {
        //CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        //authentication.getUserAuthentication().getPrincipal();
        Map<String, Object> info = new HashMap<>();
        info.put(TOKEN_SEG_USER_ID, authentication.getPrincipal());
        //info.put(REFRESH_TOKEN,accessToken.getRefreshToken());

        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(info);

        OAuth2AccessToken enhancedToken = super.enhance(customAccessToken, authentication);
        enhancedToken.getAdditionalInformation().put(TOKEN_SEG_CLIENT, authentication.getPrincipal());
        return enhancedToken;
    }

}
