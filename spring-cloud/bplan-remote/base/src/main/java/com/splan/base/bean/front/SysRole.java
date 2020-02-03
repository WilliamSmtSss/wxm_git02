package com.splan.base.bean.front;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.splan.base.bean.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * sys_role
 */
@TableName(value = "front_sys_role")
@Data
@ApiModel
public class SysRole extends BaseBean {
    /**
     * 角色名
     */
    @TableField(value = "role_name")
    @ApiModelProperty(value = "角色")
    private String roleName;

    /**
     * 是否有效  1有效  2无效
     */
    @TableField(value = "delete_status")
    @ApiModelProperty(value = "状态")
    private String deleteStatus;

    @TableField(value = "api_id")
    private String apiid;
//    /**
//     * 角色名
//     * @return role_name 角色名
//     */
//    public String getRoleName() {
//        return roleName;
//    }
//
//    /**
//     * 角色名
//     * @param roleName 角色名
//     */
//    public void setRoleName(String roleName) {
//        this.roleName = roleName == null ? null : roleName.trim();
//    }
//
//    /**
//     * 是否有效  1有效  2无效
//     * @return delete_status 是否有效  1有效  2无效
//     */
//    public String getDeleteStatus() {
//        return deleteStatus;
//    }
//
//    /**
//     * 是否有效  1有效  2无效
//     * @param deleteStatus 是否有效  1有效  2无效
//     */
//    public void setDeleteStatus(String deleteStatus) {
//        this.deleteStatus = deleteStatus == null ? null : deleteStatus.trim();
//    }

    @TableField(value = "role_describe")
    @ApiModelProperty(value = "描述")
    private String roleDescribe;

    @TableField(value = "create_id")
    private Integer createId;

    @TableField(exist = false,value = "create_name")
    @ApiModelProperty(value = "创建人")
    private String createName;

    @TableField(value = "modify_id")
    private Integer modifyId;

    @TableField(exist = false,value = "modify_name")
    @ApiModelProperty(value = "修改人")
    private String modifyName;

    @TableField(value = "operation_status")
    @ApiModelProperty(value = "可否操作")
    private boolean operationStatus=true;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", locale = "zh", timezone = "GMT+8")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", locale = "zh", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}