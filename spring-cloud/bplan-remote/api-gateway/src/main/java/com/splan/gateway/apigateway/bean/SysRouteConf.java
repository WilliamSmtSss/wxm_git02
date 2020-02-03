package com.splan.gateway.apigateway.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.splan.base.bean.BaseBean;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysRouteConf extends BaseBean {
    /**
     * 路由ID
     */
    private String routeId;
    /**
     * 路由名称
     */
    private String routeName;
    /**
     * 断言
     */
    private String predicates;
    /**
     * 过滤器
     */
    private String filters;
    /**
     * uri
     */
    private String uri;
    /**
     * 排序
     */
    @TableField(value = "`order`")
    private Integer order;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @TableLogic
    private String delFlag;
}
