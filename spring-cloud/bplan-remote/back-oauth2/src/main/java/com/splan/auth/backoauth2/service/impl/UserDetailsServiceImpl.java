package com.splan.auth.backoauth2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {

//    @Autowired
//    private UserServiceImpl userService;
//
//    /**
//     * 实现UserDetailsService中的loadUserByUsername方法，用于加载用户数据
//     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userService.queryUserByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("用户不存在");
//        }
//
//        //用户权限列表
//        Collection<? extends GrantedAuthority> authorities = userService.queryUserAuthorities(user.getId());
//
//        return new AuthUser(
//                user.getId(),
//                user.getUsername(),
//                user.getPassword(),
//                true,
//                true,
//                true,
//                true,
//                authorities);
//    }
//}
