package com.splan.data.datacenter.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.JsonObject;
import com.splan.base.bean.*;
import com.splan.base.enums.ResultStatus;
import com.splan.base.http.CommonResult;
import com.splan.base.utils.ResultUtil;
import com.splan.data.datacenter.bean.MoneyRecordBean;
import com.splan.data.datacenter.dto.DateDto;
import com.splan.data.datacenter.dto.DateDtoOrder;
import com.splan.data.datacenter.dto.DateDtoUpdate;
import com.splan.data.datacenter.enums.OrderStatus;
import com.splan.data.datacenter.mapper.*;
import com.splan.data.datacenter.service.DateCenterService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.splan.data.datacenter.bean.BetOrderBean;

@Service
public class DateCenterServiceImpl implements DateCenterService {

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
    private UserMapper userMapper;

    @Autowired
    private BetOptionBeanMapper betOptionBeanMapper;

    @Override
    public CommonResult<IPage<BetTopicsBean>> betData(Page page, String bigBusiness, String smallBusiness, String gameId, DateDto dateDto, String dataId, String betId, String betStatus) {
        List<BetTopicsBean> list=betTopicsBeanMapper.getXbetBetData(page,gameId,dateDto.getStartDate(),dateDto.getEndDate(),dataId,betId,betStatus);
        List<Integer> userIds=xbetBackMapper.getUserIdsByApiId(bigBusiness,smallBusiness);
        list.forEach(betTopicsBean -> {
            //比赛信息
            GameLeagueBean gameLeagueBean=gameLeagueBeanMapper.selectById(betTopicsBean.getLeagueId());
            List<GameTeamBean> gameTeamBeans=gameTeamBeanMapper.getXbetTeams(betTopicsBean.getDataId()+"");
            betTopicsBean.setDataInfo(betTopicsBean.getNameEn()+"\n"+gameLeagueBean.getName()+"\n"+gameTeamBeans.get(0).getAbbr()+" vs "+gameTeamBeans.get(1).getAbbr());
            betTopicsBean.setBetInfo(betTopicsBean.getGroupName()+(betTopicsBean.isRollingBall()?"滚盘":"赛前"));
            betTopicsBean.setBetStatus(betTopicsBean.isRollingBall()?"滚盘":"赛前");
            JSONObject xBetOrder=xbetViewMapper.getXbetOrder(betTopicsBean.getId()+"",userIds);
            betTopicsBean.setOrderCount(xBetOrder.getString("orderCount"));
            betTopicsBean.setOrderAmountX(xBetOrder.getString("orderAmount"));
            betTopicsBean.setOrderUserCount(xBetOrder.getString("orderUserCount"));
        });
        page.setRecords(list);
        return ResultUtil.returnSuccess(page);
    }

    @Override
    public CommonResult<IPage<BetOrderBean>> orderData(Page page, DateDto dateDto, String orderStatus, String searchId, String orderId, String orderType, DateDtoOrder dateDtoOrder, DateDtoUpdate dateDtoUpdate) {
        String appId = httpServletRequest.getHeader("App_id");
        if (StringUtils.isBlank(appId)){
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }
//        String appId="demo";
        List<Integer> userIds=xbetBackMapper.getUserIdsByApiId(null,appId);
        if(userIds!=null && userIds.size()==0)userIds.add(-1);
        List<BetOrderBean> list = xbetViewMapper.getXbetOrderData(page,userIds,null,dateDto.getStartDate(),dateDto.getEndDate(),orderStatus,searchId,orderId,orderType,null,dateDtoOrder.getStartDateOrder(),dateDtoOrder.getEndDateOrder(),dateDtoUpdate.getStartDateUpdate(),dateDtoUpdate.getEndDateUpdate());
//        if("0".equals(orderType)) {
//            list.forEach(betOrderBean -> {
//                GameDataBean gameDataBean = gameDataBeanMapper.selectById(betOrderBean.getDataId());
//                betOrderBean.setMatchTime(gameDataBean.getStartTime());
//                GameLeagueBean gameLeagueBean = gameLeagueBeanMapper.selectById(gameDataBean.getLeagueId());
//                List<String> matchInfo=new ArrayList<>();
//                matchInfo.add(betOrderBean.getNameEn() + "\n" + gameLeagueBean.getName() + "\n" + betOrderBean.getVs());
//                betOrderBean.setMatchInfo(matchInfo);
//                List<String> betInfo=new ArrayList<>();
//                betInfo.add(betOrderBean.getVsDetail() + "\n" + betOrderBean.getBetId());
//                betOrderBean.setBetInfo(betInfo);
//                betOrderBean.setOrderStatusCname(OrderStatus.getCname(betOrderBean.getStatus().toString()));
//                betOrderBean.setOrderAccount("");
//            });
//        }else if("1".equals(orderType)){
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            list.forEach(betOrderBean -> {
                List<BetOrderDetailBean> detailBeans=xbetViewMapper.getXbetDetails(betOrderBean.getId()+"");
                List<String> matchInfo=new ArrayList<>();
                List<String> betInfo=new ArrayList<>();
                List<String> play=new ArrayList<>();
                List<String> orderInfo=new ArrayList<>();
                for(BetOrderDetailBean betOrderDetailBean:detailBeans){
                    GameDataBean gameDataBean = gameDataBeanMapper.selectById(betOrderDetailBean.getDataId());
                    GameLeagueBean gameLeagueBean = gameLeagueBeanMapper.selectById(gameDataBean.getLeagueId());
                    matchInfo.add(betOrderDetailBean.getNameEn() + "|" + gameLeagueBean.getName() + "|" + betOrderDetailBean.getVs()+"|"+simpleDateFormat.format(gameDataBean.getStartTime()));
                    betInfo.add(betOrderDetailBean.getVsDetail() + "|" + betOrderDetailBean.getBetId());
                    JSONObject playInfo=xbetBackMapper.getplayInfobyOptionId(betOrderDetailBean.getBetOptionId()+"");
                    play.add(playInfo.getString("groupName"));
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
                    orderInfo.add(playInfo.getString("name")+"|"+betOrderDetailBean.getOdd()+"|"+betOrderDetailBean.getStatus()+"|"+betResult);
                }
                betOrderBean.setPlay(play);
                betOrderBean.setOrderInfo(orderInfo);
                betOrderBean.setMatchInfo(matchInfo);
                betOrderBean.setBetInfo(betInfo);
                betOrderBean.setOrderStatusCname(OrderStatus.getCname(betOrderBean.getStatus().toString()));
                betOrderBean.setOrderAccount("");
                betOrderBean.setExtraId(userMapper.selectById(betOrderBean.getTenantUserNo()).getExtraId());
            });
//        }
        page.setRecords(list);
        return ResultUtil.returnSuccess(page);
    }

    @Override
    public CommonResult<IPage<MoneyRecordBean>> moneyRecord(Page page, String searchId, String orderId, DateDto dateDto) {
        String appId = httpServletRequest.getHeader("App_id");
        if (StringUtils.isBlank(appId)){
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }
//         String appId="demo";
        if(StringUtils.isBlank(searchId))searchId=null;
        if(StringUtils.isBlank(orderId))orderId=null;
        List<MoneyRecordBean> list=xbetBackMapper.getMoneyRecordPageList(page,appId,searchId,orderId,dateDto.getStartDate(),dateDto.getEndDate());
        page.setRecords(list);
        return ResultUtil.returnSuccess(page);
    }

}
