package com.splan.base.bean.back;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.splan.base.bean.BaseBean;
import com.splan.base.enums.CheckStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "back_product_order")
@ApiModel
public class BackProductOrder extends BaseBean {

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    protected Date createTime;

    @TableField("service_id")
    @ApiModelProperty("服务ID")
    private String serviceId;

    @TableField("business_name")
    @ApiModelProperty("商户名")
    private String businessName;

    @TableField("phone")
    @ApiModelProperty("手机号")
    private String phone;

    @TableField("apptype")
    @ApiModelProperty("应用类型")
    private String apptype;

    @TableField("datatype")
    @ApiModelProperty("数据类型")
    private String datatype;

    @TableField("game")
    private String game;

    @TableField("service_name")
    @ApiModelProperty("服务名")
    private String serviceName;

    @TableField("service_starttime")
    @ApiModelProperty("服务开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date serviceStartTime;

    @TableField("service_endtime")
    @ApiModelProperty("服务结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date serviceEndTime;

    @TableField("status")
    @ApiModelProperty("状态")
    private CheckStatus status;

}
