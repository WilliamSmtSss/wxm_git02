package com.splan.xbet.frontback.frontback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.http.CommonResult;
import com.splan.base.param.backremote.BackProductOrderParam;
import com.splan.base.result.backremote.BackProductOrderResult;
import com.splan.xbet.frontback.frontback.service.ServiceOrderService;
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
@RequestMapping("/frontBack/ServiceOrder")
@Api(tags={"服务申请"})
public class ServiceOrderController {

    @Autowired
    private ServiceOrderService serviceOrderService;

    @PostMapping("/list")
    @ApiOperation("列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apptype",value = "应用类型"),
            @ApiImplicitParam(name = "datatype",value = "数据类型"),
            @ApiImplicitParam(name = "phone",value = "手机号"),
            @ApiImplicitParam(name = "current",value = ""),
            @ApiImplicitParam(name = "size",value = ""),
            @ApiImplicitParam(name = "serviceId",value = "服务ID"),
            @ApiImplicitParam(name = "status",value = "状态"),
            @ApiImplicitParam(name = "statuses",value = "状态集"),
            @ApiImplicitParam(name = "searchText",value = "商户名或手机号"),
    })
    public CommonResult<Page<List<BackProductOrderResult>>> list(@RequestBody BackProductOrderParam backProductOrderParam){
        return serviceOrderService.list(backProductOrderParam);
    }

    @PostMapping("/add")
    @ApiOperation("新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "businessName",value = "商户名"),
            @ApiImplicitParam(name = "datatype",value = "数据类型"),
            @ApiImplicitParam(name = "phone",value = "手机号"),
            @ApiImplicitParam(name = "apptype",value = "应用类型"),
            @ApiImplicitParam(name = "serviceStartTime",value = "服务开始时间"),
            @ApiImplicitParam(name = "serviceEndTime",value = "服务结束时间"),
    })
    public CommonResult add(@RequestBody BackProductOrderParam backProductOrderParam){
        return serviceOrderService.add(backProductOrderParam);
    }

    @PostMapping("/edit")
    @ApiOperation("编辑")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = ""),
            @ApiImplicitParam(name = "businessName",value = ""),
            @ApiImplicitParam(name = "datatype",value = ""),
            @ApiImplicitParam(name = "phone",value = ""),
            @ApiImplicitParam(name = "apptype",value = ""),
            @ApiImplicitParam(name = "serviceStartTime",value = ""),
            @ApiImplicitParam(name = "serviceEndTime",value = ""),
    })
    public CommonResult edit(@RequestBody BackProductOrderParam backProductOrderParam){
        return serviceOrderService.edit(backProductOrderParam);
    }

    @PostMapping("/del")
    @ApiOperation("删除")
    public CommonResult del(@RequestBody BackProductOrderParam backProductOrderParam){
        return serviceOrderService.del(backProductOrderParam);
    }

    @PostMapping("/check")
    @ApiOperation("审核 通过或拒绝")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = ""),
            @ApiImplicitParam(name = "status",value = ""),
            @ApiImplicitParam(name = "serviceStartTime",value = ""),
            @ApiImplicitParam(name = "serviceEndTime",value = ""),
    })
    public CommonResult check(@RequestBody BackProductOrderParam backProductOrderParam){
        return serviceOrderService.check(backProductOrderParam);
    }

}
