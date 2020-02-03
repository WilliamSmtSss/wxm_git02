package com.splan.xbet.admin.back.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class InviteRewardResult implements Serializable {
    @ApiModelProperty(value = "注册完成状态")
    private Boolean registerStatus=false;

    @ApiModelProperty(value = "首投完成状态")
    private Boolean firstOrderStatus=false;

    @ApiModelProperty(value = "首充完成状态")
    private Boolean firstRechargeStatus=false;

    @ApiModelProperty(value = "累计投注金额")
    private Integer totalOrder;

    @ApiModelProperty(value = "已到账")
    private Integer waitRewardIn;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "时间")
    private Date createTime;
}
