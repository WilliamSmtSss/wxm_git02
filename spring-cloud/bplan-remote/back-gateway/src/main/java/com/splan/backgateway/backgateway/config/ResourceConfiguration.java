//package com.splan.backgateway.backgateway.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.client.OAuth2ClientContext;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
//import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
//import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
//
//import java.util.Arrays;
//
//@Configuration
//@EnableOAuth2Client
//public class ResourceConfiguration {
//
//    @Autowired
//    private Oauth2ParamConfig oauth2ParamConfig;
//
//    @Bean
//    public OAuth2ProtectedResourceDetails hello() {
//        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
//        details.setId("hello");
//        details.setClientId(oauth2ParamConfig.getClientId());
//        details.setClientSecret(oauth2ParamConfig.getClientSecret());
//        details.setAccessTokenUri(oauth2ParamConfig.getAccessTokenUri());//认证服务器地址+/oauth/token
////        details.setUserAuthorizationUri("http://localhost:9311/oauth/authorize");//认证服务器地址+/oauth/authorize
//        details.setScope(Arrays.asList("select"));
////        details.setPreEstablishedRedirectUri("http://baidu.com");
//        return details;
//    }
//
//    @Bean
//    public OAuth2RestTemplate helloRestTemplate(OAuth2ClientContext oauth2Context) {//客户端的信息被封装到OAuth2RestTemplate用于请求资源
//        return new OAuth2RestTemplate(hello(), oauth2Context);
//    }
//
//}
