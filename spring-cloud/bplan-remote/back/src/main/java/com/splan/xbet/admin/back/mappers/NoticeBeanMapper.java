package com.splan.xbet.admin.back.mappers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.NoticeBean;
import com.splan.xbet.admin.back.base.SuperMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface NoticeBeanMapper extends SuperMapper<NoticeBean> {
    @Select("<script>" +
            "select * from notice t <where> t.action='notice' and t.status='ENABLE'" +
            "<if test='apiId!=null and apiId!=\"\"'> and t.api_id=#{apiId}</if>" +
            " <if test='startDate != null '> AND to_days(t.create_time) <![CDATA[>=]]> to_days(#{startDate}) </if> "+
            " <if test='endDate != null '> AND to_days(t.create_time) <![CDATA[<=]]> to_days(#{endDate}) </if> "+
            "</where>" +
            " order by t.create_time desc"+
            "</script>")
    List<NoticeBean> getNotices(Page page, String apiId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}