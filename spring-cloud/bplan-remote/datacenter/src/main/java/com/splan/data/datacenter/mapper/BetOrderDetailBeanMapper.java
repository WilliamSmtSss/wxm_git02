package com.splan.data.datacenter.mapper;

import com.splan.base.base.SuperMapper;
import com.splan.base.bean.BetOrderDetailBean;
import com.splan.base.enums.OrderStatus;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BetOrderDetailBeanMapper extends SuperMapper<BetOrderDetailBean> {


    @Select("SELECT a.*,d.game_id,e.selected_logo as logo,c.data_id as game_data_id,c.group_name,b.sequence,c.mark_value,a.vs_detail,c.topicable_type,c.rolling_ball,f.number,IF(c.topicable_type='Series','全场',CONCAT('第',f.number,'局')) as topicable_name FROM bet_order_detail as a LEFT JOIN bet_option as b on a.bet_option_id=b.id\n" +
            "LEFT join bet_topics as c on b.bet_data_id=c.id  LEFT join game_data as d on a.data_id=d.id LEFT JOIN game_type as e on d.game_id=e.id LEFT JOIN game_campaign as f on f.id=c.topicable_id where a.bet_order_id=#{betOrderId}")
    @Results({
            //@Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "data_id",property = "gameResultList",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameDataBeanMapper.selectGameWithTeamCampainsResultById"))

    })
    List<BetOrderDetailBean> selectByOrderId(Integer betOrderId);


    @Select("select * from bet_order_detail where bet_option_id=#{betOptionId} and status=#{status}")
    @Results({
            @Result(column = "bet_order_id",property = "betOrderBean",one = @One(select = "com.splan.xbet.admin.back.mappers.BetOrderBeanMapper.selectById"))

    })
    List<BetOrderDetailBean> selectByBetOptionId(Integer betOptionId, OrderStatus status);

    @Select("SELECT a.*,b.bet_result FROM bet_order_detail AS a JOIN bet_option AS b ON a.bet_option_id=b.id WHERE a.`status`='UNSETTLED' AND b.bet_result IN (1,2,3) ")//and b.update_time>DATE_ADD(NOW(),INTERVAL-1 HOUR) ")
    @Results({
            @Result(column = "bet_order_id",property = "betOrderBean",one = @One(select = "com.splan.xbet.admin.back.mappers.BetOrderBeanMapper.selectById"))

    })
    List<BetOrderDetailBean> selectByYesterday();

    @Select("select count(*) from bet_order_detail where data_id=#{dataId}")
    Integer countOrder(Integer dataId);

    @Select("select * from bet_order_detail where data_id=#{dataId}")
    List<BetOrderDetailBean> selectByDataId(Integer dataId);


    @Select("select * from bet_order_detail  where bet_option_id in (\n" +
            "select id from bet_option where bet_data_id=#{betDataId}) and status='SETTLED'")
    @Results({
            @Result(column = "bet_order_id",property = "betOrderBean",one = @One(select = "com.splan.xbet.admin.back.mappers.BetOrderBeanMapper.selectById"))

    })
    List<BetOrderDetailBean> selectByBetDataId(Integer betDataId);

}