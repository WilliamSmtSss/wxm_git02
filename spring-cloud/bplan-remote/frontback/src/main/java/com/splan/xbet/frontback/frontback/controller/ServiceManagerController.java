package com.splan.xbet.frontback.frontback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.back.BackProductInfo;
import com.splan.base.http.CommonResult;
import com.splan.base.param.backremote.BackProductInfoParam;
import com.splan.base.result.backremote.BackProductInfoResult;
import com.splan.xbet.frontback.frontback.service.ServiceManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/frontBack/ServiceManager")
@Api(tags={"服务管理"})
public class ServiceManagerController {

    @Autowired
    private ServiceManagerService serviceManagerService;

    @PostMapping("/list")
    @ApiOperation("列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "",value = ""),
    })
    public CommonResult<Page<List<BackProductInfoResult>>> list(@RequestBody BackProductInfoParam backProductInfoParam){
        return serviceManagerService.list(backProductInfoParam);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appType",value = ""),
            @ApiImplicitParam(name = "dataType",value = ""),
            @ApiImplicitParam(name = "serviceName",value = ""),
            @ApiImplicitParam(name = "serviceDescribe",value = ""),
            @ApiImplicitParam(name = "status",value = ""),
    })
    public CommonResult add(@RequestBody @Validated BackProductInfoParam backProductInfoParam){
        return serviceManagerService.add(backProductInfoParam);
    }

    @PostMapping("/edit")
    @ApiOperation("编辑")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = ""),
            @ApiImplicitParam(name = "appType",value = ""),
            @ApiImplicitParam(name = "dataType",value = ""),
            @ApiImplicitParam(name = "serviceName",value = ""),
            @ApiImplicitParam(name = "serviceDescribe",value = ""),
            @ApiImplicitParam(name = "status",value = ""),
    })
    public CommonResult edit(@RequestBody BackProductInfoParam backProductInfoParam){
        return serviceManagerService.edit(backProductInfoParam);
    }

    @PostMapping("/del")
    @ApiOperation("删除")
    public CommonResult del(@RequestBody BackProductInfoParam backProductInfoParam){
        return serviceManagerService.del(backProductInfoParam);
    }

}
