package com.splan.backgateway.backgateway.filters;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config> {
    public AuthGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            System.out.println("Welcome to AuthFilter.");
            String token = exchange.getRequest().getHeaders().getFirst("sign");
            if (!Config.secret.equals(token)) {
                return chain.filter(exchange);
            }
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            response.setStatusCode(HttpStatus.OK);
            try {
                JSONObject message = new JSONObject();
                message.put("code", "401");
                message.put("data", "");
                message.put("message", "未登录");
                message.put("success", false);
                byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(bits);
                response.getHeaders().add("Content-Type", "application/json; charset=utf-8");
                return response.writeWith(Mono.just(buffer));
            }catch (Exception e){
                return chain.filter(exchange);
            }
        };
    }

    static class Config {
        static String secret = "1234";
    }

}
