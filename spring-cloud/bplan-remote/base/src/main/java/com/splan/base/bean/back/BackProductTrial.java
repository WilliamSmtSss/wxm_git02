package com.splan.base.bean.back;

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

import java.util.Date;

@Data
@TableName(value = "back_product_trial")
@ApiModel
public class BackProductTrial extends BaseBean {

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date createTime;

    @TableField("sys_id")
    private Integer sysId;

    @TableField("business_name")
    private String businessName;

    @TableField("phone")
    private String phone;

    @TableField("trial_admin")
    @ApiModelProperty(value = "试用账号")
    private String trialAdmin;

    @TableField("trial_password")
    @ApiModelProperty(value = "试用密码")
    private String trialPassword;

    @TableField("status")
    @ApiModelProperty(value = "审核状态")
    private CheckStatus status;

}
