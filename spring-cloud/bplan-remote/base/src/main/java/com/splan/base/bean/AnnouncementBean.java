package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.splan.base.enums.NoticeAction;
import com.splan.base.enums.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * announcement
 * 公告
 */
@Data
@TableName(value = "announcement")
public class AnnouncementBean implements Serializable {
    /**
     *
     */
    @TableField(value = "action")
    private NoticeAction action;

    /**
     *
     */
    @TableField(value = "content")
    private String content;

    /**
     *
     */
    @TableField(value = "title")
    private String title;

    /**
     *
     */
    @TableField(value = "url")
    private String url;

    /**
     * 公告失效时间
     */
    @TableField(value = "deadline_time")
    @JsonIgnore
    private Date deadlineTime;

    /**
     *
     */
    @TableField(value = "status")
    @JsonIgnore
    private Status status;

    /**
     * 阅读状态 0未读 1已读
     */
    @TableField(value = "is_read")
    private Integer isRead;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 商户id
     */
    @TableField(value = "api_id")
//    @JsonIgnore
    private String apiId;


    @TableField(value = "reference_id")
    @JsonIgnore
    private Long referenceId;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd EEEE", locale = "zh", timezone = "GMT+8")
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    private Date updateTime;

    @TableField(exist = false)
    private String message;

    @TableField(value = "order_flag")
    private Integer orderFlag;

    @TableField(value = "create_name")
    private String createName;

    @TableField(value = "p_api_id")
    @ApiModelProperty(value = "上级商户")
    private String pApiId;

}