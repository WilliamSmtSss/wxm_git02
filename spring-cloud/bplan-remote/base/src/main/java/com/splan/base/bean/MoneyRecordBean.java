package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.splan.base.annotation.ExcelAnnotation;
import com.splan.base.enums.AlgorithmType;
import com.splan.base.enums.MoneyAbleType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * money_record
 */
@TableName(value = "money_record")
@Data
public class MoneyRecordBean extends BaseLongBean {

    @TableId(value = "id",type = IdType.AUTO)
    @ExcelAnnotation(id=1,name={"流水ID"},width = 5000)
    @ApiModelProperty(value = "流水ID")
    protected Long id;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelAnnotation(id=1,name={"日期"},width = 5000)
    @ApiModelProperty(value = "日期")
    protected Date createTime;
    /**
     * 
     */
    @TableField(value = "balance_id")
    private Long balanceId;

    /**
     * 
     */
    @TableField(value = "moneyable_id")
    private Long moneyableId;

    /**
     * 资金类型
     */
    @TableField(value = "moneyable_type")
    @ExcelAnnotation(id=1,name={"流水类型"},width = 5000)
//    @ApiModelProperty(value = "流水类型")
    private MoneyAbleType moneyableType;

    /**
     * 明细类别
     */
    @TableField(value = "kind")
    private Integer kind;

    /**
     * 变动金额
     */
    @TableField(value = "amount")
    @ExcelAnnotation(id=1,name={"流水"},width = 5000)
    @ApiModelProperty(value = "流水")
    private BigDecimal amount;

    /**
     * 变动之前金额
     */
    @TableField(value = "money_from")
    @ExcelAnnotation(id=1,name={"历史余额"},width = 5000)
    @ApiModelProperty(value = "历史余额")
    private BigDecimal moneyFrom;

    /**
     * add,sub
     */
    @TableField(value = "algorithm")
    private AlgorithmType algorithm;

    /**
     * 变动之后金额
     */
    @TableField(value = "money_to")
    private BigDecimal moneyTo;

    /**
     * 操作人id
     */
    @TableField(value = "modify_id")
    private Long modifyId;

    /**
     * 
     */
    @TableField(value = "detail")
    private String detail;

    //xBet
    @TableField(exist = false)
    private BusinessConfigBean businessConfigBean;

    @TableField(exist = false)
    private BusinessConfigBean businessConfigBeanUp;

    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"商户名"},width = 5000)
    @ApiModelProperty(value = "商户名")
    private String businessConfigBeanName="";

    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"上级商户名"},width = 5000)
    @ApiModelProperty(value = "上级商户名")
    private String businessConfigBeanUpName="";

    @TableField(exist = false,value = "modify_name")
    @ExcelAnnotation(id=1,name={"用户名"},width = 5000)
    @ApiModelProperty(value = "用户名")
    private String modifyName="";

    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"商户名用户名"},width = 5000)
    @ApiModelProperty(value = "商户名用户名")
    private String businessConfigBeanAdminName="";

    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"流水类型"},width = 5000)
    @ApiModelProperty(value = "流水类型")
    private String moneyableTypeName;

    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"币种"},width = 5000)
    @ApiModelProperty(value = "币种")
    private String currencyName;

    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"钱包名称"},width = 5000)
    @ApiModelProperty(value = "钱包名称")
    private String walletTypeName;

}