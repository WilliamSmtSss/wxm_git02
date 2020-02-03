package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * common_bank
 */
@Data
@TableName(value = "common_bank")
public class CommonBankBean extends BaseBean {
    /**
     * 
     */
    @TableField(value = "bank_name")
    private String bankName;

    /**
     * 
     */
    @TableField(value = "status")
    private String status;

    /**
     * 
     */
    @TableField(value = "bank_code")
    private String bankCode;

    @TableField(value = "bank_icon")
    private String bankIcon;
}