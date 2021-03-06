package com.splan.xbet.frontback.frontback.controller;

import com.alibaba.fastjson.JSONObject;
import com.splan.base.http.CommonResult;
import com.splan.base.utils.ResultUtil;
import com.splan.xbet.frontback.frontback.param.CheckCodeParam;
import com.splan.xbet.frontback.frontback.param.GetCodeParam;
import com.splan.xbet.frontback.frontback.param.RegisterParam;
import com.splan.xbet.frontback.frontback.param.ResetPasswordParam;
import com.splan.xbet.frontback.frontback.service.LoginService;
import com.splan.xbet.frontback.frontback.utils.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frontBack/login")
@Api(tags={"登录注册模块"})
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     */
    @PostMapping("/auth")
    @ApiOperation(value="管理员登陆",notes="")
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

}
