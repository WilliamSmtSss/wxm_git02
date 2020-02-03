package com.splan.bplan.http.sms;

import lombok.Data;

@Data
    public class SmsVariableRequest {

    /**
     * 创蓝API账号，必填
     */
    private String account;
    /**
     * 创蓝API密码，必填
     */
    private String password;
    /**
     * 短信内容。长度不能超过536个字符，必填
     */
    private String msg;
    /**
     * 手机号码和变量参数，多组参数使用英文分号;区分，必填
     */
    private String params;

    /**
     * 定时发送短信时间。格式为yyyyMMddHHmm，值小于或等于当前时间则立即发送，默认立即发送，选填
     */
    private String sendtime;
    /**
     * 是否需要状态报告（默认false），选填
     */
    private String report;
    /**
     * 下发短信号码扩展码，纯数字，建议1-3位，选填
     */
    private String extend;
    /**
     * 该条短信在您业务系统内的ID，如订单号或者短信发送记录流水号，选填
     */
    private String uid;

    public SmsVariableRequest() {

    }
    public SmsVariableRequest(String account, String password, String msg, String params) {
        super();
        this.account = account;
        this.password = password;
        this.msg = msg;
        this.params = params;
    }
    public SmsVariableRequest(String account, String password, String msg, String params, String report) {
        super();
        this.account = account;
        this.password = password;
        this.msg = msg;
        this.params = params;
        this.report = report;
    }
}
