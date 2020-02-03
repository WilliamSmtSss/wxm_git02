package com.splan.data.datacenter.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.base.SuperMapper;
import com.splan.data.datacenter.bean.BetOrderBean;
import com.splan.base.bean.BetOrderDetailBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface XbetViewMapper extends SuperMapper {
    @Select("<script>select count(1) as orderCount,ifnull(sum(t.amount),0.00) as orderAmount,count(DISTINCT t.tenant_user_no) as orderUserCount from x_order_view t <where> 1=1 " +
            "<if test='topicId!=null'> and t.topicId=#{topicId}</if>"+
            "<if test='userIds!=null '> and t.tenant_user_no in(<foreach collection='userIds' item='userId' separator=','>#{userId}</foreach>)</if>"+
            "</where>" +
            "</script>")
    JSONObject getXbetOrder(@Param("topicId") String topicId, List<Integer> userIds);

//    @Select("<script>" +
//            "select * from x_order_data_view t <where> 1=1 " +
//            "<if></if>" +
//            "</where>" +
//            "</script>")

    @Select("<script>" +
            "select * from " +
//            "<if test='orderType!=null and orderType==\"0\"'>x_sing_order_data_view</if>" +
            " bet_order t <where> 1=1 " +
            " <if test='userIds != null and userIds.size()!=0'> AND t.tenant_user_no in (<foreach collection='userIds' item='userId' separator=','>#{userId}</foreach>)</if> "+
            " <if test='startDate != null and startDate!=\"\"'> AND unix_timestamp(t.check_time) <![CDATA[>=]]> unix_timestamp(#{startDate}) </if> "+
            " <if test='endDate != null and endDate!=\"\"'> AND unix_timestamp(t.check_time) <![CDATA[<=]]> unix_timestamp(#{endDate}) </if> "+
            " <if test='startDateOrder != null and startDateOrder!=\"\"'> AND unix_timestamp(t.create_time) <![CDATA[>=]]> unix_timestamp(#{startDateOrder}) </if> "+
            " <if test='endDateOrder != null and endDateOrder!=\"\"'> AND unix_timestamp(t.create_time) <![CDATA[<=]]> unix_timestamp(#{endDateOrder}) </if> "+
            " <if test='startDateUpdate != null and startDateUpdate!=\"\"'> AND unix_timestamp(t.update_time) <![CDATA[>=]]> unix_timestamp(#{startDateUpdate}) </if> "+
            " <if test='endDateUpdate != null and endDateUpdate!=\"\"'> AND unix_timestamp(t.update_time) <![CDATA[<=]]> unix_timestamp(#{endDateUpdate}) </if> "+
            " <if test='gameId != null and orderType==\"0\" and gameId!=\"\"'> AND t.gameId=#{gameId} </if> "+
            " <if test='orderStatus != null and orderStatus!=\"\"'> AND t.status=#{orderStatus} </if> "+
            " <if test='searchId != null and searchId!=\"\"'>  and exists(select t2.id from user_account t2 where t.tenant_user_no=t2.id and t2.extra_id=#{searchId})</if> "+
            " <if test='orderId != null and orderId!=\"\"'> AND t.tenant_order_no=#{orderId} </if> "+
            " <if test='orderType != null and orderType!=\"\"'> AND t.order_type=#{orderType} </if> "+
            " <if test='betId != null and orderType==\"0\" and betId!=\"\"'> AND t.betId=#{betId} </if> "+
            "</where>" +
            " order by t.create_time desc"+
            "</script>")
    List<BetOrderBean> getXbetOrderData(Page page, List<Integer> userIds, String gameId, String startDate, String endDate, String orderStatus, String searchId, String orderId, String orderType, String betId,String startDateOrder, String endDateOrder,String startDateUpdate,String endDateUpdate);

    @Select("select * from x_together_order_data_view t where t.bet_order_id=#{orderId}")
    List<BetOrderDetailBean> getXbetDetails(@Param("orderId") String orderId);
}
