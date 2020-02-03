package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * common_mobile_area
 */
@Data
@TableName(value = "common_mobile_area")
public class CommonMobileAreaBean extends BaseBean {
    /**
     * 
     */
    @TableField(value = "area_name")
    private String areaName;

    /**
     * 
     */
    @TableField(value = "area_code")
    private String areaCode;

    /**
     *
     */
    @TableField(value = "area_order")
    private Integer areaOrder;
}