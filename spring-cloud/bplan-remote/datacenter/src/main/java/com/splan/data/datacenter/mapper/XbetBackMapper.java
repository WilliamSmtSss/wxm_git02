package com.splan.data.datacenter.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BusinessConfigBean;
import com.splan.data.datacenter.bean.MoneyRecordBean;
import com.splan.base.bean.SysUser;
import com.splan.base.bean.UserBean;
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
        JSONObject getOrderStatistics(List<Integer> userIds, String queryTimeType, String time);

        @Select("<script>select id from user_account t <where> 1=1 " +
                "<if test='apiId!=null'> and t.api_id=#{apiId}</if>" +
                " <if test='time != null and queryTimeType==\"0\"'> AND DATE_FORMAT(t.create_time,'%Y-%m')=#{time}</if> "+
                " <if test='time != null and queryTimeType==\"1\"'> AND DATE_FORMAT(t.create_time,'%Y-%m-%d')=#{time}</if> "+
                "</where>" +
                "</script>")
        List<Integer> getUserIds(String apiId, String queryTimeType, @Param("time") String time);

        @Select("<script>select t1.id from user_account t1 left join business_config t2 on t1.api_id =t2.api_id left join business_config t3 on t2.pid=t3.id <where> 1=1 " +
                "<if test='bigBusiness!=null'> and t3.api_id=#{bigBusiness}</if>" +
                "<if test='smallBusiness!=null'> and t2.api_id=#{smallBusiness}</if>" +
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
        Integer getActiveUserCount(String apiId, String queryTimeType, String time);

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
        Integer getAddOrderCount(String apiId, String queryTimeType, String time);

        @Select("<script>" +
                "select * from sys_user t <where> t.delete_status='1' and t.role_id=0 " +
                "</where>" +
                "</script>")
        List<SysUser> getBusAdmins();

        @Select("<script>" +
                "select * from business_config t <where> 1=1 " +
                "<if test='queryType!=null and queryType==\"0\"'> and t.pid is null</if>"+
                "<if test='queryType!=null and queryType==\"1\"'> and t.pid is not null </if>"+
                "<if test='queryType!=null and queryType==\"1\" and pid !=null'> and t.pid is not null and t.pid=#{pid}</if>"+
                "</where>" +
                "</script>")
        List<BusinessConfigBean> getBusConfig(@Param("pid") String pid, String queryType);

        //会员
        @Select("<script>" +
                "select t1.*,t2.api_id as first_api_id,t3.api_id as second_api_id from user_account t1 LEFT JOIN business_config t2 on t1.api_id=t2.api_id LEFT JOIN business_config t3 on t2.pid=t3.id  <where> 1=1 " +
                "<if test='bigBusiness!=null'> and t3.api_id=#{bigBusiness}</if>" +
                "<if test='smallBusiness!=null'> and t2.api_id=#{smallBusiness}</if>" +
                "<if test='searchId!=null'> and (t1.invite_code=#{searchId} or t1.username=#{searchId})</if>" +
                "</where>" +
                "</script>")
        @Results({
                @Result(column = "api_id",property = "businessConfigBean",one = @One(select = "com.splan.xbet.admin.back.mappers.BusinessConfigBeanMapper.getByApiId")),
                @Result(column = "second_api_id",property = "businessConfigBeanUp",one = @One(select = "com.splan.xbet.admin.back.mappers.BusinessConfigBeanMapper.getByApiId")),
                @Result(column = "id",property = "userBalanceBean",one = @One(select = "com.splan.xbet.admin.back.mappers.UserBalanceBeanMapper.selectUserBalanceByUserId")),
        })
        List<UserBean> getUserPageList(Page page, String bigBusiness, String smallBusiness, String searchId);

    @Select("<script>" +
            "select t1.* from money_record t1 LEFT JOIN user_account t2 on t1.modify_id=t2.id <where> 1=1 " +
            "<if test='searchId!=null and searchId!=\"\"'> and t2.extra_id=#{searchId}</if>" +
            "<if test='orderId!=null and orderId!=\"\"'> and t1.id=#{orderId}</if>" +
            "<if test='appId!=null and appId!=\"\"'> and t2.api_id=#{appId}</if>" +
            " <if test='startDate != null and startDate!=\"\"'> AND unix_timestamp(t1.create_time) <![CDATA[>=]]> unix_timestamp(#{startDate}) </if> "+
            " <if test='endDate != null and endDate!=\"\"'> AND unix_timestamp(t1.create_time) <![CDATA[<=]]> unix_timestamp(#{endDate}) </if> "+
            "</where>" +
            " order by t1.create_time desc"+
            "</script>")
//    @Results({
//            @Result(column = "first_api_id",property = "businessConfigBean",one = @One(select = "com.splan.xbet.admin.back.mappers.BusinessConfigBeanMapper.getByApiId")),
//            @Result(column = "second_api_id",property = "businessConfigBeanUp",one = @One(select = "com.splan.xbet.admin.back.mappers.BusinessConfigBeanMapper.getByApiId")),
//    })
    List<MoneyRecordBean> getMoneyRecordPageList(Page page, String appId, String searchId, String orderId, String startDate, String endDate);

    @Select("select t1.name,t2.group_name as groupName from bet_option t1 left join bet_topics t2 on t1.bet_data_id=t2.id where t1.id=#{id}")
    JSONObject getplayInfobyOptionId(@Param("id") String id);

}
