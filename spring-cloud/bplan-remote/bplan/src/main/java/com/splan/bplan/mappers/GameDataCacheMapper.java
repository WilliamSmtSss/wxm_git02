package com.splan.bplan.mappers;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.splan.bplan.result.GameResult;
import org.apache.ibatis.annotations.*;

import java.util.List;

@CacheNamespace(flushInterval = 5000)
//@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface GameDataCacheMapper{


    @Select("<script> select count(*) as a from (select a.* from game_data as a  <where> ((status in ('ongoing','not_start_yet') ) or (a.status='not_start_yet' and a.start_time>now()))"+
            " and 1=1 "+
            " <if test=\"gameIds != null and gameIds!='' \"> AND game_id in (${gameIds}) </if> "+
            " <if test=\"leagueIds != null and leagueIds!='' \"> AND league_id in (${leagueIds}) </if> "+
            "</where> ) as tmpa" +
            " inner JOIN ( select DISTINCT data_id from bet_topics where status in ('checked','default') and (rolling_ball=1 or be_to_rolling=1)  ) as tmpb on "+
            "tmpa.id=tmpb.data_id ORDER BY tmpa.start_time </script>")
    Integer countGameResultByRollingInt(@Param("gameIds") String gameIds, @Param("leagueIds") String leagueIds);

    @Select("<script> select count(*) as a from (SELECT id FROM game_data AS a <where> `status`='not_start_yet'  " +
            " <if test=\"gameIds != null and gameIds!='' \"> AND game_id in (${gameIds}) </if> "+
            " <if test=\"leagueIds != null and leagueIds!='' \"> AND league_id in (${leagueIds}) </if> "+
            " <if test=\"startDate != null and startDate!='' \"> AND DATE_FORMAT(start_time,'%Y-%m-%d')=#{startDate} </if> </where>"+
            ") as tmpa INNER JOIN (select DISTINCT data_id from bet_topics where status in ('default','checked') ) as tmpb   on tmpa.id=tmpb.data_id</script>")
    Integer countGameResultByBegin(@Param("gameIds") String gameIds, @Param("leagueIds") String leagueIds,String startDate);


    @Select("<script> SELECT count(*) as a from (select id from game_data as a <where> a.start_time &gt;=(now() - interval 0.5 day) "+
            "and a.start_time &lt;=(now() + interval 0.5 day) and a.status in ('not_start_yet','ongoing') "+
            " <if test=\"leagueIds != null and leagueIds!='' \"> AND league_id in (${leagueIds}) </if> "+
            " <if test=\"gameIds != null and gameIds!='' \"> AND game_id in (${gameIds}) </if> </where>"+
            ") as tmpa  inner JOIN ( select DISTINCT data_id from bet_topics where `status`='default' and final_switch=1 ) as tmpb on tmpa.id=tmpb.data_id</script>")
           // "HAVING (select count(*)>0 from bet_topics as b where  `status` in ('default','checked') and b.data_id=a.id) ) as dd </script>")
    Integer countGameResultBy24HoursAndPage(@Param("gameIds") String gameIds, @Param("leagueIds") String leagueIds);




    //@SelectProvider(type=GameDataBeanSqlProvider.class,method = "selectGameResultBy24HoursAndPage")
    //@Select("select *,id from game_data as a where a.start_time>=NOW() and a.start_time<=(now() + interval 1 day) and game_id in (${gameIds}) and a.status!='finished' order by a.start_time  limit 0,10")
    @Select(" <script> SELECT tmpa.*FROM (SELECT a.*FROM game_data AS a <where> a.start_time&gt;=(now()-INTERVAL 0.5 DAY) AND a.start_time&lt;=(now()+INTERVAL 0.5 DAY) "+
            "AND a.STATUS IN ('not_start_yet','ongoing')"+
            " <if test=\"gameIds != null and gameIds!='' \"> AND game_id in (${gameIds}) </if> "+
            " <if test=\"leagueIds != null and leagueIds!='' \"> AND league_id in (${leagueIds}) </if> "+
            "</where>) AS tmpa INNER JOIN (SELECT DISTINCT data_id FROM bet_topics WHERE `status` IN ('default','checked')) AS tmpb ON tmpa.id=tmpb.data_id ORDER BY tmpa.start_time </script>")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "league_id",property = "league",one=@One(select = "com.splan.bplan.mappers.GameLeagueBeanMapper.selectById")),
            @Result(column = "id",property = "scores",many = @Many(select = "com.splan.bplan.mappers.GameScoreBeanMapper.selectByDataId")),
            //@Result(column = "id",property = "campaigns",many = @Many(select = "com.splan.bplan.mappers.GameCampaignBeanMapper.selectWithSerialByDataId")),//,
            @Result(column = "id",property = "teams",many = @Many(select = "com.splan.bplan.mappers.GameDataTeamBeanMapper.selectGameDataTeamByDataId")),
            @Result(column = "id",property = "odds",one = @One(select = "com.splan.bplan.mappers.BetTopicsBeanMapper.selectSeriesByDataId")),
            @Result(column = "id",property = "betCount",one = @One(select = "com.splan.bplan.mappers.BetTopicsBeanMapper.countByDataId"))
    })
    List<GameResult> selectGameResultBy24HoursAndPage(IPage<GameResult> page, @Param("gameIds") String gameIds, @Param("leagueIds") String leagueIds);



    /**
     * 滚球
     * @param page
     * @param gameIds
     * @param startDate
     * @return
     */
    /*@Select("<script> select * from game_data as a <where> status in ('ongoing','not_start_yet')  " +
            " <if test=\"gameIds != null and gameIds!='' \"> AND game_id in (${gameIds}) </if> "+
            " <if test=\"leagueIds != null and leagueIds!='' \"> AND league_id in (${leagueIds}) </if> "+
            //" <if test=\"startDate != null\"> AND DATE_FORMAT(start_time,'%Y-%m-%d')=#{startDate} </if> "+
            "</where> having (select count(*)>0  from bet_topics as b where a.id=b.data_id and b.status!='canceled' and rolling_ball=1)  " +
            "  UNION ALL "+
            " select * from  game_data as a <where> a.status='not_start_yet' and a.start_time>now() "+
            " <if test=\"gameIds != null and gameIds!='' \"> AND game_id in (${gameIds}) </if> "+
            " <if test=\"leagueIds != null and leagueIds!='' \"> AND league_id in (${leagueIds}) </if> "+
            "</where> having (select count(*)>0  from bet_topics as b where a.id=b.data_id and b.status!='canceled' and rolling_ball=1)  "+
            " order by  start_time </script>")*/
    @Select("<script> select tmpa.* from (select a.* from game_data as a  <where> ((status in ('ongoing','not_start_yet') ) or (a.status='not_start_yet' and a.start_time>now()))"+
            " and 1=1 "+
            " <if test=\"gameIds != null and gameIds!='' \"> AND game_id in (${gameIds}) </if> "+
            " <if test=\"leagueIds != null and leagueIds!='' \"> AND league_id in (${leagueIds}) </if> "+
            "</where> ) as tmpa" +
            " inner JOIN ( select DISTINCT data_id from bet_topics where status in ('checked','default') and rolling_ball=1 ) as tmpb on "+
            "tmpa.id=tmpb.data_id ORDER BY tmpa.start_time </script>")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "league_id",property = "league",one=@One(select = "com.splan.bplan.mappers.GameLeagueBeanMapper.selectById")),
            @Result(column = "id",property = "scores",many = @Many(select = "com.splan.bplan.mappers.GameScoreBeanMapper.selectByDataId")),
            //@Result(column = "id",property = "campaigns",many = @Many(select = "com.splan.bplan.mappers.GameCampaignBeanMapper.selectWithSerialByDataId")),
            @Result(column = "id",property = "teams",many = @Many(select = "com.splan.bplan.mappers.GameDataTeamBeanMapper.selectGameDataTeamByDataId")),
            @Result(column = "id",property = "odds",one = @One(select = "com.splan.bplan.mappers.BetTopicsBeanMapper.selectSeriesByDataId")),
            @Result(column = "id",property = "betCount",one = @One(select = "com.splan.bplan.mappers.BetTopicsBeanMapper.countByDataId"))
    })
    List<GameResult> selectGameResultByRolling(IPage<GameResult> page, @Param("gameIds") String gameIds,String startDate,@Param("leagueIds") String leagueIds);

}