package com.splan.xbet.admin.back.mappers;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BetOrderBean;
import com.splan.base.bean.BetOrderDetailBean;
import com.splan.xbet.admin.back.base.SuperMapper;
import com.splan.xbet.admin.back.dto.DateDto;
import com.splan.xbet.admin.back.result.BetOrderDetailResult;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface XbetViewMapper extends SuperMapper {
    @Select("<script>select count(1) as orderCount,ifnull(sum(t.amount),0.00) as orderAmount,ifnull(sum(t.estimated_reward),0.00) as returnAmount,count(DISTINCT t.tenant_user_no) as orderUserCount from x_order_view t <where> 1=1 " +
            "<if test='topicId!=null'> and t.topicId=#{topicId}</if>"+
            "<if test='betStatus!=null'> and t.bet_status=#{betStatus}</if>"+
            "<if test='userIds!=null '> and t.tenant_user_no in(<foreach collection='userIds' item='userId' separator=','>#{userId}</foreach>)</if>"+
//            "<if test='bigBusiness!=null and bigBusiness!=\"\"'> and exists (select * from x_businessconfig_view tin where t.tenant_user_no=tin.id and tin.papi_id=#{bigBusiness})</if>"+
//            "<if test='smallBusiness!=null and smallBusiness!=\"\"'> and exists (select * from x_businessconfig_view tin where t.tenant_user_no=tin.id and tin.api_id=#{smallBusiness})</if>"+
            "</where>" +
            "</script>")
    JSONObject getXbetOrder(@Param("topicId") String topicId,List<Integer> userIds,String betStatus);

//    @Select("<script>" +
//            "select * from x_order_data_view t <where> 1=1 " +
//            "<if></if>" +
//            "</where>" +
//            "</script>")

    @Select("<script>" +
            "select t.* from " +
            "<if test='orderType!=null and orderType==\"0\"'>x_sing_order_data_view</if>" +
            "<if test='orderType!=null and orderType==\"1\"'>bet_order</if> t " +
            " left join user_account t2 on t.tenant_user_no=t2.id <where> 1=1 "+
            " <if test='userIds != null and userIds.size()!=0'> AND t.tenant_user_no in (<foreach collection='userIds' item='userId' separator=','>#{userId}</foreach>)</if> "+
            " <if test='startDate != null '> AND to_days(t.check_time) <![CDATA[>=]]> to_days(#{startDate}) </if> "+
            " <if test='endDate != null '> AND to_days(t.check_time) <![CDATA[<=]]> to_days(#{endDate}) </if> "+
            " <if test='gameId != null and orderType==\"0\" and gameId!=\"\"'> AND t.gameId=#{gameId} </if> "+
            " <if test='orderStatus != null and orderStatus!=\"\"'> AND t.status=#{orderStatus} </if> "+
            " <if test='searchId != null and searchId!=\"\"'> AND t2.extra_id=#{searchId} </if> "+
            " <if test='orderId != null and orderId!=\"\"'> AND t.tenant_order_no=#{orderId} </if> "+
            " <if test='orderType != null and orderType!=\"\"'> AND t.order_type=#{orderType} </if> "+
            " <if test='betId != null and orderType==\"0\" and betId!=\"\"'> AND t.betId=#{betId} </if> "+
            " <if test='dataId != null and orderType==\"0\" and dataId!=\"\"'> AND t.data_id=#{dataId} </if> "+
            "</where>" +
            " order by t.create_time desc"+
            "</script>")
    List<BetOrderBean> getXbetOrderData(Page page, List<Integer> userIds, String gameId, Date startDate, Date endDate, String orderStatus, String searchId, String orderId,String orderType, String betId,String dataId);

    @Select("select * from x_together_order_data_view t where t.bet_order_id=#{orderId}")
    List<BetOrderDetailBean> getXbetDetails(@Param("orderId") String orderId);
}
