package com.splan.xbet.back.backforbusiness.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.back.BackNotice;
import com.splan.base.bean.front.FrontCompanyInfo;
import com.splan.base.bean.front.SysUser;
import com.splan.base.enums.CheckStatus;
import com.splan.base.enums.ResultStatus;
import com.splan.base.param.backremote.BackProductOrderParam;
import com.splan.base.param.backremote.BackProductTrialParam;
import com.splan.base.result.backremote.BackProductTrialResult;
import com.splan.base.service.OauthClientDetailsService;
import com.splan.base.service.v2.FrontBackService;
import com.splan.base.http.CommonResult;
import com.splan.base.param.remote.BusinessFormsParam;
import com.splan.base.param.remote.FirstDataParam;
import com.splan.base.result.BusinessFormsResult;
import com.splan.base.result.backremote.BackProductInfoResult;
import com.splan.base.result.remote.FrontDataResultOut;
import com.splan.base.service.v2.V2BackService;
import com.splan.base.utils.ResultUtil;
import com.splan.xbet.back.backforbusiness.mappers.FrontCompanyInfoMapper;
//import com.splan.xbet.back.backforbusiness.mappers.FrontConfigMapper;
import com.splan.xbet.back.backforbusiness.service.DataService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private V2BackService v2BackService;

    @Autowired
    private FrontCompanyInfoMapper frontCompanyInfoMapper;

    @Autowired
    private FrontBackService frontBackService;

//    @Autowired
//    private FrontConfigMapper frontConfigMapper;

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @Override
    public CommonResult<FrontDataResultOut> getFirstData(FirstDataParam firstDataParam) {
        return ResultUtil.returnSuccess(JSON.parseObject(v2BackService.firstData(firstDataParam),new TypeReference<FrontDataResultOut>(){}));
    }

    @Override
    public CommonResult<List<BusinessFormsResult>> businessForms(BusinessFormsParam businessFormsParam) {
        return ResultUtil.returnSuccess(JSON.parseObject(v2BackService.businessForms(businessFormsParam),new TypeReference<List<BusinessFormsResult>>(){}));
    }

    @Override
    public CommonResult<Page<BackProductInfoResult>> servicesList(BackProductOrderParam backProductOrderParam) {
//       Test
//        List<BackProductInfoResult> results=new ArrayList<>();
//        results.add(new BackProductInfoResult());
//        return ResultUtil.returnSuccess(results);
        Page<BackProductInfoResult> page=JSON.parseObject(frontBackService.selOrder(backProductOrderParam),new TypeReference<Page<BackProductInfoResult>>(){});
        return ResultUtil.returnSuccess(page);
    }

    @Override
    public CommonResult<FrontCompanyInfo> companyInfo(SysUser sysUser) {
        return ResultUtil.returnSuccess(frontCompanyInfoMapper.selectOne(new QueryWrapper<FrontCompanyInfo>().eq("sys_id",sysUser.getId())));
    }

    @Override
    public CommonResult<BackProductTrialResult> serviceTrial(SysUser sysUser) {
        BackProductTrialParam backProductTrialParam=new BackProductTrialParam();
        backProductTrialParam.setSysId(sysUser.getId());
        BackProductTrialResult backProductTrialResult=JSON.parseObject(frontBackService.selTrial(backProductTrialParam),new TypeReference<BackProductTrialResult>(){});
        if(null != backProductTrialResult.getSysId())
            return ResultUtil.returnSuccess(backProductTrialResult);
        else {
            BackProductTrialResult result=new BackProductTrialResult();
            result.setStatus(CheckStatus.UNABLE);
            return ResultUtil.returnSuccess(result);
        }

    }

    @Override
    public CommonResult paramConfigSel(SysUser sysUser) {
//        return ResultUtil.returnSuccess(frontConfigMapper.selectOne(new QueryWrapper<FrontConfig>().eq("sys_id",sysUser.getId())));
        if(StringUtils.isNotBlank(sysUser.getApiid()))
            return ResultUtil.returnSuccess(oauthClientDetailsService.getClient2(sysUser.getApiid()));
        else
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
    }

    @Override
    public CommonResult<Page<BackNotice>> noticeSel(SysUser sysUser, Map<String, Object> req) {
        try {
            Page<BackNotice> page = JSON.parseObject(frontBackService.selNotice(req), new TypeReference<Page<BackNotice>>() {
            });
            return ResultUtil.returnSuccess(page);
        }catch (Exception e){
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
        }
    }

}
