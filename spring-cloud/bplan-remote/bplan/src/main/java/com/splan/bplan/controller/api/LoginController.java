package com.splan.bplan.controller.api;

import com.splan.base.bean.UserBean;
import com.splan.base.enums.ResultStatus;
import com.splan.base.enums.Status;
import com.splan.bplan.dto.UserDto;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.result.UserResult;
import com.splan.bplan.service.IUserService;
import com.splan.bplan.service.TokenManager;
import com.splan.bplan.token.TokenModel;
import com.splan.bplan.utils.IpUtil;
import com.splan.bplan.utils.PasswordEncoder;
import com.splan.bplan.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/users")
@Api(value="用户注册",tags={"用户操作接口"})
public class LoginController extends BaseController{

    @Autowired
    private IUserService userService;

    @Autowired
    private TokenManager tokenManager;

    /**
     * 注册接口
     * @return
     */
    @PostMapping("/register")
    @ApiOperation(value="用户注册接口",notes="")
    public CommonResult<UserResult> register(@Valid UserDto user, BindingResult bindingResult, HttpServletRequest request){
        CommonResult result = validParams(bindingResult);
        if (!result.isSuccess()){
            return result;
        }
        user.setRegisterIp(IpUtil.getIpAddr(request));
        return userService.register(user);
    }

    @PostMapping(value = "/login")
    @ApiOperation(value="用户登录接口",notes="")
    public CommonResult<TokenModel> login(@RequestParam String username, @RequestParam String password){

        Assert.notNull (username, "username can not be empty");
        Assert.notNull (password, "password can not be empty");

        UserBean user = userService.findByUsername (username);
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
            userService.updateById(user);
            return ResultUtil.returnError(ResultStatus.USERNAME_OR_PASSWORD_ERROR);
        }

        user.setLoginCount(0);
        user.setLastLoginTime(new Date());
        userService.updateById(user);


        // 生成一个 token，保存用户登录状态
        TokenModel model = tokenManager.createToken (user.getId ());
        return ResultUtil.returnSuccess(model);
    }


}
