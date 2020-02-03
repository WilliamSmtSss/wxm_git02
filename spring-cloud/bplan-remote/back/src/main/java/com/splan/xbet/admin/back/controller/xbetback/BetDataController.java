package com.splan.xbet.admin.back.controller.xbetback;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BetOrderBean;
import com.splan.base.bean.BetTopicsBean;
import com.splan.base.bean.SysUser;
import com.splan.base.enums.ResultStatus;
import com.splan.base.http.CommonResult;
import com.splan.xbet.admin.back.annotation.CurrentSysUser;
import com.splan.xbet.admin.back.annotation.Delay;
import com.splan.xbet.admin.back.dto.DateDto;
import com.splan.xbet.admin.back.enums.BusRole;
import com.splan.xbet.admin.back.service.XBetDataService;
import com.splan.xbet.admin.back.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/back/xBet/data")
@Api(tags={"xBet后台:数据"})
public class BetDataController {
    @Autowired
    private XBetDataService xbetDataService;

    @GetMapping(value = "/betData")
    @ApiOperation(value = "盘口数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gameId",value = "游戏ID"),
            @ApiImplicitParam(name = "dataId",value = "比赛ID"),
            @ApiImplicitParam(name = "betId",value = "盘口ID"),
            @ApiImplicitParam(name = "betStatus",value = "盘口状态"),
    })
    @RequiresPermissions(value = {"betData:default","betData:checked"},logical = Logical.OR)
    @Delay
    public CommonResult<IPage<BetTopicsBean>> betData(@CurrentSysUser @ApiIgnore SysUser sysUser, Page page, String bigBusiness, String smallBusiness, String gameId, DateDto dateDto, String dataId, String betId, String betStatus, String export){
        if(null!=sysUser.getApiid()){
            if(sysUser.getRoleId()== BusRole.BUS.geteName()){
                bigBusiness=sysUser.getApiid();
            }else if(sysUser.getRoleId()== BusRole.BUS_DOWN.geteName()){
                smallBusiness=sysUser.getApiid();
            }
        }
        if(StringUtils.isBlank(export))
            return xbetDataService.betData(page,bigBusiness,smallBusiness,gameId,dateDto,dataId,betId,betStatus,export);
        else{
            xbetDataService.betDataExport(page,bigBusiness,smallBusiness,gameId,dateDto,dataId,betId,betStatus,export);
            return ResultUtil.returnSuccess(null, ResultStatus.EXPORT_SUCCESS);
        }
    }

    @GetMapping(value = "/orderData")
    @ApiOperation(value = "订单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bigBusiness",value = "商户标识"),
            @ApiImplicitParam(name = "smallBusiness",value = "商户下线标识"),
            @ApiImplicitParam(name = "gameId",value = "游戏ID"),
            @ApiImplicitParam(name = "orderStatus",value = "订单状态"),
            @ApiImplicitParam(name = "searchId",value = "用户ID"),
            @ApiImplicitParam(name = "orderId",value = "订单ID"),
            @ApiImplicitParam(name = "betId",value = "盘口ID"),
            @ApiImplicitParam(name = "dataId",value = "比赛ID"),
            @ApiImplicitParam(name = "orderType",value = "订单类型 1 串 0 单"),
            @ApiImplicitParam(name = "export",value = "导出标识"),
    })
    @RequiresPermissions(value = {"orderData:single:default","orderData:single:checked","orderData:string"},logical = Logical.OR)
    @Delay
    public CommonResult<IPage<BetOrderBean>>  orderData(@CurrentSysUser @ApiIgnore SysUser sysUser, Page page, String bigBusiness, String smallBusiness, String gameId, DateDto dateDto, String orderStatus, String searchId, String orderId, String betId, String orderType, String export,String dataId){
        if(StringUtils.isNotBlank(sysUser.getApiid())){
            if(sysUser.getRoleId()== BusRole.BUS.geteName()){
                bigBusiness=sysUser.getApiid();
            }else if(sysUser.getRoleId()== BusRole.BUS_DOWN.geteName()){
                smallBusiness=sysUser.getApiid();
            }
        }
        if(StringUtils.isBlank(export))
            return xbetDataService.orderData(page,bigBusiness,smallBusiness,gameId,dateDto,orderStatus,searchId,orderId,betId,orderType,export,dataId);
        else {
             xbetDataService.orderDataExport(page, bigBusiness, smallBusiness, gameId, dateDto, orderStatus, searchId, orderId, betId, orderType, export, dataId);
             return ResultUtil.returnSuccess(null, ResultStatus.EXPORT_SUCCESS);
        }
    }

}
