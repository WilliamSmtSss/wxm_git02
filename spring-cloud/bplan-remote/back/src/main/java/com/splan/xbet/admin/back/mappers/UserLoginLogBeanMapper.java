package com.splan.xbet.admin.back.mappers;

import com.splan.base.bean.UserLoginLogBean;
import com.splan.xbet.admin.back.base.SuperMapper;
import org.apache.ibatis.annotations.Select;

public interface UserLoginLogBeanMapper extends SuperMapper<UserLoginLogBean> {

    @Select("select t.ip from user_login_log t where t.user_id=#{userId} order by t.create_time desc LIMIT 1")
    String getLastLoginIp(String userId);

}