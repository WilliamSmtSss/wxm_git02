package com.splan.gateway.apigateway.repositroy;

import com.splan.gateway.apigateway.bean.RouteDefinitionVo;
import com.splan.gateway.apigateway.constant.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
//@AllArgsConstructor
public class RedisRouteDefinitionWriter implements RouteDefinitionRepository {

    //private final RedisTemplate redisTemplate;
    //@Autowired
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(r -> {
            RouteDefinitionVo vo = new RouteDefinitionVo();
            BeanUtils.copyProperties(r, vo);
            log.info("保存路由信息{}", vo);
            redisTemplate.opsForHash().put(CommonConstants.ROUTE_KEY, r.getId(), vo);
            return Mono.empty();
        });
    }
    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        routeId.subscribe(id -> {
            log.info("删除路由信息{}", id);
            redisTemplate.opsForHash().delete(CommonConstants.ROUTE_KEY, id);
        });
        return Mono.empty();
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(RouteDefinitionVo.class));
        List<RouteDefinitionVo> values = (List<RouteDefinitionVo>)(Object)redisTemplate.opsForHash().values(CommonConstants.ROUTE_KEY);
        List<RouteDefinition> definitionList = new ArrayList<>();
        values.forEach(vo -> {
            RouteDefinition routeDefinition = new RouteDefinition();
            BeanUtils.copyProperties(vo, routeDefinition);
            definitionList.add(vo);
        });
        log.debug("redis 中路由定义条数： {}， {}", definitionList.size(), definitionList);
        return Flux.fromIterable(definitionList);
    }
}
