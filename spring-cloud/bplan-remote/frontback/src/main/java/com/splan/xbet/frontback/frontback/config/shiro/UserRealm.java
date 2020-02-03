package com.splan.xbet.frontback.frontback.config.shiro;

import com.alibaba.fastjson.JSONObject;
import com.splan.xbet.frontback.frontback.config.Myconfig;
import com.splan.xbet.frontback.frontback.contantes.Constants;
import com.splan.xbet.frontback.frontback.service.LoginService;
import com.splan.xbet.frontback.frontback.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.*;

@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @SuppressWarnings("unchecked")
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Session session = SecurityUtils.getSubject().getSession();
        //查询用户的权限
        JSONObject permission = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        String roleName=(String)session.getAttribute(Constants.SESSION_USER_ROLE);
        //log.info("permission的值为:" + permission);
//        if(null!=permission&&null!=permission.get("permissionList"))
//        log.info("本用户权限为:" + permission.get("permissionList"));
        //为当前用户设置角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if(null!=permission&&null!=permission.get("permissionList"))
            authorizationInfo.addStringPermissions((Collection<String>) permission.get("permissionList"));
        if(StringUtils.isNotBlank(roleName))
            authorizationInfo.addRole(roleName);
        return authorizationInfo;
    }

    /**
     * 验证当前登录的Subject
     * LoginController.login()方法中执行Subject.login()时 执行此方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        String loginName = (String) authcToken.getPrincipal();
        // 获取用户密码
        String password = new String((char[]) authcToken.getCredentials());
        JSONObject user = loginService.getUser(loginName, password);
        if (user == null) {
            //没找到帐号
            throw new UnknownAccountException();
        }
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getString("password"),
                //ByteSource.Util.bytes("salt"), salt=username+salt,采用明文访问时，不需要此句
                getName()
        );
        //session中不需要保存密码
        user.remove("password");
        //将用户信息放入session中
        SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USER_INFO, user);
        Set<String> set=new HashSet<>();
        if (redisTemplate == null) {
            //由于启动期间注入失败，只能运行期间注入，这段代码可以删除
            redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
            RedisSerializer redisSerializer=new StringRedisSerializer();
            redisTemplate.setStringSerializer(redisSerializer);
        }
        if(null!=redisTemplate.opsForValue().get(Constants.SHIRO_SESSION)) {
            Map<String, String> map = (HashMap<String, String>) redisTemplate.opsForValue().get(Constants.SHIRO_SESSION);
            if(map.containsKey(SecurityUtils.getSubject().getSession().getId()+"")){
                map.put(SecurityUtils.getSubject().getSession().getId()+"",user.getString("username"));
            }
            redisTemplate.opsForValue().set(Constants.SHIRO_SESSION,map);
        }

        String sessionExpireStr=Double.parseDouble(Myconfig.getSessionExpire())*60*60*1000+"";
        sessionExpireStr=sessionExpireStr.substring(0,sessionExpireStr.lastIndexOf("."));
        long sessionExpire=Long.parseLong(sessionExpireStr);
        SecurityUtils.getSubject().getSession().setTimeout(sessionExpire);
//        SecurityUtils.getSubject().getSession().setTimeout(1*60*60*1000);
        return authenticationInfo;
    }

    public static void main(String[] args) {

    }
}
