package com.splan.xbet.admin.back.mappers;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.*;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface XbetBackMapper {
    //    @Select("<script>" +
//            "select * from x_order_data_view t <where> 1=1 " +
//            "<if></if>" +
//            "</where>" +
//            "</script>")
    //商户
        @Select("<script>" +
            "select count(1) as orderCount,ifnull(sum(t.amount),0.00) as orderAmount,ifnull(sum(case when t.win_lose='win' then t.estimated_reward end),0.00) as returnAmount,count(DISTINCT t.tenant_user_no) as orderUserCount from bet_order t <where> t.status='SETTLED' and t.win_lose in('win','lose')" +
            "<if test='userIds!=null '> and t.tenant_user_no in(<foreach collection='userIds' item='userId' separator=','>#{userId}</foreach>)</if>"+
            " <if test='time != null and queryTimeType==\"0\"'> AND DATE_FORMAT(t.check_time,'%Y-%m')=#{time}</if> "+
            " <if test='time != null and queryTimeType==\"1\"'> AND DATE_FORMAT(t.check_time,'%Y-%m-%d')=#{time}</if> "+
            "</where>" +
            "</script>")
        JSONObject getOrderStatistics(List<Integer> userIds,String queryTimeType,String time);

        @Select("<script>select id from user_account t <where> 1=1 " +
                "<if test='apiId!=null'> and t.api_id=#{apiId}</if>" +
                " <if test='time != null and queryTimeType==\"0\"'> AND DATE_FORMAT(t.create_time,'%Y-%m')=#{time}</if> "+
                " <if test='time != null and queryTimeType==\"1\"'> AND DATE_FORMAT(t.create_time,'%Y-%m-%d')=#{time}</if> "+
                "</where>" +
                "</script>")
        List<Integer> getUserIds(String apiId,String queryTimeType,@Param("time") String time);

        @Select("<script>select t1.id from user_account t1 left join business_config t2 on t1.api_id =t2.api_id left join business_config t3 on t2.pid=t3.id <where> 1=1 " +
                "<if test='bigBusiness!=null and bigBusiness!=\"\" and (smallBusiness==null or smallBusiness==\"\")'> and (t3.api_id=#{bigBusiness} or t2.api_id=#{bigBusiness})</if>" +
                "<if test='smallBusiness!=null and smallBusiness!=\"\"'> and t2.api_id=#{smallBusiness}</if>" +
                "</where>" +
                "</script>")
        List<Integer> getUserIdsByApiId(String bigBusiness, String smallBusiness);

        @Select("<script>" +
            "select count(DISTINCT t1.user_id) from user_login_log t1 LEFT JOIN user_account t2 on t1.user_id=t2.id <where> 1=1 " +
            "<if test='apiId!=null'> and t2.api_id=#{apiId}</if>" +
            " <if test='time != null and queryTimeType==\"0\"'> AND DATE_FORMAT(t1.create_time,'%Y-%m')=#{time}</if> "+
            " <if test='time != null and queryTimeType==\"1\"'> AND DATE_FORMAT(t1.create_time,'%Y-%m-%d')=#{time}</if> "+
            "</where>" +
            "</script>")
        Integer getActiveUserCount(String apiId,String queryTimeType,String time);

        @Select("<script>" +
                "select count(1) from"+
                "(select t.tenant_user_no from "+
                "(select t1.* from bet_order t1 left join user_account t2 on t1.tenant_user_no=t2.id <where> 1=1 " +
                "<if test='apiId!=null'> and t2.api_id=#{apiId}</if>" +
                "</where>" +
                ") t "+
                "group by t.tenant_user_no having 1=1 "+
                " <if test='time != null and queryTimeType==\"0\"'> AND DATE_FORMAT(min(t.update_time),'%Y-%m')=#{time}</if> "+
                " <if test='time != null and queryTimeType==\"1\"'> AND DATE_FORMAT(min(t.update_time),'%Y-%m-%d')=#{time}</if> "+
                ") t"+
                "</script>")
        Integer getAddOrderCount(String apiId,String queryTimeType,String time);

        @Select("<script>" +
                "select * from sys_user t <where> t.delete_status='1' and t.role_id=0 or t.api_id=#{apiId}" +
                "</where>" +
                "</script>")
        List<SysUser> getBusAdmins(String apiId);

        @Select("<script>" +
                "select * from business_config t <where> 1=1 " +
                "<if test='queryType!=null and queryType==\"0\"'> and t.pid is null</if>"+
                "<if test='queryType!=null and queryType==\"1\"'> and t.pid is not null </if>"+
                "<if test='queryType!=null and queryType==\"1\" and pid !=null'> and t.pid is not null and t.pid=#{pid}</if>"+
                "</where>" +
                "</script>")
        List<BusinessConfigBean> getBusConfig(@Param("pid") String pid,String queryType);

        //会员
        @Select("<script>" +
                "select t1.*,t2.api_id as first_api_id,t3.api_id as second_api_id from user_account t1 LEFT JOIN business_config t2 on t1.api_id=t2.api_id LEFT JOIN business_config t3 on t2.pid=t3.id  <where> 1=1 " +
                "<if test='bigBusiness!=null and bigBusiness!=\"\" and (smallBusiness==null or smallBusiness==\"\")'> and (t3.api_id=#{bigBusiness} or t2.api_id=#{bigBusiness})</if>" +
                "<if test='smallBusiness!=null'> and t2.api_id=#{smallBusiness}</if>" +
                "<if test='searchId!=null'> and t1.extra_id=#{searchId}</if>" +
                "</where>" +
                " order by t1.create_time desc"+
                "</script>")
        @Results({
                @Result(column = "api_id",property = "businessConfigBean",one = @One(select = "com.splan.xbet.admin.back.mappers.BusinessConfigBeanMapper.getByApiId")),
                @Result(column = "second_api_id",property = "businessConfigBeanUp",one = @One(select = "com.splan.xbet.admin.back.mappers.BusinessConfigBeanMapper.getByApiId")),
                @Result(column = "id",property = "userBalanceBean",one = @One(select = "com.splan.xbet.admin.back.mappers.UserBalanceBeanMapper.selectUserBalanceByUserId")),
        })
        List<UserBean> getUserPageList(Page page, String bigBusiness, String smallBusiness, String searchId);

    @Select("<script>" +
            "select t1.*,t2.api_id as first_api_id,t3.api_id as second_api_id,t4.username as modify_name from money_record t1 LEFT JOIN user_account t4 on t1.modify_id=t4.id left join business_config t2 on t4.api_id=t2.api_id LEFT JOIN business_config t3 on t2.pid=t3.id  <where> 1=1 " +
            "<if test='bigBusiness!=null and bigBusiness!=\"\" and (smallBusiness==null or smallBusiness==\"\")'> and (t3.api_id=#{bigBusiness} or t2.api_id=#{bigBusiness})</if>" +
            "<if test='smallBusiness!=null'> and t2.api_id=#{smallBusiness}</if>" +
            "<if test='searchId!=null'> and (t4.invite_code=#{searchId} or t4.username=#{searchId} or t4.extra_id=#{searchId})</if>" +
            "<if test='orderId!=null'> and t1.id=#{orderId}</if>" +
            "</where>" +
            " order by t1.create_time desc"+
            "</script>")
    @Results({
            @Result(column = "first_api_id",property = "businessConfigBean",one = @One(select = "com.splan.xbet.admin.back.mappers.BusinessConfigBeanMapper.getByApiId")),
            @Result(column = "second_api_id",property = "businessConfigBeanUp",one = @One(select = "com.splan.xbet.admin.back.mappers.BusinessConfigBeanMapper.getByApiId")),
    })
    List<MoneyRecordBean> getMoneyRecordPageList(Page page, String bigBusiness, String smallBusiness, String searchId,String orderId);

    //v2
        //会员
    @Select("<script>" +
            "select * from  user_account t <where> 1=1 " +
            "<if test='businessName!=null'> and t.api_id=#{businessName}</if>" +
            "<if test='searchText!=null'> and t.extra_id=#{searchText}</if>" +
            "<if test='startLoginTime!=null'> and to_days(t.last_login_time) <![CDATA[>=]]> to_days(#{startLoginTime})</if>" +
            "<if test='endLoginTime!=null'> and to_days(t.last_login_time) <![CDATA[<=]]> to_days(#{endLoginTime})</if>" +
            "</where>" +
            " order by t.create_time desc"+
            "</script>")
    List<UserBean> getUserPageListV2(Page page, String businessName, String searchText,String startLoginTime,String endLoginTime);

    @Select("<script>" +
            "select count(1) as orderCount,ifnull(sum(t.amount),0.00) as orderAmount,ifnull(sum(case when t.win_lose='win' then t.estimated_reward end),0.00) as returnAmount,count(DISTINCT t.tenant_user_no) as orderUserCount from bet_order t <where> t.status='SETTLED' and t.win_lose in('win','lose')" +
            "<if test='userIds!=null '> and t.tenant_user_no in(<foreach collection='userIds' item='userId' separator=','>#{userId}</foreach>)</if>"+
            " <if test='time != null and queryTimeType==\"0\"'> AND DATE_FORMAT(t.check_time,'%Y-%m')=#{time}</if> "+
            " <if test='time != null and queryTimeType==\"1\"'> AND DATE_FORMAT(t.check_time,'%Y-%m-%d')=#{time}</if> "+
            "</where>" +
            "</script>")
    JSONObject getOrderStatisticsV2(List<Integer> userIds,String queryTimeType,String time);

        //订单
        @Select("<script>" +
                "select t.* from " +
                "<if test='orderType!=null and orderType==\"0\"'>x_sing_order_data_view</if>" +
                "<if test='orderType!=null and orderType==\"1\"'>bet_order</if> t " +
                " left join user_account t2 on t.tenant_user_no=t2.id <where> 1=1 "+
                " <if test='userIds != null and userIds.size()!=0'> AND t.tenant_user_no in (<foreach collection='userIds' item='userId' separator=','>#{userId}</foreach>)</if> "+
                " <if test='startOrderTime != null '> AND to_days(t.create_time) <![CDATA[>=]]> to_days(#{startOrderTime}) </if> "+
                " <if test='endOrderTime != null '> AND to_days(t.create_time) <![CDATA[<=]]> to_days(#{endOrderTime}) </if> "+
                " <if test='gameId != null and orderType==\"0\" and gameId!=\"\"'> AND t.gameId=#{gameId} </if> "+
                " <if test='orderStatus != null and orderStatus!=\"\"'> AND t.status=#{orderStatus} </if> "+
                " <if test='searchText != null and searchText!=\"\"'> AND t2.extra_id=#{searchText} </if> "+
                " <if test='orderId != null and orderId!=\"\"'> AND t.tenant_order_no=#{orderId} </if> "+
                " <if test='orderType != null and orderType!=\"\"'> AND t.order_type=#{orderType} </if> "+
                " <if test='betId != null and orderType==\"0\" and betId!=\"\"'> AND t.betId=#{betId} </if> "+
                " <if test='dataId != null and orderType==\"0\" and dataId!=\"\"'> AND t.data_id=#{dataId} </if> "+
                " <if test='businessName != null' > AND t2.api_id=#{businessName} </if> "+
                "</where>" +
                " order by t.create_time desc"+
                "</script>")
        List<BetOrderBean> getXbetOrderDataV2(Page page, List<Integer> userIds, String gameId, String startOrderTime, String endOrderTime, String orderStatus, String searchText, String orderId, String orderType, String betId, String dataId,String businessName);

}
