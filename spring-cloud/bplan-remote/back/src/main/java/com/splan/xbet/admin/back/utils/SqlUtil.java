package com.splan.xbet.admin.back.utils;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.splan.base.bean.XExportBean;
import com.splan.xbet.admin.back.mappers.XExportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SqlUtil {

    @Autowired
    private  XExportMapper xExportMapper;

    public  void clearExportTemp(String sysId){
        xExportMapper.delete(new UpdateWrapper<XExportBean>().eq("sys_id",sysId));
    }

}
