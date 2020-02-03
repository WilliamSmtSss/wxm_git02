package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * proxy_log
 */
@Data
@TableName(value = "proxy_log")
public class ProxyLog extends BaseLongBean implements Serializable {

    /**
     * 
     */
    @TableField(value = "client_id")
    private String clientId;

    /**
     * 
     */
    @TableField(value = "url")
    private String url;

    /**
     * 
     */
    @TableField(value = "type")
    private String type;

    /**
     * 
     */
    @TableField(value = "param")
    private String param;

    /**
     * 
     */
    @TableField(value = "retries")
    private Integer retries;

    /**
     * 
     */
    @TableField(value = "response")
    private String response;

    @TableField(value = "status")
    private String status;


}