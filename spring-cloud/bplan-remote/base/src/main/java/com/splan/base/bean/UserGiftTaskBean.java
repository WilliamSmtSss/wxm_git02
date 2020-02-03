package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.splan.base.enums.GiftStatus;
import lombok.Data;

import java.math.BigDecimal;

/**
 * user_gift_task
 */
@Data
@TableName(value = "user_gift_task")
public class UserGiftTaskBean extends BaseLongBean {

    /**
     * 
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 
     */
    @TableField(value = "gift_id")
    private Integer giftId;

    /**
     * 
     */
    @TableField(value = "task_status")
    private GiftStatus giftStatus;

    /**
     * 
     */
    @TableField(value = "task_value")
    private BigDecimal taskValue;
}