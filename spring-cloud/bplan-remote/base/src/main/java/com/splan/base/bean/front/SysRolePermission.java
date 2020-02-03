package com.splan.base.bean.front;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.splan.base.bean.BaseBean;

/**
 * sys_role_permission
 */
@TableName(value = "front_sys_role_permission")
public class SysRolePermission extends BaseBean {
    /**
     * 角色id
     */
    @TableField(value = "role_id")
    private Integer roleId;

    /**
     * 权限id
     */
    @TableField(value = "permission_id")
    private Integer permissionId;

    /**
     * 是否有效 1有效     2无效
     */
    @TableField(value = "delete_status")
    private String deleteStatus;

    /**
     * 角色id
     * @return role_id 角色id
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 角色id
     * @param roleId 角色id
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 权限id
     * @return permission_id 权限id
     */
    public Integer getPermissionId() {
        return permissionId;
    }

    /**
     * 权限id
     * @param permissionId 权限id
     */
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * 是否有效 1有效     2无效
     * @return delete_status 是否有效 1有效     2无效
     */
    public String getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * 是否有效 1有效     2无效
     * @param deleteStatus 是否有效 1有效     2无效
     */
    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus == null ? null : deleteStatus.trim();
    }
}