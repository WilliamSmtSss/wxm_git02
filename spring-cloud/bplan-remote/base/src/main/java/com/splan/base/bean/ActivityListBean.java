package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.splan.base.enums.ActivityType;
import com.splan.base.enums.Status;
import com.splan.base.enums.TriggerType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * activity_list
 */
@Data
@TableName(value = "activity_list")
public class ActivityListBean extends BaseBean implements Serializable {
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
    @JsonIgnore
    @TableField(value = "status")
    private Status status;

    /**
     * 任务完成要求
     */
    @TableField(value = "limit_value")
    private Integer limitValue;

    @TableField(exist = false)
    private boolean flag;

    /*@TableField(exist = false)
    private long signCount;

    @TableField(exist = false)
    private long rank;*/

    @JsonIgnore
    @TableField(value = "trigger_type")
    private TriggerType triggerType;


}