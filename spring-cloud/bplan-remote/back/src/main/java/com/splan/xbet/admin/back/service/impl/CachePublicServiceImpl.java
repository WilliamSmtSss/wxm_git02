package com.splan.xbet.admin.back.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.*;
import com.splan.base.enums.MoneyAbleType;
import com.splan.xbet.admin.back.dto.DateDto;
import com.splan.xbet.admin.back.enums.OrderStatus;
import com.splan.xbet.admin.back.enums.WalletType;
import com.splan.xbet.admin.back.mappers.*;
import com.splan.xbet.admin.back.service.CachePublicService;
import com.splan.xbet.admin.back.utils.export.ReportExcel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CachePublicServiceImpl implements CachePublicService {
    @Autowired
    private BetTopicsBeanMapper betTopicsBeanMapper;

    @Autowired
    private GameLeagueBeanMapper gameLeagueBeanMapper;

    @Autowired
    private GameTeamBeanMapper gameTeamBeanMapper;

    @Autowired
    private XbetViewMapper xbetViewMapper;

    @Autowired
    private GameDataBeanMapper gameDataBeanMapper;

    @Autowired
    private BetOrderDetailBeanMapper betOrderDetailBeanMapper;

    @Autowired
    private GameTypeBeanMapper gameTypeBeanMapper;

    @Autowired
    private XbetBackMapper xbetBackMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Autowired
    private XExportMapper xExportMapper;

    @Autowired
    private ReportExcel reportExcel;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private XBetCacheMapper xBetCacheMapper;

    @Autowired
    private BetOptionBeanMapper betOptionBeanMapper;

    @Autowired
    private BusinessCurrencyConfigBeanMapper businessCurrencyConfigBeanMapper;

    @Override
    public List<BetTopicsBean> betDataPublic(Page page, String bigBusiness, String smallBusiness, String gameId, DateDto dateDto, String dataId, String betId, String betStatus, String export) {
        if(StringUtils.isNotBlank(export))page=null;
        List<BetTopicsBean> list=betTopicsBeanMapper.getXbetBetData(page,gameId,dateDto.getStartDate(),dateDto.getEndDate(),dataId,betId,betStatus);
        List<Integer> userIds=xbetBackMapper.getUserIdsByApiId(bigBusiness,smallBusiness);
        if(userIds!=null&&userIds.size()==0)userIds.add(-1);
        final BigDecimal temp=new BigDecimal(100);
        list.forEach(betTopicsBean -> {
            //比赛信息
            GameLeagueBean gameLeagueBean=gameLeagueBeanMapper.selectById(betTopicsBean.getLeagueId());
            List<GameTeamBean> gameTeamBeans=gameTeamBeanMapper.getXbetTeams(betTopicsBean.getDataId()+"");
            betTopicsBean.setDataInfo(betTopicsBean.getNameEn()+"\n"+gameLeagueBean.getName()+"\n"+gameTeamBeans.get(0).getAbbr()+" vs "+gameTeamBeans.get(1).getAbbr());
            betTopicsBean.setBetInfo(betTopicsBean.getGroupName()+"\n"+(betTopicsBean.isRollingBall()?"滚盘":"赛前"));
            betTopicsBean.setBetStatus(betTopicsBean.isRollingBall()?"滚盘":"赛前");
            JSONObject xBetOrder=xbetViewMapper.getXbetOrder(betTopicsBean.getId()+"",userIds,betStatus);
            betTopicsBean.setOrderCount(xBetOrder.getString("orderCount"));
            betTopicsBean.setOrderAmountX(xBetOrder.getString("orderAmount"));
            betTopicsBean.setOrderUserCount(xBetOrder.getString("orderUserCount"));
            betTopicsBean.setReturnAmountX(xBetOrder.getString("returnAmount"));
            betTopicsBean.setOrderProfitX(xBetOrder.getBigDecimal("orderAmount").subtract(xBetOrder.getBigDecimal("returnAmount"))+"");
            try {
                System.out.println(xBetOrder.getBigDecimal("orderAmount").subtract(xBetOrder.getBigDecimal("returnAmount")));
                System.out.println(xBetOrder.getBigDecimal("orderAmount"));
                betTopicsBean.setOrderProfitRate((xBetOrder.getBigDecimal("orderAmount").subtract(xBetOrder.getBigDecimal("returnAmount"))).divide(xBetOrder.getBigDecimal("orderAmount"),2, BigDecimal.ROUND_HALF_UP).multiply(temp)+"%");
            }catch (Exception e){
                betTopicsBean.setOrderProfitRate("");
            }
            if("default".equals(betTopicsBean.getStatus())){
                betTopicsBean.setBetResult("无赛果");
            }else if("canceled".equals(betTopicsBean.getStatus())){
                betTopicsBean.setBetResult("已取消");
            }else if("checked".equals(betTopicsBean.getStatus())){
                BetOptionBean betOptionBean=betOptionBeanMapper.getBetResultByBetID(betTopicsBean.getId()+"");
                betTopicsBean.setBetResult(betOptionBean!=null?betOptionBean.getName():"");
            }else{
                betTopicsBean.setBetResult("无");
            }
        });
        return list;
    }

    @Override
    public List<BetOrderBean> orderDataPublic(Page page, String bigBusiness, String smallBusiness, String gameId, DateDto dateDto, String orderStatus, String searchId, String orderId, String betId, String orderType, String export, String dataId) {
        List<Integer> userIds=xbetBackMapper.getUserIdsByApiId(bigBusiness,smallBusiness);
        if(userIds!=null&&userIds.size()==0)userIds.add(-1);
        if(StringUtils.isNotBlank(export))page=null;
        List<BetOrderBean> list = xbetViewMapper.getXbetOrderData(page,userIds,gameId,dateDto.getStartDate(),dateDto.getEndDate(),orderStatus,searchId,orderId,orderType,betId,dataId);
        if("0".equals(orderType)) {
            list.forEach(betOrderBean -> {
                GameDataBean gameDataBean = gameDataBeanMapper.selectById(betOrderBean.getDataId());
                betOrderBean.setMatchTime(gameDataBean.getStartTime());
                GameLeagueBean gameLeagueBean = gameLeagueBeanMapper.selectById(gameDataBean.getLeagueId());
                List<String> matchInfo=new ArrayList<>();
                matchInfo.add(betOrderBean.getNameEn() + "\n" + gameLeagueBean.getName() + "\n" + betOrderBean.getVs());
                betOrderBean.setMatchInfo(matchInfo);
                List<String> betInfo=new ArrayList<>();
                betInfo.add(betOrderBean.getVsDetail() + "\n" + betOrderBean.getBetId());
                betOrderBean.setBetInfo(betInfo);
                betOrderBean.setOrderStatusCname(OrderStatus.getCname(betOrderBean.getStatus().toString()));
                UserBean userBean=userMapper.selectById(betOrderBean.getTenantUserNo());
                betOrderBean.setOrderAccount(userBean.getId()+"\n"+userBean.getExtraId()+"\n"+userBean.getApiId());
            });
        }else if("1".equals(orderType)){
            list.forEach(betOrderBean -> {
                List<BetOrderDetailBean> detailBeans=xBetCacheMapper.getXbetDetails(betOrderBean.getId()+"");
                List<String> matchInfo=new ArrayList<>();
                List<String> betInfo=new ArrayList<>();
                for(BetOrderDetailBean betOrderDetailBean:detailBeans){
                    GameDataBean gameDataBean = gameDataBeanMapper.selectById(betOrderDetailBean.getDataId());
                    GameLeagueBean gameLeagueBean = gameLeagueBeanMapper.selectById(gameDataBean.getLeagueId());
                    matchInfo.add(betOrderDetailBean.getNameEn() + "\n" + gameLeagueBean.getName() + "\n" + betOrderDetailBean.getVs()+"\n");
                    betInfo.add(betOrderDetailBean.getVsDetail() + "\n" + betOrderDetailBean.getBetId()+"\n");
                }
                betOrderBean.setMatchInfo(matchInfo);
                betOrderBean.setBetInfo(betInfo);
                betOrderBean.setOrderStatusCname(OrderStatus.getCname(betOrderBean.getStatus().toString()));
                UserBean userBean=userMapper.selectById(betOrderBean.getTenantUserNo());
                betOrderBean.setOrderAccount(userBean.getId()+"\n"+userBean.getInviteCode()+"\n"+userBean.getApiId());
            });
        }
        return list;
    }

    @Override
    public List<UserBean> listPublic(Page page, String bigBusiness, String smallBusiness, String searchId,String export) {
        if(StringUtils.isNotBlank(export))page=null;
        List<UserBean> list=xbetBackMapper.getUserPageList(page,bigBusiness,smallBusiness,searchId);
        List<Integer> userIds=new ArrayList<>();
        list.forEach(userBean -> {
            userIds.clear();
            try {
                userIds.add(Integer.parseInt(userBean.getUserBalanceBean().getUserId() + ""));
            }catch (Exception e){
                userIds.add(-1);
            }
            JSONObject jsonObject=xbetBackMapper.getOrderStatistics(userIds,null,null);
            userBean.setXBetOrderTotal(jsonObject.getBigDecimal("orderAmount"));
            userBean.setXBetOrderCount(jsonObject.getBigDecimal("orderCount"));
            userBean.setXBetOrderProfit(jsonObject.getBigDecimal("orderAmount").subtract(jsonObject.getBigDecimal("returnAmount")));
            if(null!=userBean.getBusinessConfigBean())userBean.setBusinessConfigBeanName(userBean.getBusinessConfigBean().getApiId());
            if(null!=userBean.getBusinessConfigBeanUp())userBean.setBusinessConfigBeanUpName(userBean.getBusinessConfigBeanUp().getApiId());
            userBean.setUserBalanceCoin(null!=userBean.getUserBalanceBean()?userBean.getUserBalanceBean().getCoin():new BigDecimal("0"));
            userBean.setWalletTypeName(null!=userBean.getBusinessConfigBean()? WalletType.getCnameByeName(userBean.getBusinessConfigBean().getWalletType()):"");
            try {
                userBean.setBusinessConfigBeanAdminName(userMapper.selectById(userBean.getBusinessConfigBean().getSysUserId()).getUsername());
            }catch (Exception e){
                userBean.setBusinessConfigBeanAdminName("");
            }
        });
        return list;
    }

    @Override
    public List<MoneyRecordBean> moneyRecordPublic(Page page, String bigBusiness, String smallBusiness, String searchId, String orderId,String export) {
        if(StringUtils.isNotBlank(export))page=null;
        List<MoneyRecordBean> list=xbetBackMapper.getMoneyRecordPageList(page,bigBusiness,smallBusiness,searchId,orderId);
        list.forEach(moneyRecordBean -> {
            if(null!=moneyRecordBean.getBusinessConfigBean())moneyRecordBean.setBusinessConfigBeanName(moneyRecordBean.getBusinessConfigBean().getApiId());
            if(null!=moneyRecordBean.getBusinessConfigBeanUp())moneyRecordBean.setBusinessConfigBeanUpName(moneyRecordBean.getBusinessConfigBeanUp().getApiId());
            try {
                moneyRecordBean.setBusinessConfigBeanAdminName(userMapper.selectById(moneyRecordBean.getBusinessConfigBean().getSysUserId()).getUsername());
            }catch (Exception e){
                moneyRecordBean.setBusinessConfigBeanAdminName("");
            }

            moneyRecordBean.setMoneyableTypeName(MoneyAbleType.getCnameByeName(moneyRecordBean.getMoneyableType().toString()));
            try {
                moneyRecordBean.setCurrencyName(businessCurrencyConfigBeanMapper.selectById(moneyRecordBean.getBusinessConfigBean().getCurrency()).getCode());
            }catch (Exception e){
                System.out.println(e.getClass()+"\n"+e.getMessage()+"\n"+e.getStackTrace());
                moneyRecordBean.setCurrencyName("");
            }

            try {
                moneyRecordBean.setWalletTypeName(WalletType.getCnameByeName(moneyRecordBean.getBusinessConfigBean().getWalletType()));
            }catch (Exception e){
                moneyRecordBean.setWalletTypeName("");
            }

        });
        return list;
    }

    public static void main(String[] args) {
//        -1871.46
//        666.00
        BigDecimal b1=new BigDecimal(-1871.46);
        BigDecimal b2=new BigDecimal(666.00);
        System.out.println(b1.divide(b2,2,BigDecimal.ROUND_HALF_UP));
    }
}
