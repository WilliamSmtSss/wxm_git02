package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.splan.base.enums.LoginType;
import lombok.Data;

import java.io.Serializable;

/**
 * user_login_log
 */
@Data
@TableName(value = "user_login_log")
public class UserLoginLogBean extends BaseLongBean implements Serializable {


    /**
     * 
     */
    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "ip")
    private String ip;

    /**
     * 
     */
    @TableField(value = "type")
    private LoginType type;

    /**
     * 
     */
    @TableField(value = "channel")
    private String channel;

    public UserLoginLogBean(){

    }

    public UserLoginLogBean(Long userId,LoginType type,String ip,String channel){
        this.ip = ip;
        this.userId = userId;
        this.type = type;
        this.channel = channel;
    }


}