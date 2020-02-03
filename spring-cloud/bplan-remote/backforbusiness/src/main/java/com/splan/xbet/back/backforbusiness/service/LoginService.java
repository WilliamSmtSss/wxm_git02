package com.splan.xbet.back.backforbusiness.service;

import com.alibaba.fastjson.JSONObject;
import com.splan.base.http.CommonResult;
import com.splan.xbet.back.backforbusiness.param.CheckCodeParam;
import com.splan.xbet.back.backforbusiness.param.GetCodeParam;
import com.splan.xbet.back.backforbusiness.param.RegisterParam;
import com.splan.xbet.back.backforbusiness.param.ResetPasswordParam;

public interface LoginService {

    /**
     * 登录表单提交
     */
    CommonResult<JSONObject> authLogin(JSONObject jsonObject);

    /**
     * 根据用户名和密码查询对应的用户
     *
     * @param username 用户名
     * @param password 密码
     */
    JSONObject getUser(String username, String password);

    /**
     * 查询当前登录用户的权限等信息
     */
    JSONObject getInfo();

    /**
     * 退出登录
     */
    String logout();

    CommonResult register(RegisterParam registerParam);

    CommonResult getCode(GetCodeParam getCodeParam);

    CommonResult checkCode(CheckCodeParam checkCodeParam);

    CommonResult resetPassword(ResetPasswordParam resetPasswordParam);

}
