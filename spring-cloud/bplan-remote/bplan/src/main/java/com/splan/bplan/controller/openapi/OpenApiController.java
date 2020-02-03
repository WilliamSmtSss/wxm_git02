package com.splan.bplan.controller.openapi;

import com.splan.bplan.dto.AppUserDto;
import com.splan.base.enums.ResultStatus;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.result.UserResult;
import com.splan.bplan.service.IUserService;
import com.splan.bplan.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Version;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/v1/user")
public class OpenApiController {

    @Autowired
    private IUserService userService;

    @GetMapping("/test")
    public CommonResult<String> getTest(HttpServletRequest request){
        System.out.println(request.getHeader("App_id"));
        return ResultUtil.returnSuccess("11111");
    }


    @PostMapping("/login")
    @Version
    public CommonResult<UserResult> login(HttpServletRequest request, AppUserDto appUserDto){
        String appId = request.getHeader("App_id");
        if (StringUtils.isBlank(appId)){
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }
        appUserDto.setApiId(appId);
        log.info(appUserDto.toString());
        CommonResult<UserResult> userResultCommonResult = userService.appUserlogin(appUserDto);
        System.out.println(request.getHeader("App_id"));
        return userResultCommonResult;
    }


}
