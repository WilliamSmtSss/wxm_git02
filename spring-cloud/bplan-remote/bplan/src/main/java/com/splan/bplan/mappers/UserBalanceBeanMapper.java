package com.splan.bplan.mappers;

import com.splan.base.base.SuperMapper;
import com.splan.base.bean.UserBalanceBean;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

public interface UserBalanceBeanMapper extends SuperMapper<UserBalanceBean> {


    @Select({"select  * from user_balance where user_id=#{userId}"})
    //@Options(flushCache = Options.FlushCachePolicy.TRUE,timeout = 10000)
    UserBalanceBean selectUserBalanceByUserId(Long userId);

    @Select({"select  * from user_balance where user_id=#{userId}"})
    UserBalanceBean selectUserBalanceByUserIdWithCache(Long userId);

    @Update("update user_balance set available_coin=available_coin-#{amount},frozen_coin=frozen_coin+#{amount},version=version+1 where user_id=#{userId}  and available_coin>=#{amount}")
    Integer frozenBalance(Long userId,BigDecimal amount);

    @Update("update user_balance set available_coin=available_coin+#{amount},coin=coin+#{amount},version=version+1 where user_id=#{userId}")
    Integer inBalance(Long userId, BigDecimal amount);

    @Update("update user_balance set available_coin=available_coin-#{amount},coin=coin-#{amount},version=version+1 where user_id=#{userId} and available_coin>=#{amount}")
    Integer outBalance(Long userId, BigDecimal amount);


}