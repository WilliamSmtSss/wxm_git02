package com.splan.gateway.apigateway.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.splan.gateway.apigateway.bean.RouteDefinitionVo;
import com.splan.gateway.apigateway.constant.CommonConstants;
import com.splan.gateway.apigateway.manager.SysRouteConfService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.config.PropertiesRouteDefinitionLocator;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.Resource;
import java.net.URI;

@Slf4j
@Configuration
//@AllArgsConstructor
public class DynamicRouteInitRunner {

    @Resource
    private RedisTemplate<String,Object> cloudRedisTemplate;

    @Autowired
    private SysRouteConfService routeConfService;

    @Async
    @Order
    @EventListener({WebServerInitializedEvent.class, DynamicRouteInitEvent.class})
    public void initRoute() {
        Boolean result = cloudRedisTemplate.delete(CommonConstants.ROUTE_KEY);
        log.info("初始化网关路由 {} ", result);

        routeConfService.routes().forEach(route -> {
            RouteDefinitionVo vo = new RouteDefinitionVo();
            vo.setRouteName(route.getRouteName());
            vo.setId(route.getRouteId());
            vo.setUri(URI.create(route.getUri()));
            vo.setOrder(route.getOrder());

            JSONArray filterObj = JSON.parseArray(route.getFilters());
            vo.setFilters(filterObj.toJavaList(FilterDefinition.class));
            JSONArray predicateObj = JSON.parseArray(route.getPredicates());
            vo.setPredicates(predicateObj.toJavaList(PredicateDefinition.class));

            log.info("加载路由ID：{},{}", route.getRouteId(), vo);
            cloudRedisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(RouteDefinitionVo.class));
            cloudRedisTemplate.opsForHash().put(CommonConstants.ROUTE_KEY, route.getRouteId(), vo);
        });
        log.debug("初始化网关路由结束 ");
    }

    /**
     * 配置文件设置为空redis 加载的为准
     *
     * @return
     */
    @Bean
    public PropertiesRouteDefinitionLocator propertiesRouteDefinitionLocator() {
        return new PropertiesRouteDefinitionLocator(new GatewayProperties());
    }
}
