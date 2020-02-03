package com.splan.xbet.admin.back.mappers;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FrontBackMapper {

    @Select("<script>select t1.id from user_account t1 <where> 1=1 " +
            " <if test='apiId != null'> AND t1.api_id=#{apiId}</if> "+
            "</where>" +
            "</script>")
    List<Integer> getUserIdsByApiId(@Param("apiId") String apiId);

    @Select("<script>" +
            "select count(1) as orderCount,ifnull(sum(t.amount),0.00) as orderAmount,ifnull(sum(case when t.win_lose='win' then t.estimated_reward end),0.00) as returnAmount,count(DISTINCT t.tenant_user_no) as orderUserCount from bet_order t <where> t.status='SETTLED' and t.win_lose in('win','lose')" +
            "<if test='userIds!=null '> and t.tenant_user_no in(<foreach collection='userIds' item='userId' separator=','>#{userId}</foreach>)</if>"+
            " <if test='startTime != null'> AND to_days(t.check_time) <![CDATA[>=]]> to_days(#{startTime})</if> "+
            " <if test='endTime != null'> AND to_days(t.check_time) <![CDATA[<=]]> to_days(#{endTime})</if> "+
            "</where>" +
            "</script>")
    JSONObject getOrderStatistics(List<Integer> userIds, String startTime,String endTime);

    @Select("<script>select count(DISTINCT t1.user_id) as activeUser from user_login_log t1 INNER JOIN user_account t2 on t1.user_id=t2.id where 1=1 " +
            " <if test='startTime != null'> AND to_days(t1.create_time) <![CDATA[>=]]> to_days(#{startTime})</if> "+
            " <if test='endTime != null'> AND to_days(t1.create_time) <![CDATA[<=]]> to_days(#{endTime})</if> "+
            "<if test='apiId != null'> and t2.api_id =#{apiId}</if>"+
            "</script>")
    Integer getActiveUser(String apiId,String startTime,String endTime);

}
