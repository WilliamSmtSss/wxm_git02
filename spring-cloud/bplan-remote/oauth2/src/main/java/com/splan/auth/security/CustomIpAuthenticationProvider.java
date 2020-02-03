//package com.splan.auth.security;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@Slf4j
//public class CustomIpAuthenticationProvider implements AuthenticationProvider {
//
//
//    @Override
//    public Authentication authenticate(Authentication auth) throws AuthenticationException {
//        WebAuthenticationDetails details = (WebAuthenticationDetails) auth.getDetails();
//        String userIp = details.getRemoteAddress();
//        log.info(userIp);
//        if(!whitelist.contains(userIp)){
//            throw new BadCredentialsException("Invalid IP Address");
//        }
//        final String name = auth.getName();
//        final String password = auth.getCredentials().toString();
//
//        if (name.equals("john") && password.equals("123")) {
//            List<GrantedAuthority> authorities =new ArrayList<GrantedAuthority>();
//            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//            return new UsernamePasswordAuthenticationToken(name, password, authorities);
//        }
//        else{
//            throw new BadCredentialsException("Invalid username or password");
//        }
//
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return true;
//    }
//}
