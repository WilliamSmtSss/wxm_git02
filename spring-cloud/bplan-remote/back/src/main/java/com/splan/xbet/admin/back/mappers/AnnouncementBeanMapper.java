package com.splan.xbet.admin.back.mappers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.AnnouncementBean;
import com.splan.base.bean.BusinessConfigBean;
import com.splan.base.bean.NoticeBean;
import com.splan.base.enums.NoticeAction;
import com.splan.xbet.admin.back.base.SuperMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface AnnouncementBeanMapper extends SuperMapper<AnnouncementBean> {
    @Select("<script>" +
            "select * from announcement t <where>  t.status='ENABLE'" +
            "<if test='apiId!=null and apiId!=\"\"'> and t.api_id=#{apiId}</if>" +
            " <if test='startDate != null '> AND to_days(t.create_time) <![CDATA[>=]]> to_days(#{startDate}) </if> "+
            " <if test='endDate != null '> AND to_days(t.create_time) <![CDATA[<=]]> to_days(#{endDate}) </if> "+
            " <if test='action != null '> and t.action=#{action} or (t.action=#{action} and t.api_id is null and t.status='ENABLE') </if> "+
//            " <if test='action != null and action.toString()==\"notice\"'> or (t.api_id is null and t.action='notice') </if>"+
            "</where>" +
            " order by t.create_time desc"+
            "</script>")
    List<AnnouncementBean> getNotices(Page page, String apiId, @Param("startDate") Date startDate, @Param("endDate") Date endDate, NoticeAction action);
}
