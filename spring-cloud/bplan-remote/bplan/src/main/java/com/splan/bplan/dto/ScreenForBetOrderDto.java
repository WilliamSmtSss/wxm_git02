package com.splan.bplan.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class ScreenForBetOrderDto implements Serializable {
    @ApiModelProperty(name = "username", value = "用户名")
    private String username;
    @ApiModelProperty(name = "gametype",value = "游戏类型ID")
    private Integer gametype;
    @ApiModelProperty(name = "orderstatus",value = "订单状态 已结算：SETTLED，未结算：UNSETTLED，失败：FAIL")
    private String orderstatus;
    @ApiModelProperty(name = "orderresult",value = "订单结果 赢：WIN，输：LOSE，未开始：NOTOPEN")
    private String orderresult;
    @ApiModelProperty(name = "ordreid",value = "订单号")
    private String ordreid;
    @ApiModelProperty(name = "ordertype",value = "订单类型 单注：0，串单：1")
    private Integer ordertype;
}
