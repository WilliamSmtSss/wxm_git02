package com.splan.bplan.mappers;

import com.splan.base.base.SuperMapper;
import com.splan.base.bean.GameCampaignBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface GameCampaignBeanMapper extends SuperMapper<GameCampaignBean> {

    @Select("select id,data_id,number from game_campaign where data_id=#{dataId}")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "id",property = "betTopics",many=@Many(select = "com.splan.bplan.mappers.BetTopicsBeanMapper.selectSeriByTopicableId")),
    })
    List<GameCampaignBean> selectWithSerialByDataId(Integer dataId);


    @Select("select id,data_id,number from game_campaign where data_id=#{dataId}")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "id",property = "betTopics",many=@Many(select = "com.splan.bplan.mappers.BetTopicsBeanMapper.selectByTopicableId")),
    })
    List<GameCampaignBean> selectByDataId(Integer dataId);

    /**
     * 获取全部盘口
     * @param dataId
     * @return
     */
    @Select("select id,data_id,number from game_campaign where data_id=#{dataId}")
    /*@Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "data_id",property = "betTopics",many=@Many(select = "com.splan.bplan.mappers.BetTopicsBeanMapper.selectAllByDataId")),
    })*/
    List<GameCampaignBean> selectAllByDataId(Integer dataId);

}