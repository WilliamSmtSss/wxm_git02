package com.splan.gateway.apigateway.manager;


import com.splan.base.token.TokenModel;

public interface TokenManager {

    /**
     * 创建一个 token 关联上指定用户
     * @param userId 指定用户的 id
     * @return 生成的 token
     */
    TokenModel createToken(long userId);

    /**
     * 检查 token 是否有效
     * @param model token
     * @return 是否有效
     */
    boolean checkToken(TokenModel model);

    /**
     * 从字符串中解析 token
     * @param authentication 加密后的字符串
     * @return
     */
    TokenModel getToken(String authentication);

    /**
     * 清除 token
     * @param userId 登录用户的 id
     */
    void deleteToken(long userId);

    /**
     * 从字符串中解析 token
     * @return
     */
    String getTokenByUserId(long userId);
}
