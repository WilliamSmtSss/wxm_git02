package com.splan.xbet.admin.back.mappers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.XExportBean;
import com.splan.xbet.admin.back.base.SuperMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("xExportMapper")
public interface XExportMapper extends SuperMapper<XExportBean> {

    @Select("select * from x_export t where to_days(now())-to_days(t.create_time) <![CDATA[>=]]> 7")
//    @Select("select * from x_export t ")
    List<XExportBean> getExpires();

    @Select("select t.*,ifnull(t2.username,'') as sys_name from x_export t  left join sys_user t2 on t.sys_id=t2.id where t.sys_id=#{sysId} order by t.create_time desc")
//    @Select("select * from x_export t ")
    List<XExportBean> getList(Page page, Integer sysId);

}
