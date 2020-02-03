package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.splan.base.enums.Status;
import lombok.Data;

/**
 * common_pay_param
 */
@Data
@TableName(value = "common_pay_param")
public class CommonPayParamBean extends BaseBean {
    /**
     * 
     */
    @TableField(value = "type")
    private String type;

    /**
     * 
     */
    @TableField(value = "api_id")
    private String apiId;

    /**
     * 
     */
    @TableField(value = "api_key")
    private String apiKey;

    /**
     * 
     */
    @TableField(value = "api_url")
    private String apiUrl;

    /**
     * 
     */
    @TableField(value = "callback_url")
    private String callbackUrl;

    /**
     * 
     */
    @TableField(value = "hrefback_url")
    private String hrefbackUrl;

    /**
     * 
     */
    @TableField(value = "comment")
    private String comment;

    /**
     * 
     */
    @TableField(value = "status")
    private Status status;


}