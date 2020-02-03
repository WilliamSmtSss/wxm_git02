package com.splan.gateway.apigateway.manager.impl;

import com.splan.base.contans.Constants;
import com.splan.base.token.TokenModel;
import com.splan.gateway.apigateway.manager.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class RedisTokenManager implements TokenManager {

    @Autowired
    private StringRedisTemplate redis;

//    @Autowired
//    public void setRedis(RedisTemplate redis){
//        this.redis = redis;
//        redis.setKeySerializer(new JdkSerializationRedisSerializer());
//    }

    @Override
    public TokenModel createToken(long userId) {
        // 使用 uuid 作为源 token
        String token = UUID.randomUUID ().toString ().replace ("-", "");
        TokenModel model = new TokenModel (userId, token);
        // 存储到 redis 并设置过期时间
        redis.boundValueOps (String.valueOf(userId)).set (token, Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return model;
    }

    @Override
    public boolean checkToken(TokenModel model) {
        if (model == null) {
            return false;
        }
        String token = redis.boundValueOps (String.valueOf(model.getUserId())).get();
        if (token == null || !token.equals (model.getToken ())) {
            return false;
        }
        // 如果验证成功，说明此用户进行了一次有效操作，延长 token 的过期时间
        redis.boundValueOps (String.valueOf(model.getUserId ())).expire (Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return true;
    }

    @Override
    public TokenModel getToken(String authentication) {
        if (authentication == null || authentication.length () == 0) {
            return null;
        }
        String [] param = authentication.split ("_");
        if (param.length != 2) {
            return null;
        }
        // 使用 userId 和源 token 简单拼接成的 token，可以增加加密措施
        long userId = Long.parseLong (param [0]);
        String token = param [1];
        return new TokenModel (userId, token);
    }

    @Override
    public void deleteToken(long userId) {
        redis.delete (String.valueOf(userId));

    }

    @Override
    public String getTokenByUserId(long userId) {
        String token = redis.boundValueOps(String.valueOf(userId)).get();
        return token;
    }
}
