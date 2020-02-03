package com.splan.bplan.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.splan.base.enums.ResultStatus;
import com.splan.bplan.http.CommonResult;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ResultUtil {

    /**
     * return success
     *
     * @param data
     * @return
     */
    public static <T> CommonResult<T> returnSuccess(T data) {
        CommonResult<T> result = new CommonResult();
        result.setCode(ResultStatus.SUCCESS.getCode());
        result.setSuccess(true);
        result.setData(data);
        result.setMessage(ResultStatus.SUCCESS.getMessage());
        return result;
    }

    /**
     * return error
     *
     * @param code error code
     * @param msg  error message
     * @return
     */
    public static CommonResult returnError(String code, String msg) {
        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setData("");
        result.setMessage(msg);
        return result;

    }

    /**
     * use enum
     *
     * @param status
     * @return
     */
    public static CommonResult returnError(ResultStatus status) {
        return returnError(status.getCode(), status.getMessage());
    }


    public static void sendJsonMessage(HttpServletResponse response, Object obj) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat));
        writer.close();
        response.flushBuffer();
    }


}
