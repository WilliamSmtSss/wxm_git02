package com.splan.xbet.admin.back.controller.xbetback;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BusinessConfigBean;
import com.splan.base.bean.BusinessCurrencyConfigBean;
import com.splan.base.bean.BusinessCurrencyRecordBean;
import com.splan.base.bean.SysUser;
import com.splan.base.http.CommonResult;
import com.splan.base.result.BusinessFormsResult;
import com.splan.xbet.admin.back.annotation.CurrentSysUser;
import com.splan.xbet.admin.back.dto.DateDto;
import com.splan.xbet.admin.back.dto.InBusinessConfigDto;
import com.splan.xbet.admin.back.dto.InBusinessCurrencyConfigDto;
import com.splan.xbet.admin.back.service.XBetBusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/back/xBet/business")
@Api(tags={"xBet后台:商户管理"})
public class BusinessController {

    @Autowired
    private XBetBusinessService xBetBusinessService;

    @GetMapping("/add")
    @ApiOperation(value = "新增商户", notes = "")
    @RequiresPermissions(value = {"businessManage:add"},logical = Logical.OR)
    public CommonResult add(@CurrentSysUser @ApiIgnore SysUser sysUser, @Validated InBusinessConfigDto inBusinessConfigDto){
        return xBetBusinessService.add(sysUser,inBusinessConfigDto);
    }

    @GetMapping("/edit")
    @ApiOperation(value = "编辑商户", notes = "")
    @RequiresPermissions(value = {"businessManage:list","businessManage:downList"},logical = Logical.OR)
    public CommonResult edit(InBusinessConfigDto inBusinessConfigDto){
        return xBetBusinessService.edit(inBusinessConfigDto);
    }

    @GetMapping("/list")
    @ApiOperation(value = "商户列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态"),
            @ApiImplicitParam(name = "businessName", value = "商户名称"),
    })
    @RequiresPermissions(value = "businessManage:list")
    public CommonResult<IPage<BusinessConfigBean>> list(@CurrentSysUser @ApiIgnore SysUser sysUser,Page page, String status, String businessName){
        return xBetBusinessService.list(sysUser,page,status,businessName);
    }

    @GetMapping("/currencyAdd")
    @ApiOperation(value = "新增币种", notes = "")
    @ApiImplicitParams({
    })
    @RequiresPermissions(value = "businessManage:currency")
    public CommonResult currencyAdd(InBusinessCurrencyConfigDto inBusinessCurrencyConfigDto){
        return xBetBusinessService.currencyAdd(inBusinessCurrencyConfigDto);
    }

    @GetMapping("/currencyEdit")
    @ApiOperation(value = "编辑币种", notes = "")
    @ApiImplicitParams({
    })
    @RequiresPermissions(value = "businessManage:currency")
    public CommonResult currencyEdit(InBusinessCurrencyConfigDto inBusinessCurrencyConfigDto){
        return xBetBusinessService.currencyEdit(inBusinessCurrencyConfigDto);
    }

    @GetMapping("/currencyList")
    @ApiOperation(value = "币种列表", notes = "")
    @ApiImplicitParams({
    })
    @RequiresPermissions(value = "businessManage:currency")
    public CommonResult<IPage<BusinessCurrencyConfigBean>> currencyList(Page page, DateDto dateDto){
        return xBetBusinessService.currencyList(page,dateDto);
    }

    @GetMapping("/currencyRecordList")
    @ApiOperation(value = "币种更新记录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currencyId", value = "币种ID"),
    })
    @RequiresPermissions(value = "businessManage:currency")
    public CommonResult<IPage<BusinessCurrencyRecordBean>> currencyRecordList(Page page, DateDto dateDto,String currencyId){
        return xBetBusinessService.currencyRecordList(page,dateDto,currencyId);
    }

    @GetMapping("/businessForms")
    @ApiOperation(value = "商户月/日报表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "selectType", value = "显示模式 0:单月 1:单商户"),
            @ApiImplicitParam(name = "queryTimeType", value = "时间模式 0:月 1:日"),
            @ApiImplicitParam(name = "time", value = "时间 月格式YYYY-MM  日格式YYYY-MM-dd"),
            @ApiImplicitParam(name = "businessName", value = "商户名称"),
            @ApiImplicitParam(name = "pid", value = "查询商户下线使用 上级商户ID"),
    })
    @RequiresPermissions(value = {"businessManage:monthForm","businessManage:dayForm","businessManage:downMonthForm","businessManage:downDayForm"},logical = Logical.OR)
    public CommonResult<IPage<BusinessFormsResult>> businessForms(@CurrentSysUser @ApiIgnore SysUser sysUser, Page page, String selectType, String queryTimeType, String time, String businessName, String pid){
        return xBetBusinessService.businessForms(sysUser,page,selectType,queryTimeType,time,businessName,pid);
    }

    @GetMapping("/downList")
    @ApiOperation(value = "下级商户列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态"),
            @ApiImplicitParam(name = "businessName", value = "商户名称"),
            @ApiImplicitParam(name = "pid", value = "上级商户ID"),
    })
    public CommonResult<IPage<BusinessConfigBean>> downList(@CurrentSysUser @ApiIgnore SysUser sysUser,Page page, String status, String businessName, String pid){
        return xBetBusinessService.downList(sysUser,page,status,businessName,pid);
    }

}
