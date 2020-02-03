package com.splan.xbet.admin.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.base.bean.UserBean;

import java.util.List;

public interface IUserService extends IService<UserBean> {


    /**
     * 注册
     * @param user
     * @return
     */
//    CommonResult<UserResult> register(UserDto user);
//
////    CommonResult<TokenModel> loginregister(UserCodeDto user);
//
//    CommonResult<UserResult> getUserInfo(UserBean user);
//
//    CommonResult<String> modifyMobile(UserBean user);
//
////    CommonResult<String> modifyPassword(UserBean user, PasswordDto password);
//
////    CommonResult<String> modifyPassword(PasswordDto password);
//
//
//    /**--back---**/
//    JSONObject getRegisterInfo(JSONObject jsonObject);
//
//    List<Map<String, Object>> getDayRegisterInfo(JSONObject jsonObject);
//
////    CommonResult<IPage<UserAccountResult>> selectAll(JSONObject jsonObject, Page page, Level... levels);
//
//    CommonResult<IPage<UserAccountResult>> selectMoneyAll(JSONObject jsonObject, Page page);
//
//    CommonResult<UserResult> getMemberInfo(UserBean user);
//
//    CommonResult<String> levelUp(UserBean user);

    List<Long> getUserIds(String... apiid);

////    CommonResult<UserResult> appUserlogin(AppUserDto appUserDto);
////
////    CommonResult<Integer> updateUser(UserBean userBean, Level... levels);
//
//    CommonResult<IPage<UserBean>> listBlackUsers(Page page);
//
//    CommonResult<Integer> relieveBlack(JSONObject jsonObject);
//
//    List<Integer> getUserIdsByApiId(String apiId);
//
//    CommonResult<String> setPassword(UserBean user, String password);
//
//    CommonResult<UserBean> createUser(UserDto user);
//
////    CommonResult<InviteRewardResultOut> inviteRewardList(@CurrentUser @ApiIgnore UserBean user, Page page);
////
////    CommonResult<RewardInviteResult> getInviteReward(UserBean user);
}
