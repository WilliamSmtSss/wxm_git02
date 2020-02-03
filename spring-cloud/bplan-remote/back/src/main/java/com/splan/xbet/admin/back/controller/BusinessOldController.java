package com.splan.xbet.admin.back.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BetOrderBean;
import com.splan.base.bean.BetOrderDetailBean;
import com.splan.base.bean.GameTypeBean;
import com.splan.base.bean.UserBean;
import com.splan.base.http.CommonResult;
import com.splan.xbet.admin.back.dto.DateDto;
import com.splan.xbet.admin.back.dto.ScreenForBetOrderDto;
import com.splan.base.enums.orderenums.*;
import com.splan.xbet.admin.back.result.BetResult;
import com.splan.xbet.admin.back.service.BusinessOldService;
import com.splan.xbet.admin.back.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/businessBack")
@Api(tags={"商户后台原始接口"})
public class BusinessOldController {
    @Autowired
    private BusinessOldService businessOldService;

    @GetMapping(value = "/revenue/list")
    @ApiOperation(value = "营收统计", notes = "")
    @RequiresPermissions("businessback:revenue")
    public CommonResult<JSONArray> list(DateDto dateDto, String apiid) {
        JSONArray array = businessOldService.revenueList(dateDto,apiid);
        return ResultUtil.returnSuccess(array);
    }

    @GetMapping("/data/bets/orderList")
    @ApiOperation(value = "盘口明细详情", notes = "")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId",value = "用户ID"),@ApiImplicitParam(name = "betId",value = "盘口ID"),@ApiImplicitParam(name = "option",value = "竞猜ID")}
    )
    @RequiresPermissions("businessback:betorderList")
    public CommonResult<IPage<BetOrderBean>> orderList(Long betId, Long userId, Long option, Page page, BetDetailOrderBy betDetailOrderBy, OrderByComm orderByComm) {
        return ResultUtil.returnSuccess(businessOldService.getBetOrderPage(betId, userId,option, page,betDetailOrderBy,orderByComm));
    }

    @GetMapping("/data/bets/subOrderList")
    @ApiOperation(value = "盘口明细串单详情", notes = "")
    public CommonResult<List<BetOrderDetailBean>> subList(Long betOrderId) {
        return ResultUtil.returnSuccess(businessOldService.getBetDetail(betOrderId));
    }

    @GetMapping("/data/bets/list")
    @ApiOperation(value = "盘口明细列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status",value = "盘口结算状态 checked:已结算,default:未结算,canceled:已取消"),
            @ApiImplicitParam(name = "betOrderBy",value = "排序字段"),
            @ApiImplicitParam(name = "orderByComm",value = "排序顺序"),
            @ApiImplicitParam(name = "hasorder",value = "全部盘口:0 有订单盘口:1")
    })
    @RequiresPermissions("businessback:betorderList")
    public CommonResult<IPage<BetResult>> list(Integer gameTypeId, DateDto dateDto, String status, IPage<BetResult> page, BetOrderBy betOrderBy, OrderByComm orderByComm, String hasOrder) {
//    public CommonResult<IPage<BetResult>> list(Integer gameTypeId, DateDto dateDto, String status, IPage<BetResult> page) {
        return ResultUtil.returnSuccess(businessOldService.getBetResult(gameTypeId, dateDto, status, page, betOrderBy,orderByComm,hasOrder));
//        return ResultUtil.returnSuccess(dataService.getBetResult(gameTypeId, dateDto, status, page));
    }

    @GetMapping("/data/bets/gameType")
    @ApiOperation(value = "游戏类别", notes = "")
    public CommonResult<List<GameTypeBean>> gameTypes() {
        return ResultUtil.returnSuccess(businessOldService.gameTypes());
    }

    @GetMapping("/data/user/list")
    @ApiOperation(value = "用户明细列表", notes = "")
    @ApiImplicitParam(name = "apiid",value = "商户ID")
//    @RequiresPermissions("businessback:userlist")
    public CommonResult<IPage<UserBean>> userList(String userId, IPage<UserBean> page, String apiid) {
        return ResultUtil.returnSuccess(businessOldService.getUserList(userId,page,apiid));
    }

    @GetMapping("/data/user/orderList")
    @ApiOperation(value = "用户订单列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userOrderOrderBy",value = "排序字段"),
            @ApiImplicitParam(name = "orderByComm",value = "排序顺序")
    })
//    @RequiresPermissions("businessback:userorderList")
    public CommonResult<IPage<BetOrderBean>> userOrderList(Long userId, Integer gameTypeId, DateDto dateDto, IPage page, UserOrderOrderBy userOrderOrderBy, OrderByComm orderByComm) {
        return ResultUtil.returnSuccess(businessOldService.getUserOrderList(userId, gameTypeId, dateDto, page,userOrderOrderBy,orderByComm));
    }

    @GetMapping("/data/order/list")
    @ApiOperation(value = "下注明细列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderOrderBy",value = "排序字段"),
            @ApiImplicitParam(name = "orderByComm",value = "排序顺序"),
            @ApiImplicitParam(name = "apiid",value = "商户ID")
            //  @ApiImplicitParam(name = "screenForBetOrderDto",value = "筛选字段"),
    })
//    @RequiresPermissions("businessback:orderlist")
    public CommonResult<IPage<BetOrderBean>> orderList(String userId, Page page, ScreenForBetOrderDto screenForBetOrderDto, String apiid, OrderOrderBy orderOrderBy, OrderByComm orderByComm) {
        return ResultUtil.returnSuccess(businessOldService.getOrderPage(userId, page,screenForBetOrderDto,apiid,orderOrderBy,orderByComm));
    }

}
