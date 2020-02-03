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
import java.util.List;

@TableName(value = "back_notice")
@Data
@ApiModel
public class BackNotice extends BaseBean {

    @TableField(value = "title")
    @ApiModelProperty(value = "标题")
    private String title;

    @TableField(value = "type")
    @ApiModelProperty(value = "公告类型")
    private String type;

    @TableField(value = "product")
    @ApiModelProperty(value = "产品")
    private String product;

    @TableField(value = "datatype")
    @ApiModelProperty(value = "数据类型")
    private String datatype;

    @TableField(value = "name")
    @ApiModelProperty(value = "服务名称")
    private String name;

    @TableField(value = "read")
    @ApiModelProperty(value = "读")
    private Integer read;

    @TableField(value = "total")
    @ApiModelProperty(value = "总")
    private Integer total;

    @TableField(value = "status")
    @ApiModelProperty(value = "状态")
    private String status;

    @TableField(value = "zd_id")
    @ApiModelProperty(value = "置顶标识")
    private Integer zdId;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "发布时间")
    protected Date createTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "公告类型 显示")
    private String typeName;

    @TableField(exist = false)
    @ApiModelProperty(value = "状态 显示")
    private String statusName;

    @TableField(exist = false)
    @ApiModelProperty(value = "产品 显示")
    private String productName;

    //参数
    @TableField(exist = false)
    private List<String> statuss;

}
