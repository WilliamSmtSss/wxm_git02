package com.splan.bplan.exception;

import com.splan.base.enums.ResultStatus;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.utils.ResultUtil;

public class CommonJsonException extends RuntimeException{

    private CommonResult resultJson;

    /**
     * 调用时可以在任何代码处直接throws这个Exception,
     * 都会统一被拦截,并封装好json返回给前台
     *
     * @param errorEnum 以错误的ErrorEnum做参数
     */
    public CommonJsonException(ResultStatus errorEnum) {
        this.resultJson = ResultUtil.returnError(errorEnum);
    }

    public CommonJsonException(CommonResult resultJson) {
        this.resultJson = resultJson;
    }

    public CommonResult getResultJson() {
        return resultJson;
    }
}
