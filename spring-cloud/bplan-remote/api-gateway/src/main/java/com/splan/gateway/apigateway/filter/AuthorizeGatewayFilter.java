//package com.splan.gateway.apigateway.filter;
//
//import com.alibaba.fastjson.JSONObject;
//import com.splan.base.contans.Constants;
//import com.splan.base.token.TokenModel;
//import com.splan.gateway.apigateway.manager.TokenManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.nio.charset.StandardCharsets;
//
//@Component
//public class AuthorizeGatewayFilter implements GatewayFilter, Ordered {
//
//    @Autowired
//    private TokenManager manager;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        HttpHeaders headers = request.getHeaders();
//        String token = headers.getFirst(Constants.AUTHORIZATION);
//        if (token == null) {
//            token = request.getQueryParams().getFirst(Constants.AUTHORIZATION);
//        }
//
//        ServerHttpResponse response = exchange.getResponse();
//
//        TokenModel model = manager.getToken (token);
//        if (token == null || !manager.checkToken (model)) {
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            JSONObject message = new JSONObject();
//            message.put("code", "401");
//            message.put("data", "");
//            message.put("message","未登录");
//            message.put("success",false);
//            byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
//            DataBuffer buffer = response.bufferFactory().wrap(bits);
//            response.getHeaders().add("Content-Type", "application/json; charset=utf-8");
//            return response.writeWith(Mono.just(buffer));
//        }else {
//            // 如果 token 验证成功，将 token 对应的用户 id 存在 request 中，便于之后注入
//            request.mutate()
//                    .header(Constants.CURRENT_USER_ID, model.getUserId().toString())
//                    .build();
//        }
//
//
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
