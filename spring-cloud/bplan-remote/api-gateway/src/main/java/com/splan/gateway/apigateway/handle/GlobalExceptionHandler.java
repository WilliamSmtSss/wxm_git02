package com.splan.gateway.apigateway.handle;

import com.splan.base.enums.ResultStatus;
import com.splan.base.http.CommonResult;
import com.splan.base.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder 绑定器
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * GlobalException.
     *
     * @param e Exception
     * @return Response
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResult<String> exceptionHandle(Exception e){
        log.error(e.getClass()+"=="+e.getMessage());
        return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
    }

    /**
     * MethodArgumentNotValidException
     *
     * @param e MethodArgumentNotValidException
     * @return Response
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult<String> handleBodyValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        log.error("参数校验异常,ex = {}", fieldErrors.get(0).getDefaultMessage());
        return ResultUtil.returnError(ResultStatus.METHOD_NOT_FOUND);
    }

    /**
     * BadCredentialsException.
     *
     * @param e IllegalArgumentException
     * @return Response
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleArgumentException(BadCredentialsException e) {
        log.error("凭证错误异常 ex={}", e.getMessage(), e);
        return Response.failed(ErrorCode.BAD_CREDENTIALS.getCode(), e.getMessage());
    }*/

}
