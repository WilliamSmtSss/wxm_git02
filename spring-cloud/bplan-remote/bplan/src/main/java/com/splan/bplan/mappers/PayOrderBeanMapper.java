package com.splan.bplan.mappers;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.splan.base.base.SuperMapper;
import com.splan.base.bean.PayOrderBean;
import com.splan.base.enums.AccessType;
import com.splan.base.enums.Level;
import com.splan.base.enums.OperationResult;
import com.splan.base.enums.OrderStatus;
import com.splan.bplan.result.OperateOrderResult;
import com.splan.bplan.result.OperateResult;
import com.splan.bplan.result.PayOrderResult;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface PayOrderBeanMapper extends SuperMapper<PayOrderBean> {
    @Insert({
        "insert into pay_order (id, tenant_order_no, ",
        "tenant_user_no, status, ",
        "amount, channel, ",
        "access_type, operator_id, ",
        "operation_result, operation_time, ",
        "card_id, card_no, bank_address, ",
        "error_reason, bank_code)",
        "values (#{id,jdbcType=BIGINT}, #{tenantOrderNo,jdbcType=VARCHAR}, ",
        "#{tenantUserNo,jdbcType=BIGINT}, #{status,jdbcType=VARCHAR}, ",
        "#{amount,jdbcType=DECIMAL}, #{channel,jdbcType=VARCHAR}, ",
        "#{accessType,jdbcType=VARCHAR}, #{operatorId,jdbcType=BIGINT}, ",
        "#{operationResult,jdbcType=VARCHAR}, #{operationTime,jdbcType=TIMESTAMP}, ",
        "#{cardId,jdbcType=BIGINT}, #{cardNo,jdbcType=VARCHAR}, #{bankAddress,jdbcType=VARCHAR}, ",
        "#{errorReason,jdbcType=VARCHAR}, #{bankCode,jdbcType=VARCHAR})"
    })
    int insert(PayOrderBean record);

    @InsertProvider(type=PayOrderBeanSqlProvider.class, method="insertSelective")
    int insertSelective(PayOrderBean record);

    @Insert({
            "insert into pay_order (id, tenant_order_no, ",
            "tenant_user_no, status, ",
            "amount, channel, ",
            "access_type, operator_id, ",
            "operation_result, operation_time, ",
            "card_id, card_no, bank_address, ",
            "error_reason, bank_code)",
            "values (#{id,jdbcType=BIGINT}, #{tenantOrderNo,jdbcType=VARCHAR}, ",
            "#{tenantUserNo,jdbcType=BIGINT}, #{status,jdbcType=VARCHAR}, ",
            "#{amount,jdbcType=DECIMAL}, #{channel,jdbcType=VARCHAR}, ",
            "#{accessType,jdbcType=VARCHAR}, #{operatorId,jdbcType=BIGINT}, ",
            "#{operationResult,jdbcType=VARCHAR}, #{operationTime,jdbcType=TIMESTAMP}, ",
            "#{cardId,jdbcType=BIGINT}, #{cardNo,jdbcType=VARCHAR}, #{bankAddress,jdbcType=VARCHAR}, ",
            "#{errorReason,jdbcType=VARCHAR}, #{bankCode,jdbcType=VARCHAR})"
    })

    @Select({"select * from pay_order where tenant_order_no = #{tenantOrderNo}"})
    PayOrderBean selectByTenantOrderNo(@Param("tenantOrderNo")String tenantOrderNo);

    @Select("<script>select * from pay_order " +
            "<where> access_type = #{access_type}" +
            " <if test='status != null '> AND status = #{status} </if> "+
            " <if test='startDate != null '> AND create_time <![CDATA[>=]]> #{startDate} </if> "+
            " <if test='endDate != null '> AND create_time <![CDATA[<=]]> #{endDate} </if> "+
            " <if test='operationResults != null '> AND operation_result in (" +
            " <foreach collection='operationResults' item='result' separator=','> #{result}" +
            " </foreach>) </if> "+
            "</where></script>")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Long.class),
            @Result(column = "tenant_user_no",property = "user",one=@One(select = "com.splan.bplan.mappers.UserMapper.selectById"))
    })
    List<PayOrderResult> selectOrderPage(IPage<PayOrderResult> page, @Param("access_type")AccessType accessType,
                                         @Param("startDate")Date startDate, @Param("endDate")Date endDate,
                                         @Param("operationResults")List<OperationResult> operationResults, @Param("status")OrderStatus orderStatus);

    @Select("<script>select * from pay_order " +
            "<where> access_type = #{access_type}" +
            " <if test='status != null '> AND status = #{status} </if> "+
            " <if test='startDate != null '> AND create_time <![CDATA[>=]]> #{startDate} </if> "+
            " <if test='endDate != null '> AND create_time <![CDATA[<=]]> #{endDate} </if> "+
            " <if test='operationResults != null '> AND operation_result in (" +
            " <foreach collection='operationResults' item='result' separator=','> #{result}" +
            " </foreach>) </if> "+
            "</where> exists(select id from user_account t where t.id=pay_order.tenant_user_no and t.level in('Agent','SuperAgent'))</script>")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Long.class),
            @Result(column = "tenant_user_no",property = "user",one=@One(select = "com.splan.bplan.mappers.UserMapper.selectById"))
    })
    List<PayOrderResult> selectAgentOrderPage(IPage<PayOrderResult> page, @Param("access_type")AccessType accessType,
                                         @Param("startDate")Date startDate, @Param("endDate")Date endDate,
                                         @Param("operationResults")List<OperationResult> operationResults, @Param("status")OrderStatus orderStatus);

    @Select("select t1.operation_time as operateTime,t2.real_name as operateName,t1.tenant_user_no as tenantuserno,t1.operation_result as operateresult,t1.access_type as accessType,t1.amount as amount from pay_order t1 left join user_account t2 on t1.operator_id=t2.id where t1.operator_id is not null order by operateTime desc")
    List<OperateResult> operateList(IPage<OperateResult> page);

    @Select("<script>select ifnull(sum(t.amount),0.00) from pay_order t where exists (select t2.id from user_account t2 <where> t.tenant_user_no=t2.id " +
            "<if test='types!=null'>and t2.level in" +
            "(<foreach collection='types' item='type' separator=','>#{type}</foreach>)" +
            "</if>" +
            " and TO_DAYS(t.create_time)=TO_DAYS(NOW()) and t.access_type='WITHDRAW' and t.status='SETTLED')</where></script>")
    BigDecimal getTodayamountByUserType(@Param("types") List<String> types);

    @Select("<script>select ifnull(sum(t.amount),0.00) from pay_order t where exists (select t2.id from user_account t2 <where> t.tenant_user_no=t2.id " +
            "<if test='levels!=null'>and t2.level in" +
            "(<foreach collection='levels' item='level' separator=','>#{level}</foreach>)" +
            "</if>" +
            "<if test='operationResults!=null'>and t.operation_result in" +
            "(<foreach collection='operationResults' item='operationResult' separator=','>#{operationResult}</foreach>)" +
            "</if>" +
            "<if test='day!=-1'> and TO_DAYS(t.create_time)=TO_DAYS(NOW())-#{day}</if> and t.access_type='WITHDRAW' and t.status='SETTLED')</where></script>")
    BigDecimal queryWitdraw(@Param("levels")List<Level> levels, @Param("operationResults")List<OperationResult> operationResults,int day);
}