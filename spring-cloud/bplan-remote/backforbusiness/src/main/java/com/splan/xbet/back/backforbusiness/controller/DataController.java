package com.splan.xbet.back.backforbusiness.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.back.BackNotice;
import com.splan.base.bean.front.FrontCompanyInfo;
import com.splan.base.bean.front.SysUser;
import com.splan.base.param.backremote.BackProductOrderParam;
import com.splan.base.result.backremote.BackProductTrialResult;
import com.splan.base.http.CommonResult;
import com.splan.base.param.remote.BusinessFormsParam;
import com.splan.base.param.remote.FirstDataParam;
import com.splan.base.result.BusinessFormsResult;
import com.splan.base.result.backremote.BackProductInfoResult;
import com.splan.base.result.remote.FrontDataResultOut;
import com.splan.base.utils.ResultUtil;
import com.splan.xbet.back.backforbusiness.annotation.CurrentSysUser;
import com.splan.xbet.back.backforbusiness.service.DataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/front/data")
@Api(tags={"数据模块"})
public class DataController {

    @Autowired
    private DataService dataService;

    @PostMapping("/test111111")
    public String test1(){
        return "2222";
    }

    @PostMapping("/getFirstData")
    @ApiOperation(value = "首页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "",value = "")
    })
    public CommonResult<FrontDataResultOut> getFirstData(@ApiIgnore @CurrentSysUser SysUser sysUser, @RequestBody FirstDataParam firstDataParam){
        firstDataParam.setApiId(sysUser.getApiid());
        return dataService.getFirstData(firstDataParam);
    }

    @PostMapping("/businessForms")
    @ApiOperation(value = "报表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime",value = "开始时间"),
            @ApiImplicitParam(name = "endTime",value = "结束时间")
    })
    public CommonResult<List<BusinessFormsResult>> businessForms(@ApiIgnore @CurrentSysUser SysUser sysUser,@RequestBody BusinessFormsParam businessFormsParam){
        businessFormsParam.setApiId(sysUser.getApiid());
        return dataService.businessForms(businessFormsParam);
    }

    @PostMapping("/servicesList")
    @ApiOperation(value = "应用权限列表 需同步")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime",value = "开始时间"),
            @ApiImplicitParam(name = "endTime",value = "结束时间"),
            @ApiImplicitParam(name = "current",value = "页码"),
            @ApiImplicitParam(name = "size",value = "每页条数"),
    })
    public CommonResult<Page<BackProductInfoResult>> servicesList(@ApiIgnore @CurrentSysUser SysUser sysUser, @RequestBody BackProductOrderParam backProductOrderParam){
        backProductOrderParam.setBusinessName(sysUser.getApiid());
        return dataService.servicesList(backProductOrderParam);
    }

    @PostMapping("/accountInfo")
    @ApiOperation(value = "账号信息")
    public CommonResult<SysUser> accountInfo(@ApiIgnore @CurrentSysUser SysUser sysUser){
        return ResultUtil.returnSuccess(sysUser);
    }

    @PostMapping("/companyInfo")
    @ApiOperation(value = "企业信息")
    public CommonResult<FrontCompanyInfo> companyInfo(@ApiIgnore @CurrentSysUser SysUser sysUser){
        return dataService.companyInfo(sysUser);
    }

    @PostMapping("/serviceTrial")
    @ApiOperation(value = "服务试用 查询")
    public CommonResult<BackProductTrialResult> serviceTrial(@ApiIgnore @CurrentSysUser SysUser sysUser){
        return dataService.serviceTrial(sysUser);
    }

    //参数配置
    @PostMapping("/paramConfigSel")
    @ApiOperation(value = "参数配置 查询 需同步")
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "businessName",value = "商户名"),
    })
    public CommonResult paramConfigSel(@ApiIgnore @CurrentSysUser SysUser sysUser){
        return dataService.paramConfigSel(sysUser);
    }

    @PostMapping("/noticeSel")
    @ApiOperation(value = "公告 查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页"),
            @ApiImplicitParam(name = "size",value = "条数"),
    })
    public CommonResult<Page<BackNotice>> noticeSel(@ApiIgnore @CurrentSysUser SysUser sysUser, @RequestBody Map<String,Object> req){
        return dataService.noticeSel(sysUser,req);
    }

}
