package com.splan.base.bean.back;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.splan.base.bean.BaseBean;
import com.splan.base.enums.CheckStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "back_product_info")
public class BackProductInfo extends BaseBean {

    @TableField(value = "service_name")
    @ApiModelProperty(value = "服务名")
    private String serviceName;

    @TableField(value = "data_type")
    @ApiModelProperty(value = "数据类型")
    private String dataType;

    @TableField(value = "service_type")
    @ApiModelProperty(value = "服务类型")
    private String serviceType;

    @TableField(value = "app_type")
    @ApiModelProperty(value = "应用类型")
    private String appType;

    @TableField(value = "service_describe")
    @ApiModelProperty(value = "服务描述")
    private String serviceDescribe;

    @TableField(value = "status")
    @ApiModelProperty(value = "激活状态")
    private boolean status;

    @TableField(exist = false,value = "check_status")
    @ApiModelProperty(value = "开通状态")
    private CheckStatus checkStatus;

    @TableField(exist = false,value = "service_start")
    @ApiModelProperty(value = "服务开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date serviceStart;

    @TableField(exist = false,value = "service_end")
    @ApiModelProperty(value = "服务结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date serviceEnd;

}
