package com.splan.xbet.admin.back.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.*;
import com.splan.base.enums.Language;
import com.splan.base.enums.ResultStatus;
import com.splan.base.http.CommonResult;
import com.splan.base.result.BusinessFormsResult;
import com.splan.base.service.OauthClientDetailsService;
import com.splan.base.service.ProxyConfigProvideService;
import com.splan.xbet.admin.back.dto.DateDto;
import com.splan.xbet.admin.back.dto.InBusinessConfigDto;
import com.splan.xbet.admin.back.dto.InBusinessCurrencyConfigDto;
import com.splan.xbet.admin.back.enums.*;
import com.splan.xbet.admin.back.mappers.*;
import com.splan.base.result.CommonOperatorResult;
import com.splan.xbet.admin.back.service.XBetBusinessService;
import com.splan.xbet.admin.back.utils.CommonUtil;
import com.splan.xbet.admin.back.utils.ResultUtil;
import com.splan.xbet.admin.back.utils.SqlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class XBetBusinessServiceImpl implements XBetBusinessService {

    @Autowired
    private BusinessConfigBeanMapper businessConfigBeanMapper;

    @Autowired
    private BusinessCurrencyConfigBeanMapper businessCurrencyConfigBeanMapper;

    @Autowired
    private BusinessCurrencyRecordBeanMapper businessCurrencyRecordBeanMapper;

    @Autowired
    private UserLoginLogBeanMapper userLoginLogBeanMapper;

    @Autowired
    private XbetBackMapper xbetBackMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ProxyConfigProvideService configProvideService;

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @Autowired
    private SqlUtil sqlUtil;

    @Override
    public CommonResult add(SysUser sysUserParam, InBusinessConfigDto inBusinessConfigDto) {
        if(!CommonUtil.checkNotChinese(inBusinessConfigDto.getApiId()+inBusinessConfigDto.getClientSecret()))
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
        if(StringUtils.isNotBlank(sysUserParam.getApiid())){
            if(sysUserParam.getRoleId()==BusRole.BUS_DOWN.geteName()) {
                return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
            }
            else if(sysUserParam.getRoleId()==BusRole.BUS.geteName()){
                if(null==inBusinessConfigDto.getPid()){
                    return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
                }
            }
        }
        BusinessConfigBean businessConfigBean=new BusinessConfigBean();
        businessConfigBean.setPid(inBusinessConfigDto.getPid());
        businessConfigBean.setApiId(inBusinessConfigDto.getApiId());
        businessConfigBean.setSysUserId(inBusinessConfigDto.getSysUserId());
        businessConfigBean.setProductType(inBusinessConfigDto.getProductType());
        businessConfigBean.setProductSkin(inBusinessConfigDto.getProductSkin());
        businessConfigBean.setCurrency(inBusinessConfigDto.getCurrency());
        businessConfigBean.setCoefficient(inBusinessConfigDto.getCoefficient());
        businessConfigBean.setWalletType(inBusinessConfigDto.getWalletType());
        businessConfigBean.setLogo(inBusinessConfigDto.getLogo());
        businessConfigBean.setStatus(inBusinessConfigDto.getStatus());
        businessConfigBean.setLanguage(inBusinessConfigDto.getLanguage());
        int x=businessConfigBeanMapper.insert(businessConfigBean);
        if(x!=0){
            SysUser sysUser=new SysUser();
            if(null==inBusinessConfigDto.getPid())
                sysUser.setRoleId(BusRole.BUS.geteName());
            else
                sysUser.setRoleId(BusRole.BUS_DOWN.geteName());

            sysUser.setApiid(inBusinessConfigDto.getApiId());
            x=sysUserMapper.update(sysUser,new UpdateWrapper<SysUser>().eq("id",inBusinessConfigDto.getSysUserId()));
            if(x!=0){
                x=configProvideService.save(inBusinessConfigDto.getApiId());
                x=oauthClientDetailsService.saveClient(inBusinessConfigDto.getApiId(),inBusinessConfigDto.getClientSecret(),inBusinessConfigDto.getIpWhitelist());
            }
        }
        return ResultUtil.returnSuccess(x);
    }

    @Override
    public CommonResult edit(InBusinessConfigDto inBusinessConfigDto) {

        if(!CommonUtil.checkNotChinese(inBusinessConfigDto.getApiId()+inBusinessConfigDto.getClientSecret()))
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);

        if(null==inBusinessConfigDto.getId()){
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
        }
        BusinessConfigBean have=businessConfigBeanMapper.selectById(inBusinessConfigDto.getId());
        if(null==have)
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
        BusinessConfigBean businessConfigBean=new BusinessConfigBean();
        if(null!=inBusinessConfigDto.getPid())
            businessConfigBean.setPid(inBusinessConfigDto.getPid());
        if(StringUtils.isNotBlank(inBusinessConfigDto.getApiId()))
            businessConfigBean.setApiId(inBusinessConfigDto.getApiId());
        if(null!=inBusinessConfigDto.getSysUserId())
            businessConfigBean.setSysUserId(inBusinessConfigDto.getSysUserId());
        if(null!=inBusinessConfigDto.getProductType())
            businessConfigBean.setProductType(inBusinessConfigDto.getProductType());
        if(null!=inBusinessConfigDto.getProductSkin())
            businessConfigBean.setProductSkin(inBusinessConfigDto.getProductSkin());
        if(null!=inBusinessConfigDto.getCurrency())
            businessConfigBean.setCurrency(inBusinessConfigDto.getCurrency());
        if(null!=inBusinessConfigDto.getCoefficient())
            businessConfigBean.setCoefficient(inBusinessConfigDto.getCoefficient());
        if(null!=inBusinessConfigDto.getWalletType())
            businessConfigBean.setWalletType(inBusinessConfigDto.getWalletType());
//        if(StringUtils.isNotBlank(inBusinessConfigDto.getLogo()))
            businessConfigBean.setLogo(inBusinessConfigDto.getLogo());
        if(StringUtils.isNotBlank(inBusinessConfigDto.getStatus()))
            businessConfigBean.setStatus(inBusinessConfigDto.getStatus());
        if(StringUtils.isNotBlank(inBusinessConfigDto.getLanguage()))
            businessConfigBean.setLanguage(inBusinessConfigDto.getLanguage());
        UpdateWrapper<BusinessConfigBean> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",inBusinessConfigDto.getId());
        int x=businessConfigBeanMapper.update(businessConfigBean,updateWrapper);
        if(x!=0){
            SysUser sysUser=new SysUser();
            sysUser.setApiid(inBusinessConfigDto.getApiId());
            if(null==have.getPid())
                sysUser.setRoleId(BusRole.BUS.geteName());
            else
                sysUser.setRoleId(BusRole.BUS_DOWN.geteName());

            x=sysUserMapper.update(sysUser,new UpdateWrapper<SysUser>().eq("id",inBusinessConfigDto.getSysUserId()));
            if(x!=0){
                if(have.getSysUserId()!=null && have.getSysUserId().intValue()!=inBusinessConfigDto.getSysUserId().intValue()) {
                    SysUser old = new SysUser();
                    old.setApiid("");
                    old.setRoleId(0);
                    sysUserMapper.update(old, new UpdateWrapper<SysUser>().eq("id", have.getSysUserId()));
                    sqlUtil.clearExportTemp(have.getSysUserId()+"");
                }
//                if(StringUtils.isNotBlank(inBusinessConfigDto.getClientSecret()) || StringUtils.isNotBlank(inBusinessConfigDto.getIpWhitelist()))
                    x=oauthClientDetailsService.updateClient(have.getApiId(),inBusinessConfigDto.getClientSecret(),inBusinessConfigDto.getIpWhitelist());
            }
        }
        return ResultUtil.returnSuccess(x);
    }

    @Override
    public CommonResult<IPage<BusinessConfigBean>> list(SysUser sysUser,Page page,String status, String businessName) {
        if (StringUtils.isBlank(status))status=null;
        if (StringUtils.isBlank(businessName))businessName=null;
        String businessType="0";
        if(StringUtils.isNotBlank(sysUser.getApiid())){
            businessName=sysUser.getApiid();
            businessType=null;
        }
        List<BusinessConfigBean> list=businessConfigBeanMapper.getList(page,status,businessName,businessType,null);
        list.forEach(businessConfigBean -> {
            businessConfigBean.setDownCount(businessConfigBeanMapper.getDownBusCount(businessConfigBean.getId()+""));
            Map<String,Object> response=new HashMap<>();
            try {
                response=oauthClientDetailsService.getClient2(businessConfigBean.getApiId());
            }catch (Exception e){
                response.put("clientSecret","");
                response.put("ipWhitelist","");
            }
            businessConfigBean.setClientSecret(response.get("clientSecret").toString());
            businessConfigBean.setIpWhitelist(response.get("ipWhitelist").toString());
            businessConfigBean.setProductSkinName(ProductSkin.getCnameByeName(businessConfigBean.getProductSkin()));
            businessConfigBean.setCoefficientName(Coefficient.getCnameByeName(businessConfigBean.getCoefficient()));
            businessConfigBean.setWalletTypeName(WalletType.getCnameByeName(businessConfigBean.getWalletType()));
            businessConfigBean.setLanguageName(Language.getCnameByeName(businessConfigBean.getLanguage()));
            if(null!=businessConfigBean.getPid())businessConfigBean.setOperationStatus(false);
        });
        page.setRecords(list);
        CommonOperatorResult commonOperatorResult=new CommonOperatorResult();
        if(sysUser.getRoleId()==BusRole.BUS.geteName() || sysUser.getRoleId()==BusRole.BUS_DOWN.geteName()) {
            commonOperatorResult.setEdit(false);
            commonOperatorResult.setAdd(false);
        }
        return ResultUtil.returnSuccess(page,commonOperatorResult);
    }

    @Override
    public CommonResult currencyAdd(InBusinessCurrencyConfigDto inBusinessCurrencyConfigDto) {
        BusinessCurrencyConfigBean businessCurrencyConfigBean=new BusinessCurrencyConfigBean();
        businessCurrencyConfigBean.setCurrency(inBusinessCurrencyConfigDto.getCurrency());
        businessCurrencyConfigBean.setCode(inBusinessCurrencyConfigDto.getCode());
        businessCurrencyConfigBean.setName(inBusinessCurrencyConfigDto.getName());
        businessCurrencyConfigBean.setToCny(inBusinessCurrencyConfigDto.getToCny());
        return ResultUtil.returnSuccess(businessCurrencyConfigBeanMapper.insert(businessCurrencyConfigBean));
    }

    @Override
    public CommonResult currencyEdit(InBusinessCurrencyConfigDto inBusinessCurrencyConfigDto) {
        if(null==inBusinessCurrencyConfigDto.getId())
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);

        BusinessCurrencyConfigBean have=businessCurrencyConfigBeanMapper.selectById(inBusinessCurrencyConfigDto.getId());
        if(null==have)
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);

        JSONObject sysUser = (JSONObject) SecurityUtils.getSubject().getPrincipal();

        BusinessCurrencyConfigBean businessCurrencyConfigBean=new BusinessCurrencyConfigBean();
        businessCurrencyConfigBean.setCurrency(inBusinessCurrencyConfigDto.getCurrency());
        businessCurrencyConfigBean.setCode(inBusinessCurrencyConfigDto.getCode());
        businessCurrencyConfigBean.setName(inBusinessCurrencyConfigDto.getName());
        businessCurrencyConfigBean.setToCny(inBusinessCurrencyConfigDto.getToCny());
        UpdateWrapper<BusinessCurrencyConfigBean> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",inBusinessCurrencyConfigDto.getId());
        int x= businessCurrencyConfigBeanMapper.update(businessCurrencyConfigBean,updateWrapper);
        if(x!=0){
            BusinessCurrencyRecordBean businessCurrencyRecordBean=new BusinessCurrencyRecordBean();
            businessCurrencyRecordBean.setCurrencyId(have.getId());
            businessCurrencyRecordBean.setCurrencyCode(have.getCode());
            businessCurrencyRecordBean.setCurrencyName(have.getName());
            businessCurrencyRecordBean.setBeforeCny(have.getToCny());
            businessCurrencyRecordBean.setAfterCny(businessCurrencyConfigBean.getToCny());
            businessCurrencyRecordBean.setChangeTime(new Date());
            businessCurrencyRecordBean.setOperationName(sysUser.getString("username"));
            businessCurrencyRecordBeanMapper.insert(businessCurrencyRecordBean);
        }
        return ResultUtil.returnSuccess(x);
    }

    @Override
    public CommonResult<IPage<BusinessCurrencyConfigBean>> currencyList(Page page, DateDto dateDto) {
        List<BusinessCurrencyConfigBean> list=businessCurrencyConfigBeanMapper.getPageList(page,dateDto.getStartDate(),dateDto.getEndDate());
        page.setRecords(list);
        return ResultUtil.returnSuccess(page);
    }

    @Override
    public CommonResult<IPage<BusinessCurrencyRecordBean>> currencyRecordList(Page page, DateDto dateDto,String currencyId) {
        List<BusinessCurrencyRecordBean> list=businessCurrencyRecordBeanMapper.getPageList(page,dateDto.getStartDate(),dateDto.getEndDate(),currencyId);
        page.setRecords(list);
        return ResultUtil.returnSuccess(page);
    }

    @Override
    public CommonResult<IPage<BusinessFormsResult>> businessForms(SysUser sysUser, Page page, String selectType, String queryTimeType, String time, String businessName, String pid) {
        if(StringUtils.isBlank(selectType)||StringUtils.isBlank(queryTimeType))
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
        if(StringUtils.isBlank(businessName))businessName=null;
        String businessType="1";
        if(StringUtils.isBlank(pid)){
            pid=null;
            businessType="0";
        }
        if(StringUtils.isNotBlank(sysUser.getApiid())){
            businessName=sysUser.getApiid();
            businessType=null;
            if(StringUtils.isNotBlank(pid))
                businessName=null;
        }
        List<BusinessFormsResult> results=new ArrayList<>();
        Calendar calendar=Calendar.getInstance();
        Calendar calendarTemp=Calendar.getInstance();
        SimpleDateFormat simpleDateFormatMonth=new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat simpleDateFormatDay=new SimpleDateFormat("yyyy-MM-dd");
        if("0".equals(selectType)){
            if(StringUtils.isBlank(time)){
                if("0".equals(queryTimeType)){
                    time=simpleDateFormatMonth.format(new Date());
                }else{
                    time=simpleDateFormatDay.format(new Date());
                }
            }
            List<BusinessConfigBean> list=businessConfigBeanMapper.getList(page,"1",businessName,businessType,pid);
//            list.forEach(businessConfigBean -> {
            for(BusinessConfigBean businessConfigBean:list){
                BusinessFormsResult businessFormsResult=new BusinessFormsResult();
                businessFormsResult.setTime(time);
                businessFormsResult.setBusinessId(businessConfigBean.getId()+"");
                businessFormsResult.setBusinessName(businessConfigBean.getApiId()+"");
                BusinessCurrencyConfigBean currencyConfigBean=businessCurrencyConfigBeanMapper.selectById(businessConfigBean.getCurrency());
                businessFormsResult.setCurrency(currencyConfigBean.getCode());
                List<Integer> userIds=xbetBackMapper.getUserIds(businessConfigBean.getApiId(),null,null);
                if(userIds!=null && userIds.size()==0)userIds.add(-1);
                JSONObject jsonObject=xbetBackMapper.getOrderStatistics(userIds,queryTimeType,time);
                businessFormsResult.setOrderTotal(jsonObject.getBigDecimal("orderAmount")+"");
                businessFormsResult.setOrderReturn(jsonObject.getBigDecimal("returnAmount")+"");
                businessFormsResult.setOrderProfit(jsonObject.getBigDecimal("orderAmount").subtract(jsonObject.getBigDecimal("returnAmount"))+"");
                businessFormsResult.setOrderCount(jsonObject.getInteger("orderCount")+"");
                businessFormsResult.setActiveUserCount(xbetBackMapper.getActiveUserCount(businessConfigBean.getApiId(),queryTimeType,time)+"");
                businessFormsResult.setOrderUserCount(jsonObject.getInteger("orderUserCount")+"");
                businessFormsResult.setAddUserCount(xbetBackMapper.getUserIds(businessConfigBean.getApiId(),queryTimeType,time).size()+"");
                businessFormsResult.setAddOrderUserCount(xbetBackMapper.getAddOrderCount(businessConfigBean.getApiId(),queryTimeType,time)+"");
                results.add(businessFormsResult);
            };
        }else{
            if(StringUtils.isBlank(businessName)) {
                if (StringUtils.isBlank(sysUser.getApiid())) {
                    if(StringUtils.isBlank(pid)){
                        businessName=businessConfigBeanMapper.selectList(new QueryWrapper<BusinessConfigBean>().isNull("pid")).get(0).getApiId();
                    }else{
                        businessName=businessConfigBeanMapper.selectList(new QueryWrapper<BusinessConfigBean>().isNotNull("pid")).get(0).getApiId();
                    }
                } else {
                    businessName=sysUser.getApiid();
                }
            }
            if("0".equals(queryTimeType)){
                if(StringUtils.isNotBlank(time)){
                    try {
                        calendar.setTime(simpleDateFormatMonth.parse(time));
                        calendarTemp.setTime(calendar.getTime());
                    }catch (Exception e){
                        log.info("XBetBusinessServiceImpl time================");
                    }
                }else{
                    calendarTemp.setTime(calendar.getTime());
                    calendarTemp.add(Calendar.MONTH,-12);
                }
                BusinessConfigBean businessConfigBean=businessConfigBeanMapper.selectOne(new QueryWrapper<BusinessConfigBean>().eq("api_id",businessName));
                while(calendar.getTime().compareTo(calendarTemp.getTime())!=-1){
                    time=simpleDateFormatMonth.format(calendar.getTime());
                    BusinessFormsResult businessFormsResult=new BusinessFormsResult();
                    businessFormsResult.setTime(simpleDateFormatMonth.format(calendar.getTime()));
                    businessFormsResult.setBusinessId(businessConfigBean.getId()+"");
                    businessFormsResult.setBusinessName(businessConfigBean.getApiId()+"");
                    BusinessCurrencyConfigBean currencyConfigBean=businessCurrencyConfigBeanMapper.selectById(businessConfigBean.getCurrency());
                    businessFormsResult.setCurrency(currencyConfigBean.getCode());
                    List<Integer> userIds=xbetBackMapper.getUserIds(businessConfigBean.getApiId(),null,null);
                    if(userIds!=null && userIds.size()==0)userIds.add(-1);
                    JSONObject jsonObject=xbetBackMapper.getOrderStatistics(userIds,queryTimeType,time);
                    businessFormsResult.setOrderTotal(jsonObject.getBigDecimal("orderAmount")+"");
                    businessFormsResult.setOrderReturn(jsonObject.getBigDecimal("returnAmount")+"");
                    businessFormsResult.setOrderProfit(jsonObject.getBigDecimal("orderAmount").subtract(jsonObject.getBigDecimal("returnAmount"))+"");
                    businessFormsResult.setOrderCount(jsonObject.getInteger("orderCount")+"");
                    businessFormsResult.setActiveUserCount(xbetBackMapper.getActiveUserCount(businessConfigBean.getApiId(),queryTimeType,time)+"");
                    businessFormsResult.setOrderUserCount(jsonObject.getInteger("orderUserCount")+"");
                    businessFormsResult.setAddUserCount(xbetBackMapper.getUserIds(businessConfigBean.getApiId(),queryTimeType,time).size()+"");
                    businessFormsResult.setAddOrderUserCount(xbetBackMapper.getAddOrderCount(businessConfigBean.getApiId(),queryTimeType,time)+"");
                    results.add(businessFormsResult);
                    calendar.add(Calendar.MONTH,-1);
                }
            }else{
                if(StringUtils.isNotBlank(time)){
                    try {
                        Date date=simpleDateFormatDay.parse(time);
                        log.info(simpleDateFormatDay.format(date));
                        calendar.setTime(date);
                        log.info(simpleDateFormatDay.format(calendar.getTime())+"");
                        calendarTemp.setTime(calendar.getTime());
                    }catch (Exception e){
                        log.info("XBetBusinessServiceImpl time================");
                    }
                }else{
                    calendarTemp.setTime(calendar.getTime());
                    calendarTemp.add(Calendar.DATE,-7);
                }
                BusinessConfigBean businessConfigBean=businessConfigBeanMapper.selectOne(new QueryWrapper<BusinessConfigBean>().eq("api_id",businessName));
                while (calendar.getTime().compareTo(calendarTemp.getTime())!=-1){
                    time=simpleDateFormatDay.format(calendar.getTime());
                    BusinessFormsResult businessFormsResult=new BusinessFormsResult();
                    businessFormsResult.setTime(simpleDateFormatDay.format(calendar.getTime()));
                    businessFormsResult.setBusinessId(businessConfigBean.getId()+"");
                    businessFormsResult.setBusinessName(businessConfigBean.getApiId()+"");
                    BusinessCurrencyConfigBean currencyConfigBean=businessCurrencyConfigBeanMapper.selectById(businessConfigBean.getCurrency());
                    businessFormsResult.setCurrency(currencyConfigBean.getCode());
                    List<Integer> userIds=xbetBackMapper.getUserIds(businessConfigBean.getApiId(),null,null);
                    if(userIds!=null && userIds.size()==0)userIds.add(-1);
                    JSONObject jsonObject=xbetBackMapper.getOrderStatistics(userIds,queryTimeType,time);
                    businessFormsResult.setOrderTotal(jsonObject.getBigDecimal("orderAmount")+"");
                    businessFormsResult.setOrderReturn(jsonObject.getBigDecimal("returnAmount")+"");
                    businessFormsResult.setOrderProfit(jsonObject.getBigDecimal("orderAmount").subtract(jsonObject.getBigDecimal("returnAmount"))+"");
                    businessFormsResult.setOrderCount(jsonObject.getInteger("orderCount")+"");
                    businessFormsResult.setActiveUserCount(xbetBackMapper.getActiveUserCount(businessConfigBean.getApiId(),queryTimeType,time)+"");
                    businessFormsResult.setOrderUserCount(jsonObject.getInteger("orderUserCount")+"");
                    businessFormsResult.setAddUserCount(xbetBackMapper.getUserIds(businessConfigBean.getApiId(),queryTimeType,time).size()+"");
                    businessFormsResult.setAddOrderUserCount(xbetBackMapper.getAddOrderCount(businessConfigBean.getApiId(),queryTimeType,time)+"");
                    results.add(businessFormsResult);
                    calendar.add(Calendar.DATE,-1);
                }
            }
        }
        if(StringUtils.isNotBlank(pid) || sysUser.getRoleId()==BusRole.BUS_DOWN.geteName()){
            for(BusinessFormsResult b:results){
                b.setOperationStatus(false);
            }
        }
        page.setRecords(results);
        return ResultUtil.returnSuccess(page);
    }

    @Override
    public CommonResult<IPage<BusinessConfigBean>> downList(SysUser sysUser,Page page, String status, String businessName, String pid) {
        if(null!=sysUser.getApiid()){
            if(sysUser.getRoleId()== BusRole.BUS.geteName()){
                pid=businessConfigBeanMapper.selectOne(new QueryWrapper<BusinessConfigBean>().eq("api_id",sysUser.getApiid())).getId()+"";
            }
        }
//        if(StringUtils.isBlank(pid))
//            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
        if(StringUtils.isBlank(status))status=null;
        if(StringUtils.isBlank(businessName))businessName=null;
        if(StringUtils.isBlank(pid))pid=null;
        List<BusinessConfigBean> list=businessConfigBeanMapper.getList(page,status,businessName,"1",pid);
        list.forEach(businessConfigBean -> {
            Map<String,Object> response=oauthClientDetailsService.getClient2(businessConfigBean.getApiId());
            businessConfigBean.setClientSecret(response.get("clientSecret").toString());
            businessConfigBean.setIpWhitelist(response.get("ipWhitelist").toString());
            businessConfigBean.setProductSkinName(ProductSkin.getCnameByeName(businessConfigBean.getProductSkin()));
            businessConfigBean.setCoefficientName(Coefficient.getCnameByeName(businessConfigBean.getCoefficient()));
            businessConfigBean.setWalletTypeName(WalletType.getCnameByeName(businessConfigBean.getWalletType()));
            businessConfigBean.setLanguageName(Language.getCnameByeName(businessConfigBean.getLanguage()));
        });
        page.setRecords(list);
        CommonOperatorResult commonOperatorResult=new CommonOperatorResult();
        if(sysUser.getRoleId()==BusRole.BUS.geteName() || sysUser.getRoleId()==BusRole.BUS_DOWN.geteName()) {
            commonOperatorResult.setEdit(false);
            commonOperatorResult.setAdd(false);
        }
        return ResultUtil.returnSuccess(page,commonOperatorResult);
    }

}
