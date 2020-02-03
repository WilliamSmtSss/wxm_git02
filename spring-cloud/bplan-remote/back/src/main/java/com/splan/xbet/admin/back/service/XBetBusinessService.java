package com.splan.xbet.admin.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BusinessConfigBean;
import com.splan.base.bean.BusinessCurrencyConfigBean;
import com.splan.base.bean.BusinessCurrencyRecordBean;
import com.splan.base.bean.SysUser;
import com.splan.base.http.CommonResult;
import com.splan.base.result.BusinessFormsResult;
import com.splan.xbet.admin.back.dto.DateDto;
import com.splan.xbet.admin.back.dto.InBusinessConfigDto;
import com.splan.xbet.admin.back.dto.InBusinessCurrencyConfigDto;

public interface XBetBusinessService {

    CommonResult add(SysUser sysUser, InBusinessConfigDto inBusinessConfigDto);

    CommonResult edit(InBusinessConfigDto inBusinessConfigDto);

    CommonResult<IPage<BusinessConfigBean>> list(SysUser sysUser,Page page,String status, String businessName);

    CommonResult currencyAdd(InBusinessCurrencyConfigDto inBusinessCurrencyConfigDto);

    CommonResult currencyEdit(InBusinessCurrencyConfigDto inBusinessCurrencyConfigDto);

    CommonResult<IPage<BusinessCurrencyConfigBean>> currencyList(Page page, DateDto dateDto);

    CommonResult<IPage<BusinessCurrencyRecordBean>> currencyRecordList(Page page, DateDto dateDto,String currencyId);

    CommonResult<IPage<BusinessFormsResult>> businessForms(SysUser sysUser, Page page, String selectType, String queryTimeType, String time, String businessName, String pid);

    CommonResult<IPage<BusinessConfigBean>> downList(SysUser sysUser,Page page, String status, String businessName, String pid);
}
