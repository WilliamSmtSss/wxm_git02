package com.splan.bplan.mappers;

import com.splan.base.base.SuperMapper;
import com.splan.base.bean.GameScoreBean;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GameScoreBeanMapper extends SuperMapper<GameScoreBean> {


    @InsertProvider(type=GameScoreBeanSqlProvider.class, method="insertSelective")
    int insertSelective(GameScoreBean record);

    @Select("select * from game_score where data_id=#{dataId}")
    List<GameScoreBean> selectByDataId(Integer dataId);
}