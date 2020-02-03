package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * sys_param
 */
@TableName(value = "sys_param")
public class SysParamBean extends BaseBean {
    /**
     * 
     */
    @TableField(value = "param_name")
    private String paramName;

    /**
     * 
     */
    @TableField(value = "param_value")
    private String paramValue;

    /**
     * 
     */
    @TableField(value = "comment")
    private String comment;

    /**
     * 
     * @return param_name 
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * 
     * @param paramName 
     */
    public void setParamName(String paramName) {
        this.paramName = paramName == null ? null : paramName.trim();
    }

    /**
     * 
     * @return param_value 
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * 
     * @param paramValue 
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue == null ? null : paramValue.trim();
    }

    /**
     * 
     * @return comment 
     */
    public String getComment() {
        return comment;
    }

    /**
     * 
     * @param comment 
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}