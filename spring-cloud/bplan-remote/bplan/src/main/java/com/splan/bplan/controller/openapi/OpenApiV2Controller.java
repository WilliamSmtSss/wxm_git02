package com.splan.bplan.controller.openapi;

import com.splan.base.enums.ResultStatus;
import com.splan.bplan.dto.ApiUserDto;
import com.splan.bplan.dto.BalanceDto;
import com.splan.bplan.dto.TransferDto;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.result.BalanceResult;
import com.splan.bplan.result.UserResult;
import com.splan.bplan.service.IUserService;
import com.splan.bplan.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Version;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/v2/user")
public class OpenApiV2Controller {

    @Autowired
    private IUserService userService;

    @GetMapping("/check")
    public CommonResult<String> check(HttpServletRequest request){
        //System.out.println(request.getHeader("App_id"));
        log.info(request.getHeader("App_id"));
        return ResultUtil.returnSuccess("ok");
    }


    @PostMapping(value = "/login",consumes = "application/json")
    @Version
    public CommonResult<UserResult> login(HttpServletRequest request,@RequestBody ApiUserDto apiUserDto){
        String appId = request.getHeader("App_id");
        if (StringUtils.isBlank(appId)){
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }
        apiUserDto.setApiId(appId);
        log.info(apiUserDto.toString());
        CommonResult<UserResult> userResultCommonResult = userService.apiUserLogin(apiUserDto);
        System.out.println(request.getHeader("App_id"));
        return userResultCommonResult;
    }

    @PostMapping(value = "/transfer",consumes = "application/json")
    @Version
    public CommonResult<String> transfer(HttpServletRequest request, @RequestBody TransferDto transferDto){
        String appId = request.getHeader("App_id");
        if (StringUtils.isBlank(appId)){
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }
        transferDto.setApiId(appId);
        log.info(transferDto.toString());
        CommonResult<String> transfer = userService.transfer(transferDto);
        return transfer;
    }

    @PostMapping(value = "/getBalance",consumes = "application/json")
    @Version
    public CommonResult<BalanceResult> getBalance(HttpServletRequest request, @RequestBody BalanceDto balanceDto){
        String appId = request.getHeader("App_id");
        if (StringUtils.isBlank(appId)){
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }
        balanceDto.setApiId(appId);
        BalanceResult balanceResult = userService.findByApiIdAndExtraId(balanceDto.getApiId(),balanceDto.getExtraId());
        /*String appId = request.getHeader("App_id");
        if (StringUtils.isBlank(appId)){
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }
        transferDto.setApiId(appId);
        log.info(transferDto.toString());
        CommonResult<String> transfer = userService.transfer(transferDto);*/
        return ResultUtil.returnSuccess(balanceResult);
    }


}
