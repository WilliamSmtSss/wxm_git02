package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "business_currency_record")
@ApiModel
public class BusinessCurrencyRecordBean extends BaseBean implements Serializable {

    @TableField(value = "currency_id")
    @ApiModelProperty(value = "币种ID")
    private Integer currencyId;

    @TableField(value = "currency_code")
    @ApiModelProperty(value = "币种简称")
    private String currencyCode;

    @TableField(value = "currency_name")
    @ApiModelProperty(value = "币种名称")
    private String currencyName;

    @TableField(value = "before_cny")
    @ApiModelProperty(value = "更新前")
    private BigDecimal beforeCny;

    @TableField(value = "after_cny")
    @ApiModelProperty(value = "更新后")
    private BigDecimal afterCny;

    @TableField(value = "change_time")
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date changeTime;

    @TableField(value = "operation_name")
    @ApiModelProperty(value = "操作人")
    private String operationName;

}
