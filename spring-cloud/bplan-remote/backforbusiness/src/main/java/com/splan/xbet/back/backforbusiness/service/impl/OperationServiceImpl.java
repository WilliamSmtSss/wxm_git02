package com.splan.xbet.back.backforbusiness.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BusinessConfigBean;
import com.splan.base.bean.front.FrontCompanyInfo;
import com.splan.base.bean.front.SysUser;
import com.splan.base.enums.CheckStatus;
import com.splan.base.enums.back.NoticeType;
import com.splan.base.param.backremote.BackProductOrderParam;
import com.splan.base.param.backremote.BackProductTrialParam;
import com.splan.base.result.backremote.BackProductTrialResult;
import com.splan.base.service.OauthClientDetailsService;
import com.splan.base.service.ProxyConfigProvideService;
import com.splan.base.service.v2.FrontBackService;
import com.splan.base.enums.ResultStatus;
import com.splan.base.http.CommonResult;
import com.splan.base.param.frontremote.ParamConfigParam;
import com.splan.base.service.v2.V2BackService;
import com.splan.base.utils.ResultUtil;
import com.splan.xbet.back.backforbusiness.mappers.FrontCompanyInfoMapper;
//import com.splan.xbet.back.backforbusiness.mappers.FrontConfigMapper;
import com.splan.xbet.back.backforbusiness.mappers.SysUserMapper;
import com.splan.xbet.back.backforbusiness.param.AuthenticationEnterpriseParam;
import com.splan.xbet.back.backforbusiness.service.OperationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OperationServiceImpl implements OperationService {

    @Autowired
    private FrontCompanyInfoMapper frontCompanyInfoMapper;

    @Autowired
    private FrontBackService frontBackService;

//    @Autowired
//    private FrontConfigMapper frontConfigMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @Autowired
    private ProxyConfigProvideService proxyConfigProvideService;

    @Autowired
    private V2BackService v2BackService;

    @Override
    public CommonResult openService(BackProductOrderParam backProductOrderParam) {
        frontBackService.addOrder(backProductOrderParam);
        return ResultUtil.returnSuccess(1);
    }

    @Override
    public CommonResult paramConfig(ParamConfigParam paramConfigParam) {
        Map<String, Object> requestParam=new HashMap<>();
          if(StringUtils.isNotBlank(paramConfigParam.getBusinessName())) {
              requestParam.put("businessName",paramConfigParam.getBusinessName());
              requestParam.put("status","0");
              requestParam.put("current","1");
              requestParam.put("size","10");
              Page<BusinessConfigBean> page= JSON.parseObject(v2BackService.busSel(requestParam),new TypeReference<Page<BusinessConfigBean>>(){});
              if(page.getRecords().size() == 0){
                  requestParam.clear();
                  requestParam.put("apiId",paramConfigParam.getBusinessName());
                  v2BackService.busAdd(requestParam);
              }
              if(null==oauthClientDetailsService.getClient2(paramConfigParam.getBusinessName())){
                  proxyConfigProvideService.save(paramConfigParam.getBusinessName());
                  oauthClientDetailsService.saveClient(paramConfigParam.getBusinessName(),paramConfigParam.getClientSecret(),paramConfigParam.getIpWhitelist());
              }else{
                  oauthClientDetailsService.updateClient(paramConfigParam.getBusinessName(),paramConfigParam.getClientSecret(),paramConfigParam.getIpWhitelist());
              }
          }
          return ResultUtil.returnSuccess(1);
    }

    @Override
    public CommonResult authenticationEnterprise(SysUser sysUser, AuthenticationEnterpriseParam authenticationEnterpriseParam) {
        FrontCompanyInfo frontCompanyInfo=new FrontCompanyInfo();
        frontCompanyInfo.setName(authenticationEnterpriseParam.getName());
        frontCompanyInfo.setAddress(authenticationEnterpriseParam.getAddress());
        frontCompanyInfo.setCard(authenticationEnterpriseParam.getCard());
        frontCompanyInfo.setImageUrl(authenticationEnterpriseParam.getImageUrl());
        frontCompanyInfo.setRepresentative(authenticationEnterpriseParam.getRepresentative());
        frontCompanyInfo.setPhone(authenticationEnterpriseParam.getPhone());
        frontCompanyInfo.setWebsite(authenticationEnterpriseParam.getWebsite());
        frontCompanyInfo.setCheckStatus(CheckStatus.UNCHECK);
        frontCompanyInfo.setStatus(false);
        try {
            FrontCompanyInfo sel = frontCompanyInfoMapper.selectList(new QueryWrapper<FrontCompanyInfo>().eq("sys_id", sysUser.getId())).get(0);
            Map<String,Object> req=new HashMap<>();
            req.put("msgId",sel.getId());
            req.put("msgType", NoticeType.company_check.toString());
            req.put("apiId",sysUser.getApiid());
            frontBackService.addMessage(req);
        }catch (Exception e){

        }
        return ResultUtil.returnSuccess(frontCompanyInfoMapper.update(frontCompanyInfo,new UpdateWrapper<FrontCompanyInfo>().eq("sys_id",sysUser.getId())));
    }

    @Override
    public CommonResult editEnterprise(AuthenticationEnterpriseParam authenticationEnterpriseParam) {
        if(authenticationEnterpriseParam.getId()==null)
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
        FrontCompanyInfo have=frontCompanyInfoMapper.selectById(authenticationEnterpriseParam.getId());
        if(null==have)
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
        if(have.getCheckStatus() != CheckStatus.UNCHECK)
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
        FrontCompanyInfo update=new FrontCompanyInfo();
        update.setName(authenticationEnterpriseParam.getName());
        update.setAddress(authenticationEnterpriseParam.getAddress());
        update.setCard(authenticationEnterpriseParam.getCard());
        update.setImageUrl(authenticationEnterpriseParam.getImageUrl());
        update.setRepresentative(authenticationEnterpriseParam.getRepresentative());
        update.setPhone(authenticationEnterpriseParam.getPhone());
        update.setWebsite(authenticationEnterpriseParam.getWebsite());
        return ResultUtil.returnSuccess(frontCompanyInfoMapper.update(update,new UpdateWrapper<FrontCompanyInfo>().eq("id",authenticationEnterpriseParam.getId())));
    }

    @Override
    public CommonResult serviceTrialAdd(SysUser sysUser) {
        BackProductTrialParam backProductTrialParam=new BackProductTrialParam();
        backProductTrialParam.setSysId(sysUser.getId());
        backProductTrialParam.setBusinessName(sysUser.getApiid());
        backProductTrialParam.setPhone(sysUser.getUsername());
        BackProductTrialResult backProductTrialResult= JSON.parseObject(frontBackService.selTrial(backProductTrialParam),new TypeReference<BackProductTrialResult>(){});

        if(null!=backProductTrialResult.getSysId())
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);

        frontBackService.addTrial(backProductTrialParam);
        return ResultUtil.returnSuccess(1);
    }

    @Override
    public CommonResult<SysUser> editAccountInfo(SysUser sysUser,String nickName) {
        SysUser update=new SysUser();
        update.setNickname(nickName);
        update.setId(sysUser.getId());
        sysUserMapper.updateById(update);
        return ResultUtil.returnSuccess(sysUserMapper.selectById(sysUser.getId()));
    }

}
