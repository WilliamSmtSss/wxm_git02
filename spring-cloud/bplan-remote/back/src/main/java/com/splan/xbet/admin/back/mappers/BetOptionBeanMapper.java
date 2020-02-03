package com.splan.xbet.admin.back.mappers;

import com.splan.xbet.admin.back.base.SuperMapper;
import com.splan.base.bean.BetOptionBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BetOptionBeanMapper extends SuperMapper<BetOptionBean> {


    @Select("select * from bet_option where bet_data_id=#{betDataId} ")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class)
    })
    List<BetOptionBean> selectByBetDataId(Integer betDataId);

    @Select("select CONCAT(b.group_name,' ',a.name) as vsDetail from bet_option as a LEFT JOIN bet_topics as b on a.bet_data_id=b.id where a.id=#{betDataId} ")
    String getVsDetail(Integer betDataId);

    @Select("select * from bet_option t where t.bet_data_id=#{betId} and t.bet_result='1'")
    BetOptionBean getBetResultByBetID(@Param("betId") String betId);
}