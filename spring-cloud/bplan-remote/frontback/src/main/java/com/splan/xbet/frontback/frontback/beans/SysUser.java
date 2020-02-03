package com.splan.xbet.frontback.frontback.beans;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.splan.base.bean.BaseBean;
import com.splan.base.enums.front.RegisterType;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * back_sys_user
 */
@TableName(value = "back_sys_user")
@Data
public class SysUser extends BaseBean {
    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    @JsonIgnore
    private String password;

    /**
     * 昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    @JsonIgnore
    private Integer roleId;

    /**
     * 是否有效  1有效  2无效
     */
    @TableField(value = "delete_status")
    @JsonIgnore
    private String deleteStatus;

    @TableField(exist = false)
    @JsonIgnore
    private List<SysPermission> permissions;

    /**
     * 权限
     */
    @TableField(value = "jurisdiction")
    @JsonIgnore
    private String jurisdiction;

    /**
     * 来源账号
     */
    @TableField(value = "fromid")
    @JsonIgnore
    private Integer fromid;

    @TableField(exist = false,value = "from_name")
    @JsonIgnore
    private String fromName;

    /**
     * 来源渠道
     */
    @TableField(value = "api_id")
    @JsonIgnore
    private String apiid;

    /**
     * 角色名称
     */
    @TableField(value = "role_name",exist = false)
    @JsonIgnore
    private String rolename;

    @TableField(value = "modify_id")
    @JsonIgnore
    private Integer modifyId;

    @TableField(exist = false,value = "modify_name")
    @JsonIgnore
    private String modifyName;

    @TableField(value = "login_ip")
    private String loginIp;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @TableField(exist = false)
    @JsonIgnore
    private boolean isOnLine=false;

//    front
    @TableField(value = "email")
    private String email;

    @TableField(value = "register_type")
    private RegisterType registerType;

}