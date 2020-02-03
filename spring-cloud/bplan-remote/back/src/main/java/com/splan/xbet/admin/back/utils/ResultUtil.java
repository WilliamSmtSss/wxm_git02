package com.splan.xbet.admin.back.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.splan.base.enums.ResultStatus;
import com.splan.base.http.CommonResult;
import com.splan.base.result.CommonOperatorResult;

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

    public static <T> CommonResult<T> returnSuccess(T data, CommonOperatorResult commonOperatorResult) {
        CommonResult<T> result = new CommonResult();
        result.setCode(ResultStatus.SUCCESS.getCode());
        result.setSuccess(true);
        result.setData(data);
        result.setMessage(ResultStatus.SUCCESS.getMessage());
        result.setCommonOperatorResult(commonOperatorResult);
        return result;
    }

    /**
     * return success
     *
     * @param data
     * @return
     */
    public static <T> CommonResult<T> returnSuccess(T data,ResultStatus resultStatus) {
        CommonResult<T> result = new CommonResult();
        result.setCode(resultStatus.getCode());
        result.setSuccess(true);
        result.setData(data);
        result.setMessage(resultStatus.getMessage());
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

    public static CommonResult returnError(String data) {
        CommonResult result = new CommonResult();
        result.setCode(ResultStatus.OPERATIONFAIL.getCode());
        result.setMessage(data);
        return result;
    }

    public static void sendJsonMessage(HttpServletResponse response, Object obj) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat));
        writer.close();
        response.flushBuffer();
    }

    public static void sendHtmlMessage(HttpServletResponse response, Object obj) throws Exception {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print("<script>alert('"+obj+"')</script>");
        writer.close();
        response.flushBuffer();
    }

}
