package com.splan.base.bean.front;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.splan.base.bean.BaseBean;
import com.splan.base.enums.CheckStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "front_company_info")
@Data
@ApiModel
public class FrontCompanyInfo extends BaseBean {

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date createTime;

    @TableField(value = "sys_id")
    private Integer sysId;

    @TableField(value = "name")
    @ApiModelProperty(value = "公司名称")
    private String name;

    @TableField(value = "address")
    @ApiModelProperty(value = "公司地址")
    private String address;

    @TableField(value = "card")
    @ApiModelProperty(value = "纳税人识别号")
    private String card;

    @TableField(value = "image_url")
    @ApiModelProperty(value = "营业执照")
    private String imageUrl;

    @TableField(value = "representative")
    @ApiModelProperty(value = "法定代表人")
    private String representative;

    @TableField(value = "website")
    @ApiModelProperty(value = "公司网站")
    private String website;

    @TableField(value = "phone")
    @ApiModelProperty(value = "公司电话")
    private String phone;

    @TableField(value = "status")
    @ApiModelProperty(value = "状态")
    private boolean status;

    @TableField(value = "email")
    @ApiModelProperty(value = "公司邮箱")
    private String email;

    @TableField(value = "check_status")
    @ApiModelProperty(value = "认证状态")
    private CheckStatus checkStatus;


}
