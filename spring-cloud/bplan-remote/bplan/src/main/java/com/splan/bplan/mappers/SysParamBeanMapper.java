package com.splan.bplan.mappers;

import com.splan.base.base.SuperMapper;
import com.splan.base.bean.SysParamBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SysParamBeanMapper extends SuperMapper<SysParamBean> {
    @Select({"select * from sys_param where param_name = #{paramName}"})
    SysParamBean selectByParamName(@Param("paramName")String paramName);
}