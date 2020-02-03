package com.splan.bplan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.splan.bplan.constants.BussinessContants;
import com.splan.bplan.constants.Constants;
import com.splan.bplan.mappers.SysPermissionMapper;
import com.splan.bplan.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    /**
     * 查询某用户的 角色  菜单列表   权限列表
     */
    @Override
    public JSONObject getUserPermission(String username) {
        JSONObject userPermission = getUserPermissionFromDB(username);
        return userPermission;
    }

    /**
     * 从数据库查询用户权限信息
     */
    private JSONObject getUserPermissionFromDB(String username) {
        JSONObject userPermission = sysPermissionMapper.getUserRoleId(username);
        //管理员角色ID为1
        int adminRoleId = 1;
        //如果是管理员
        String roleIdKey = "roleId";
        Set<String> menuList;
        Set<String> permissionList;
        if (adminRoleId == userPermission.getIntValue(roleIdKey)) {
            //查询所有菜单  所有权限
             menuList = sysPermissionMapper.getAllMenu();
             permissionList = sysPermissionMapper.getAllPermission();
        }else{
            menuList = sysPermissionMapper.getMenuByusername(username);
            permissionList = sysPermissionMapper.getPermissionByusernamme(username);
        }
        //排除特殊选项
        if(BussinessContants.BUSINESSBACK_ROLEID!=userPermission.getIntValue(roleIdKey)){
            Iterator<String> iterator=menuList.iterator();
            while(iterator.hasNext()){
                String meun=iterator.next();
                if(meun.contains(BussinessContants.BUSINESSBACK_JURISDICTION))iterator.remove();
            }
        }

        userPermission.put("menuList", menuList);
        userPermission.put("permissionList", permissionList);
        return userPermission;
    }

}
