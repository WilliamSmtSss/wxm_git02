package com.splan.bplan.mappers;

import com.splan.base.base.SuperMapper;
import com.splan.base.bean.BetExampleBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;

public interface BetExampleBeanMapper extends SuperMapper<BetExampleBean> {


    @InsertProvider(type=BetExampleBeanSqlProvider.class, method="insertSelective")
    int insertSelective(BetExampleBean record);

    @Select("select * from bet_example_co where topicable_type=#{topicableType} and category=#{category} and support=#{support} and game_type=#{gameType} limit 0,1")
    BetExampleBean selectBySupport(String topicableType, Integer category, Integer support, Integer gameType);
}