package com.splan.xbet.admin.back.mappers;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.xbet.admin.back.base.SuperMapper;
import com.splan.base.bean.MoneyRecordBean;
import com.splan.base.enums.MoneyAbleType;
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
    List<JSONObject> selectTotleGroupByType(@Param("date") String date);


    @Select("select * from money_record where balance_id=#{balanceId} and moneyable_id=#{moneyableId} and moneyable_type=#{moneyAbleType}")
    MoneyRecordBean getMoneyRecord(Long balanceId, Long moneyableId, MoneyAbleType moneyAbleType);

    @Select("select count(*) from money_record where balance_id=#{balanceId} and moneyable_id=#{moneyableId} and moneyable_type=#{moneyAbleType}")
    Integer countMoneyRecord(Long balanceId, Long moneyableId, MoneyAbleType moneyAbleType);

    @Select("select * from money_record where modify_id=#{userId} and moneyable_type=#{moneyAbleType} ORDER BY id desc LIMIT 0,1")
    MoneyRecordBean getLastRecord(Long userId, MoneyAbleType moneyAbleType);

    @Select("<script>" +
            "select t.*,t2.invite_code from money_record t left join user_account t2 on t.modify_id=t2.id  where 1=1 " +
            " <if test='startDate != null '> AND to_days(t.create_time) <![CDATA[>=]]> to_days(#{startDate}) </if> "+
            " <if test='endDate != null '> AND to_days(t.create_time) <![CDATA[<=]]> to_days(#{endDate}) </if> "+
            " <if test='inviteCode != null '> AND t2.invite_code=#{inviteCode} </if> "+
            " <if test='moneyAbleType != null '> AND t.moneyable_type=#{moneyAbleType} </if> "+
            " <if test='userIds != null and userIds.size()!=0'> and t.modify_id in(<foreach collection='userIds' item='userid' separator=','>#{userid}</foreach>) </if> "+
            " order by t.create_time desc "+
            "</script>")
    List<MoneyRecordBean> moneyRecordList(Page page, Date startDate, Date endDate, String inviteCode, List<Integer> userIds, String moneyAbleType);
}