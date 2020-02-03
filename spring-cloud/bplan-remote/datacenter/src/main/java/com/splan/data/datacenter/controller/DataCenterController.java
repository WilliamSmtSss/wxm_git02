package com.splan.data.datacenter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.data.datacenter.bean.BetOrderBean;
import com.splan.base.bean.BetTopicsBean;
import com.splan.data.datacenter.bean.MoneyRecordBean;
import com.splan.base.http.CommonResult;
import com.splan.data.datacenter.dto.*;
import com.splan.data.datacenter.service.DateCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/back/game/data")
@Api(tags={"游戏数据中心"})
public class DataCenterController {

    @Autowired
    private DateCenterService dateCenterService;


    @GetMapping(value = "/betData",consumes = "application/json")
    @ApiOperation(value = "盘口数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gameId",value = "游戏ID"),
            @ApiImplicitParam(name = "dataId",value = "比赛ID"),
            @ApiImplicitParam(name = "betId",value = "盘口ID"),
            @ApiImplicitParam(name = "betStatus",value = "盘口状态"),
    })
    public CommonResult<IPage<BetTopicsBean>> betData( Page page, String bigBusiness, String smallBusiness, String gameId, DateDto dateDto, String dataId, String betId, String betStatus){
        return dateCenterService.betData(page,bigBusiness,smallBusiness,gameId,dateDto,dataId,betId,betStatus);
    }

    @PostMapping(value = "/orderData",consumes = "application/json")
    @ApiOperation(value = "订单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderStatus",value = "订单状态"),
            @ApiImplicitParam(name = "searchId",value = "用户ID"),
            @ApiImplicitParam(name = "orderId",value = "订单ID"),
            @ApiImplicitParam(name = "orderType",value = "订单类型 1 串 0 单"),
    })
    public CommonResult<IPage<BetOrderBean>> orderData(@RequestBody OrderParam orderParam){
        if(orderParam.getCurrent()==0)orderParam.setCurrent(1);
        if(orderParam.getSize()==0)orderParam.setSize(100);
        Page page=new Page();
        page.setCurrent(orderParam.getCurrent());
        if(orderParam.getSize()>=100)orderParam.setSize(100);
        page.setSize(orderParam.getSize());
        DateDto dateDto=new DateDto();
        dateDto.setStartDate(orderParam.getStartDate());
        dateDto.setEndDate(orderParam.getEndDate());
        DateDtoOrder dateDtoOrder=new DateDtoOrder();
        dateDtoOrder.setStartDateOrder(orderParam.getStartDateOrder());
        dateDtoOrder.setEndDateOrder(orderParam.getEndDateOrder());
        DateDtoUpdate dateDtoUpdate=new DateDtoUpdate();
        dateDtoUpdate.setStartDateUpdate(orderParam.getStartDateUpdate());
        dateDtoUpdate.setEndDateUpdate(orderParam.getEndDateUpdate());
        return dateCenterService.orderData(page,dateDto,orderParam.getOrderStatus(),orderParam.getSearchId(),orderParam.getOrderId(),orderParam.getOrderType(),dateDtoOrder,dateDtoUpdate);
    }


    @PostMapping(value = "/moneyRecord",consumes = "application/json")
    @ApiOperation(value = "会员流水", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchId", value = "搜索ID"),
            @ApiImplicitParam(name = "orderId", value = "流水ID"),
    })
    public CommonResult<IPage<MoneyRecordBean>> moneyRecord(@RequestBody MoneyParam moneyParam){
        if(moneyParam.getCurrent()==0)moneyParam.setCurrent(1);
        if(moneyParam.getSize()==0)moneyParam.setSize(100);
        Page page=new Page();
        page.setCurrent(moneyParam.getCurrent());
        if(moneyParam.getSize()>=100)moneyParam.setSize(100);
        page.setSize(moneyParam.getSize());
        DateDto dateDto=new DateDto();
        dateDto.setStartDate(moneyParam.getStartDate());
        dateDto.setEndDate(moneyParam.getEndDate());
        return dateCenterService.moneyRecord(page,moneyParam.getSearchId(),moneyParam.getOrderId(),dateDto);
    }

}
