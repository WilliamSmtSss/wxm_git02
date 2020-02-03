package com.splan.xbet.admin.back.mappers;

import com.splan.base.bean.BetOrderDetailBean;
import com.splan.xbet.admin.back.config.cache.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@CacheNamespace(implementation = MybatisRedisCache.class,eviction = MybatisRedisCache.class)
public interface XBetCacheMapper {

    @Select("select * from x_together_order_data_view t where t.bet_order_id=#{orderId}")
    List<BetOrderDetailBean> getXbetDetails(@Param("orderId") String orderId);

}
