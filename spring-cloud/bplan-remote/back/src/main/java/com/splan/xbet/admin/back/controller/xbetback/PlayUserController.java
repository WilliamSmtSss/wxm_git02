package com.splan.xbet.admin.back.controller.xbetback;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.MoneyRecordBean;
import com.splan.base.bean.SysUser;
import com.splan.base.bean.UserBean;
import com.splan.base.enums.ResultStatus;
import com.splan.base.http.CommonResult;
import com.splan.xbet.admin.back.annotation.CurrentSysUser;
import com.splan.xbet.admin.back.enums.BusRole;
import com.splan.xbet.admin.back.service.XBetPlayerUserService;
import com.splan.xbet.admin.back.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/back/xBet/playUser")
@Api(tags={"xBet后台:会员管理"})
public class PlayUserController {

    @Autowired
    private XBetPlayerUserService xbetPlayerUserService;

    @GetMapping("/list")
    @ApiOperation(value = "会员列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bigBusiness", value = "上级商户名"),
            @ApiImplicitParam(name = "smallBusiness", value = "下级商户名"),
            @ApiImplicitParam(name = "searchId", value = "搜索ID"),
            @ApiImplicitParam(name = "export", value = "导出标记"),
    })
    @RequiresPermissions(value = "userManage:list")
    public CommonResult<IPage<UserBean>> list(@CurrentSysUser @ApiIgnore SysUser sysUser, Page page, String bigBusiness, String smallBusiness, String searchId, String export){
        if(null!=sysUser.getApiid()){
//            if(sysUser.getRoleId()== BusRole.BUS.geteName()){
//                bigBusiness=sysUser.getApiid();
//            }else if(sysUser.getRoleId()== BusRole.BUS_DOWN.geteName()){
//                smallBusiness=sysUser.getApiid();
//            }
            if(sysUser.getRoleId()== BusRole.BUS_DOWN.geteName()){
                smallBusiness=sysUser.getApiid();
            }
            else if(sysUser.getRoleId()== BusRole.BUS.geteName()){
                if(StringUtils.isBlank(smallBusiness))
                    smallBusiness=sysUser.getApiid();
            }
        }
        if(StringUtils.isBlank(export))
            return xbetPlayerUserService.list(page,bigBusiness,smallBusiness,searchId,export);
        else {
            xbetPlayerUserService.listExport(page, bigBusiness, smallBusiness, searchId, export);
            return ResultUtil.returnSuccess(null,ResultStatus.EXPORT_SUCCESS);
        }

    }

    @GetMapping("/moneyRecord")
    @ApiOperation(value = "会员流水", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bigBusiness", value = "上级商户名"),
            @ApiImplicitParam(name = "smallBusiness", value = "下级商户名"),
            @ApiImplicitParam(name = "searchId", value = "搜索ID"),
            @ApiImplicitParam(name = "orderId", value = "流水ID"),
            @ApiImplicitParam(name = "export", value = "导出标记"),
    })
    @RequiresPermissions(value = "userManage:money")
    public CommonResult<IPage<MoneyRecordBean>> moneyRecord(@CurrentSysUser @ApiIgnore SysUser sysUser, Page page, String bigBusiness, String smallBusiness, String searchId, String orderId, String export){
        if(null!=sysUser.getApiid()){
            if(sysUser.getRoleId()== BusRole.BUS_DOWN.geteName()){
                smallBusiness=sysUser.getApiid();
            }
            else if(sysUser.getRoleId()== BusRole.BUS.geteName()){
                if(StringUtils.isBlank(smallBusiness))
                    smallBusiness=sysUser.getApiid();
            }
        }
        if(StringUtils.isBlank(export))
            return xbetPlayerUserService.moneyRecord(page,bigBusiness,smallBusiness,searchId,orderId,export);
        else {
            xbetPlayerUserService.moneyRecordExport(page, bigBusiness, smallBusiness, searchId, orderId, export);
            return ResultUtil.returnSuccess(null,ResultStatus.EXPORT_SUCCESS);
        }

    }

}
