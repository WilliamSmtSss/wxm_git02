package com.splan.xbet.admin.back.mappers;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.xbet.admin.back.base.SuperMapper;
import com.splan.base.bean.BetTopicsBean;
import com.splan.base.enums.orderenums.BetOrderBy;
import com.splan.base.enums.orderenums.OrderByComm;
import com.splan.xbet.admin.back.dto.DateDto;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
//@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface BetTopicsBeanMapper extends SuperMapper<BetTopicsBean> {


    @InsertProvider(type=BetTopicsBeanSqlProvider.class, method="insertSelective")
    int insertSelective(BetTopicsBean record);

    //@Select("select *,id from bet_topics where topicable_id=#{topicableId} and topicable_type='Series' ")
    @Select("SELECT*FROM (\n" +
            "SELECT*FROM bet_topics WHERE topicable_id=#{topicableId} AND topicable_type='Series') AS a INNER JOIN (SELECT category,support,topicable_id,topicable_type,max(rolling_ball) AS rb FROM bet_topics WHERE topicable_id=#{topicableId} AND topicable_type='Series' GROUP BY category,support,topicable_id,topicable_type) AS b ON a.category=b.category AND a.support=b.support AND a.topicable_id=b.topicable_id AND a.rolling_ball=b.rb WHERE `status`='default'  UNION ALL SELECT * FROM (\n" +
            "SELECT*FROM bet_topics WHERE topicable_id=#{topicableId} AND topicable_type='Series') AS a INNER JOIN (SELECT category,support,topicable_id,topicable_type,max(rolling_ball) AS rb FROM bet_topics WHERE topicable_id=#{topicableId} AND topicable_type='Series' GROUP BY category,support,topicable_id,topicable_type) AS b ON a.category=b.category AND a.support=b.support AND a.topicable_id=b.topicable_id AND a.rolling_ball=b.rb WHERE `status`='checked'")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "id",property = "betOptions",many=@Many(select = "com.splan.xbet.admin.back.mappers.BetOptionBeanMapper.selectByBetDataId"))
    })
    List<BetTopicsBean> selectSeriByTopicableId(Integer topicableId);


    @Select("SELECT a.*,number FROM bet_topics as a LEFT JOIN game_campaign as b on a.topicable_id=b.id where a.topicable_type='Campaign' and " +
            "b.data_id=#{topicableId} and status!='canceled' and rolling_ball=1   UNION ALL select *,0 as number from bet_topics where topicable_id=#{topicableId} and topicable_type='Series' and status!='canceled' rolling_ball=1 ")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "id",property = "betOptions",many=@Many(select = "com.splan.xbet.admin.back.mappers.BetOptionBeanMapper.selectByBetDataId"))
    })
    List<BetTopicsBean> selectRollingByTopicableId(Integer topicableId);

    //@Select("select *,id from bet_topics where topicable_id=#{topicableId} and topicable_type='Campaign'  ")
    @Select("SELECT*FROM (\n" +
            "SELECT*FROM bet_topics WHERE topicable_id=#{topicableId} AND topicable_type='Campaign') AS a INNER JOIN (SELECT category,support,topicable_id,topicable_type,max(rolling_ball) AS rb FROM bet_topics WHERE topicable_id=#{topicableId} AND topicable_type='Campaign' GROUP BY category,support,topicable_id,topicable_type) AS b ON a.category=b.category AND a.support=b.support AND a.topicable_id=b.topicable_id AND a.rolling_ball=b.rb WHERE `status`='default'  UNION ALL SELECT * FROM (\n" +
            "SELECT*FROM bet_topics WHERE topicable_id=#{topicableId} AND topicable_type='Campaign') AS a INNER JOIN (SELECT category,support,topicable_id,topicable_type,max(rolling_ball) AS rb FROM bet_topics WHERE topicable_id=#{topicableId} AND topicable_type='Campaign' GROUP BY category,support,topicable_id,topicable_type) AS b ON a.category=b.category AND a.support=b.support AND a.topicable_id=b.topicable_id AND a.rolling_ball=b.rb WHERE `status`='checked'")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "id",property = "betOptions",many=@Many(select = "com.splan.xbet.admin.back.mappers.BetOptionBeanMapper.selectByBetDataId"))
    })
    List<BetTopicsBean> selectByTopicableId(Integer topicableId);


    @Select("SELECT a.*,number FROM bet_topics as a LEFT JOIN game_campaign as b on a.topicable_id=b.id where a.topicable_type='Campaign' and " +
            "b.data_id=#{topicableId} and status!='canceled'   UNION ALL select *,0 as number from bet_topics where topicable_id=#{topicableId} and topicable_type='Series' and status!='canceled' ")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "id",property = "betOptions",many=@Many(select = "com.splan.xbet.admin.back.mappers.BetOptionBeanMapper.selectByBetDataId"))
    })
    List<BetTopicsBean> selectAllByDataId(Integer dataId);


    @Select("  SELECT * from ( SELECT a.*,number FROM bet_topics as a LEFT JOIN game_campaign as b on a.topicable_id=b.id where a.topicable_type='Campaign' and " +
            "b.data_id=#{dataId}   UNION ALL select *,0 as number from bet_topics where topicable_id=#{dataId} and topicable_type='Series' ) as a where a.number=#{number}")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "id",property = "betOptions",many=@Many(select = "com.splan.xbet.admin.back.mappers.BetOptionBeanMapper.selectByBetDataId"))
    })
    List<BetTopicsBean> selectAllByDataIdAndNumber(Integer dataId, Integer number);

    //@Select("select *,id from bet_topics where data_id=#{dataId} and topicable_type='Series' and category=1 and support=0 limit 0,1")
    //@Select("select *,id from bet_topics where data_id=#{dataId} and  final_switch=1 and `status`='default' ORDER BY topicable_type desc  limit 0,1")
    @Select("  SELECT a.*,a.id,b.number from (select * from bet_topics where data_id=#{dataId} and  final_switch=1 and `status`='default' ORDER BY topicable_type desc,category,topicable_id  limit 0,1) as a \n" +
            "\t LEFT JOIN game_campaign as b\n" +
            "\t on a.topicable_id=b.id and a.topicable_type='Campaign' ")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "id",property = "betOptions",many=@Many(select = "com.splan.xbet.admin.back.mappers.BetOptionBeanMapper.selectByBetDataId"))
    })
    BetTopicsBean selectSeriesByDataId(Integer dataId);

    @Select("select * from bet_topics where data_id=#{dataId} and `status`='checked' ORDER BY topicable_type desc,category,topicable_id  limit 0,1 ")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "id",property = "betOptions",many=@Many(select = "com.splan.xbet.admin.back.mappers.BetOptionBeanMapper.selectByBetDataId"))
    })
    BetTopicsBean selectSeriesEndByDataId(Integer dataId);

    @Select("select count(*) as a from bet_topics where data_id=#{dataId} and status!='canceled' ")
    Integer countByDataId(Integer dataId);

    @Select("select count(*) as a from bet_topics where data_id=#{dataId} and status!='canceled' ")
    Integer countEndByDataId(Integer dataId);

    @Select("<script>select a.*, c.name_en name_en ,d.id as optionid,d.sequence as sequence from bet_topics a, game_data b, game_type c,bet_option d " +
            "<where> a.data_id = b.id and b.game_id = c.id and d.bet_data_id=a.id" +
            " <if test='gameType != null '> AND c.id = #{gameType} </if> "+
            " <if test='startDate != null '> AND to_days(b.start_time) <![CDATA[>=]]> to_days(#{startDate}) </if> "+
            " <if test='endDate != null '> AND to_days(b.start_time) <![CDATA[<=]]> to_days(#{endDate}) </if> "+
            " <if test='status != null '> AND a.status=#{status} </if> "+
            "</where>" +
            "order by b.start_time desc" +
            "</script>")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "id",property = "betOptions",many=@Many(select = "com.splan.xbet.admin.back.mappers.BetOptionBeanMapper.selectByBetDataId")),
            @Result(column = "optionid",property = "optionid",javaType=Integer.class),
            @Result(column = "sequence",property = "sequence",javaType=Integer.class)
    })
    List<BetTopicsBean> selectAll(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("status") String status, @Param("gameType") Integer gameType, IPage page);
