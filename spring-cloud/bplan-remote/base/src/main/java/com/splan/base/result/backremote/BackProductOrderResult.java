package com.splan.base.result.backremote;

import com.splan.base.bean.back.BackProductOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class BackProductOrderResult extends BackProductOrder implements Serializable {

    @ApiModelProperty("开通后状态")
    private String passStatus;

}
