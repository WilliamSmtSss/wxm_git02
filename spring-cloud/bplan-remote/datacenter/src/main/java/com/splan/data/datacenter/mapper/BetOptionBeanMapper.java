package com.splan.data.datacenter.mapper;

import com.splan.base.base.SuperMapper;
import com.splan.base.bean.BetOptionBean;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BetOptionBeanMapper extends SuperMapper<BetOptionBean> {


    @Select("select * from bet_option where bet_data_id=#{betDataId} ")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Integer.class)
    })
    List<BetOptionBean> selectByBetDataId(Integer betDataId);

    @Select("select CONCAT(b.group_name,' ',a.name) as vsDetail from bet_option as a LEFT JOIN bet_topics as b on a.bet_data_id=b.id where a.id=#{betDataId} ")
    String getVsDetail(Integer betDataId);
}