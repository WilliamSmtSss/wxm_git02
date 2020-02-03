package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * proxy_config
 */
@Data
@TableName(value = "proxy_config")
public class ProxyConfig extends BaseBean implements Serializable {
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
    @TableField(value = "encode")
    private Integer encode;

    /**
     * 
     */
    @TableField(value = "type")
    private String type;

    /**
     * 
     */
    @TableField(value = "status")
    private String status;

    @TableField(value = "redirect_uri")
    private String redirectUri;

    @TableField(value = "has_extra")
    private Integer hasExtra;

    @TableField(value = "push_config")
    private Integer pushConfig;

    @TableField(exist=false)
    private String clientSecret;

    @TableField(value = "virtual_client_id")
    private String virtualClientId;

}