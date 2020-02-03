package com.splan.xbet.admin.back.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
@ApiModel
public class OperateResult implements Serializable {
    @ApiModelProperty(value = "操作时间")
    private Date operateTime;
    @ApiModelProperty(value = "操作人")
    private String operateName;
    @ApiModelProperty(value = "被操作人ID")
    private Long tenantuserno;
    @ApiModelProperty(value = "审核结果：通过/拒绝")
    private String operateresult;
    @ApiModelProperty(value = "审核类型：提款/取款")
    private String accessType;
    @ApiModelProperty(value = "审核金额")
    private BigDecimal amount;
}
