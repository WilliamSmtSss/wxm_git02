package com.splan.bplan.result;

import com.splan.base.bean.UserBalanceBean;
import com.splan.base.bean.UserCardBean;
import com.splan.base.enums.Level;
import com.splan.base.enums.RegisterChannel;
import com.splan.base.enums.Status;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class UserAccountResult extends UserBalanceBean implements Serializable {

    private Long id;

    private String username;

    private String mobile;

    private String mobileArea;

    private String inviteCode;

    private String beInviteCode;

    private Date birthday;

    private Status status;

    private String realName;

    private Date lastLoginTime;

    private Level level;

    private String registerIp;

    private Integer loginCount;

    private Date createTime;

    private RegisterChannel registerChannel;

    private Integer totalInvite;

    private BigDecimal totalDepositCoin;

    private List<Integer> agentids;

    private List<Integer> normals;

    private Integer agentidslen;

    private Integer normalidslen;

    private Integer agentprofit;

    private Integer normalprofit;

    private Integer totleprofit;

    private UserBalanceBean userBalanceBean;

    private UserCardBean userCardBean;

}
