package com.splan.xbet.frontback.frontback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.http.CommonResult;
import com.splan.base.param.backremote.BackProductTrialParam;
import com.splan.base.result.backremote.BackProductTrialResult;
import com.splan.xbet.frontback.frontback.service.ServiceTrialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/frontBack/ServiceTrial")
@Api(tags={"服务试用"})
public class ServiceTrialController {

    @Autowired
    private ServiceTrialService serviceTrialService;

    @PostMapping("/list")
    @ApiOperation("列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status",value = "状态"),
            @ApiImplicitParam(name = "searchText",value = "商户名或手机号"),
            @ApiImplicitParam(name = "statuses",value = "状态集"),
    })
    public CommonResult<Page<List<BackProductTrialResult>>> list(@RequestBody BackProductTrialParam backProductTrialParam){
        return serviceTrialService.list(backProductTrialParam);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "businessName",value = "商户名"),
            @ApiImplicitParam(name = "phone",value = "手机号"),
            @ApiImplicitParam(name = "trialAdmin",value = "账号"),
            @ApiImplicitParam(name = "trialPassword",value = "密码"),
    })
    public CommonResult add(@RequestBody BackProductTrialParam backProductTrialParam){
        return serviceTrialService.add(backProductTrialParam);
    }

    @PostMapping("/check")
    @ApiOperation("审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status",value = "状态"),
            @ApiImplicitParam(name = "trialAdmin",value = ""),
            @ApiImplicitParam(name = "trialPassword",value = ""),
    })
    public CommonResult check(@RequestBody BackProductTrialParam backProductTrialParam){
        return serviceTrialService.check(backProductTrialParam);
    }

    @PostMapping("/del")
    @ApiOperation("删除")
    public CommonResult del(@RequestBody BackProductTrialParam backProductTrialParam){
        return null;
    }

    @PostMapping("/edit")
    @ApiOperation("编辑")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = ""),
            @ApiImplicitParam(name = "businessName",value = ""),
            @ApiImplicitParam(name = "phone",value = ""),
            @ApiImplicitParam(name = "trialAdmin",value = ""),
            @ApiImplicitParam(name = "trialPassword",value = ""),
    })
    public CommonResult edit(@RequestBody BackProductTrialParam backProductTrialParam){
        return serviceTrialService.edit(backProductTrialParam);
    }

}
