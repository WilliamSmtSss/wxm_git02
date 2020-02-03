package com.splan.xbet.admin.back.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.SysUser;
import com.splan.xbet.admin.back.mappers.SysUserMapper;
import com.splan.xbet.admin.back.service.ISysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
}
