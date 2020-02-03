package com.splan.xbet.back.backforbusiness.mappers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.base.SuperMapper;
import com.splan.base.bean.front.FrontCompanyInfo;
import com.splan.base.enums.CheckStatus;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FrontCompanyInfoMapper extends SuperMapper<FrontCompanyInfo> {

    @Select("<script>" +
            "select * from front_company_info t where 1=1 " +
            "<if test='searchText!=null'> and (t.phone=#{searchText} or t.id=#{searchText})</if>"+
            "<if test='checkStatus!=null'> and t.check_status=#{checkStatus}</if>"+
            "<if test='status!=null'> and t.status=#{status}</if>"+
            "</script>")
    List<FrontCompanyInfo> getList(Page page, String searchText, CheckStatus checkStatus,Boolean status);

}
