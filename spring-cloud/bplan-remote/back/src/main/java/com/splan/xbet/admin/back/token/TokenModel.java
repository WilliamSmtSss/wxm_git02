package com.splan.xbet.admin.back.token;

import com.splan.base.enums.Level;
import lombok.Data;

@Data
public class TokenModel {

    // 用户 id
    private long userId;

    // 随机生成的 uuid
    private String token;

    private Level level;

    public TokenModel (long userId, String token,Level level) {
        this.userId = userId;
        this.token = token;
        this.level = level;
    }

    public TokenModel (long userId, String token) {
        this.userId = userId;
        this.token = token;
        this.level = Level.Normal;
    }

    public TokenModel(){

    }


}
