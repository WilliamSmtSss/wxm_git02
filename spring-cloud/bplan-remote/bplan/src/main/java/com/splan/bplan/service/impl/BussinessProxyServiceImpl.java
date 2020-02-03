package com.splan.bplan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.SysUser;
import com.splan.base.enums.ResultStatus;
import com.splan.bplan.dto.SysUserDto;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.service.BussinessProxyService;
import com.splan.bplan.service.ISysUserService;
import com.splan.bplan.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class BussinessProxyServiceImpl implements BussinessProxyService {
    @Autowired
    private ISysUserService sysUserService;
    /*@Autowired
    private IProxyConfigService iProxyConfigService;*/
    @Override
    public CommonResult<IPage<SysUser>> list(Page page) {
        //从session获取用户信息
        JSONObject sysUser = (JSONObject) SecurityUtils.getSubject().getPrincipal();
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("fromid",sysUser.getInteger("userId"));
        return ResultUtil.returnSuccess(sysUserService.page(page,queryWrapper));
    }

    @Override
    public CommonResult<Integer> add(SysUserDto sysUserDto) {
        if (sysUserDto.getId() != null) {
            ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }
        if (!StringUtils.equals(sysUserDto.getPassword(), sysUserDto.getSurePassword())) {
            ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }
        if (StringUtils.isBlank(sysUserDto.getUsername())) {
            ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }
        //从session获取用户信息
        JSONObject sysUsersession = (JSONObject) SecurityUtils.getSubject().getPrincipal();
        SysUser sysUser = new SysUser();
        sysUser.setNickname(sysUserDto.getRealName());
        sysUser.setUsername(sysUserDto.getUsername());
        sysUser.setPassword(sysUserDto.getPassword());
        sysUser.setRoleId(sysUserDto.getRoleId());
        sysUser.setFromid(sysUsersession.getInteger("userId"));
        return ResultUtil.returnSuccess(sysUserService.save(sysUser)==true?1:0);
    }

    @Override
    public CommonResult<List<String>> apilist() {
        //从session获取用户信息
        JSONObject sysUser = (JSONObject) SecurityUtils.getSubject().getPrincipal();
        List<String> apilist=new ArrayList<>();
//        List<String> apilist=iProxyConfigService.getclientids();
//        if(apilist.contains(sysUser.getString("jurisdiction")))
//            apilist.remove(sysUser.getString("jurisdiction"));
        if(StringUtils.isNotBlank(sysUser.getString("jurisdiction"))){
            apilist.addAll(Arrays.asList(sysUser.getString("jurisdiction").split("[,|，]")));
        }
        return ResultUtil.returnSuccess(apilist);
    }
}
