package com.splan.xbet.back.backforbusiness.controller;

import com.splan.base.bean.front.SysUser;
import com.splan.base.param.backremote.BackProductOrderParam;
import com.splan.base.service.v2.V2BackService;
import com.splan.base.http.CommonResult;
import com.splan.base.param.frontremote.ParamConfigParam;
import com.splan.xbet.back.backforbusiness.annotation.CurrentSysUser;
import com.splan.xbet.back.backforbusiness.param.AuthenticationEnterpriseParam;
import com.splan.xbet.back.backforbusiness.service.OperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@RestController
@RequestMapping("/front/operation")
@Api(tags={"界面操作模块"})
public class OperationController {

    @Autowired
    private OperationService operationService;

    @Autowired
    private V2BackService v2BackService;

    //服务管理
        //开通服务
    @PostMapping("/openService")
    @ApiOperation(value = "开通服务 需同步")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serviceId",value = "服务ID 逗号分隔"),
            @ApiImplicitParam(name = "phone",value = "手机号"),
            @ApiImplicitParam(name = "businessName",value = "商户名"),
    })
    public CommonResult openService(@ApiIgnore @CurrentSysUser SysUser sysUser, @RequestBody BackProductOrderParam backProductOrderParam){
        backProductOrderParam.setPhone(sysUser.getUsername());
        backProductOrderParam.setBusinessName(sysUser.getApiid());
        return operationService.openService(backProductOrderParam);
    }
        //参数配置
    @PostMapping("/paramConfig")
    @ApiOperation(value = "参数配置 需同步")
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "businessName",value = "商户名"),
            @ApiImplicitParam(name = "clientSecret",value = "公钥"),
            @ApiImplicitParam(name = "ipWhitelist",value = "白名单 逗号分隔"),
    })
    public CommonResult paramConfig(@ApiIgnore @CurrentSysUser SysUser sysUser,@RequestBody ParamConfigParam paramConfigParam){
        paramConfigParam.setBusinessName(sysUser.getApiid());
        paramConfigParam.setSysId(sysUser.getId());
        return operationService.paramConfig(paramConfigParam);
    }


        //新增企业认证
    @PostMapping("/authenticationEnterprise")
    @ApiOperation(value = "提交企业认证 状态为unable时可提交")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "公司名称"),
            @ApiImplicitParam(name = "address",value = "公司地址"),
            @ApiImplicitParam(name = "card",value = "纳税人识别号"),
            @ApiImplicitParam(name = "imageUrl",value = "营业执照"),
            @ApiImplicitParam(name = "representative",value = "法定代表人"),
            @ApiImplicitParam(name = "phone",value = "公司电话"),
            @ApiImplicitParam(name = "website",value = "公司网站"),
    })
    public CommonResult authenticationEnterprise(@ApiIgnore @CurrentSysUser SysUser sysUser, @RequestBody AuthenticationEnterpriseParam authenticationEnterpriseParam){
        return operationService.authenticationEnterprise(sysUser,authenticationEnterpriseParam);
    }

        //编辑企业
    @PostMapping("/editEnterprise")
    @ApiOperation(value = "编辑企业 状态为uncheck时可编辑")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "公司ID"),
            @ApiImplicitParam(name = "name",value = "公司名称"),
            @ApiImplicitParam(name = "address",value = "公司地址"),
            @ApiImplicitParam(name = "card",value = "纳税人识别号"),
            @ApiImplicitParam(name = "imageUrl",value = "营业执照"),
            @ApiImplicitParam(name = "representative",value = "法定代表人"),
            @ApiImplicitParam(name = "phone",value = "公司电话"),
            @ApiImplicitParam(name = "website",value = "公司网站"),
    })
    public CommonResult editEnterprise(@RequestBody AuthenticationEnterpriseParam authenticationEnterpriseParam){
        return operationService.editEnterprise(authenticationEnterpriseParam);
    }

    //服务试用
    @PostMapping("/serviceTrialAdd")
    @ApiOperation(value = "申请试用")
    public CommonResult serviceTrialAdd(@ApiIgnore @CurrentSysUser SysUser sysUser){
        return operationService.serviceTrialAdd(sysUser);
    }

    @PostMapping("/editAccountInfo")
    @ApiOperation(value = "账号信息:修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickName", value = "昵称"),
    })
    public CommonResult<SysUser> editAccountInfo(@ApiIgnore @CurrentSysUser SysUser sysUser, @RequestBody Map<String,Object> requestParam){
        return operationService.editAccountInfo(sysUser,requestParam.get("nickName").toString());
    }

}
