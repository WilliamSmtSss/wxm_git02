package com.splan.data.datacenter.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang.StringUtils;

public class GameDataBeanSqlProvider {



    public String selectGameResultBy24HoursAndPage(IPage page, String gameIds,String leagueIds){
        StringBuffer sql = new StringBuffer();
        sql.append("select a.* from game_data as a where a.start_time>=(now() - interval 0.5 day) and a.start_time<=(now() + interval 0.5 day) and a.status in ('not_start_yet','ongoing') ");
        if (StringUtils.isNotBlank(gameIds)){
            //String[] strings = gameIds.split(",");
            sql.append(" and game_id in ( ");
            sql.append(gameIds);
            sql.append(")");
        }
        if (StringUtils.isNotBlank(leagueIds)){
            //String[] strings = gameIds.split(",");
            sql.append(" and league_id in ( ");
            sql.append(leagueIds);
            sql.append(")");
        }

        //sql.append(" having (select count(*)>0 from bet_topics as b where  ((`status`='default' AND final_switch=1) or (`status`='checked')) and b.data_id=a.id ) ");
        sql.append(" having (select count(*)>0 from bet_topics as b where  `status` in ('default','checked') and b.data_id=a.id ) ");
        sql.append(" order by a.start_time ");
        return sql.toString();
    }

    public String countGameResultBy24HoursAndPage(String gameIds){
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT count(*) from (select id from game_data as a where a.start_time>=(now() - interval 0.5 day) and a.start_time<=(now() + interval 0.5 day) and a.status in ('not_start_yet','ongoing') ");
        if (StringUtils.isNotBlank(gameIds)){
            //String[] strings = gameIds.split(",");
            sql.append(" and game_id in ( ");
            sql.append(gameIds);
            sql.append(")");
        }
        //sql.append(" having (select count(*) from bet_topics as b where  ((`status`='default' AND final_switch=1) or (`status`='checked')) and b.data_id=a.id) ) as a");
        sql.append(" having (select count(*) from bet_topics as b where  `status` in ('default','checked') and b.data_id=a.id) ) as a");

        return sql.toString();
    }
}