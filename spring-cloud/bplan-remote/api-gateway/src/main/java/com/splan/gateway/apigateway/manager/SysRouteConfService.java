package com.splan.gateway.apigateway.manager;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.gateway.apigateway.bean.SysRouteConf;
import reactor.core.publisher.Mono;

import java.util.List;

public interface SysRouteConfService extends IService<SysRouteConf> {

    /**
     * 获取全部路由
     * <p>
     * RedisRouteDefinitionWriter.java
     * PropertiesRouteDefinitionLocator.java
     *
     * @return
     */
    List<SysRouteConf> routes();

    /**
     * 更新路由信息
     *
     * @param routes 路由信息
     * @return
     */
    Mono<Void> updateRoutes(JSONArray routes);
}
