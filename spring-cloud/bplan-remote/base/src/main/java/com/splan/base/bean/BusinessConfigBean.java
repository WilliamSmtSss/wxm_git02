package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "business_config")
@Data
@ApiModel
public class BusinessConfigBean extends BaseBean implements Serializable {
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField(value = "pid")
    @ApiModelProperty(value = "上级商户ID")
    private Integer pid;

    @TableField(value = "api_id")
    @ApiModelProperty(value = "商户名称")
    private String apiId;

    @TableField(value = "sys_user_id")
    @ApiModelProperty(value = "关联账号ID")
    private Integer sysUserId;

    @TableField(exist = false,value = "sys_user_name")
    @ApiModelProperty(value = "关联账号")
    private String sysUserName;

    @TableField(value = "product_type")
    @ApiModelProperty(value = "产品形态")
    private String productType;

    @TableField(value = "product_skin")
    @ApiModelProperty(value = "产品皮肤")
    private Integer productSkin;

    @TableField(exist = false)
    @ApiModelProperty(value = "产品皮肤名称")
    private String productSkinName;

    @TableField(value = "currency")
    @ApiModelProperty(value = "商户币种")
    private Integer currency;

    @TableField(exist = false,value = "currency_name")
    @ApiModelProperty(value = "商户币种名称")
    private String currencyName;

    @TableField(value = "coefficient")
    @ApiModelProperty(value = "赔率影响")
    private Integer coefficient;

    @TableField(exist = false)
    @ApiModelProperty(value = "赔率影响名称")
    private String coefficientName;

    @TableField(value = "wallet_type")
    @ApiModelProperty(value = "钱包类型")
    private Integer walletType;

    @TableField(exist = false)
    @ApiModelProperty(value = "钱包类型名称")
    private String walletTypeName;

    @TableField(value = "logo")
    @ApiModelProperty(value = "LOGO")
    private String logo;

    @TableField(value = "status")
    @ApiModelProperty(value = "状态")
    private String status;

    @TableField(value = "language")
    @ApiModelProperty(value = "语言")
    private String language;

    @TableField(exist = false)
    @ApiModelProperty(value = "语言名称")
    private String languageName;

    @ApiModelProperty(value = "下级商户数")
    @TableField(exist = false)
    private Integer downCount;

    @ApiModelProperty(value = "商户秘钥")
    @TableField(exist = false)
    private String clientSecret;

    @ApiModelProperty(value = "白名单")
    @TableField(exist = false)
    private String ipWhitelist;

    @TableField(exist = false)
    private boolean operationStatus=true;

//  v2
    @TableField(value = "header")
    @ApiModelProperty(value = "页头展示")
    private Boolean header;

    @TableField(value = "odd_type")
    private String oddType;

    @TableField(value = "odd_type",exist = false)
    private String oddTypeName;

    @Override
    public String toString() {
        return "BusinessConfigBean{" +
                "createTime=" + createTime +
                ", pid=" + pid +
                ", apiId='" + apiId + '\'' +
                ", sysUserId=" + sysUserId +
                ", sysUserName='" + sysUserName + '\'' +
                ", productType='" + productType + '\'' +
                ", productSkin=" + productSkin +
                ", productSkinName='" + productSkinName + '\'' +
                ", currency=" + currency +
                ", currencyName='" + currencyName + '\'' +
                ", coefficient=" + coefficient +
                ", coefficientName='" + coefficientName + '\'' +
                ", walletType=" + walletType +
                ", walletTypeName='" + walletTypeName + '\'' +
                ", logo='" + logo + '\'' +
                ", status='" + status + '\'' +
                ", language='" + language + '\'' +
                ", downCount=" + downCount +
                ", clientSecret='" + clientSecret + '\'' +
                '}';
    }
}
