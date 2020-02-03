package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.splan.base.annotation.ExcelAnnotation;
import com.splan.base.enums.Level;
import com.splan.base.enums.RegisterChannel;
import com.splan.base.enums.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "user_account")
@ApiModel
public class UserBean implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("username")
    //@NotEmpty(message = "验证码不能为空")
    @ExcelAnnotation(id=1,name={"用户名"},width = 5000)
    @ApiModelProperty(value = "用户名")
    private String username="";


    @TableField("password")
    //@NotEmpty(message = "密码不能为空")
    @JsonIgnore
    private String password;

    @TableField("salt")
    @JsonIgnore
    private String salt;

    @TableField("mobile")
    @NotEmpty(message = "手机号不能为空")
    //@ExcelAnnotation(id=1,name={"手机号码"},width = 5000)
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @TableField("mobile_area")
    @NotEmpty(message = "手机区号不能为空")
    private String mobileArea;

    @TableField("invite_code")
    @ExcelAnnotation(id=1,name={"用户ID"},width = 5000)
//    @ApiModelProperty(value = "用户ID")
    private String inviteCode;

    @TableField("be_invite_code")
    private String beInviteCode;

    @TableField("qq")
    private String qq;

    @TableField("birthday")
    private Date birthday;

    @TableField("status")
    //@ExcelAnnotation(id=1,name={"用户状态"},width = 5000)
    private Status status;

    @TableField("real_name")
    //@ApiModelProperty(value = "真实姓名")
    //@ExcelAnnotation(id=1,name={"真实姓名"},width = 5000)
    private String realName;

    @TableField("last_login_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "最后登录时间")
    private Date lastLoginTime;

    @TableField("level")
    private Level level;

    @TableField("register_ip")
    //@ExcelAnnotation(id=1,name={"用户IP"},width = 5000)
    private String registerIp;

    @TableField("login_count")
    private Integer loginCount;

    @TableField("register_channel")
    private RegisterChannel registerChannel;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelAnnotation(id=1,name={"创建时间"},width = 5000)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(value = "member_level")
    private Integer memberLevel;

    @TableField(value = "member_status")
    private Status memberStatus;

    @TableField(value = "birthday_check")
    private Integer birthdayCheck;

    @TableField(value = "risk_reason")
    private String riskReason;

    @TableField(value = "risk_advise")
    private String riskAdvise;

    @TableField(value = "risk_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date riskTime;

    @TableField(value = "extra_id")
    @ApiModelProperty(value = "用户ID")
    private String extraId;

    @TableField(value = "api_id")
    @ApiModelProperty(value = "商户名称")
    private String apiId;


    @TableField(exist = false)
    @ApiModelProperty(value = "账户余额对象")
    private UserBalanceBean userBalanceBean;

    @TableField(exist = false)
    private String businessName;

    @TableField(exist = false,value = "ordertotle")
    //@ApiModelProperty(value = "累计投注额")
    //@ExcelAnnotation(id=1,name={"累计投注额"},width = 5000)
    private BigDecimal ordertotle;

    //@ApiModelProperty(value = "累计输赢额")
    @TableField(exist = false,value = "rewardtotle")
    //@ExcelAnnotation(id=1,name={"累计输赢额"},width = 5000)
    private BigDecimal  rewardtotle;

    //@ApiModelProperty(value = "累计返回额")
    @TableField(exist = false,value = "returnAmount")
    //@ExcelAnnotation(id=1,name={"累计返回额"},width = 5000)
    private BigDecimal  returnAmount;

    //@ApiModelProperty(value = "累计盈利率")
    @TableField(exist = false,value = "ratedata")
    //@ExcelAnnotation(id=1,name={"累计盈利率"},width = 5000)
    private String  ratedata;

    //风暴娱乐后台新增属性

    //账户余额
    @TableField(exist = false)
    //@ApiModelProperty(value = "账户余额")
    //@ExcelAnnotation(id=1,name={"账户余额"},width = 5000)
    private String userBalance;
    //账户可用金额
    @TableField(exist = false)
    //@ApiModelProperty(value = "账户可用金额")
    //@ExcelAnnotation(id=1,name={"账户可用金额"},width = 5000)
    private String availableCoin;
    //账户冻结金额
    @TableField(exist = false)
    //@ApiModelProperty(value = "账户冻结金额")
    //@ExcelAnnotation(id=1,name={"账户冻结金额"},width = 5000)
    private String frozenCoin;
    //银行卡数量
    @TableField(exist = false)
    //@ApiModelProperty(value = "银行卡数量")
    //@ExcelAnnotation(id=1,name={"银行卡资料"},width = 5000)
    private String cardCount;
    //账户VIP等级
    @TableField(exist = false)
    //@ApiModelProperty(value = "账户vip等级")
    //@ExcelAnnotation(id=1,name={"账户vip等级"},width = 5000)
    private  String vipLevel;
    //累计存款
    @TableField(exist =false,value = "depositTotle")
    //@ApiModelProperty(value = "累计存款")
    //@ExcelAnnotation(id=1,name={"累计存款"},width = 5000)
    private  String depositTotle;
    //累计取款
    @TableField(exist =false,value = "withdrawTotle")
    //@ApiModelProperty(value = "累计提款")
    //@ExcelAnnotation(id=1,name={"累计提款"},width = 5000)
    private  String withdrawTotle;

    //累计领取优惠
    @TableField(exist =false)
    //@ApiModelProperty(value = "累计领取优惠")
    //@ExcelAnnotation(id=1,name={"累计领取优惠"},width = 5000)
    private  String preferentialTotle="0";

    //最后登录IP
    @TableField(exist =false)
    //@ApiModelProperty(value = "最后登录IP")
    //@ExcelAnnotation(id=1,name={"最后登录IP"},width = 5000)
    private String lastLoginIp;

    @TableField(exist =false)
    //@ApiModelProperty(value = "首存时间")
    //@ExcelAnnotation(id=1,name={"首存时间"},width = 5000)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date firstDepositTime;

    /*@JsonIgnore
    @TableField(value = "extra_password")
    private String extraPassword;*/


    @TableField(exist = false)
    private String orderCoin;

    @TableField(exist = false,value = "invite_count")
    private Integer inviteCount;

    //xBet
    @TableField(exist = false)
    @ApiModelProperty(value = "商户用户名对象")
    private BusinessConfigBean businessConfigBean;

    @TableField(exist = false)
    @ApiModelProperty(value = "上级商户用户名对象")
    private BusinessConfigBean businessConfigBeanUp;

    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"总下单额"},width = 5000)
    @ApiModelProperty(value = "总下单额")
    private BigDecimal xBetOrderTotal;

    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"总注单数"},width = 5000)
    @ApiModelProperty(value = "总注单数")
    private BigDecimal xBetOrderCount;

    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"盈亏"},width = 5000)
    @ApiModelProperty(value = "盈亏")
    private BigDecimal xBetOrderProfit;


    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"商户名"},width = 5000)
    @ApiModelProperty(value = "商户名")
    private String businessConfigBeanName="";

    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"商户名用户名"},width = 5000)
    @ApiModelProperty(value = "商户名用户名")
    private String businessConfigBeanAdminName="";

    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"上级商户名"},width = 5000)
    @ApiModelProperty(value = "上级商户名")
    private String businessConfigBeanUpName="";

    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"用户余额"},width = 5000)
    @ApiModelProperty(value = "用户余额")
    private BigDecimal userBalanceCoin;

    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"钱包类型"},width = 5000)
    @ApiModelProperty(value = "钱包类型")
    private String walletTypeName;

    //v2
    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"盈亏"},width = 5000)
    @ApiModelProperty(value = "盈利率")
    private BigDecimal xBetOrderReturnTotal;

    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"盈亏"},width = 5000)
    @ApiModelProperty(value = "盈利率")
    private BigDecimal xBetOrderProfitRate;

    @Override
    public String toString() {
        return "UserBean{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

}
