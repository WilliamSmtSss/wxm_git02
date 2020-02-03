package com.splan.bplan.mappers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.splan.base.base.SuperMapper;
import com.splan.base.bean.MoneyRecordBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface MoneyRecordBeanMapper extends SuperMapper<MoneyRecordBean> {
    @Insert({
        "insert into money_record (id, balance_id, ",
        "moneyable_id, moneyable_type, ",
        "kind, amount, money_from, ",
        "algorithm, money_to, ",
        "modify_id, detail)",
        "values (#{id,jdbcType=BIGINT}, #{balanceId,jdbcType=BIGINT}, ",
        "#{moneyableId,jdbcType=BIGINT}, #{moneyableType,jdbcType=VARCHAR}, ",
        "#{kind,jdbcType=INTEGER}, #{amount,jdbcType=DECIMAL}, #{moneyFrom,jdbcType=DECIMAL}, ",
        "#{algorithm,jdbcType=VARCHAR}, #{moneyTo,jdbcType=DECIMAL}, ",
        "#{modifyId,jdbcType=BIGINT}, #{detail,jdbcType=VARCHAR})"
    })
    int insert(MoneyRecordBean record);

    @InsertProvider(type=MoneyRecordBeanSqlProvider.class, method="insertSelective")
    int insertSelective(MoneyRecordBean record);

    @Select("<script>select t.moneyable_type as moneyabletype,ifnull(sum(t.amount),0) as totle  from (select * from  money_record  <where> 1=1 <if test='date!=null'>and to_days(create_time)=to_days(#{date})</if></where>) t group by t.moneyable_type</script>")
    List<JSONObject> selectTotleGroupByType(@Param("date")String date);
}