package com.splan.base.result.backremote;

import com.splan.base.bean.back.BackProductInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class BackProductInfoResult extends BackProductInfo implements Serializable {

//    private String dataType;
//
//    private String appType;
//
//    private String describe;
//
//    private String status;
      @ApiModelProperty(value = "开通状态")
      private Boolean openStatus;
      @ApiModelProperty(value = "剩余时长")
      private Integer leftTime;

}
