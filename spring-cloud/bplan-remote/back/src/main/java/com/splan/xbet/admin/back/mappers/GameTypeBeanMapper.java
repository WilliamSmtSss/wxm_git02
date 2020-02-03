package com.splan.xbet.admin.back.mappers;

import com.splan.xbet.admin.back.base.SuperMapper;
import com.splan.base.bean.GameTypeBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GameTypeBeanMapper extends SuperMapper<GameTypeBean> {


    @Select("select id, name_en, name_ch, logo, selected_logo from game_type where status = 'ENABLE'")
    List<GameTypeBean> selectGameTypeList();

    @Select("   SELECT a.*,IFNULL(b.game_count,0) as game_count from (select id, name_en, name_ch, logo, selected_logo from game_type where status = 'ENABLE') as a LEFT JOIN (\n" +
            " select count(*) as game_count,game_id from game_data where `status` in ('not_start_yet','ongoing') GROUP BY game_id) as b on a.id=b.game_id")
    List<GameTypeBean> selectPcGameTypeList();

}