package com.splan.base.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class BusinessFormsResult implements Serializable {

    @ApiModelProperty(value = "月份")
    private String time;

    @ApiModelProperty(value = "商户ID")
    private String businessId;

    @ApiModelProperty(value = "商户名称")
    private String businessName;

    @ApiModelProperty(value = "币种")
    private String currency;

    @ApiModelProperty(value = "总投注额")
    private String orderTotal;

    @ApiModelProperty(value = "总派奖")
    private String orderReturn;

    @ApiModelProperty(value = "总收入")
    private String orderProfit;

    @ApiModelProperty(value = "订单数")
    private String orderCount;

    @ApiModelProperty(value = "活跃用户")
    private String activeUserCount;

    @ApiModelProperty(value = "下单用户")
    private String orderUserCount;

    @ApiModelProperty(value = "新增用户")
    private String addUserCount;

    @ApiModelProperty(value = "新增下单用户")
    private String addOrderUserCount;

    @ApiModelProperty(value = "可操作状态")
    private boolean operationStatus=true;
}
