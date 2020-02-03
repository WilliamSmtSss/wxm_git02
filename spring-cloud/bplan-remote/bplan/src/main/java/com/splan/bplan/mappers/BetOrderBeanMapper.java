package com.splan.bplan.mappers;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.base.SuperMapper;
import com.splan.base.bean.BetOrderBean;
import com.splan.base.bean.UserBean;
import com.splan.bplan.dto.ScreenForBetOrderDto;
import com.splan.base.enums.OrderStatus;
import com.splan.base.enums.orderenums.OrderByComm;
import com.splan.base.enums.orderenums.OrderOrderBy;
import com.splan.base.enums.orderenums.UserOrderOrderBy;
import com.splan.bplan.result.BetOrderResult;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface BetOrderBeanMapper extends SuperMapper<BetOrderBean> {

    @Select("<script>select * from bet_order <where> status in (${orderStatus}) and tenant_user_no=#{userId} " +
            " <if test='orderDate != null '> AND DATE_FORMAT(create_time,'%Y-%m-%d') = #{orderDate} </if> " +
            "</where> order by create_time desc </script>")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Long.class),
            @Result(column = "id",property = "betOrderDetails",many = @Many(select = "com.splan.bplan.mappers.BetOrderDetailBeanMapper.selectByOrderId"))
    })
    List<BetOrderBean> selectByType(Page page, String orderStatus, String orderDate,Long userId);

    @Select("select * from bet_order where id=#{id}")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Long.class),
            @Result(column = "id",property = "betOrderDetails",many = @Many(select = "com.splan.bplan.mappers.BetOrderDetailBeanMapper.selectByOrderId"))
    })
    BetOrderBean selectById(Long id);

    @Select("select * from bet_order where tenant_order_no=#{orderNo}")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Long.class),
            @Result(column = "id",property = "betOrderDetails",many = @Many(select = "com.splan.bplan.mappers.BetOrderDetailBeanMapper.selectByOrderId"))
    })
    BetOrderBean selectByOrderNo(String orderNo);

    @Select("select b.mobile,a.amount,a.create_time from bet_order as a LEFT JOIN user_account as b on a.tenant_user_no=b.id WHERE to_days(a.create_time)=to_days(now()) order by create_time desc LIMIT 0,100")
    List<BetOrderResult> selectRollOrderList();

    @SelectProvider(type=BetOrderBeanSqlProvider.class, method="selectByDate")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Long.class),
            @Result(column = "id",property = "betOrderDetails",many = @Many(select = "com.splan.bplan.mappers.BetOrderDetailBeanMapper.selectByOrderId"))
    })
    List<BetOrderBean> selectByDate(Page page, JSONObject jsonObject);

    @Select("<script>select a.*,a.amount as orderamount,a.create_time as createtime,ifnull((a.amount-a.estimated_reward),0) as rewardamount, d.name_en name_en from bet_order a, bet_topics b, game_data c, game_type d" +
            "<where> a.data_id = b.id and b.data_id = c.id and c.game_id = d.id" +
            " <if test='userId != null '> AND a.tenant_user_no = #{userId} </if> "+
            " <if test='gameType != null '> AND d.id = #{gameType} </if> "+
            " <if test='startDate != null '> AND to_days(a.create_time) <![CDATA[>=]]> to_days(#{startDate}) </if> "+
            " <if test='endDate != null '> AND to_days(a.create_time) <![CDATA[<=]]> to_days(#{endDate}) </if> "+
            "</where>" +
            "<if test='userOrderOrderBy!=null'> order by ${userOrderOrderBy}<if test='orderByComm!=null'>${orderByComm}</if></if>"+
            "<if test='userOrderOrderBy==null'>order by a.create_time desc</if>" +
            "</script>")
    List<BetOrderBean> selectByUserId(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
                                      @Param("gameType")Integer gameType, @Param("userId")Long userId, IPage page,@Param("userOrderOrderBy") UserOrderOrderBy userOrderOrderBy,@Param("orderByComm") OrderByComm orderByComm);

    @Select("<script>select a.*,a.amount as orderamount,a.create_time as createtime,ifnull((a.amount-a.estimated_reward),0) as rewardamount,ifnull(b.name_en,'串注') as name_en from bet_order a " +
            " left join (select t1.id,t4.id as gameid,t4.name_en from bet_option t1,bet_topics t2,game_data t3,game_type t4 where t1.bet_data_id=t2.id and t2.data_id=t3.id and t3.game_id=t4.id) b on a.bet_option_id=b.id "+
            "<where>" +
            " <if test='userId != null '> AND a.tenant_user_no = #{userId} </if> "+
            " <if test='gameType != null '> AND b.gameid = #{gameType} </if> "+
            " <if test='startDate != null '> AND to_days(a.create_time) <![CDATA[>=]]> to_days(#{startDate}) </if> "+
            " <if test='endDate != null '> AND to_days(a.create_time) <![CDATA[<=]]> to_days(#{endDate}) </if> "+
            "</where>" +
            "<if test='userOrderOrderBy!=null'> order by ${userOrderOrderBy}<if test='orderByComm!=null'>${orderByComm}</if></if>"+
            "<if test='userOrderOrderBy==null'>order by a.create_time desc</if>" +
            "</script>")
    List<BetOrderBean> selectByUserId2(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
                                      @Param("gameType")Integer gameType, @Param("userId")Long userId, IPage page,@Param("userOrderOrderBy") UserOrderOrderBy userOrderOrderBy,@Param("orderByComm") OrderByComm orderByComm);

    @Select("<script>select a.*,a.amount as orderamount,a.create_time as createtime,ifnull((a.amount-a.estimated_reward),0) as rewardamount,ifnull(b.name_en,'串注') as name_en from bet_order a " +
            " left join (select t1.id,t4.id as gameid,t4.name_en from bet_option t1,bet_topics t2,game_data t3,game_type t4 where t1.bet_data_id=t2.id and t2.data_id=t3.id and t3.game_id=t4.id) b on a.bet_option_id=b.id "+
            "<where>" +
            " <if test='userId != null '> AND a.tenant_user_no = #{userId} </if> "+
            " <if test='gameType != null '> AND b.gameid = #{gameType} </if> "+
            " <if test='startDate != null '> AND to_days(a.create_time) <![CDATA[>=]]> to_days(#{startDate}) </if> "+
            " <if test='endDate != null '> AND to_days(a.create_time) <![CDATA[<=]]> to_days(#{endDate}) </if> "+
            "</where>" +
            "order by a.create_time desc" +
            "</script>")
    List<BetOrderBean> selectByUserIdNoPage(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
                                      @Param("gameType")Integer gameType, @Param("userId")Long userId);

    //@Select(" select a.*,b.api_id,b.extra_id from bet_order as a LEFT JOIN user_account as b on a.tenant_user_no=b.id where b.api_id is not null and sync_flag='SYNC' and a.status='SETTLED' and bet_option_id=#{optionId} ")
    @Select(" SELECT DISTINCT  a.*,b.api_id,b.extra_id FROM bet_order_detail as d LEFT JOIN  bet_order AS a on d.bet_order_id=a.id LEFT JOIN user_account AS b ON a.tenant_user_no=b.id where b.api_id is not null and a.sync_flag='SYNC' and a.status='SETTLED' and d.bet_option_id=#{optionId} ")
    List<BetOrderBean> selectByOptionId(Integer optionId);

    @Select(" SELECT * FROM bet_order AS a LEFT JOIN user_account AS b ON a.tenant_user_no=b.id WHERE a.sync_flag='SYNC' AND a.`status`='SETTLED' AND b.api_id IS NOT NULL ")
    List<BetOrderBean> selectByYesterday();

    @Select("select ifnull(sum(t.amount),0) as amount from bet_order t where t.status='SETTLED' and tenant_user_no =#{userId}")
    Integer getTotleOrderByuserId(Long userId);

    @Select("select ifnull(sum(t.estimated_reward),0.00) as estimated_reward from bet_order t where t.status='SETTLED' and tenant_user_no =#{userId}")
    BigDecimal getTotleRewardByuserId(Long userId);

    @Select("<script>select ifnull(sum(t.amount),0)-ifnull(sum(t.estimated_reward),0.00) from bet_order t " +
            "<where> 1=1" +
            " <if test='userids != null '> AND t.tenant_user_no in (" +
            " <foreach collection='userids' item='userid' separator=','>#{userid}" +
            " </foreach>) </if> "+
            "</where>"+
            "</script>")
    BigDecimal getAllprofit(@Param("userids") List<Integer> userids);

    @Select("select ifnull(sum(t.amount),0.00) from bet_order t where to_days(t.create_time)=to_days(nows()) and t.status='SETTLED'")
    BigDecimal getTodayOrderAmount();

    @Select("select ifnull(sum(t.amount),0.00) from bet_order t where to_days(t.create_time)=to_days(nows())-1 and t.status='SETTLED'")
    BigDecimal getYestTodayOrderAmount();

    @Select("select ifnull(sum(t.amount),0.00)-ifnull(sum(t.estimated_reward),0.00) from bet_order t  where to_days(t.create_time)=to_days(nows()) and t.status='SETTLED'")
    BigDecimal getTodayProfitAmount();

    @Select("select ifnull(sum(t.amount),0.00)-ifnull(sum(t.estimated_reward),0.00) from bet_order t  where to_days(t.create_time)=to_days(nows())-1 and t.status='SETTLED'")
    BigDecimal getYestTodayProfitAmount();
