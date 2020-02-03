package com.splan.bplan.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.base.bean.UserBean;
import com.splan.base.enums.Level;
import com.splan.bplan.dto.*;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.result.BalanceResult;
import com.splan.bplan.result.MemberResult;
import com.splan.bplan.result.UserAccountResult;
import com.splan.bplan.result.UserResult;

import java.util.List;
import java.util.Map;

public interface IUserService extends IService<UserBean> {

    UserBean findByUsername(String username);

    BalanceResult findByApiIdAndExtraId(String apiId,String extraId);


    /**
     * 注册
     * @param user
     * @return
     */
    CommonResult<UserResult> register(UserDto user);


    CommonResult<UserResult> getUserInfo(UserBean user);

    CommonResult<String> modifyMobile(UserBean user);

    CommonResult<String> modifyPassword(UserBean user, PasswordDto password);

    CommonResult<String> modifyPassword(PasswordDto password);


    /**--back---**/
    JSONObject getRegisterInfo(JSONObject jsonObject);

    List<Map<String, Object>> getDayRegisterInfo(JSONObject jsonObject);

    CommonResult<IPage<UserAccountResult>> selectAll(JSONObject jsonObject, Page page, Level... levels);

    CommonResult<IPage<UserAccountResult>> selectMoneyAll(JSONObject jsonObject, Page page);

    CommonResult<MemberResult> getMemberInfo(UserBean user);

    CommonResult<String> levelUp(UserBean user);

    //List<Long> getUserIds(String... apiid);

    CommonResult<UserResult> appUserlogin(AppUserDto appUserDto);

    CommonResult<UserResult> apiUserLogin(ApiUserDto apiUserDto);

    CommonResult<Integer> updateUser(UserBean userBean,Level... levels);

    CommonResult<IPage<UserBean>> listBlackUsers(Page page);

    CommonResult<Integer> relieveBlack(JSONObject jsonObject);

    CommonResult<String> transfer(TransferDto transferDto);
}
