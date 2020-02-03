package com.splan.xbet.frontback.frontback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BetOrderBean;
import com.splan.base.bean.UserBean;
import com.splan.base.http.CommonResult;
import com.splan.xbet.frontback.frontback.service.DataManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/frontBack/dataMessage")
@Api(tags={"数据管理"})
public class DataManagerController {

    @Autowired
    private DataManagerService dataManagerService;

    @PostMapping("/userList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataType",value = "数据类型 0：电竞 1：体育"),
            @ApiImplicitParam(name = "businessName",value = "商户名"),
            @ApiImplicitParam(name = "searchText",value = "用户名称ID"),
            @ApiImplicitParam(name = "startLoginTime",value = "上次登陆时间开始 yyyy-mm-dd"),
            @ApiImplicitParam(name = "endLoginTime",value = "上次登陆时间结束 yyyy-mm-dd"),
            @ApiImplicitParam(name = "current",value = "页码"),
            @ApiImplicitParam(name = "size",value = "每页页数"),
    })
    @ApiOperation("用户管理")
    public CommonResult<Page<UserBean>> userList(@RequestBody Map<String,Object> requestParam){
        return dataManagerService.userList(requestParam);
    }

    @PostMapping("/orderList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataType",value = "数据类型 0：电竞 1：体育"),
            @ApiImplicitParam(name = "orderType",value = "订单类型"),
            @ApiImplicitParam(name = "businessName",value = "商户名"),
            @ApiImplicitParam(name = "gameId",value = "游戏ID 接口"),
            @ApiImplicitParam(name = "orderId",value = "订单号"),
            @ApiImplicitParam(name = "dataId",value = "比赛ID"),
            @ApiImplicitParam(name = "betId",value = "盘口ID"),
            @ApiImplicitParam(name = "searchText",value = "用户名称/ID"),
            @ApiImplicitParam(name = "startOrderTime",value = "开始投注时间 yyyy-mm-dd"),
            @ApiImplicitParam(name = "endOrderTime",value = "结束投注时间 yyyy-mm-dd"),
            @ApiImplicitParam(name = "orderStatus",value = "订单状态 FAIL:失败,UNSETTLED：未结算,SETTLED：结算"),
            @ApiImplicitParam(name = "current",value = "页码"),
            @ApiImplicitParam(name = "size",value = "每页条数"),
    })
    @ApiOperation("订单管理")
    public CommonResult<Page<BetOrderBean>> orderList(@RequestBody Map<String,Object> requestParam){
        if(null!=requestParam.get("gameId") || null!=requestParam.get("dataId") || null!=requestParam.get("betId"))
            requestParam.put("orderType","0");
        return dataManagerService.orderList(requestParam);
    }


}
