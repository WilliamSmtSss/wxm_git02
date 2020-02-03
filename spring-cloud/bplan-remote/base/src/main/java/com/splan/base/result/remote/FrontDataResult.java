package com.splan.base.result.remote;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel
@Data
public class FrontDataResult implements Serializable {

    @ApiModelProperty(value = "日期")
    private String time;

    @ApiModelProperty(value = "活跃用户")
    private Integer activeUserCount;

    @ApiModelProperty(value = "下注用户")
    private Integer orderUserCount;

    @ApiModelProperty(value = "总注单数")
    private Integer orderCount;

    @ApiModelProperty(value = "总下注额")
    private BigDecimal orderAmount;

    @ApiModelProperty(value = "盈利率")
    private BigDecimal profitRate;

}
