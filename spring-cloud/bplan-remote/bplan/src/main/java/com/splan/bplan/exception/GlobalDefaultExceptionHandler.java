package com.splan.bplan.exception;

import com.splan.base.enums.ResultStatus;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.utils.ResultUtil;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public CommonResult<ResultStatus> defaultExceptionHandler(HttpServletRequest req, Exception e){
        return ResultUtil.returnError(ResultStatus.NOTPERMISSION);
    }

}
