package com.splan.bplan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.SysUser;
import com.splan.bplan.dto.SysUserDto;
import com.splan.bplan.http.CommonResult;

import java.util.List;

public interface BussinessProxyService {
    CommonResult<IPage<SysUser>> list(Page page);
    CommonResult<Integer> add(SysUserDto sysUserDto);
     CommonResult<List<String>> apilist();
}
