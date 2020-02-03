package com.splan.xbet.admin.back.mappers;

import com.splan.base.bean.GameLeagueBean;
import org.apache.ibatis.jdbc.SQL;

public class GameLeagueBeanSqlProvider {

    public String insertSelective(GameLeagueBean record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("game_league");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getIconUrl() != null) {
            sql.VALUES("icon_url", "#{iconUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getLeagueId() != null) {
            sql.VALUES("league_id", "#{leagueId,jdbcType=INTEGER}");
        }
        
        if (record.getDetailUrl() != null) {
            sql.VALUES("detail_url", "#{detailUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=VARCHAR}");
        }
        
        if (record.getRegion() != null) {
            sql.VALUES("region", "#{region,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getGameDetail() != null) {
            sql.VALUES("game_detail", "#{gameDetail,jdbcType=VARCHAR}");
        }
        
        if (record.getGameAlias() != null) {
            sql.VALUES("game_alias", "#{gameAlias,jdbcType=VARCHAR}");
        }
        
        if (record.getAbleStatus() != null) {
            sql.VALUES("able_status", "#{ableStatus,jdbcType=VARCHAR}");
        }
        
        if (record.getGameId() != null) {
            sql.VALUES("game_id", "#{gameId,jdbcType=INTEGER}");
        }
        
        if (record.getHidden() != null) {
            sql.VALUES("hidden", "#{hidden,jdbcType=VARCHAR}");
        }
        
        if (record.getLevel() != null) {
            sql.VALUES("level", "#{level,jdbcType=INTEGER}");
        }
        
        if (record.getHot() != null) {
            sql.VALUES("hot", "#{hot,jdbcType=INTEGER}");
        }
        
        if (record.getStartTime() != null) {
            sql.VALUES("start_time", "#{startTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEndTime() != null) {
            sql.VALUES("end_time", "#{endTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getProgressState() != null) {
            sql.VALUES("progress_state", "#{progressState,jdbcType=VARCHAR}");
        }
        
        if (record.getPlace() != null) {
            sql.VALUES("place", "#{place,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }
}