package com.splan.xbet.admin.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.*;
import com.splan.base.http.CommonResult;
import com.splan.xbet.admin.back.annotation.CurrentSysUser;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

public interface XBetCommonService {

    CommonResult<String> upload(MultipartFile file, String type);

    CommonResult<List<BusinessCurrencyConfigBean>> currencyList();

    CommonResult<List<SysUser>> assignAdmin(String apiId);

    CommonResult<List<String>> businessList(SysUser sysUser, String queryType,String bigBusiness);

    CommonResult<List<GameTypeBean>> gameType();

    CommonResult getExcelTemp(SysUser sysUser);

    CommonResult<IPage<XExportBean>> getExportTempFiles(@CurrentSysUser @ApiIgnore SysUser sysUser, Page page);
}