// 查有订单的盘口
    @Select("<script>select a.*, c.name_en name_en ,d.id as optionid,d.sequence as sequence,e.* from bet_topics a, game_data b, game_type c,bet_option d,\n" +
//            排序用
            "(SELECT\n" +
            "\tt2.*, (\n" +
            "\t\tt2.returnamount - t2.orderamount\n" +
            "\t) AS rewardamount,\n" +
            "\tifnull(CONVERT (\n" +
            "\t\t(\n" +
            "\t\t\tt2.returnamount - t2.orderamount\n" +
            "\t\t) / t2.orderamount,\n" +
            "\t\tDECIMAL (10, 4)\n" +
            "\t),0) * 100 AS rewardrate\n" +
            "FROM\n" +
            "\t(\n" +
            "\t\tSELECT\n" +
            "\t\t\tt.bet_option_id,\n" +
            "\t\t\tifnull(sum(\n" +
            "\t\t\t\tCASE\n" +
            "\t\t\t\tWHEN t. STATUS = 'SETTLED' and t.tenant_user_no in(<foreach collection='userids' item='userid' separator=','>#{userid}</foreach>) THEN\n" +
            "\t\t\t\t\tt.amount\n" +
            "\t\t\t\tEND\n" +
            "\t\t\t),0) AS orderamount,\n" +
            "\t\t\tifnull(sum(\n" +
            "\t\t\t\tCASE\n" +
            "\t\t\t\tWHEN t. STATUS = 'SETTLED'\n" +
            "\t\t\t\tAND t.win_lose = 'WIN' and t.tenant_user_no in(<foreach collection='userids' item='userid' separator=','>#{userid}</foreach>) THEN\n" +
            "\t\t\t\t\tt.estimated_reward\n" +
            "\t\t\t\tEND\n" +
            "\t\t\t),0) AS returnamount,\n" +
            "\t\t\tifnull(count(\n" +
            "\t\t\t\tCASE\n" +
            "\t\t\t\tWHEN t. STATUS = 'SETTLED' and t.tenant_user_no in(<foreach collection='userids' item='userid' separator=','>#{userid}</foreach>) THEN\n" +
            "\t\t\t\t\t1\n" +
            "\t\t\t\tEND\n" +
            "\t\t\t),0) AS ordercount,\n" +
            "\t\t\tifnull(count(\n" +
            "\t\t\t\tDISTINCT CASE\n" +
            "\t\t\t\tWHEN t. STATUS = 'SETTLED'  and t.tenant_user_no in(<foreach collection='userids' item='userid' separator=','>#{userid}</foreach>) THEN\n" +
            "\t\t\t\t\tt.tenant_user_no\n" +
            "\t\t\t\tEND\n" +
            "\t\t\t),0) AS usercount\n" +
            "\t\tFROM\n" +
            "\t\t\tbet_order t\n" +
            "\t\tGROUP BY\n" +
            "\t\t\tt.bet_option_id\n" +
            "\t) t2) e\n"+
//            END
            "<where> a.data_id = b.id and b.game_id = c.id and d.bet_data_id=a.id and d.id=e.bet_option_id" +
            " <if test='gameType != null '> AND c.id = #{gameType} </if> "+
            " <if test='startDate != null '> AND to_days(b.start_time) <![CDATA[>=]]> to_days(#{startDate}) </if> "+
            " <if test='endDate != null '> AND to_days(b.start_time) <![CDATA[<=]]> to_days(#{endDate}) </if> "+
            " <if test='status != null'> AND a.status=#{status} </if> "+
            "</where>\n" +
          //  "order by b.start_time desc" +
           // "left join\n"+
           // "(select t.bet_option_id, sum(case when t.status='SETTLED' then t.amount end) as orderamount,sum(case when t.status='UNSETTLED' and t.win_lose='WIN' then t.estimated_reward end) as returnamount from bet_order t GROUP BY t.bet_option_id) b\n"+
           // "on a.optionid=b.bet_option_id \n"+
            "<if test='betOrder!=null'>order by ${betOrder} <if test='orderType!=null'>${orderType}</if></if>"+
            "<if test='betOrder==null'>order by b.start_time desc</if>"+
            "</script>")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "id",property = "betOptions",many=@Many(select = "com.splan.xbet.admin.back.mappers.BetOptionBeanMapper.selectByBetDataId")),
            @Result(column = "optionid",property = "optionid",javaType=Integer.class),
            @Result(column = "sequence",property = "sequence",javaType=Integer.class),
            @Result(column = "orderamount",property = "orderamount",javaType=Integer.class)
    })
    List<BetTopicsBean> selectAllOrderBy(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("status") String status, @Param("gameType") Integer gameType, IPage page, @Param("betOrder") BetOrderBy betOrderBy, @Param("orderType") OrderByComm orderType, @Param("userids") List<Long> userids);

    //查所有盘口
    @Select("<script>select * from ( select a.*, c.name_en name_en ,d.id as optionid,d.sequence as sequence,b.start_time from bet_topics a, game_data b, game_type c,bet_option d\n" +
            "<where> a.data_id = b.id and b.game_id = c.id and d.bet_data_id=a.id" +
            " <if test='gameType != null '> AND c.id = #{gameType} </if> "+
            " <if test='startDate != null '> AND to_days(b.start_time) <![CDATA[>=]]> to_days(#{startDate}) </if> "+
            " <if test='endDate != null '> AND to_days(b.start_time) <![CDATA[<=]]> to_days(#{endDate}) </if> "+
            " <if test='status != null'> AND a.status=#{status} </if> "+
            "</where>) e\n" +
          //  "<if test=''>left join</if>\n"+
            "<if test='hasOrder==\"0\"'>left join</if>\n"+
            "<if test='hasOrder==\"1\"'>inner join</if>\n"+
//            排序用
            "(SELECT\n" +
            "\tt2.*, (\n" +
            "\t\tt2.orderamount - t2.returnamount\n" +
            "\t) AS rewardamount,\n" +
            "\tifnull(CONVERT (\n" +
            "\t\t(\n" +
            "\t\t\tt2.orderamount - t2.returnamount\n" +
            "\t\t) / t2.orderamount,\n" +
            "\t\tDECIMAL (10, 4)\n" +
            "\t),0) * 100 AS rewardrate\n" +
            "FROM\n" +
            "\t(\n" +
            "\t\tSELECT\n" +
            "\t\t\tt.bet_option_id,\n" +
            "\t\t\tifnull(sum(\n" +
            "\t\t\t\tCASE\n" +
            "\t\t\t\tWHEN t. STATUS = 'SETTLED' and t.tenant_user_no in(<foreach collection='userids' item='userid' separator=','>#{userid}</foreach>) THEN\n" +
            "\t\t\t\t\tt.amount\n" +
            "\t\t\t\tEND\n" +
            "\t\t\t),0) AS orderamount,\n" +
            "\t\t\tifnull(sum(\n" +
            "\t\t\t\tCASE\n" +
            "\t\t\t\tWHEN t. STATUS = 'SETTLED'\n" +
            "\t\t\t\tAND t.win_lose = 'WIN' and t.tenant_user_no in(<foreach collection='userids' item='userid' separator=','>#{userid}</foreach>) THEN\n" +
            "\t\t\t\t\tt.estimated_reward\n" +
            "\t\t\t\tEND\n" +
            "\t\t\t),0) AS returnamount,\n" +
            "\t\t\tifnull(count(\n" +
            "\t\t\t\tCASE\n" +
            "\t\t\t\tWHEN t. STATUS = 'SETTLED' and t.tenant_user_no in(<foreach collection='userids' item='userid' separator=','>#{userid}</foreach>) THEN\n" +
            "\t\t\t\t\t1\n" +
            "\t\t\t\tEND\n" +
            "\t\t\t),0) AS ordercount,\n" +
            "\t\t\tifnull(count(\n" +
            "\t\t\t\tDISTINCT CASE\n" +
            "\t\t\t\tWHEN t. STATUS = 'SETTLED'  and t.tenant_user_no in(<foreach collection='userids' item='userid' separator=','>#{userid}</foreach>) THEN\n" +
            "\t\t\t\t\tt.tenant_user_no\n" +
            "\t\t\t\tEND\n" +
            "\t\t\t),0) AS usercount\n" +
            "\t\tFROM\n" +
            "\t\t\tbet_order t\n" +
            "\t\tGROUP BY\n" +
            "\t\t\tt.bet_option_id\n" +
            "\t) t2) f\n"+
//            END
            " on e.optionid=f.bet_option_id\n"+
            //  "order by b.start_time desc" +
            // "left join\n"+
            // "(select t.bet_option_id, sum(case when t.status='SETTLED' then t.amount end) as orderamount,sum(case when t.status='UNSETTLED' and t.win_lose='WIN' then t.estimated_reward end) as returnamount from bet_order t GROUP BY t.bet_option_id) b\n"+
            // "on a.optionid=b.bet_option_id \n"+
            "<if test='betOrder!=null'>order by ${betOrder} <if test='orderType!=null'>${orderType}</if></if>"+
            "<if test='betOrder==null'>order by e.start_time desc</if>"+
            "</script>")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "id",property = "betOptions",many=@Many(select = "com.splan.xbet.admin.back.mappers.BetOptionBeanMapper.selectByBetDataId")),
            @Result(column = "optionid",property = "optionid",javaType=Integer.class),
            @Result(column = "sequence",property = "sequence",javaType=Integer.class),
            @Result(column = "orderamount",property = "orderamount",javaType=Integer.class)
    })
    List<BetTopicsBean> selectAllOrderBy2(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("status") String status, @Param("gameType") Integer gameType, IPage page, @Param("betOrder") BetOrderBy betOrderBy, @Param("orderType") OrderByComm orderType, @Param("userids") List<Long> userids, @Param("hasOrder") String hasOrder);

    @Select({
            "select * from bet_topics a, bet_option b where b.bet_data_id = a.id and b.id = #{optionId}"
    })
    List<BetTopicsBean> selectTopicByOptionId(@Param("optionId") Integer optionId);

    @Select("<script>select * from( select a.*, c.name_en name_en ,d.id as optionid,d.sequence as sequence,b.start_time from bet_topics a, game_data b, game_type c,bet_option d " +
            "<where> a.data_id = b.id and b.game_id = c.id and d.bet_data_id=a.id" +
            " <if test='gameType != null '> AND c.id = #{gameType} </if> "+
            " <if test='startDate != null '> AND to_days(b.start_time) <![CDATA[>=]]> to_days(#{startDate}) </if> "+
            " <if test='endDate != null '> AND to_days(b.start_time) <![CDATA[<=]]> to_days(#{endDate}) </if> "+
            " <if test='status != null '> AND a.status=#{status} </if> "+
            "</where>) e\n" +
            "<if test='hasOrder!=null and hasOrder==\"1\"'> inner join (select t.bet_option_id from bet_order t group by t.bet_option_id) f on e.optionid=f.bet_option_id\n</if>"+
            "order by e.start_time desc" +
            "</script>")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "id",property = "betOptions",many=@Many(select = "com.splan.xbet.admin.back.mappers.BetOptionBeanMapper.selectByBetDataId")),
            @Result(column = "optionid",property = "optionid",javaType=Integer.class),
            @Result(column = "sequence",property = "sequence",javaType=Integer.class)
    })
    List<BetTopicsBean> selectAll2(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("gameType") Integer gameType, @Param("status") String status, @Param("hasOrder") String hasOrder);

    //风暴娱乐后台
    //查询某天某厅的盘口数量

    /**
     * 准备滚盘
     * @param dataId
     * @return
     */
    @Select("select 1 from bet_topics where data_id=#{dataId} and be_to_rolling=1 LIMIT 1")
    Integer countBeToRollingByDataId(Integer dataId);

    /**
     * 正在滚盘
     * @param dataId
     * @return
     */
    @Select("select count(*) from bet_topics where data_id=#{dataId} and rolling_ball=1 LIMIT 1")
    Integer countRollingByDataId(Integer dataId);


    @Select("SELECT *,id from bet_topics where topicable_id=#{topicableId} and topicable_type='Series' ")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "id",property = "betOptions",many=@Many(select = "com.splan.xbet.admin.back.mappers.BetOptionBeanMapper.selectByBetDataId"))
    })
    List<BetTopicsBean> selectListSeriesEndByDataId(Integer topicableId);

    @Select("select *,id from bet_topics where topicable_id=#{topicableId} and topicable_type='Campaign'  ")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class),
            @Result(column = "id",property = "betOptions",many=@Many(select = "com.splan.xbet.admin.back.mappers.BetOptionBeanMapper.selectByBetDataId"))
    })
    List<BetTopicsBean> selectListEndByTopicableId(Integer topicableId);

    //xBet
    @Select("<script>" +
            "select * from x_bet_view t <where> 1=1 " +
            " <if test='gameId != null and gameId!=\"\"'> AND t.gameId=#{gameId} </if> "+
            " <if test='startDate != null '> AND to_days(t.update_time) <![CDATA[>=]]> to_days(#{startDate}) </if> "+
            " <if test='endDate != null '> AND to_days(t.update_time) <![CDATA[<=]]> to_days(#{endDate}) </if> "+
            " <if test='dataId != null and dataId!=\"\"'> AND t.data_id=#{dataId} </if> "+
            " <if test='betId != null and betId!=\"\"'> AND t.id=#{betId} </if> "+
            " <if test='betStatus != null and betStatus!=\"\"'> AND t.status=#{betStatus} </if> "+
            "</where>"+
            " order by t.create_time desc"+
            "</script>")
    List<BetTopicsBean> getXbetBetData(Page page, String gameId, Date startDate, Date endDate, String dataId, String betId, String betStatus);
}