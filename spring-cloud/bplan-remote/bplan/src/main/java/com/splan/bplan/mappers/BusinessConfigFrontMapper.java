package com.splan.bplan.mappers;

import com.splan.base.base.SuperMapper;
import com.splan.base.bean.BusinessConfigBean;
import org.apache.ibatis.annotations.Select;

public interface BusinessConfigFrontMapper extends SuperMapper<BusinessConfigBean> {

    @Select("select * from business_config where api_id=#{apiId} ")
    BusinessConfigBean selectByApiId(String apiId);
}
