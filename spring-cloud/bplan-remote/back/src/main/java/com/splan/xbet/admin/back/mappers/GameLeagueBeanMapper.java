package com.splan.xbet.admin.back.mappers;

import com.splan.xbet.admin.back.base.SuperMapper;
import com.splan.base.bean.GameLeagueBean;
import com.splan.xbet.admin.back.result.GameLeagueResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GameLeagueBeanMapper extends SuperMapper<GameLeagueBean> {

    @Select("select a.*,b.`name` from (\n" +
            "\t select count(*) as game_count,league_id from game_data where `status` in ('not_start_yet','ongoing') \n" +
            "\t and game_id=#{gameId} \n" +
            "\t GROUP BY league_id) as a\n" +
            "\t LEFT JOIN game_league as b on a.league_id=b.id#) as tmp\n" +
            "\t ORDER BY game_count desc")
    List<GameLeagueResult> selectLeagueList(Integer gameId);

}