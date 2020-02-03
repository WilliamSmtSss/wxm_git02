package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName(value = "oauth_client_details")
@Data
public class OauthClientDetailsBean implements Serializable {

    @TableField(value = "client_id")
    private String clientId;

    @TableField(value = "client_secret")
    private String clientSecret;

    @TableField(value = "ip_whitelist")
    private String ipWhitelist;

}
