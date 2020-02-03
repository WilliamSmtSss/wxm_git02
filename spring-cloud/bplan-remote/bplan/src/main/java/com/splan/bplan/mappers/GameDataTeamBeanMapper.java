package com.splan.bplan.mappers;

import com.splan.base.base.SuperMapper;
import com.splan.base.bean.GameDataTeamBean;
import com.splan.base.bean.GameTeamBean;
import com.splan.bplan.result.GameResult;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface GameDataTeamBeanMapper extends SuperMapper<GameDataTeamBean> {

    @Select("select b.abbr,b.name,b.logo from game_data_team as a left join game_team as b on a.team_id=b.id where a.data_id=#{dataId} ")
    List<GameTeamBean> selectGameDataTeamByDataId(Integer dataId);

}