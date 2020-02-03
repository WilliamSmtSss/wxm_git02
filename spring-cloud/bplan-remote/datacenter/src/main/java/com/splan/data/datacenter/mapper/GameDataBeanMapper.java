package com.splan.data.datacenter.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.splan.base.base.SuperMapper;
import com.splan.base.bean.GameDataBean;
import com.splan.base.result.GameResult;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

//@CacheNamespace
//@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface GameDataBeanMapper extends SuperMapper<GameDataBean> {

    //@SelectProvider(type=GameDataBeanSqlProvider.class,method = "selectGameResultBy24HoursAndPage")
    @Select(" <script> SELECT tmpa.*FROM (SELECT a.*FROM game_data AS a <where> a.start_time&gt;=(now()-INTERVAL 0.5 DAY) AND a.start_time&lt;=(now()+INTERVAL 0.5 DAY) "+
            "AND a.STATUS IN ('not_start_yet','ongoing')"+
            " <if test=\"gameIds != null and gameIds!='' \"> AND game_id in (${gameIds}) </if> "+
            " <if test=\"leagueIds != null and leagueIds!='' \"> AND league_id in (${leagueIds}) </if> "+
            "</where>) AS tmpa INNER JOIN (SELECT DISTINCT data_id FROM bet_topics WHERE `status`='default' and final_switch=1) AS tmpb ON tmpa.id=tmpb.data_id ORDER BY tmpa.start_time </script>")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "league_id",property = "league",one=@One(select = "com.splan.xbet.admin.back.mappers.GameLeagueBeanMapper.selectById")),
            @Result(column = "id",property = "scores",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameScoreBeanMapper.selectByDataId")),
            //@Result(column = "id",property = "campaigns",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameCampaignBeanMapper.selectWithSerialByDataId")),//,
            @Result(column = "id",property = "teams",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameDataTeamBeanMapper.selectGameDataTeamByDataId")),
            @Result(column = "id",property = "odds",one = @One(select = "com.splan.xbet.admin.back.mappers.BetTopicsBeanMapper.selectSeriesByDataId")),
            @Result(column = "id",property = "betCount",one = @One(select = "com.splan.xbet.admin.back.mappers.BetTopicsBeanMapper.countByDataId")),
            @Result(column = "id",property = "beToRolling",one = @One(select = "com.splan.xbet.admin.back.mappers.BetTopicsBeanMapper.countBeToRollingByDataId")),
            @Result(column = "id",property = "rollingBall",one = @One(select = "com.splan.xbet.admin.back.mappers.BetTopicsBeanMapper.countRollingByDataId"))
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
            " inner JOIN ( select DISTINCT data_id from bet_topics where status in ('checked','default') and (rolling_ball=1 or be_to_rolling=1) ) as tmpb on "+
            "tmpa.id=tmpb.data_id ORDER BY tmpa.start_time </script>")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "league_id",property = "league",one=@One(select = "com.splan.xbet.admin.back.mappers.GameLeagueBeanMapper.selectById")),
            @Result(column = "id",property = "scores",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameScoreBeanMapper.selectByDataId")),
            //@Result(column = "id",property = "campaigns",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameCampaignBeanMapper.selectWithSerialByDataId")),
            @Result(column = "id",property = "teams",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameDataTeamBeanMapper.selectGameDataTeamByDataId")),
            @Result(column = "id",property = "odds",one = @One(select = "com.splan.xbet.admin.back.mappers.BetTopicsBeanMapper.selectSeriesByDataId")),
            @Result(column = "id",property = "betCount",one = @One(select = "com.splan.xbet.admin.back.mappers.BetTopicsBeanMapper.countByDataId")),
            @Result(column = "id",property = "beToRolling",one = @One(select = "com.splan.xbet.admin.back.mappers.BetTopicsBeanMapper.countBeToRollingByDataId")),
            @Result(column = "id",property = "rollingBall",one = @One(select = "com.splan.xbet.admin.back.mappers.BetTopicsBeanMapper.countRollingByDataId"))
    })
    List<GameResult> selectGameResultByRolling(IPage<GameResult> page, @Param("gameIds") String gameIds, String startDate, @Param("leagueIds") String leagueIds);


    /**
     * 滚球
     * @param status
     * @param gameIds
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
            " inner JOIN ( select DISTINCT data_id from bet_topics where status in ('checked','default') and (rolling_ball=1 or be_to_rolling=1) ) as tmpb on "+
            "tmpa.id=tmpb.data_id ORDER BY tmpa.start_time </script>")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            //@Result(column = "league_id",property = "league",one=@One(select = "com.splan.xbet.admin.back.mappers.GameLeagueBeanMapper.selectById")),
            //@Result(column = "id",property = "scores",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameScoreBeanMapper.selectByDataId")),
            //@Result(column = "id",property = "campaigns",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameCampaignBeanMapper.selectWithSerialByDataId")),
            @Result(column = "id",property = "teams",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameDataTeamBeanMapper.selectGameDataTeamByDataId"))
            //@Result(column = "id",property = "odds",one = @One(select = "com.splan.xbet.admin.back.mappers.BetTopicsBeanMapper.selectSeriesByDataId")),
           // @Result(column = "id",property = "betCount",one = @One(select = "com.splan.xbet.admin.back.mappers.BetTopicsBeanMapper.countByDataId"))
    })
    List<GameResult> selectGameResultSimpleByRolling(@Param("status") String status, @Param("gameIds") String gameIds, @Param("leagueIds") String leagueIds);

    /**
     * 赛前
     * @param page
     * @param status
     * @param gameIds
     * @param startDate
     * @return
     */
    /*@Select("<script> select * from game_data as a <where> status=#{status}  " +
            " <if test=\"gameIds != null and gameIds!='' \"> AND game_id in (${gameIds}) </if> "+
            " <if test=\"startDate != null  and startDate!='' \"> AND DATE_FORMAT(start_time,'%Y-%m-%d')=#{startDate} </if> "+
            " <if test=\"leagueIds != null and leagueIds!='' \"> AND league_id in (${leagueIds}) </if> "+
            " having (select count(*)>0  from bet_topics as b where a.id=b.data_id and b.status!='canceled')  " +
            "</where> order by  start_time </script>")*/
    @Select("<script>SELECT tmpa.* from (select * from game_data as a <where> status='not_start_yet' "+
            " <if test=\"gameIds != null and gameIds!='' \"> AND game_id in (${gameIds}) </if> "+
            " <if test=\"startDate != null  and startDate!='' \"> AND DATE_FORMAT(start_time,'%Y-%m-%d')=#{startDate} </if> "+
            " <if test=\"leagueIds != null and leagueIds!='' \"> AND league_id in (${leagueIds}) </if> "+
            " </where>) as tmpa INNER JOIN (select DISTINCT data_id from bet_topics where status in ('default','checked') ) as tmpb on tmpa.id=tmpb.data_id order by start_time </script>")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "league_id",property = "league",one=@One(select = "com.splan.xbet.admin.back.mappers.GameLeagueBeanMapper.selectById")),
            @Result(column = "id",property = "scores",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameScoreBeanMapper.selectByDataId")),
            //@Result(column = "id",property = "campaigns",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameCampaignBeanMapper.selectWithSerialByDataId")),
            @Result(column = "id",property = "teams",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameDataTeamBeanMapper.selectGameDataTeamByDataId")),
            @Result(column = "id",property = "odds",one = @One(select = "com.splan.xbet.admin.back.mappers.BetTopicsBeanMapper.selectSeriesByDataId")),
            @Result(column = "id",property = "betCount",one = @One(select = "com.splan.xbet.admin.back.mappers.BetTopicsBeanMapper.countByDataId"))
    })
    List<GameResult> selectGameResultByNotStart(IPage<GameResult> page, @Param("status") String status, @Param("gameIds") String gameIds, String startDate, @Param("leagueIds") String leagueIds);

    /**
     * 结束
     * @param page
     * @param status
     * @param gameIds
     * @param startDate
     * @return
     */
    @Select("<script> select * from game_data as a <where> status=#{status}  " +
            " <if test=\"gameIds != null and gameIds!='' \"> AND game_id in (${gameIds}) </if> "+
            " <if test=\"startDate != null and startDate!='' \"> AND DATE_FORMAT(start_time,'%Y-%m-%d')=#{startDate} </if> "+
            " <if test=\"leagueIds != null and leagueIds!='' \"> AND league_id in (${leagueIds}) </if> "+
            //" having (select count(*)>0  from bet_topics as b where a.id=b.data_id and b.status!='canceled')  " +
            "</where> order by  start_time desc</script>")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "league_id",property = "league",one=@One(select = "com.splan.xbet.admin.back.mappers.GameLeagueBeanMapper.selectById")),
            @Result(column = "id",property = "scores",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameScoreBeanMapper.selectByDataId")),
            //@Result(column = "id",property = "campaigns",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameCampaignBeanMapper.selectWithSerialByDataId")),
            @Result(column = "id",property = "teams",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameDataTeamBeanMapper.selectGameDataTeamByDataId")),
            //@Result(column = "id",property = "odds",one = @One(select = "com.splan.xbet.admin.back.mappers.BetTopicsBeanMapper.selectSeriesEndByDataId")),
            @Result(column = "id",property = "betCount",one = @One(select = "com.splan.xbet.admin.back.mappers.BetTopicsBeanMapper.countEndByDataId"))
    })
    List<GameResult> selectGameResultByEnd(IPage<GameResult> page, @Param("status") String status, @Param("gameIds") String gameIds, String startDate, @Param("leagueIds") String leagueIds);
    /**
     * 获取比赛详情页
     * @param dataId
     * @return
     */
    @Select("select * from game_data where id=#{dataId} ")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            //@Result(column = "league_id",property = "league",one=@One(select = "com.splan.xbet.admin.back.mappers.GameLeagueBeanMapper.selectById")),
            @Result(column = "id",property = "scores",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameScoreBeanMapper.selectByDataId")),
            //@Result(column = "id",property = "campaigns",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameCampaignBeanMapper.selectWithSerialByDataId")),//,
            //@Result(column = "id",property = "teams",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameDataTeamBeanMapper.selectGameDataTeamByDataId")),
            //@Result(column = "id",property = "odds",one = @One(select = "com.splan.xbet.admin.back.mappers.BetTopicsBeanMapper.selectSeriesByDataId")),
            @Result(column = "id",property = "betCount",one = @One(select = "com.splan.xbet.admin.back.mappers.BetTopicsBeanMapper.countByDataId"))
    })
    GameResult selectGameResultWebSocketById(Integer dataId);

    /**
     * 获取比赛详情页
     * @param dataId
     * @return
     */
    @Select("select * from game_data where id=#{dataId} ")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "league_id",property = "league",one=@One(select = "com.splan.xbet.admin.back.mappers.GameLeagueBeanMapper.selectById")),
            @Result(column = "id",property = "scores",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameScoreBeanMapper.selectByDataId")),
            @Result(column = "id",property = "campaigns",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameCampaignBeanMapper.selectAllByDataId")),
            @Result(column = "id",property = "teams",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameDataTeamBeanMapper.selectGameDataTeamByDataId")),
            @Result(column = "id",property = "rollingBall",one = @One(select = "com.splan.xbet.admin.back.mappers.BetTopicsBeanMapper.countRollingByDataId"))
    })
    GameResult selectGameResultById(Integer dataId);

    /**
     * 获取比赛详情页
     * @param dataId
     * @return
     */
    @Select("select * from game_data where id=#{dataId} ")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "league_id",property = "league",one=@One(select = "com.splan.xbet.admin.back.mappers.GameLeagueBeanMapper.selectById")),
            @Result(column = "game_id",property = "gameTypeBean",one=@One(select = "com.splan.xbet.admin.back.mappers.GameTypeBeanMapper.selectById")),
            @Result(column = "id",property = "teams",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameDataTeamBeanMapper.selectGameDataTeamByDataId"))
    })
    GameResult selectGameResultWithTeamById(Integer dataId);


    @Select("select * from game_data where id=#{dataId} ")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            //@Result(column = "id",property = "campaigns",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameCampaignBeanMapper.selectWithSerialByDataId")),
            @Result(column = "id",property = "teams",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameDataTeamBeanMapper.selectGameDataTeamByDataId"))
    })
    GameResult selectGameWithTeamCampainsResultById(Integer dataId);

    @SelectProvider(type=GameDataBeanSqlProvider.class,method = "countGameResultBy24HoursAndPage")
    Integer countGameResultBy24HoursAndPage(@Param("gameIds") String gameIds);


    @Select("<script> SELECT count(*) AS a,`status` FROM (SELECT id,`status` FROM game_data AS a <where> `status` in ('not_start_yet','finished') " +
            " <if test=\"gameIds != null and gameIds!='' \"> AND game_id in (${gameIds}) </if> "+
            " <if test=\"startDate != null and startDate!='' \"> AND DATE_FORMAT(start_time,'%Y-%m-%d')=#{startDate} </if> </where>"+
            "HAVING (select count(*)>0  from bet_topics as b where a.id=b.data_id and b.status!='canceled')) AS dd GROUP BY STATUS </script>")
    List<Map<String,Object>> countGameResultByRolling(@Param("gameIds") String gameIds, String startDate);

    @Select("<script> SELECT count(*) AS a FROM (SELECT id,`status` FROM game_data AS a <where> `status`='not_start_yet' " +
            " <if test=\"gameIds != null and gameIds!='' \"> AND game_id in (${gameIds}) </if> "+
            " <if test=\"startDate != null and startDate!='' \"> AND DATE_FORMAT(start_time,'%Y-%m-%d')=#{startDate} </if> </where>"+
            "HAVING (select count(*)>0  from bet_topics as b where a.id=b.data_id and b.status!='canceled')) AS dd  </script>")
    Integer countGameResultByBegin(@Param("gameIds") String gameIds, String startDate);


    @Select("<script>  select count(*) from ( " +
            " select * from game_data as a <where> status='ongoing' " +
            " <if test=\"gameIds != null and gameIds!='' \"> AND game_id in (${gameIds}) </if> </where>"+
            "             having (select count(*)>0  from bet_topics as b where a.id=b.data_id and b.status!='canceled' and rolling_ball=1)  " +
            "  UNION ALL " +
            " select * from  game_data as a <where> a.status='not_start_yet' and a.start_time>now()  " +
            " <if test=\"gameIds != null and gameIds!='' \"> AND game_id in (${gameIds}) </if> </where>"+
            " having (select count(*)>0  from bet_topics as b where a.id=b.data_id and b.status!='canceled' and rolling_ball=1) ) as dd </script>")
    Integer countGameResultByRollingInt(@Param("gameIds") String gameIds);



    @Select(" <script> SELECT tmpa.id,tmpa.league_id,tmpa.start_time,tmpa.game_id FROM (SELECT a.*FROM game_data AS a <where> a.start_time&gt;=(now()-INTERVAL 0.5 DAY) AND a.start_time&lt;=(now()+INTERVAL 0.5 DAY) "+
            "AND a.STATUS IN ('not_start_yet','ongoing')"+
            "</where>) AS tmpa INNER JOIN (SELECT DISTINCT data_id FROM bet_topics WHERE `status`='default' and final_switch=0) AS tmpb ON tmpa.id=tmpb.data_id ORDER BY tmpa.start_time limit 0,5 </script>")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "league_id",property = "league",one=@One(select = "com.splan.xbet.admin.back.mappers.GameLeagueBeanMapper.selectById")),
            @Result(column = "id",property = "teams",many = @Many(select = "com.splan.xbet.admin.back.mappers.GameDataTeamBeanMapper.selectGameDataTeamByDataId")),
            @Result(column = "game_id",property = "gameTypeBean",one=@One(select = "com.splan.xbet.admin.back.mappers.GameTypeBeanMapper.selectById")),
            @Result(column = "id",property = "orderCount",many = @Many(select = "com.splan.xbet.admin.back.mappers.BetOrderDetailBeanMapper.countOrder"))
    })
    List<GameResult> selectGameResultByHotGame();

    //xBet
}