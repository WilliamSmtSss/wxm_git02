package com.splan.xbet.admin.back.mappers;

import com.splan.xbet.admin.back.base.SuperMapper;
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

    //xBet
    @Select("select t1.* from game_team t1 left join game_data_team t2 on t1.id=t2.team_id where t2.data_id=#{dataId}")
    List<GameTeamBean> getXbetTeams(String dataId);
}