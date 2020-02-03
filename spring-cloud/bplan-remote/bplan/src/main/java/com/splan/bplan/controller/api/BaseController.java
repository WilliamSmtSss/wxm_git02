package com.splan.bplan.controller.api;

import com.splan.base.enums.ResultStatus;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.utils.ResultUtil;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public abstract class BaseController {

    /**
     * validate params
     *
     * @param bindingResult
     * @return
     */
    protected CommonResult validParams(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            return processBindingError(fieldError);
        }
        return ResultUtil.returnSuccess("");
    }

    /**
     * 根据spring binding 错误信息自定义返回错误码和错误信息
     *
     * @param fieldError
     * @return
     */
    private CommonResult processBindingError(FieldError fieldError) {
        String code = fieldError.getCode();
        //LOGGER.debug("validator error code: {}", code);
        switch (code) {
            case "NotEmpty":
                return ResultUtil.returnError(ResultStatus.PARAM_EMPTY.getCode(), fieldError.getDefaultMessage());
            case "NotBlank":
                return ResultUtil.returnError(ResultStatus.PARAM_EMPTY.getCode(), fieldError.getDefaultMessage());
            case "NotNull":
                return ResultUtil.returnError(ResultStatus.PARAM_EMPTY.getCode(), fieldError.getDefaultMessage());
            case "Pattern":
                return ResultUtil.returnError(ResultStatus.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
            case "Min":
                return ResultUtil.returnError(ResultStatus.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
            case "Max":
                return ResultUtil.returnError(ResultStatus.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
            case "Length":
                return ResultUtil.returnError(ResultStatus.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
            case "Range":
                return ResultUtil.returnError(ResultStatus.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
            case "Email":
                return ResultUtil.returnError(ResultStatus.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
            case "DecimalMin":
                return ResultUtil.returnError(ResultStatus.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
            case "DecimalMax":
                return ResultUtil.returnError(ResultStatus.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
            case "Size":
                return ResultUtil.returnError(ResultStatus.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
            case "Digits":
                return ResultUtil.returnError(ResultStatus.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
            case "Past":
                return ResultUtil.returnError(ResultStatus.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
            case "Future":
                return ResultUtil.returnError(ResultStatus.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
            default:
                return ResultUtil.returnError(ResultStatus.UNKNOWN_ERROR);
        }
    }
}
