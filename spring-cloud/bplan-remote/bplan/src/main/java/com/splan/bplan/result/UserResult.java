package com.splan.bplan.result;

import com.splan.base.enums.Level;
import com.splan.bplan.token.TokenModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UserResult extends TokenModel implements Serializable {

    private String username;

    private String realName;

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

    private String extraId;

    public UserResult(long userId, String token, Level level) {
        super(userId, token,level);
    }

    public UserResult(){

    }
}
