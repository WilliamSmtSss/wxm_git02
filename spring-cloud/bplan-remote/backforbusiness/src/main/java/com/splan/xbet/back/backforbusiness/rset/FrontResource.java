package com.splan.xbet.back.backforbusiness.rset;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.front.FrontCompanyInfo;
import com.splan.base.bean.front.SysUser;
import com.splan.base.enums.CheckStatus;
import com.splan.base.service.v2.FrontBackService;
import com.splan.base.service.v2.FrontService;
import com.splan.xbet.back.backforbusiness.mappers.FrontCompanyInfoMapper;
import com.splan.xbet.back.backforbusiness.mappers.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class FrontResource implements FrontService {

    @Autowired
    private FrontCompanyInfoMapper frontCompanyInfoMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public String checkCompanyInfo(Map<String, Object> requestParam) {
        Integer id=Integer.parseInt(requestParam.get("id").toString());
        CheckStatus checkStatus=requestParam.get("checkStatus")!=null?CheckStatus.getCheckStatus(requestParam.get("checkStatus").toString()):null;
        FrontCompanyInfo frontCompanyInfo=new FrontCompanyInfo();
        frontCompanyInfo.setId(id);
        frontCompanyInfo.setCheckStatus(checkStatus);
        return frontCompanyInfoMapper.updateById(frontCompanyInfo)+"";
    }

    @Override
    public String selCompanyInfo(Map<String, Object> requestParam) {
        String searchTxt=requestParam.get("searchText")!=null?requestParam.get("searchText").toString():null;
        CheckStatus checkStatus=requestParam.get("checkStatus")!=null?CheckStatus.getCheckStatus(requestParam.get("checkStatus").toString()):null;
        Boolean status=requestParam.get("status")!=null?(boolean)requestParam.get("status"):null;
        long current=Long.parseLong(requestParam.get("current").toString());
        long size=Long.parseLong(requestParam.get("size").toString());
        Page page=new Page();
        page.setCurrent(current);
        page.setSize(size);
        List<FrontCompanyInfo> list = frontCompanyInfoMapper.getList(page,searchTxt,checkStatus,status);
        page.setRecords(list);
        return JSON.toJSONString(page);
    }

    @Override
    public String busInfoSel(Map<String, Object> requestParam) {
        String searchTxt=requestParam.get("searchText")!=null?requestParam.get("searchText").toString():null;
        CheckStatus checkStatus=requestParam.get("checkStatus")!=null?CheckStatus.getCheckStatus(requestParam.get("checkStatus").toString()):null;
        Integer status=requestParam.get("status")!=null?Integer.parseInt(requestParam.get("status").toString()):null;
        long current=Long.parseLong(requestParam.get("current").toString());
        long size=Long.parseLong(requestParam.get("size").toString());
        Page page=new Page();
        page.setCurrent(current);
        page.setSize(size);
        List<SysUser> list=sysUserMapper.getBusList(page,searchTxt,checkStatus,status);
        for(SysUser s:list){
            try {
                s.setFrontCompanyInfo(frontCompanyInfoMapper.selectList(new QueryWrapper<FrontCompanyInfo>().eq("sys_id", s.getId())).get(0));
            }catch (Exception e){
                s.setFrontCompanyInfo(new FrontCompanyInfo());
            }
        }
        page.setRecords(list);
        return JSON.toJSONString(page);
    }

}
