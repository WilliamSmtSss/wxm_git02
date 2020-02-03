package com.splan.bplan.http.sms;

import lombok.Data;

@Data
public class SmsVariableResponse {

    /**
     * 响应时间
     */
    private String time;
    /**
     * 消息id
     */
    private String msgId;
    /**
     * 状态码说明（成功返回空）
     */
    private String errorMsg;
    /**
     * 失败的个数
     */
    private String failNum;
    /**
     * 成功的个数
     */
    private String successNum;
    /**
     * 状态码（详细参考提交响应状态码）
     */
    private String code;
}
