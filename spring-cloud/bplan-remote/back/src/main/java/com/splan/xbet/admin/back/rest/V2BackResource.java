package com.splan.xbet.admin.back.rest;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.JsonObject;
import com.splan.base.bean.*;
import com.splan.base.enums.back.OddType;
import com.splan.base.http.CommonResult;
import com.splan.base.param.remote.BusinessFormsParam;
import com.splan.base.param.remote.FirstDataParam;
import com.splan.base.result.BusinessFormsResult;
import com.splan.base.result.remote.FrontDataResult;
import com.splan.base.result.remote.FrontDataResultOut;
import com.splan.base.service.OauthClientDetailsService;
import com.splan.base.service.v2.V2BackService;
import com.splan.xbet.admin.back.enums.OrderStatus;
import com.splan.xbet.admin.back.mappers.*;
import com.splan.xbet.admin.back.service.XBetBusinessService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/")
public class V2BackResource implements V2BackService {

    @Autowired
    private FrontBackMapper frontBackMapper;

    @Autowired
    private XbetBackMapper xbetBackMapper;

    @Autowired
    private BusinessConfigBeanMapper businessConfigBeanMapper;

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @Autowired
    private BusinessCurrencyConfigBeanMapper businessCurrencyConfigBeanMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private XBetCacheMapper xBetCacheMapper;

    @Autowired
    private GameDataBeanMapper gameDataBeanMapper;

    @Autowired
    private GameLeagueBeanMapper gameLeagueBeanMapper;

    @Autowired
    private BetOptionBeanMapper betOptionBeanMapper;

    @Autowired
    private GameTypeBeanMapper gameTypeBeanMapper;

