//package com.splan.auth.backoauth2.config2;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//
//@Configuration
//@EnableAuthorizationServer
//public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {
//    private static final String SPARKLR_RESOURCE_ID = "apple";
//
//    int accessTokenValiditySeconds = 3600;
//
//    @Autowired
//    @Qualifier("authenticationManagerBean")
//    private AuthenticationManager authenticationManager;
//
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey("123");
//        return converter;
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode("secret");
//        // @formatter:off
//        clients.inMemory().withClient("tonr")
//                .resourceIds(SPARKLR_RESOURCE_ID)
//                .authorizedGrantTypes("authorization_code", "implicit")
//                .authorities("ROLE_CLIENT")
//                .scopes("read", "write")
//                .secret(finalSecret)
//                .accessTokenValiditySeconds(accessTokenValiditySeconds);
//        // @formatter:on
//    }
//    //jdbc
////    @Bean
////    public DataSource jdbcTokenDataSource(){
////        DriverManagerDataSource dataSource = new DriverManagerDataSource();
////        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
////        dataSource.setUrl("jdbc:mysql://localhost/test");
////        dataSource.setUsername("root");
////        dataSource.setPassword("root");
////        return dataSource;
////    }
//
//    @Bean
//    public TokenStore tokenStore() {
////        return new InMemoryTokenStore();
////        return new JdbcTokenStore(jdbcTokenDataSource());
//        return new JwtTokenStore(accessTokenConverter());
//    }
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.tokenStore(tokenStore())
//                .authenticationManager(this.authenticationManager)
//                .accessTokenConverter(accessTokenConverter());
//    }
//    @Bean
//    @Primary
//    public DefaultTokenServices tokenServices() {
//        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//        defaultTokenServices.setTokenStore(tokenStore());
//        defaultTokenServices.setSupportRefreshToken(true);
//        return defaultTokenServices;
//    }
//}
