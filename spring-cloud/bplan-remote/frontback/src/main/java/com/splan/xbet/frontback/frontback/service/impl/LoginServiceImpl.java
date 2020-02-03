package com.splan.xbet.frontback.frontback.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.splan.xbet.frontback.frontback.beans.SysUser;
import com.splan.base.enums.ResultStatus;
import com.splan.base.http.CommonResult;
import com.splan.base.utils.ResultUtil;
import com.splan.xbet.frontback.frontback.contantes.CommonContant;
import com.splan.xbet.frontback.frontback.contantes.Constants;
import com.splan.xbet.frontback.frontback.contantes.MsgTestContant;
import com.splan.xbet.frontback.frontback.contantes.RedisContant;
import com.splan.xbet.frontback.frontback.mappers.SysRoleMapper;
import com.splan.xbet.frontback.frontback.mappers.SysUserMapper;
import com.splan.xbet.frontback.frontback.param.CheckCodeParam;
import com.splan.xbet.frontback.frontback.param.GetCodeParam;
import com.splan.xbet.frontback.frontback.param.RegisterParam;
import com.splan.xbet.frontback.frontback.param.ResetPasswordParam;
import com.splan.xbet.frontback.frontback.service.LoginService;
import com.splan.xbet.frontback.frontback.service.PermissionService;
import com.splan.xbet.frontback.frontback.utils.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public CommonResult<JSONObject> authLogin(JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        JSONObject info = new JSONObject();
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            currentUser.login(token);
            //若未关联则抛出异常
            JSONObject loginInfo=(JSONObject) SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_USER_INFO);
            if(0==loginInfo.getIntValue("roleid"))
                return ResultUtil.returnError(ResultStatus.NO_JURISDICTION);
            SysUser sysUser=new SysUser();
            sysUser.setLoginIp(CommonUtil.getIpAddr(httpServletRequest));
            sysUserMapper.update(sysUser,new UpdateWrapper<SysUser>().eq("username",username));
            return ResultUtil.returnSuccess(info);
        } catch (AuthenticationException e) {
            return ResultUtil.returnError(ResultStatus.LOGIN_FAIL);
        }
    }

    /**
     * 根据用户名和密码查询对应的用户
     */
    @Override
    public JSONObject getUser(String username, String password) {
        return sysUserMapper.getUser(username, password);
    }

    /**
     * 查询当前登录用户的权限等信息
     */
    @Override
    public JSONObject getInfo() {
        //从session获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
        String username = userInfo.getString("username");
        String roleid = userInfo.getString("roleid");
        JSONObject info = new JSONObject();
        JSONObject userPermission = permissionService.getUserPermission(username);
        String roleName="";
        if(null!=sysRoleMapper.selectById(roleid))
            roleName=sysRoleMapper.selectById(roleid).getRoleName();
        session.setAttribute(Constants.SESSION_USER_PERMISSION, userPermission);
        session.setAttribute(Constants.SESSION_USER_ROLE, roleName);
        info.put("userPermission", userPermission);
        return info;
    }

    /**
     * 退出登录
     */
    @Override
    public String logout() {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
        } catch (Exception e) {
        }
        return "";
    }

}
