package com.splan.bplan.mappers;

import com.splan.base.base.SuperMapper;
import com.splan.base.bean.CommonMobileAreaBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommonMobileAreaBeanMapper extends SuperMapper<CommonMobileAreaBean> {


    @InsertProvider(type=CommonMobileAreaBeanSqlProvider.class, method="insertSelective")
    int insertSelective(CommonMobileAreaBean record);

    @Select({"select id, area_name, area_code from common_mobile_area"})
    List<CommonMobileAreaBean> selectAreaList();
}