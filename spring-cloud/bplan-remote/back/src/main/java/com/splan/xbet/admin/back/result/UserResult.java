package com.splan.xbet.admin.back.result;

import com.splan.base.enums.Level;
import com.splan.xbet.admin.back.token.TokenModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UserResult extends TokenModel implements Serializable {

    private String username;

    private String realName;

    private String nickname;

    private BigDecimal coin;

    private String mobile;

    /**
     * 1有活动 0无没活动
     */
    private Integer hasActive;

    /**
     * 1有消息 0无消息
     */
    private Integer hasMessage;

    private String redirectUri;

    private String qq;

    private MemberResult member;

    private Integer hasPassword = 0;//1没有密码

    private Integer hasCard = 0;//1没有银行卡

    private BigDecimal availableWithdrawCoin;//可提金额

    private BigDecimal limitCoin;//限制金额

    private String virtualId;//虚拟id

    public UserResult(long userId, String token, Level level) {
        super(userId, token,level);
    }

    public UserResult(){

    }
}
