package com.splan.base.bean.back;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.splan.base.bean.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
@TableName(value = "back_message")
public class BackMessage extends BaseBean {

    @TableField(value = "msg_id")
    private Integer msgId;

    @TableField(value = "api_id")
    @ApiModelProperty(value = "商户名称")
    private String apiId;

    @TableField(value = "msg_type")
    @ApiModelProperty(value = "消息类型 传参")
    private String msgType;

    @TableField(value = "status")
    @ApiModelProperty(value = "消息状态 0未处理 1已处理")
    private Integer status;

    @TableField(exist = false)
    @ApiModelProperty(value = "消息类型 显示")
    private String msgTypeName;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @ApiModelProperty(value = "时间")
    protected Date createTime;

}
