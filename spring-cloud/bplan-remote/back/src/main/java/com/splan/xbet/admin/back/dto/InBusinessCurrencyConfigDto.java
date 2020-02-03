package com.splan.xbet.admin.back.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel
public class InBusinessCurrencyConfigDto implements Serializable {

    private Integer id;

    @ApiModelProperty(value = "currency")
    @NotEmpty
    private Integer currency;

    @ApiModelProperty(value = "code")
    @NotEmpty
    private String code;

    @ApiModelProperty(value = "name")
    @NotEmpty
    private String name;

    @ApiModelProperty(value = "to_cny")
    @NotEmpty
    private BigDecimal toCny;
}
