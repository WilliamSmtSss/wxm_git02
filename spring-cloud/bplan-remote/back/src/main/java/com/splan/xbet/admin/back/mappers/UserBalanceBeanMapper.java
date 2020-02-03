package com.splan.xbet.admin.back.mappers;

import com.splan.xbet.admin.back.base.SuperMapper;
import com.splan.base.bean.UserBalanceBean;
import com.splan.base.enums.Level;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

public interface UserBalanceBeanMapper extends SuperMapper<UserBalanceBean> {


    @Select({"select  * from user_balance where user_id=#{userId}"})
    //@Options(flushCache = Options.FlushCachePolicy.TRUE,timeout = 10000)
    UserBalanceBean selectUserBalanceByUserId(Long userId);

    @Select({"select  * from user_balance where user_id=#{userId}"})
    UserBalanceBean selectUserBalanceByUserIdWithCache(Long userId);

    @Update("update user_balance set available_coin=available_coin-#{amount},frozen_coin=frozen_coin+#{amount},version=version+1 where user_id=#{userId}  and available_coin>=#{amount}")
    Integer frozenBalance(Long userId, BigDecimal amount);

    @Update("update user_balance set available_coin=available_coin+#{amount},coin=coin+#{amount},version=version+1 where user_id=#{userId}")
    Integer outBalance(Long userId, BigDecimal amount);

//    @Select("<script>select ifnull(sum(t.coin),0.00) from user_balance t where EXISTS (select t2.id from user_account t2 " +
//            "<where> 1=1 " +
//            "<if test='levels!=null'> and t2.level in " +
//            "(<foreach collection='levels' item='level' separator=','>#{level}</foreach>) " +
//            "</if>" +
//            "and t2.id=t.user_id) " +
//            "</where>"+
//            "<script>")
//    BigDecimal getAmount(@Param("levels") List<Level> levels,String date);

//    风暴娱乐后台
        //查询余额
    @Select("<script>select ifnull(sum(t1.coin),0.00) from user_balance t1,user_account t2 where t1.user_id=t2.id <if test='levels!=null'>and t2.level in(<foreach collection='levels' item='level' separator=','>#{level}</foreach>)</if> limit 1</script>")
    BigDecimal getCoin(@Param("levels") List<Level> levels);

    @Select("<script>select ifnull(sum(t1.coin),0.00) from user_balance t1,user_account t2 where t1.user_id=t2.id <if test='levels!=null'>and t2.level in(<foreach collection='levels' item='level' separator=','>#{level}</foreach>)</if> and t1.limit_coin=0 limit 1</script>")
    BigDecimal getCanCoin(@Param("levels") List<Level> levels);

    @Update("update user_balance set limit_coin=limit_coin+wait_benefits,available_coin=available_coin+wait_benefits,coin=coin+wait_benefits,already_benefits=already_benefits+wait_benefits,version=version+1,wait_benefits=0 where user_id=#{userId} and version=#{version} and wait_benefits>0")
    Integer updateBenefit(Long userId, Integer version);

    @Update("update user_balance set wait_benefits=wait_benefits+#{amount},version=version+1 where user_id=#{userId} and version=#{version}")
    Integer updateWaitBenefit(Long userId, Integer version, Integer amount);

}