package com.splan.gateway.apigateway.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.api.R;
import com.splan.gateway.apigateway.bean.SysRouteConf;
import com.splan.gateway.apigateway.manager.SysRouteConfService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/route")
public class SysRouteConfController {

    private final SysRouteConfService sysRouteConfService;


    /**
     * 获取当前定义的路由信息
     *
     * @return
     */
    @GetMapping("/list")
    public List<SysRouteConf> listRoutes() {
        return sysRouteConfService.list();
    }

    /**
     * 修改路由
     *
     * @param routes 路由定义
     * @return
     */
    //@SysLog("修改路由")
    @PutMapping
    public String updateRoutes(@RequestBody JSONArray routes) {
        sysRouteConfService.updateRoutes(routes);
        return "";
    }
}
