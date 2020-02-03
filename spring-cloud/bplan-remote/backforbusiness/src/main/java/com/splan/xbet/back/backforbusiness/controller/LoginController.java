package com.splan.xbet.back.backforbusiness.controller;

import com.alibaba.fastjson.JSONObject;
import com.splan.base.http.CommonResult;
import com.splan.base.utils.ResultUtil;
import com.splan.xbet.back.backforbusiness.param.CheckCodeParam;
import com.splan.xbet.back.backforbusiness.param.GetCodeParam;
import com.splan.xbet.back.backforbusiness.param.RegisterParam;
import com.splan.xbet.back.backforbusiness.param.ResetPasswordParam;
import com.splan.xbet.back.backforbusiness.service.LoginService;
import com.splan.xbet.back.backforbusiness.utils.CommonUtil;
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

@RestController
@RequestMapping("/front/login")
@Api(tags={"登录注册模块"})
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     */
    @PostMapping("/auth")
    @ApiOperation(value="管理员登陆",notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "username",name = "用户名"),
            @ApiImplicitParam(value = "password",name = "密码")
    })
    public CommonResult<JSONObject> authLogin(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "username,password");
        return loginService.authLogin(requestJson);
    }

    /**
     * 查询当前登录用户的信息
     */
    @PostMapping("/getInfo")
    public CommonResult<JSONObject> getInfo() {
        return ResultUtil.returnSuccess(loginService.getInfo());
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    public CommonResult<String> logout() {
        return ResultUtil.returnSuccess(loginService.logout());
    }

    /**
     * 注册
     * @return
     */
    @PostMapping("/register")
    @ApiOperation(value = "注册")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "phone",name = "手机号 2选1"),
            @ApiImplicitParam(value = "email",name = "邮箱 2选1"),
            @ApiImplicitParam(value = "password",name = "密码"),
            @ApiImplicitParam(value = "confirmPassword",name = "确认密码"),
            @ApiImplicitParam(value = "code",name = "验证码"),
            @ApiImplicitParam(value = "bindingEmail",name = "绑定邮箱"),
            @ApiImplicitParam(value = "companyName",name = "公司名"),
    })
    public CommonResult register(@RequestBody @Validated RegisterParam registerParam){
        return loginService.register(registerParam);
    }

    /**
     * 获取验证码
     *
     */
    @PostMapping("/getCode")
    @ApiOperation(value = "获取验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "phone",name = "手机号 2选1"),
            @ApiImplicitParam(value = "email",name = "邮箱 2选1"),
    })
    public CommonResult getCode(@RequestBody @Validated GetCodeParam getCodeParam){
        return loginService.getCode(getCodeParam);
    }

    /**
     * 验证验证码
     * @param checkCodeParam
     * @return
     */
    @PostMapping("/checkCode")
    @ApiOperation(value = "验证验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "phone",name = "手机号 2选1"),
            @ApiImplicitParam(value = "email",name = "邮箱 2选1"),
            @ApiImplicitParam(value = "code",name = "验证码")
    })
    public CommonResult checkCode(@RequestBody @Validated CheckCodeParam checkCodeParam){
        return loginService.checkCode(checkCodeParam);
    }

    /**
     * 重置密码
     * @param resetPasswordParam
     * @return
     */
    @PostMapping("/resetPassword")
    @ApiOperation(value = "重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "phone",name = "手机号 2选1"),
            @ApiImplicitParam(value = "email",name = "邮箱 2选1"),
            @ApiImplicitParam(value = "password",name = "密码"),
            @ApiImplicitParam(value = "confirmPassword",name = "确认密码"),
    })
    public CommonResult resetPassword(@RequestBody @Validated ResetPasswordParam resetPasswordParam){
        return loginService.resetPassword(resetPasswordParam);
    }

}
