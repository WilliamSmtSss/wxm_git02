package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.splan.base.enums.ActivityType;
import com.splan.base.enums.Status;
import com.splan.base.enums.TriggerType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * gift_list
 */
@Data
@TableName(value = "gift_list")
public class GiftListBean extends BaseBean {
    /**
     * 
     */
    @TableField(value = "name")
    private String name;

    /**
     * 可完成次数
     */
    @TableField(value = "activity_type")
    private ActivityType activityType;

    /**
     * 增加活跃值
     */
    @TableField(value = "activity_value")
    private BigDecimal activityValue;

    /**
     * 
     */
    @TableField(value = "status")
    private Status status;

    /**
     * 任务完成要求
     */
    @TableField(value = "limit_value")
    private Integer limitValue;

    /**
     * BACK,FRONT
     */
    @TableField(value = "trigger_type")
    private TriggerType triggerType;

    /**
     * 
     */
    @TableField(value = "gift_value")
    private BigDecimal giftValue;
}