package com.splan.xbet.frontback.frontback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BusinessConfigBean;
import com.splan.base.bean.front.FrontCompanyInfo;
import com.splan.base.bean.front.SysUser;
import com.splan.base.http.CommonResult;
import com.splan.base.service.v2.FrontService;
import com.splan.xbet.frontback.frontback.service.AccessMessageService;
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
@RequestMapping("/frontBack/AccessMessage")
@Api(tags={"账户管理"})
public class AccessMessageController {

    @Autowired
    private AccessMessageService accessMessageService;

    //商户信息管理
    @PostMapping("/checkCompanyInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "企业ID"),
            @ApiImplicitParam(name = "checkStatus",value = "审核状态"),
    })
    @ApiOperation("企业认证")
    public CommonResult checkCompanyInfo(@RequestBody Map<String,Object> requestParam){
        return accessMessageService.checkCompanyInfo(requestParam);
    }

    @PostMapping("/selCompanyInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchText",value = "商户名或手机"),
            @ApiImplicitParam(name = "checkStatus",value = "审核状态"),
            @ApiImplicitParam(name = "status",value = "状态"),
            @ApiImplicitParam(name = "current",value = "当前页"),
            @ApiImplicitParam(name = "size",value = "每页条数"),
    })
    @ApiOperation("企业认证 列表")
    public CommonResult<Page<FrontCompanyInfo>> selCompanyInfo(@RequestBody Map<String,Object> requestParam){
        return accessMessageService.selCompanyInfo(requestParam);
    }

    @PostMapping("/busInfoSel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchText",value = "商户名或手机"),
            @ApiImplicitParam(name = "checkStatus",value = "审核状态"),
            @ApiImplicitParam(name = "status",value = "状态"),
            @ApiImplicitParam(name = "current",value = "当前页"),
            @ApiImplicitParam(name = "size",value = "每页条数"),
    })
    @ApiOperation("商户信息 列表")
    public CommonResult<Page<SysUser>> busInfoSel(@RequestBody Map<String,Object> requestParam){
        return accessMessageService.busInfoSel(requestParam);
    }

    //商户参数配置
    @PostMapping("/busSel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchText",value = "商户名或手机"),
            @ApiImplicitParam(name = "current",value = "当前页"),
            @ApiImplicitParam(name = "size",value = "每页条数"),
    })
    @ApiOperation("商户参数配置 列表")
    public CommonResult<Page<BusinessConfigBean>> busSel(@RequestBody Map<String, Object> requestParam){
        return accessMessageService.busSel(requestParam);
    }

    @PostMapping("/busEdit")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ipWhitelist",value = "白名单"),
            @ApiImplicitParam(name = "language",value = "语言 接口"),
            @ApiImplicitParam(name = "currency",value = "币种 接口"),
            @ApiImplicitParam(name = "coefficient",value = "影响系数 0-100"),
            @ApiImplicitParam(name = "logo",value = "logo"),
            @ApiImplicitParam(name = "header",value = "页头展示 true：展示"),
            @ApiImplicitParam(name = "clientSecret",value = "密钥"),
            @ApiImplicitParam(name = "walletType",value = "钱包类型 接口"),
            @ApiImplicitParam(name = "status",value = "状态 1激活 0未激活"),
            @ApiImplicitParam(name = "oddType",value = "赔率类型 接口"),
    })
    @ApiOperation("商户参数配置 编辑")
    public CommonResult busEdit(@RequestBody Map<String, Object> requestParam){
        return accessMessageService.busEdit(requestParam);
    }

    @PostMapping("/busAdd")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchText",value = "商户名或手机"),
            @ApiImplicitParam(name = "current",value = "当前页"),
            @ApiImplicitParam(name = "size",value = "每页条数"),
    })
    public CommonResult busAdd(@RequestBody Map<String, Object> requestParam){
        return null;
    }

}
