package com.splan.bplan.mappers;

import com.splan.base.bean.CommonMobileAreaBean;
import org.apache.ibatis.jdbc.SQL;

public class CommonMobileAreaBeanSqlProvider {

    public String insertSelective(CommonMobileAreaBean record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("common_mobile_area");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getAreaName() != null) {
            sql.VALUES("area_name", "#{areaName,jdbcType=VARCHAR}");
        }
        
        if (record.getAreaCode() != null) {
            sql.VALUES("area_code", "#{areaCode,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }
}