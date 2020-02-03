package com.splan.xbet.admin.back.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@ApiModel
@Data
public class InBusinessConfigDto implements Serializable {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "上级商户ID")
    private Integer pid;

    @ApiModelProperty(value = "商户名称")
    @NotEmpty(message = "商户名称不能为空")
    private String apiId;

    @ApiModelProperty(value = "关联账号ID")
    @NotNull(message = "关联账号不能为空")
    private Integer sysUserId;

    @ApiModelProperty(value = "产品类型")
    @NotEmpty
    private String productType;

    @ApiModelProperty(value = "产品皮肤")
    @NotNull
    private Integer productSkin;

    @ApiModelProperty(value = "币种")
    @NotNull
    private Integer currency;

    @ApiModelProperty(value = "影响系数")
    @NotNull
    private Integer coefficient;

    @ApiModelProperty(value = "钱包类型")
    @NotNull
    private Integer walletType;

    @ApiModelProperty(value = "logo")
//    @NotEmpty
    private String logo;

    @ApiModelProperty(value = "激活状态")
    @NotEmpty
    private String status;

    @ApiModelProperty(value = "语言")
    @NotEmpty
    private String language;

    //rSet
    @ApiModelProperty(value = "商户秘钥")
//    @NotEmpty
    @Pattern(regexp = "([a-zA-Z]{1}[a-zA-Z0-9]{15,31})|([\\s]{0})")
    private String clientSecret;

    @ApiModelProperty(value = "白名单")
    @Pattern(regexp = "[0-9\\.，,]*")
    private String ipWhitelist;

}