//单注查询
    @Select("<script>select a.*,a.amount as orderamount,a.create_time as createtime,ifnull((a.estimated_reward-a.amount),0) as rewardamount,c.api_id as channel,c.username as useracc,ifnull(c.extra_id,'') as extra_id,d.name_en as gametype from bet_order a,game_data b,user_account c,game_type d where a.data_id=b.id and a.tenant_user_no=c.id and b.game_id=d.id " +
            "<if test='screenForBetOrderDto.username!=null'>and c.username=#{screenForBetOrderDto.username}\n</if>" +
            "<if test='screenForBetOrderDto.gametype!=null'>and b.game_id=#{screenForBetOrderDto.gametype}\n</if>" +
            "<if test='screenForBetOrderDto.orderstatus!=null'>and a.status=#{screenForBetOrderDto.orderstatus}\n</if>" +
            "<if test='screenForBetOrderDto.orderresult!=null'>and a.win_lose=#{screenForBetOrderDto.orderresult}\n</if>" +
            "<if test='screenForBetOrderDto.ordreid!=null'>and a.tenant_order_no=#{screenForBetOrderDto.ordreid}\n</if>" +
            "<if test='screenForBetOrderDto.ordertype!=null'>and a.order_type=#{screenForBetOrderDto.ordertype}\n</if>" +
            "<if test='userIds!=null'>and a.tenant_user_no in" +
            "(<foreach collection='userIds' item='userid' separator=','>#{userid}</foreach>)\n" +
            "</if>" +
            "<if test='orderOrderBy!=null'> order by ${orderOrderBy}<if test='orderByComm!=null'>${orderByComm}</if></if>"+
            "<if test='orderOrderBy==null'>order by a.create_time desc</if>" +
            "</script>")
    List<BetOrderBean> screenBetOrder(IPage page, @Param("screenForBetOrderDto") ScreenForBetOrderDto screenForBetOrderDto, @Param("userIds") List<Long> userIds, OrderOrderBy orderOrderBy, OrderByComm orderByComm);

    //串注查询
    @Select("<script>select * from ( select a.*,a.amount as orderamount,a.create_time as createtime,ifnull((a.estimated_reward-a.amount),0) as rewardamount,c.api_id as channel,c.username as useracc,ifnull(c.extra_id,'') as extra_id from bet_order a,user_account c where a.tenant_user_no=c.id ) b " +
//        "<if test='screenForBetOrderDto.ordertype==null or screenForBetOrderDto.ordertype==\"1\"'>inner join bet_order_detail d on b.id=d.bet_order_id  </if>"+
        "<if test='screenForBetOrderDto.ordertype!=null and screenForBetOrderDto.ordertype==\"0\"'>inner join game_data d on b.data_id=d.id  </if>"+
            "<where> 1=1 "+
        "<if test='screenForBetOrderDto.username!=null'>and b.username=#{screenForBetOrderDto.username}\n</if>" +
        "<if test='screenForBetOrderDto.gametype!=null and screenForBetOrderDto.ordertype==\"0\"'>and d.game_id=#{screenForBetOrderDto.gametype}\n</if>" +
        "<if test='screenForBetOrderDto.orderstatus!=null'>and b.status=#{screenForBetOrderDto.orderstatus}\n</if>" +
        "<if test='screenForBetOrderDto.orderresult!=null'>and b.win_lose=#{screenForBetOrderDto.orderresult}\n</if>" +
        "<if test='screenForBetOrderDto.ordreid!=null'>and b.tenant_order_no=#{screenForBetOrderDto.ordreid}\n</if>" +
        "<if test='screenForBetOrderDto.ordertype!=null'>and b.order_type=#{screenForBetOrderDto.ordertype}\n</if>" +
        "<if test='userIds!=null'>and b.tenant_user_no in" +
        "(<foreach collection='userIds' item='userid' separator=','>#{userid}</foreach>)\n" +
        "</if>" +
        "<if test='orderOrderBy!=null'> order by ${orderOrderBy}<if test='orderByComm!=null'>${orderByComm}</if></if>"+
        "<if test='orderOrderBy==null'>order by b.create_time desc</if>" +
        "</where></script>")
    List<BetOrderBean> screenBetOrder2(IPage page, @Param("screenForBetOrderDto") ScreenForBetOrderDto screenForBetOrderDto, @Param("userIds") List<Long> userIds, OrderOrderBy orderOrderBy, OrderByComm orderByComm);

    @Select("<script>select * from ( select a.*,a.amount as orderamount,a.create_time as createtime,ifnull((a.estimated_reward-a.amount),0) as rewardamount,c.api_id as channel,c.username as useracc,ifnull(c.extra_id,'') as extra_id from bet_order a,user_account c where a.tenant_user_no=c.id ) b " +
//        "<if test='screenForBetOrderDto.ordertype==null or screenForBetOrderDto.ordertype==\"1\"'>inner join bet_order_detail d on b.id=d.bet_order_id  </if>"+
            "<if test='screenForBetOrderDto.ordertype!=null and screenForBetOrderDto.ordertype==\"0\"'>inner join game_data d on b.data_id=d.id  </if>"+
            "<where> 1=1 "+
            "<if test='screenForBetOrderDto.username!=null'>and b.username=#{screenForBetOrderDto.username}\n</if>" +
            "<if test='screenForBetOrderDto.gametype!=null and screenForBetOrderDto.ordertype==\"0\"'>and d.game_id=#{screenForBetOrderDto.gametype}\n</if>" +
            "<if test='screenForBetOrderDto.orderstatus!=null'>and b.status=#{screenForBetOrderDto.orderstatus}\n</if>" +
            "<if test='screenForBetOrderDto.orderresult!=null'>and b.win_lose=#{screenForBetOrderDto.orderresult}\n</if>" +
            "<if test='screenForBetOrderDto.ordreid!=null'>and b.tenant_order_no=#{screenForBetOrderDto.ordreid}\n</if>" +
            "<if test='screenForBetOrderDto.ordertype!=null'>and b.order_type=#{screenForBetOrderDto.ordertype}\n</if>" +
            "<if test='userIds!=null'>and b.tenant_user_no in" +
            "(<foreach collection='userIds' item='userid' separator=','>#{userid}</foreach>)\n" +
            "</if>" +
            "</where></script>")
    List<BetOrderBean> screenBetOrderNoPage(@Param("screenForBetOrderDto") ScreenForBetOrderDto screenForBetOrderDto,@Param("userIds") List<Long> userIds);

    @Select("select IFNULL(SUM(estimated_reward)-SUM(amount),0) as orderamount from bet_order where bet_option_id=#{betOptionId} and tenant_user_no=#{userId} and `status`='UNSETTLED' and win_lose='NOTOPEN'")
    BigDecimal getUserOrderSum(@Param("betOptionId")Integer betOptionId,@Param("userId")Long userId);

    //风暴娱乐后台
        //查询投注用户数和新增投注用户数
    @Select("<script>" +
            "select count(1) from " +
            "(select t.tenant_user_no from " +
            "(select t.tenant_user_no,t.create_time from bet_order_view t <where>t.`status`='settled' <if test='add==null'> and date_format(t.create_time,'%Y-%m-%d')=#{timeStr}</if>" +
//            "<if test='gameHalls!=null and gameHalls.size>0'> and t.gamehall in(<foreach collection='gameHalls' item='gameHall' separator=','>#{gameHall}</foreach>)</if>" +
            "<if test='gameHall!=null'> and t.gamehall =#{gameHall} </if>" +
            "<if test='registerChannel!=null'> and t.register_channel=#{registerChannel} </if></where>"+
            ") t" +
            " group by t.tenant_user_no " +
            "<if test='add!=null'>having date_format(min(t.create_time),'%Y-%m-%d')=#{timeStr} </if>" +
            ") t" +
            "</script>\n")
    Integer selectOrderUserCount(String timeStr, Object add, String gameHall,String registerChannel);
        //查询查询投注用户数和新增投注用户列表
        @Select("<script>" +
                "select t.tenant_user_no from " +
                "(select t.tenant_user_no,t.create_time from bet_order_view t <where>t.`status`='settled' <if test='add==null'> and date_format(t.create_time,'%Y-%m-%d')=#{timeStr}</if>" +
//            "<if test='gameHalls!=null and gameHalls.size>0'> and t.gamehall in(<foreach collection='gameHalls' item='gameHall' separator=','>#{gameHall}</foreach>)</if>" +
                "<if test='gameHall!=null'> and t.gamehall =#{gameHall} </if>" +
                "<if test='registerChannel!=null'> and t.register_channel=#{registerChannel} </if></where>"+
                ") t" +
                " group by t.tenant_user_no " +
                "<if test='add!=null'>having date_format(min(t.create_time),'%Y-%m-%d')=#{timeStr} </if>" +
                "" +
                "</script>\n")
        Set<Integer> selectOrderUserList(String timeStr, Object add, String gameHall, String registerChannel);
    //查询某日新增投注用户且是当日注册的
    @Select("<script>" +
            "select count(1) from " +
            "(select t.tenant_user_no from " +
            "(select t.tenant_user_no,t.create_time from bet_order_view t <where>t.`status`='settled' " +
//            "<if test='gameHalls!=null and gameHalls.size>0'> and t.gamehall in(<foreach collection='gameHalls' item='gameHall' separator=','>#{gameHall}</foreach>)</if>" +
            "<if test='gameHall!=null'> and t.gamehall =#{gameHall} </if>" +
            "<if test='registerChannel!=null'> and t.register_channel=#{registerChannel} </if></where>"+
            ") t" +
            " group by t.tenant_user_no " +
            "having date_format(min(t.create_time),'%Y-%m-%d')=#{timeStr} " +
            ") t " +
            "where exists (select * from user_account t2 where t.tenant_user_no=t2.id and to_days(t2.create_time)=to_days(#{timeStr}))" +
            "</script>\n")
    Set<Integer> selectOrderUserCountLimitReg(String timeStr,String gameHall,String registerChannel);

    //查询某段日期内某渠道新增的投注用户数
    @Select("<script>" +
            " SELECT t.tenant_user_no from (select * from bet_order t <where> t.`status`='SETTLED' " +
            "<if test='startDate!=null and add==null'> and t.create_time <![CDATA[>=]]> #{startDate} </if>" +
            "<if test='endDate!=null and add==null'> and t.create_time <![CDATA[<=]]> #{endDate} </if>" +
            "<if test='userIds!=null'> and t.tenant_user_no in (" +
            "<foreach collection='userIds' item='userid' separator=','>#{userid}</foreach>" +
            ")</if></where>) t GROUP BY t.tenant_user_no having 1=1 " +
            "<if test='add!=null and startDate!=null'> and min(t.create_time) <![CDATA[>=]]> #{startDate} </if>"+
            "<if test='add!=null and endDate!=null'> and min(t.create_time) <![CDATA[<=]]> #{endDate} </if>"+
            "</script>")
    List<Integer> getBetOrderUserBettingDays(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("userIds") List<Integer> userIds , Object add);

    //查询某段时间内某渠道新增/累计投注额
    @Select("<script>" +
            "select ifnull(sum(t.amount),0) from bet_order t <where> t.`status`='SETTLED' " +
            "<if test='startDate!=null'> and t.create_time <![CDATA[>=]]> #{startDate}</if>" +
            "<if test='endDate!=null'> and t.create_time <![CDATA[<=]]> #{endDate}</if> " +
            "<if test='userIds!=null'> and t.tenant_user_no in (" +
            "<foreach collection='userIds' item='userid' separator=','>#{userid}</foreach>" +
            ")</if></where>" +
            "</script>")
    BigDecimal getBetOrderAmountBettingDays(@Param("startDate") Date startDate, @Param("endDate") Date endDate,@Param("userIds") List<Integer> userIds);

    //查询某段时间某渠道的订单数
    @Select("<script>" +
            "select count(1) from bet_order t <where> t.`status`='SETTLED' " +
            "<if test='startDate!=null'> and t.create_time <![CDATA[>=]]> #{startDate}</if>" +
            "<if test='endDate!=null'> and t.create_time <![CDATA[>=]]> #{endDate}</if> " +
            "<if test='userIds!=null'> and t.tenant_user_no in (" +
            "<foreach collection='userIds' item='userid' separator=','>#{userid}</foreach>" +
            ")</if></where>" +
            "</script>")
    Integer getBetOrderCountBettingDays(@Param("startDate") Date startDate, @Param("endDate") Date endDate,@Param("userIds") List<Integer> userIds);

    //视图
    @Select("select * from bet_order_view limit 10")
    List<BetOrderBean> getAllOrder();

    //投注中心
    @Select("<script>" +
            "select *,ifnull(t.estimated_reward,0)-ifnull(t.amount,0) as profit_amount from <if test='gameHall!=null'>bet_order_view</if><if test='gameHall==null'>bet_order_view_c</if> t <where> 1=1 " +
            "<if test='startDate!=null'> and to_days(t.create_time)<![CDATA[>=]]>to_days(#{startDate}) </if>" +
            "<if test='endDate!=null'> and to_days(t.create_time)<![CDATA[<=]]>to_days(#{endDate}) </if>" +
            "<if test='userIdOrPhoneNum!=null and isPhone==\"0\"'> and  (t.invite_code=#{userIdOrPhoneNum} or t.username=#{userIdOrPhoneNum}) </if>" +
            "<if test='userIdOrPhoneNum!=null and isPhone==\"1\"'> and t.mobile=#{userIdOrPhoneNum} </if>" +
            "<if test='orderId!=null'> and t.tenant_order_no=#{orderId} </if>" +
            "<if test='orderStatus!=null'> and t.status=#{orderStatus} </if>" +
            "<if test='gameHall!=null'> and t.gamehall=#{gameHall} </if>" +
            "</where> order by t.create_time desc" +
            "</script>")
    List<BetOrderBean> orderCenterList(Page page, Date startDate,Date endDate, String userIdOrPhoneNum, String orderId, OrderStatus orderStatus,String isPhone,String gameHall);

    //查询某用户的某段时间的累计投注，累计输赢额，累计盈利率
    @Select("<script>" +
            "select t.*,(t.returnAmount-t.ordertotle) as rewardtotle,CONVERT((t.returnAmount-t.ordertotle)/t.ordertotle,DECIMAL (10, 2)) as ratedata from "+
            "(select ifnull(sum(t.amount),0) as ordertotle,ifnull(sum(t.estimated_reward),0) as returnAmount from"+
            "(select * from bet_order t <where> t.status='SETTLED' and t.win_lose!='CANCEL' " +
            "<if test='startDate!=null'> and to_days(t.create_time)<![CDATA[>=]]>to_days(#{startDate}) </if>" +
            "<if test='endDate!=null'> and to_days(t.create_time)<![CDATA[<=]]>to_days(#{endDate}) </if>" +
            "<if test='userId!=null'> and t.tenant_user_no=#{userId} </if>"+
            "</where>) t) t" +
            "</script>")
    List<UserBean> getStatistics(Date startDate, Date endDate, String userId);

    //查询某段时间某渠道某个厅的投注用户数
    @Select("<script>" +
            "select count(1) from " +
            "(select t.tenant_user_no from "+
            "(select * from bet_order_view t <where> t.status='SETTLED' " +
            "<if test='startDate!=null'> and to_days(t.update_time) <![CDATA[>=]]> to_days(#{startDate}) </if>" +
            "<if test='endDate!=null'> and to_days(t.update_time) <![CDATA[<=]]> to_days(#{endDate}) </if>" +
            "<if test='userIds!=null'> and t.tenant_user_no in(<foreach collection='userIds' item='userid' separator=','>#{userid}</foreach>) </if>" +
            "<if test='gameHall!=null'> and t.gamehall =#{gameHall} </if>" +
            "</where>) t "+
            "group by t.tenant_user_no) t "+
            "</script>")
    Integer getGameHallOrderUserCount(Date startDate,Date endDate,List<Integer> userIds,String gameHall);

    //查询某段时间某渠道某个厅的投注额
    @Select("<script>" +
            "select ifnull(sum(t.amount),0) from bet_order_view t <where> t.status='SETTLED' " +
            "<if test='startDate!=null'> and to_days(t.update_time) <![CDATA[>=]]> to_days(#{startDate}) </if>" +
            "<if test='endDate!=null'> and to_days(t.update_time) <![CDATA[<=]]> to_days(#{endDate}) </if>" +
            "<if test='userIds!=null'> and t.tenant_user_no in(<foreach collection='userIds' item='userid' separator=','>#{userid}</foreach>) </if>" +
            "<if test='gameHall!=null'> and t.gamehall =#{gameHall} </if>" +
            "</where> "+
            "</script>")
    BigDecimal getGameHallOrderAmount(Date startDate,Date endDate,List<Integer> userIds,String gameHall);

    //查询某段时间某渠道某个厅的订单数
    @Select("<script>" +
            "select count(1) from bet_order_view t <where> t.status='SETTLED' " +
            "<if test='startDate!=null'> and to_days(t.update_time) <![CDATA[>=]]> to_days(#{startDate}) </if>" +
            "<if test='endDate!=null'> and to_days(t.update_time) <![CDATA[<=]]> to_days(#{endDate}) </if>" +
            "<if test='userIds!=null'> and t.tenant_user_no in(<foreach collection='userIds' item='userid' separator=','>#{userid}</foreach>) </if>" +
            "<if test='gameHall!=null'> and t.gamehall =#{gameHall} </if>" +
            "</where> "+
            "</script>")
    Integer getGameHallOrderCount(Date startDate,Date endDate,List<Integer> userIds,String gameHall);

    //数据统计
    //盘口统计
    //查询某段时间某厅

    //邀请有奖相关查询
    @Select("<script>" +
            "select count(1) from user_account t where " +
            "exists (select * from bet_order t2 where t2.tenant_user_no=t.id and t2.tenant_user_no=#{userId} and t2.status='SETTLED') or " +
            "exists (select * from as_order t3 where t3.username=t.username and t.id=#{userId} and t3.check_limit='SETTLED')" +
            "</script>")
    Integer firstOrderfeiqi(String userId);

    @Select("<script>" +
            "select sum(a) from ("+
            "select count(*) as a from bet_order as t2 where  t2.tenant_user_no=#{userId} and t2.status='SETTLED' "+
            "UNION all "+
            "select count(*) as a from as_order t3 "+
            "LEFT JOIN user_account as ttt on t3.username=ttt.username "+
            "where ttt.id=#{userId} and t3.check_limit='SETTLED' " +
            "UNION all " +
            "select count(1) as a from nbtech_order t4 where t4.user_id=#{userId} and t4.status in(3,9) "+
            ") as dd"+
            "</script>")
    Integer firstOrder(String userId);

    @Select("select ifnull(sum(t.amount),0) from bet_order t where t.tenant_user_no=#{userId} and t.status='SETTLED'")
    BigDecimal getBetOrderTotle(String userId);

    @Select("select ifnull(sum(t.valid_account),0) from as_order t where t.username=#{userName} and t.check_limit='SETTLED' ")
    BigDecimal getAsOrderTotle(String userName);

    @Select("select IFNULL(sum(t.amount),0) from bet_order t where t.tenant_user_no=#{userId} and t.status!='FAIL'")
    Integer getBetOrderTotal(Long userId);

    //三合一总查询
    @Select("<script>select t0.id,ifnull(t1.t1c,0) as t1c,ifnull(t2.t2c,0) as t2c,ifnull(t3.t3c,0) as t3c,,ifnull(t1.t1s,0.00) as t1s,,ifnull(t2.t2s,0.00) as t2s,,ifnull(t3.t3s,0.00) as t3s from (select t0.id,t0,username from user_account t0 <where>1=1 " +
            "<if test='userIds!=null'> and t0.id in(<foreach collection='userIds' item='userid' separator=','>#{userid}</foreach>) </if>" +
            "</where>) t0 \n" +
            "\n" +
            "left join (select t1.tenant_user_no,count(1) as t1c,sum(t1.amount) as t1s from bet_order t1 <where> t1.`status`='settled' and t1.win_lose in('win','lose') " +
            "<if test='startDate!=null'> and to_days(t1.update_time) <![CDATA[>=]]> to_days(#{startDate}) </if>" +
            "<if test='endDate!=null'> and to_days(t1.update_time) <![CDATA[<=]]> to_days(#{endDate}) </if>" +
            "</where> GROUP BY t1.tenant_user_no) t1 on t0.id=t1.tenant_user_no\n" +
            "\n" +
            "left join ( select t2.username,count(1) as t2c,sum(t2.valid_account) as t2s from as_order t2 <where> t2.check_limit='settled' " +
            "<if test='startDate!=null'> and to_days(t2.update_time) <![CDATA[>=]]> to_days(#{startDate}) </if>" +
            "<if test='endDate!=null'> and to_days(t2.update_time) <![CDATA[<=]]> to_days(#{endDate}) </if>" +
            "</where>GROUP BY t2.username) t2 on t0.username=t2.username\n" +
            "\n" +
            "left join ( select t3.user_id,count(1) as t3c,sum(t3.order_amount) as t3s from nbtech_order t3 <where> t3.`status` in(3,9) " +
            "<if test='startDate!=null'> and to_days(t3.update_time) <![CDATA[>=]]> to_days(#{startDate}) </if>" +
            "<if test='endDate!=null'> and to_days(t3.update_time) <![CDATA[<=]]> to_days(#{endDate}) </if>" +
            "</where> GROUP BY t3.user_id) t3 on t0.id=t3.user_id"+

            "</script>"
    )
    List<JSONObject> getTotal(@Param("startDate") Date startDate, @Param("endDate") Date endDate,@Param("userIds") List<Integer> userIds);

    @Select("<script>" +
            "select t1.tenant_user_no,count(1) as t1c,ifnull(sum(t1.amount),0.00) as t1s from bet_order t1 <where> t1.`status`='settled' and t1.win_lose in('win','lose') " +
            "<if test='startDate!=null'> and to_days(t1.update_time) <![CDATA[>=]]> to_days(#{startDate}) </if>" +
            "<if test='endDate!=null'> and to_days(t1.update_time) <![CDATA[<=]]> to_days(#{endDate}) </if>" +
            "<if test='userIds!=null'> and t1.id in(<foreach collection='userIds' item='userid' separator=','>#{userid}</foreach>) </if>" +
            "</where> GROUP BY t1.tenant_user_no"+
            "</script>")
    List<JSONObject> getBetTotle(@Param("startDate") Date startDate, @Param("endDate") Date endDate,@Param("userIds") List<Integer> userIds);

    @Select("<script>" +
            "select t1.id,t2.* from user_account t1 left join ("+
            "select t2.username,count(1) as t2c,ifnull(sum(t2.valid_account),0.00) as t2s from as_order t2 <where> t2.check_limit='settled' " +
            "<if test='startDate!=null'> and to_days(t2.update_time) <![CDATA[>=]]> to_days(#{startDate}) </if>" +
            "<if test='endDate!=null'> and to_days(t2.update_time) <![CDATA[<=]]> to_days(#{endDate}) </if>" +
            "</where>GROUP BY t2.username " +
            ") t2 "+
            "ON\n" +
            "t1.username=t2.username "+
            "<where>1=1 <if test='userIds!=null'> and t1.id in(<foreach collection='userIds' item='userid' separator=','>#{userid}</foreach>) </if></where>"+
            "</script>")
    List<JSONObject> getAsTotle(@Param("startDate") Date startDate, @Param("endDate") Date endDate,@Param("userIds") List<Integer> userIds);

    @Select("<script>" +
            "select t3.user_id,count(1) as t3c,ifnull(sum(t3.order_amount),0.00) as t3s from nbtech_order t3 <where> t3.`status` in(3,9) " +
            "<if test='startDate!=null'> and to_days(t3.update_time) <![CDATA[>=]]> to_days(#{startDate}) </if>" +
            "<if test='endDate!=null'> and to_days(t3.update_time) <![CDATA[<=]]> to_days(#{endDate}) </if>" +
            "<if test='userIds!=null'> and t3.user_id in(<foreach collection='userIds' item='userid' separator=','>#{userid}</foreach>) </if>" +
            "</where> GROUP BY t3.user_id" +
            "</script>")
    List<JSONObject> getSportTotle(@Param("startDate") Date startDate, @Param("endDate") Date endDate,@Param("userIds") List<Integer> userIds);
}