package com.splan.bplan.mappers;

import com.splan.base.bean.BetOrderDetailBean;
import com.splan.base.bean.BetTopicsBean;
import com.splan.base.bean.GameCampaignBean;
import com.splan.bplan.config.MybatisRedisCache2;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@CacheNamespace(implementation= MybatisRedisCache2.class,eviction=MybatisRedisCache2.class)
public interface BussinessBackCacheMapper {
    @Select("select * from bet_order_detail t where t.bet_order_id=#{orderId}")
    List<BetOrderDetailBean> getDetailsCache(String orderId);

    @Select({
            "select * from bet_topics a, bet_option b where b.bet_data_id = a.id and b.id = #{optionId}"
    })
    List<BetTopicsBean> selectTopicByOptionIdCache(@Param("optionId")Integer optionId);

    @Select("select * from game_campaign t where t.id=#{TopicableId} limit 1")
    GameCampaignBean getGameCampaignCache(String TopicableId);

}
