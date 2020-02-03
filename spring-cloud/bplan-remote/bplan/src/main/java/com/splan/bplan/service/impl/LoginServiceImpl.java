package com.splan.bplan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.splan.base.bean.SysRole;
import com.splan.bplan.constants.Constants;
import com.splan.base.enums.ResultStatus;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.mappers.SysUserMapper;
import com.splan.bplan.service.ISysRoleService;
import com.splan.bplan.service.LoginService;
import com.splan.bplan.service.PermissionService;
import com.splan.bplan.utils.CommonUtil;
import com.splan.bplan.utils.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Override
    public CommonResult<JSONObject> authLogin(JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        JSONObject info = new JSONObject();
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            currentUser.login(token);
            return ResultUtil.returnSuccess(info);
        } catch (AuthenticationException e) {
            return ResultUtil.returnError(ResultStatus.USERNAME_OR_PASSWORD_ERROR);
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
        String roleName=sysRoleService.getById(roleid).getRoleName();
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