    @Override
    public String businessForms(BusinessFormsParam businessFormsParam) {
        List<BusinessFormsResult> results=new ArrayList<>();
        Calendar calendar=Calendar.getInstance();
        Calendar calendarTemp=Calendar.getInstance();
        SimpleDateFormat simpleDateFormatMonth=new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat simpleDateFormatDay=new SimpleDateFormat("yyyy-MM-dd");
        Date startTime=null;
        Date endTime=null;
        if("0".equals(businessFormsParam.getQueryTimeType())){
            if(StringUtils.isBlank(businessFormsParam.getStartTime()) || StringUtils.isBlank(businessFormsParam.getEndTime())){
                Date nowTime=new Date();
                try {
                    endTime=simpleDateFormatMonth.parse(simpleDateFormatMonth.format(nowTime));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                calendar.setTime(endTime);
                calendar.add(Calendar.MONTH,-11);
                startTime=calendar.getTime();
            }else{
                try {
                    startTime=simpleDateFormatMonth.parse(businessFormsParam.getStartTime());
                    endTime=simpleDateFormatMonth.parse(businessFormsParam.getEndTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }else{
            if(StringUtils.isBlank(businessFormsParam.getStartTime()) || StringUtils.isBlank(businessFormsParam.getEndTime())){
                Date nowTime=new Date();
                try {
                    endTime=simpleDateFormatDay.parse(simpleDateFormatDay.format(nowTime));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                calendar.setTime(endTime);
                calendar.add(Calendar.DATE,-6);
                startTime=calendar.getTime();
            }else{
                try {
                    startTime=simpleDateFormatDay.parse(businessFormsParam.getStartTime());
                    endTime=simpleDateFormatDay.parse(businessFormsParam.getEndTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        calendar.setTime(startTime);

        while(calendar.getTime().compareTo(endTime)!=1){
            BusinessFormsResult businessFormsResult=new BusinessFormsResult();
            if("0".equals(businessFormsParam.getQueryTimeType())) {
                businessFormsResult.setTime(simpleDateFormatMonth.format(calendar.getTime()));
            }else{
                businessFormsResult.setTime(simpleDateFormatDay.format(calendar.getTime()));
            }
            businessFormsResult.setBusinessName(businessFormsParam.getApiId()+"");
            List<Integer> userIds=xbetBackMapper.getUserIds(businessFormsParam.getApiId(),null,null);
            if(userIds!=null && userIds.size()==0)userIds.add(-1);
            JSONObject jsonObject=xbetBackMapper.getOrderStatistics(userIds,businessFormsParam.getQueryTimeType(),businessFormsResult.getTime());
            businessFormsResult.setOrderTotal(jsonObject.getBigDecimal("orderAmount")+"");
            businessFormsResult.setOrderReturn(jsonObject.getBigDecimal("returnAmount")+"");
            businessFormsResult.setOrderProfit(jsonObject.getBigDecimal("orderAmount").subtract(jsonObject.getBigDecimal("returnAmount"))+"");
            businessFormsResult.setOrderCount(jsonObject.getInteger("orderCount")+"");
            businessFormsResult.setActiveUserCount(xbetBackMapper.getActiveUserCount(businessFormsParam.getApiId(),businessFormsParam.getQueryTimeType(),businessFormsResult.getTime())+"");
            businessFormsResult.setOrderUserCount(jsonObject.getInteger("orderUserCount")+"");
            businessFormsResult.setAddUserCount(xbetBackMapper.getUserIds(businessFormsParam.getApiId(),businessFormsParam.getQueryTimeType(),businessFormsResult.getTime()).size()+"");
            businessFormsResult.setAddOrderUserCount(xbetBackMapper.getAddOrderCount(businessFormsParam.getApiId(),businessFormsParam.getQueryTimeType(),businessFormsResult.getTime())+"");
            results.add(businessFormsResult);
            if("0".equals(businessFormsParam.getQueryTimeType())){
                calendar.add(Calendar.MONTH,1);
            }else{
                calendar.add(Calendar.DATE,1);
            }
        }
        return JSON.toJSONString(results);
    }

    @Override
    public String firstData(FirstDataParam firstDataParam) {
        List<Integer> userIds=frontBackMapper.getUserIdsByApiId(firstDataParam.getApiId());
        if(userIds!=null && userIds.size()==0)userIds.add(-1);
        List<FrontDataResult> results=new ArrayList<>();
        Calendar calendar=Calendar.getInstance();
        Date nowTime=new Date();
        Date finalNowTime=nowTime;
        calendar.setTime(nowTime);
        calendar.add(Calendar.DATE,-1);
        nowTime=calendar.getTime();
        calendar.add(Calendar.DATE,-5);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        while(calendar.getTime().compareTo(nowTime)!=1){
            FrontDataResult frontDataResult=new FrontDataResult();
            frontDataResult.setTime(simpleDateFormat.format(calendar.getTime()));
            JSONObject jsonObject=frontBackMapper.getOrderStatistics(userIds,simpleDateFormat.format(calendar.getTime()),simpleDateFormat.format(calendar.getTime()));
            frontDataResult.setActiveUserCount(frontBackMapper.getActiveUser(firstDataParam.getApiId(),simpleDateFormat.format(calendar.getTime()),simpleDateFormat.format(calendar.getTime())));
            frontDataResult.setOrderUserCount(jsonObject.getInteger("orderUserCount"));
            frontDataResult.setOrderAmount(jsonObject.getBigDecimal("orderAmount"));
            frontDataResult.setOrderCount(jsonObject.getInteger("orderCount"));
            BigDecimal sub=jsonObject.getBigDecimal("orderAmount").subtract(jsonObject.getBigDecimal("returnAmount"));
            frontDataResult.setProfitRate(jsonObject.getBigDecimal("orderAmount").intValue()!=0?sub.divide(jsonObject.getBigDecimal("orderAmount"),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)):new BigDecimal(0));
            results.add(frontDataResult);
            calendar.add(Calendar.DATE,1);
        }
        FrontDataResultOut frontDataResultOut=new FrontDataResultOut();
        frontDataResultOut.setList(results);
        frontDataResultOut.setYesterDay(results.get(results.size()-1));
        frontDataResultOut.setBeforeYesterDay(results.get(results.size()-2));
        frontDataResultOut.setActiveUserCount(results.get(results.size()-1).getActiveUserCount().compareTo(results.get(results.size()-2).getActiveUserCount())==1);
        frontDataResultOut.setOrderCount(results.get(results.size()-1).getOrderCount().compareTo(results.get(results.size()-2).getOrderCount())==1);
        return JSON.toJSONString(frontDataResultOut);
    }

    @Override
    public String busSel(Map<String, Object> requestParam) {
        String apiId=requestParam.get("searchText")!=null?requestParam.get("searchText").toString():null;
        String current=requestParam.get("current")!=null?requestParam.get("current").toString():"1";
        String size=requestParam.get("size")!=null?requestParam.get("size").toString():"10";
        Page page=new Page();
        page.setCurrent(Long.parseLong(current));
        page.setSize(Long.parseLong(size));
        List<BusinessConfigBean> list=businessConfigBeanMapper.getListv2(page,apiId);
        for(BusinessConfigBean b:list){
            try {
                Map<String, Object> map = oauthClientDetailsService.getClient2(b.getApiId());
                b.setClientSecret(map.get("clientSecret").toString());
                b.setIpWhitelist(map.get("ipWhitelist").toString());
            }catch (Exception e){

            }
        }
        page.setRecords(list);
        return JSON.toJSONString(page);
    }

    @Override
    public void busEdit(Map<String, Object> requestParam) {
        BusinessConfigBean update=new BusinessConfigBean();
        Class clazz;
        List<Field> field1=new ArrayList<>();
        clazz = update.getClass();
        while (clazz != null){
            field1.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        for(Field f:field1){
            if(f.getType().getName().contains("String")) {
                f.setAccessible(true);
                if (requestParam.containsKey(f.getName())) {
                    try {
                        f.set(update, requestParam.get(f.getName()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        update.setId(requestParam.get("id")!=null?Integer.parseInt(requestParam.get("id").toString()):-1);
        update.setCurrency(requestParam.get("currency")!=null?Integer.parseInt(requestParam.get("currency").toString()):1);
        update.setCoefficient(requestParam.get("coefficient")!=null?Integer.parseInt(requestParam.get("coefficient").toString()):0);
        update.setWalletType(requestParam.get("walletType")!=null?Integer.parseInt(requestParam.get("walletType").toString()):1);
        update.setHeader(requestParam.get("header")!=null?(Boolean)(requestParam.get("header")):false);
        businessConfigBeanMapper.updateById(update);
        oauthClientDetailsService.updateClient(update.getApiId(),update.getClientSecret(),update.getIpWhitelist());
    }

    @Override
    public void busAdd(Map<String, Object> requestParam) {
        BusinessConfigBean add=new BusinessConfigBean();
        Class clazz;
        List<Field> field1=new ArrayList<>();
        clazz = add.getClass();
        while (clazz != null){
            field1.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        for(Field f:field1){
            f.setAccessible(true);
            if(requestParam.containsKey(f.getName())){
                try {
                    System.out.println(requestParam.get(f.getName()));
                    System.out.println(f.getName());
                    f.set(add, requestParam.get(f.getName()));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        businessConfigBeanMapper.insert(add);
    }

    @Override
    public String CurrencySel(Map<String, Object> requestParam) {
        List<BusinessCurrencyConfigBean> list=businessCurrencyConfigBeanMapper.getPageList(null,null,null);
        return JSON.toJSONString(list);
    }

    @Override
    public String userManager(Map<String, Object> requestParam) {
        String dataType=requestParam.get("dataType")!=null?requestParam.get("dataType").toString():null;
        String businessName=requestParam.get("businessName")!=null?requestParam.get("businessName").toString():null;
        String searchText=requestParam.get("searchText")!=null?requestParam.get("searchText").toString():null;
        String startLoginTime=requestParam.get("startLoginTime")!=null?requestParam.get("startLoginTime").toString():null;
        String endLoginTime=requestParam.get("endLoginTime")!=null?requestParam.get("endLoginTime").toString():null;
        String current=requestParam.get("current")!=null?requestParam.get("current").toString():"1";
        String size=requestParam.get("size")!=null?requestParam.get("size").toString():"10";
        Page page=new Page();
        page.setCurrent(Long.parseLong(current));
        page.setSize(Long.parseLong(size));
        List<UserBean> list=xbetBackMapper.getUserPageListV2(page,businessName,searchText,startLoginTime,endLoginTime);
        List<Integer> userIds=new ArrayList<>();
        list.forEach(userBean -> {
            userIds.clear();
            userIds.add(Integer.parseInt(userBean.getId()+""));
            JSONObject jsonObject=xbetBackMapper.getOrderStatisticsV2(userIds,null,null);
            BigDecimal orderCount=jsonObject.getBigDecimal("orderCount");
            BigDecimal orderAmount=jsonObject.getBigDecimal("orderAmount");
            BigDecimal returnAmount=jsonObject.getBigDecimal("returnAmount");
            BigDecimal profitAmount=orderAmount.subtract(returnAmount);
            BigDecimal profitRate=orderAmount.intValue()!=0?profitAmount.divide(orderAmount,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)):new BigDecimal(0);
            userBean.setXBetOrderCount(orderCount);
            userBean.setXBetOrderTotal(orderAmount);
            userBean.setReturnAmount(returnAmount);
            userBean.setXBetOrderProfit(profitAmount);
            userBean.setXBetOrderProfitRate(profitRate);
        });
        page.setRecords(list);
        return JSON.toJSONString(page);
    }

    @Override
    public String orderManager(Map<String, Object> requestParam) {
        String dataType=requestParam.get("dataType")!=null?requestParam.get("dataType").toString():null;
        String orderType=requestParam.get("orderType")!=null?requestParam.get("orderType").toString():"0";
        String businessName=requestParam.get("businessName")!=null?requestParam.get("businessName").toString():null;
        String gameId=requestParam.get("gameId")!=null?requestParam.get("gameId").toString():null;
        String orderId=requestParam.get("orderId")!=null?requestParam.get("orderId").toString():null;
        String dataId=requestParam.get("dataId")!=null?requestParam.get("dataId").toString():null;
        String betId=requestParam.get("betId")!=null?requestParam.get("betId").toString():null;
        String searchText=requestParam.get("searchText")!=null?requestParam.get("searchText").toString():null;
        String startOrderTime=requestParam.get("startOrderTime")!=null?requestParam.get("startOrderTime").toString():null;
        String endOrderTime=requestParam.get("endOrderTime")!=null?requestParam.get("endOrderTime").toString():null;
        String orderStatus=requestParam.get("orderStatus")!=null?requestParam.get("orderStatus").toString():null;
        String current=requestParam.get("current")!=null?requestParam.get("current").toString():"1";
        String size=requestParam.get("size")!=null?requestParam.get("size").toString():"10";
        Page page=new Page();
        page.setCurrent(Long.parseLong(current));
        page.setSize(Long.parseLong(size));
        List<BetOrderBean> list=xbetBackMapper.getXbetOrderDataV2(page,null,gameId,startOrderTime,endOrderTime,orderStatus,searchText,orderId,orderType,betId,dataId,businessName);
        list.forEach(betOrderBean -> {
            List<BetOrderDetailBean> detailBeans=xBetCacheMapper.getXbetDetails(betOrderBean.getId()+"");
            List<String> matchInfo=new ArrayList<>();
            List<String> betInfo=new ArrayList<>();
            for(BetOrderDetailBean betOrderDetailBean:detailBeans){
                GameDataBean gameDataBean = gameDataBeanMapper.selectById(betOrderDetailBean.getDataId());
                GameLeagueBean gameLeagueBean = gameLeagueBeanMapper.selectById(gameDataBean.getLeagueId());
                matchInfo.add(betOrderDetailBean.getNameEn() + "\n" + gameLeagueBean.getName() + "\n" + betOrderDetailBean.getVs()+"\n");
                betInfo.add(betOrderDetailBean.getVsDetail() + "\n" + betOrderDetailBean.getBetId()+"\n");
                BetOptionBean betOptionBean=betOptionBeanMapper.selectById(betOrderDetailBean.getBetOptionId());
                String betResult="";
                Integer betResulti=betOptionBean.getBetResult();
                if(betResulti==0){
                    betResult="未开奖";
                }else if(betResulti==1){
                    betResult="胜利";
                }else if(betResulti==2){
                    betResult="失败";
                }else{
                    betResult="取消";
                }
            }
            betOrderBean.setMatchInfo(matchInfo);
            betOrderBean.setBetInfo(betInfo);
            betOrderBean.setOrderStatusCname(OrderStatus.getCname(betOrderBean.getStatus().toString()));
            UserBean userBean=userMapper.selectById(betOrderBean.getTenantUserNo());
            betOrderBean.setOrderAccount(userBean.getExtraId());
            betOrderBean.setProfit(new BigDecimal(betOrderBean.getAmount()).subtract(betOrderBean.getEstimatedReward()));
        });
        page.setRecords(list);
        return JSON.toJSONString(page);
    }

    @Override
    public String gameList(Map<String, Object> requestParam) {
        return JSON.toJSONString(gameTypeBeanMapper.selectGameTypeList());
    }

}
