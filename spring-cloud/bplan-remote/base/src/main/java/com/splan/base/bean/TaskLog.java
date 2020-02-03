package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.splan.base.enums.TaskStatus;
import com.splan.base.enums.TaskType;
import lombok.Data;

/**
 * task_log
 */
@Data
@TableName(value = "task_log")
public class TaskLog extends BaseBean {
    /**
     * 
     */
    @TableField(value = "task_type")
    private TaskType taskType;

    /**
     * 
     */
    @TableField(value = "detail")
    private String detail;

    /**
     * 
     */
    @TableField(value = "status")
    private TaskStatus status;

    /**
     * 影响行数
     */
    @TableField(value = "task_count")
    private Integer taskCount;


}