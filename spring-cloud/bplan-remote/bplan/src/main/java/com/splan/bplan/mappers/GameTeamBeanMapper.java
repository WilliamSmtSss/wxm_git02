package com.splan.bplan.mappers;

import com.splan.base.base.SuperMapper;
import com.splan.base.bean.GameScoreBean;
import com.splan.base.bean.GameTeamBean;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GameTeamBeanMapper extends SuperMapper<GameTeamBean> {


    @InsertProvider(type=GameTeamBeanSqlProvider.class, method="insertSelective")
    int insertSelective(GameTeamBean record);

//    @Select("select * from game_team where data_id=#{dataId}")
//    List<GameTeamBean> selectByDataId(Integer dataId);

    @Select("select * from game_team a, game_data_team b where a.id = b.team_id and b.data_id = #{data.id}")
    List<GameTeamBean> selectByDataId(Integer dataId);
}