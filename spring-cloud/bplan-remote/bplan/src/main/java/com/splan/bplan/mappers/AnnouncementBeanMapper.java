package com.splan.bplan.mappers;

import com.splan.base.base.SuperMapper;
import com.splan.base.bean.AnnouncementBean;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AnnouncementBeanMapper extends SuperMapper<AnnouncementBean> {

    @Select("select * from announcement where ((action='notice' and api_id=#{apiId}) or action='bet_notice')  and status='ENABLE' order by create_time desc limit 0,10")
    List<AnnouncementBean> getNotice(String apiId);
}