package com.splan.gateway.apigateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.splan.base.contans.Constants;
import com.splan.base.token.TokenModel;
import com.splan.gateway.apigateway.manager.TokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class AuthorizeGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizeGatewayFilterFactory.Config> {

    @Autowired
    private TokenManager manager;

    public AuthorizeGatewayFilterFactory() {
        super(Config.class);
        log.info("Loaded GatewayFilterFactory [Authorize]");
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("enabled");
    }

    @Override
    public GatewayFilter apply(AuthorizeGatewayFilterFactory.Config config) {
        return (exchange, chain) -> {
            if (!config.isEnabled()) {
                return chain.filter(exchange);
            }

            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders headers = request.getHeaders();
            String token = headers.getFirst(Constants.AUTHORIZATION);
            if (token == null) {
                token = request.getQueryParams().getFirst(Constants.AUTHORIZATION);
            }

            ServerHttpResponse response = exchange.getResponse();

            TokenModel model = manager.getToken (token);
            if (token == null || !manager.checkToken (model)) {
                response.setStatusCode(HttpStatus.OK);
                JSONObject message = new JSONObject();
                message.put("code", "401");
                message.put("data", "");
                message.put("message","未登录");
                message.put("success",false);
                byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(bits);
                response.getHeaders().add("Content-Type", "application/json; charset=utf-8");
                return response.writeWith(Mono.just(buffer));
            }else {
                // 如果 token 验证成功，将 token 对应的用户 id 存在 request 中，便于之后注入
                request.mutate()
                        .header(Constants.CURRENT_USER_ID, model.getUserId().toString())
                        .build();
            }


            return chain.filter(exchange);
        };
    }

    public static class Config {
        // 控制是否开启认证
        private boolean enabled = true;

        public Config() {}

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}
