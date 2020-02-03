package com.splan.xbet.admin.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.*;
import com.splan.base.http.CommonResult;
import com.splan.xbet.admin.back.dto.DateDto;
import com.splan.xbet.admin.back.mappers.*;
import com.splan.xbet.admin.back.service.CachePublicService;
import com.splan.xbet.admin.back.service.XBetDataService;
import com.splan.xbet.admin.back.utils.ResultUtil;
import com.splan.xbet.admin.back.utils.export.ReportExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class XBetDataServiceImpl implements XBetDataService {

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
    private CachePublicService cachePublicService;

    @Override
    public CommonResult<IPage<BetTopicsBean>> betData(Page page, String bigBusiness, String smallBusiness, String gameId, DateDto dateDto, String dataId, String betId, String betStatus, String export) {
        List<BetTopicsBean> list=betDataPublic(page, bigBusiness, smallBusiness, gameId, dateDto, dataId, betId, betStatus, export);
        page.setRecords(list);
        return ResultUtil.returnSuccess(page);
    }

    @Override
    @Async
    public CommonResult<IPage<BetTopicsBean>> betDataExport(Page page, String bigBusiness, String smallBusiness, String gameId, DateDto dateDto, String dataId, String betId, String betStatus, String export) {
        List<BetTopicsBean> list=betDataPublic(page, bigBusiness, smallBusiness, gameId, dateDto, dataId, betId, betStatus, export);
        reportExcel.excelExport2(list,"盘口数据", BetTopicsBean.class,1);
        return null;
    }
//    @Cacheable(value = "backCache",key = "#p0.toString()+#gameId",condition = "#export!=''")
    public List<BetTopicsBean> betDataPublic(Page page, String bigBusiness, String smallBusiness, String gameId, DateDto dateDto, String dataId, String betId, String betStatus, String export){
//        if(StringUtils.isNotBlank(export))page=null;
//        List<BetTopicsBean> list=betTopicsBeanMapper.getXbetBetData(page,gameId,dateDto.getStartDate(),dateDto.getEndDate(),dataId,betId,betStatus);
//        List<Integer> userIds=xbetBackMapper.getUserIdsByApiId(bigBusiness,smallBusiness);
//        final BigDecimal temp=new BigDecimal(100);
//        list.forEach(betTopicsBean -> {
//            //比赛信息
//            GameLeagueBean gameLeagueBean=gameLeagueBeanMapper.selectById(betTopicsBean.getLeagueId());
//            List<GameTeamBean> gameTeamBeans=gameTeamBeanMapper.getXbetTeams(betTopicsBean.getDataId()+"");
//            betTopicsBean.setDataInfo(betTopicsBean.getNameEn()+"\n"+gameLeagueBean.getName()+"\n"+gameTeamBeans.get(0).getAbbr()+" vs "+gameTeamBeans.get(1).getAbbr());
//            betTopicsBean.setBetInfo(betTopicsBean.getGroupName()+(betTopicsBean.isRollingBall()?"滚盘":"赛前"));
//            betTopicsBean.setBetStatus(betTopicsBean.isRollingBall()?"滚盘":"赛前");
//            JSONObject xBetOrder=xbetViewMapper.getXbetOrder(betTopicsBean.getId()+"",userIds);
//            betTopicsBean.setOrderCount(xBetOrder.getString("orderCount"));
//            betTopicsBean.setOrderAmountX(xBetOrder.getString("orderAmount"));
//            betTopicsBean.setOrderUserCount(xBetOrder.getString("orderUserCount"));
//            betTopicsBean.setReturnAmountX(xBetOrder.getString("returnAmount"));
//            betTopicsBean.setOrderProfitX(xBetOrder.getBigDecimal("orderAmount").subtract(xBetOrder.getBigDecimal("returnAmount"))+"");
//            try {
//                betTopicsBean.setOrderProfitRate((xBetOrder.getBigDecimal("orderAmount").subtract(xBetOrder.getBigDecimal("returnAmount"))).divide(xBetOrder.getBigDecimal("orderAmount"), BigDecimal.ROUND_HALF_UP,2).multiply(temp)+"%");
//            }catch (Exception e){
//                betTopicsBean.setOrderProfitRate("");
//            }
//            if("default".equals(betTopicsBean.getStatus())){
//                betTopicsBean.setBetResult("无赛果");
//            }else if("canceled".equals(betTopicsBean.getStatus())){
//                betTopicsBean.setBetResult("已取消");
//            }else if("checked".equals(betTopicsBean.getStatus())){
//                BetOptionBean betOptionBean=betOptionBeanMapper.getBetResultByBetID(betTopicsBean.getId()+"");
//                betTopicsBean.setBetResult(betOptionBean!=null?betOptionBean.getName():"");
//            }else{
//                betTopicsBean.setBetResult("无");
//            }
//        });
//        return list;
        return cachePublicService.betDataPublic(page, bigBusiness, smallBusiness, gameId, dateDto, dataId, betId, betStatus, export);
    }


    @Override
    public CommonResult<IPage<BetOrderBean>> orderData(Page page, String bigBusiness, String smallBusiness, String gameId, DateDto dateDto, String orderStatus, String searchId, String orderId, String betId,String orderType, String export,String dataId) {
        List<BetOrderBean> list =cachePublicService.orderDataPublic(page, bigBusiness, smallBusiness, gameId, dateDto, orderStatus, searchId, orderId, betId, orderType, export, dataId);
//        if(StringUtils.isNotBlank(export)){
//            ReportExcel reportExcel = new ReportExcel();
//            reportExcel.excelExport(list,"注单数据", BetOrderBean.class,1,httpServletResponse,httpServletRequest);
//            return null;
//        }
        page.setRecords(list);
        return ResultUtil.returnSuccess(page);
    }

    @Override
    @Async
    public CommonResult<IPage<BetOrderBean>> orderDataExport(Page page, String bigBusiness, String smallBusiness, String gameId, DateDto dateDto, String orderStatus, String searchId, String orderId, String betId, String orderType, String export,String dataId) {
            List<BetOrderBean> list =cachePublicService.orderDataPublic(page, bigBusiness, smallBusiness, gameId, dateDto, orderStatus, searchId, orderId, betId, orderType, export, dataId);
            reportExcel.excelExport2(list,"注单数据", BetOrderBean.class,1);
            return null;
    }

//    private List<BetOrderBean> orderDataPublic(Page page, String bigBusiness, String smallBusiness, String gameId, DateDto dateDto, String orderStatus, String searchId, String orderId, String betId, String orderType, String export,String dataId){
//        List<Integer> userIds=xbetBackMapper.getUserIdsByApiId(bigBusiness,smallBusiness);
//        if(StringUtils.isNotBlank(export))page=null;
//        List<BetOrderBean> list = xbetViewMapper.getXbetOrderData(page,userIds,gameId,dateDto.getStartDate(),dateDto.getEndDate(),orderStatus,searchId,orderId,orderType,betId,dataId);
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
//                UserBean userBean=userMapper.selectById(betOrderBean.getTenantUserNo());
//                betOrderBean.setOrderAccount(userBean.getId()+"\n"+userBean.getInviteCode()+"\n"+userBean.getApiId());
//            });
//        }else if("1".equals(orderType)){
//            list.forEach(betOrderBean -> {
//                List<BetOrderDetailBean> detailBeans=xBetCacheMapper.getXbetDetails(betOrderBean.getId()+"");
//                List<String> matchInfo=new ArrayList<>();
//                List<String> betInfo=new ArrayList<>();
//                for(BetOrderDetailBean betOrderDetailBean:detailBeans){
//                    GameDataBean gameDataBean = gameDataBeanMapper.selectById(betOrderDetailBean.getDataId());
//                    GameLeagueBean gameLeagueBean = gameLeagueBeanMapper.selectById(gameDataBean.getLeagueId());
//                    matchInfo.add(betOrderDetailBean.getNameEn() + "\n" + gameLeagueBean.getName() + "\n" + betOrderDetailBean.getVs());
//                    betInfo.add(betOrderDetailBean.getVsDetail() + "\n" + betOrderDetailBean.getBetId());
//                }
//                betOrderBean.setMatchInfo(matchInfo);
//                betOrderBean.setBetInfo(betInfo);
//                betOrderBean.setOrderStatusCname(OrderStatus.getCname(betOrderBean.getStatus().toString()));
//                UserBean userBean=userMapper.selectById(betOrderBean.getTenantUserNo());
//                betOrderBean.setOrderAccount(userBean.getId()+"\n"+userBean.getInviteCode()+"\n"+userBean.getApiId());
//            });
//        }
//        return list;
//    }

}
