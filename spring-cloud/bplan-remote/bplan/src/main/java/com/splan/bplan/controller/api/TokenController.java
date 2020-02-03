package com.splan.bplan.controller.api;


import com.splan.bplan.annotation.Authorization;
import com.splan.bplan.annotation.CurrentUser;
import com.splan.base.bean.UserBean;
import com.splan.base.enums.ResultStatus;
import com.splan.base.enums.Status;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.http.ResultModel;
import com.splan.bplan.mappers.UserMapper;
import com.splan.bplan.service.TokenManager;
import com.splan.bplan.token.TokenModel;
import com.splan.bplan.utils.PasswordEncoder;
import com.splan.bplan.utils.ResultUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;

@RestController
@RequestMapping("/api/tokens")
@Api(value="用户登陆",tags={"用户操作接口"})
public class TokenController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenManager tokenManager;


    /*@RequestMapping(value = "login",method = RequestMethod.POST)
    public CommonResult<TokenModel> login(@RequestParam(value="mobileArea", defaultValue="86") String mobileArea,@RequestParam String username, @RequestParam String password){

        Assert.notNull (username, "username can not be empty");
        Assert.notNull (password, "password can not be empty");

        UserBean user = userMapper.findByMobile (mobileArea,username);
        if (user == null ) { // 密码错误
            // 提示用户不存在
            return ResultUtil.returnError(ResultStatus.USER_NOT_FOUND);
        }
        if (user.getStatus().equals(Status.DISABLE)){
            //账户禁用
            return ResultUtil.returnError(ResultStatus.USER_DISABLE);
        }

        if (user.getStatus().equals(Status.LOCK)){
            long startDate = user.getLastLoginTime().getTime();
            long nowDate = (new Date()).getTime();
            if ((nowDate-startDate)>1*60*60*1000){
                user.setLoginCount(0);
                user.setStatus(Status.ENABLE);
                //解锁
            }else {
                //账户禁用
                return ResultUtil.returnError(ResultStatus.USER_LOCK);
            }

        }

        PasswordEncoder passwordEncoder = new PasswordEncoder(user.getSalt(),"SHA");
        String encode = passwordEncoder.encode(password);
        //boolean isPasswordValid = passwordEncoder.isPasswordValid(encode,user.getPassword());
        if (!encode.equalsIgnoreCase(user.getPassword())){
            user.setLoginCount(user.getLoginCount()+1);
            if (user.getLoginCount()>5){
                user.setStatus(Status.LOCK);
            }
            user.setLastLoginTime(new Date());
            userMapper.updateById(user);
            return ResultUtil.returnError(ResultStatus.USERNAME_OR_PASSWORD_ERROR);
        }

        user.setLoginCount(0);
        user.setLastLoginTime(new Date());
        userMapper.updateById(user);


        // 生成一个 token，保存用户登录状态
        TokenModel model = tokenManager.createToken (user.getId ());
        return ResultUtil.returnSuccess(model);
    }*/

    @RequestMapping (value = "logout",method = RequestMethod.DELETE)
    @Authorization
    public CommonResult<String> logout (@CurrentUser @ApiIgnore UserBean user) {
        tokenManager.deleteToken (user.getId ());
        return ResultUtil.returnSuccess("");
    }

    @RequestMapping (value = "userlogout",method = RequestMethod.GET)
    public CommonResult<String> exlogout (String token) {
        TokenModel tokenModel = tokenManager.getToken(token);
        tokenManager.deleteToken (tokenModel.getUserId());
        return ResultUtil.returnSuccess("");
    }

    /**
     * @RequestMapping (value = " userlogout ", method = RequestMethod.GET)
     *     public CommonResult<String> exlogout (String loginName) {
     *         UserBean userBean = userMapper.findByUsername2(loginName);
     *         if (userBean!=null){
     *             tokenManager.deleteToken (userBean.getId());
     *         }
     *         //TokenModel tokenModel = tokenManager.getToken(token);
     *
     *         return ResultUtil.returnSuccess("");
     *     }
     */
}
