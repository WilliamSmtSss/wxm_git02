package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * sys_permission
 */
@TableName(value = "sys_permission")
@Data
public class SysPermission  {
    @TableId(value = "id")
    protected Integer id;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonIgnore
    protected Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    protected Date updateTime;
    /**
     * 归属菜单,前端判断并展示菜单使用,
     */
    @TableField(value = "menu_code")
    private String menuCode;

    /**
     * 菜单的中文释义
     */
    @TableField(value = "menu_name")
    private String menuName;

    /**
     * 权限的代码/通配符,对应代码中@RequiresPermissions 的value
     */
    @TableField(value = "permission_code")
    private String permissionCode;

    /**
     * 本权限的中文释义
     */
    @TableField(value = "permission_name")
    private String permissionName;

    /**
     * 是否本菜单必选权限, 1.必选 2非必选 通常是"列表"权限是必选
     */
    @TableField(value = "required_permission")
    private Boolean requiredPermission;

    /**
     * 归属菜单,前端判断并展示菜单使用,
     * @return menu_code 归属菜单,前端判断并展示菜单使用,
     */
    public String getMenuCode() {
        return menuCode;
    }

    /**
     * 归属菜单,前端判断并展示菜单使用,
     * @param menuCode 归属菜单,前端判断并展示菜单使用,
     */
    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode == null ? null : menuCode.trim();
    }

    /**
     * 菜单的中文释义
     * @return menu_name 菜单的中文释义
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * 菜单的中文释义
     * @param menuName 菜单的中文释义
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    /**
     * 权限的代码/通配符,对应代码中@RequiresPermissions 的value
     * @return permission_code 权限的代码/通配符,对应代码中@RequiresPermissions 的value
     */
    public String getPermissionCode() {
        return permissionCode;
    }

    /**
     * 权限的代码/通配符,对应代码中@RequiresPermissions 的value
     * @param permissionCode 权限的代码/通配符,对应代码中@RequiresPermissions 的value
     */
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode == null ? null : permissionCode.trim();
    }

    /**
     * 本权限的中文释义
     * @return permission_name 本权限的中文释义
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * 本权限的中文释义
     * @param permissionName 本权限的中文释义
     */
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }

    /**
     * 是否本菜单必选权限, 1.必选 2非必选 通常是"列表"权限是必选
     * @return required_permission 是否本菜单必选权限, 1.必选 2非必选 通常是"列表"权限是必选
     */
    public Boolean getRequiredPermission() {
        return requiredPermission;
    }

    /**
     * 是否本菜单必选权限, 1.必选 2非必选 通常是"列表"权限是必选
     * @param requiredPermission 是否本菜单必选权限, 1.必选 2非必选 通常是"列表"权限是必选
     */
    public void setRequiredPermission(Boolean requiredPermission) {
        this.requiredPermission = requiredPermission;
    }

    @TableField(exist = false)
    private Long uid;

    @TableField(exist = false)
    private String uname;

    @TableField(exist = false)
    private String unickname;

    @TableField(exist = false)
    private String rolename;

    @TableField(exist = false)
    private Long rid;
}