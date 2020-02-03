package com.splan.xbet.frontback.frontback.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BusinessConfigBean;
import com.splan.base.bean.back.BackMessage;
import com.splan.base.bean.back.BackProductOrder;
import com.splan.base.bean.front.FrontCompanyInfo;
import com.splan.base.bean.front.SysUser;
import com.splan.base.enums.Language;
import com.splan.base.enums.ResultStatus;
import com.splan.base.enums.WalletType;
import com.splan.base.enums.back.NoticeType;
import com.splan.base.enums.back.OddType;
import com.splan.base.http.CommonResult;
import com.splan.base.service.v2.FrontService;
import com.splan.base.service.v2.V2BackService;
import com.splan.base.utils.ResultUtil;
import com.splan.xbet.frontback.frontback.mappers.BackMessageMapper;
import com.splan.xbet.frontback.frontback.mappers.BackProductOrderMapper;
import com.splan.xbet.frontback.frontback.service.AccessMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class AccessMessageServiceImpl implements AccessMessageService {

    @Autowired
    private FrontService frontService;

    @Autowired
    private V2BackService v2BackService;

    @Autowired
    private BackProductOrderMapper backProductOrderMapper;

    @Autowired
    private BackMessageMapper backMessageMapper;

    @Override
    public CommonResult checkCompanyInfo(Map<String, Object> requestParam) {
        backMessageMapper.updateByMsgId(requestParam.get("id").toString(), NoticeType.company_check.toString());
        return ResultUtil.returnSuccess(frontService.checkCompanyInfo(requestParam));
    }

    @Override
    public CommonResult<Page<FrontCompanyInfo>> selCompanyInfo(Map<String, Object> requestParam) {
        return ResultUtil.returnSuccess(JSON.parseObject(frontService.selCompanyInfo(requestParam),new TypeReference<Page<FrontCompanyInfo>>(){}));
    }

    @Override
    public CommonResult<Page<BusinessConfigBean>> busSel(Map<String, Object> requestParam) {
        Page<BusinessConfigBean> result=JSON.parseObject(v2BackService.busSel(requestParam),new TypeReference<Page<BusinessConfigBean>>(){});
        for(BusinessConfigBean b:result.getRecords()){
            b.setLanguageName(Language.getCnameByeName(b.getLanguage()));
            b.setWalletTypeName(WalletType.getCnameByeName(b.getWalletType()));
            b.setOddTypeName(OddType.getCnameByeName(b.getOddType()));
        }
        return ResultUtil.returnSuccess(result);
    }

    @Override
    public CommonResult busEdit(Map<String, Object> requestParam) {
        if(null==requestParam.get("id"))
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
        v2BackService.busEdit(requestParam);
        return ResultUtil.returnSuccess(1);
    }

    @Override
    public CommonResult busAdd(Map<String, Object> requestParam) {
        return null;
    }

    @Override
    public CommonResult<Page<SysUser>> busInfoSel(Map<String, Object> requestParam) {
        Page<SysUser> page=JSON.parseObject(frontService.busInfoSel(requestParam),new TypeReference<Page<SysUser>>(){});
        for(SysUser s:page.getRecords()){
            if(StringUtils.isNotBlank(s.getApiid())) {
                if(backProductOrderMapper.getPassOrder(s.getApiid())!=0)
                    s.setOpenStatus(true);
                else
                    s.setOpenStatus(false);
            }else{
                s.setOpenStatus(false);
            }
        }
        return ResultUtil.returnSuccess(page);
    }

}
