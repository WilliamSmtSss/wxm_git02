package com.splan.bplan.mappers;

import com.splan.base.base.SuperMapper;
import com.splan.base.bean.UserCardBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserCardBeanMapper extends SuperMapper<UserCardBean> {
    @Insert({
        "insert into user_card (id, user_id, ",
        "bank_code, bank_name, ",
        "status, bank_id, credit_card)",
        "values (#{id,jdbcType=BIGINT}, #{userId,jdbcType=BIGINT}, ",
        "#{bankCode,jdbcType=VARCHAR}, #{bankName,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=VARCHAR}, #{bankId,jdbcType=BIGINT}, #{creditCard,jdbcType=VARCHAR})"
    })
    int insert(UserCardBean record);

    @InsertProvider(type=UserCardBeanSqlProvider.class, method="insertSelective")
    int insertSelective(UserCardBean record);

    @Select({"select count(1) from user_card where user_id = #{userId} and credit_card = #{creditCard}"})
    int selectCountByUserIdAndCreditCard(@Param("userId")Long userId, @Param("creditCard")String creditCard);

    @Select({"select id, user_id, bank_code, bank_name, status, bank_id, credit_card, bank_icon, bank_province, " +
            "bank_city, bank_county from user_card where id = #{id}"})
    UserCardBean selectById(@Param("id")String id);
}