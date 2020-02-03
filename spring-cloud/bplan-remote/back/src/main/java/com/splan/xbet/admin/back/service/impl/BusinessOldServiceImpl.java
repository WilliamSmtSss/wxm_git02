package com.splan.xbet.admin.back.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.*;
import com.splan.xbet.admin.back.dto.DateDto;
import com.splan.xbet.admin.back.dto.ScreenForBetOrderDto;
import com.splan.base.enums.MoneyAbleType;
import com.splan.base.enums.OrderStatus;
import com.splan.base.enums.WinLoseStatus;
import com.splan.base.enums.orderenums.*;
import com.splan.xbet.admin.back.mappers.*;
import com.splan.xbet.admin.back.result.BetResult;
import com.splan.xbet.admin.back.service.*;
import com.splan.xbet.admin.back.utils.CommonUtil;
import com.splan.xbet.admin.back.utils.DateUtil;
import com.splan.xbet.admin.back.utils.export.ReportExcel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BusinessOldServiceImpl implements BusinessOldService {
    ConcurrentHashMap<Long, List> map = new ConcurrentHashMap<>();

    @Autowired
    private IBetOrderService betOrderService;

    @Autowired
    private IUserService userService;

    @Autowired
    private MoneyRecordBeanMapper moneyRecordBeanMapper;

    @Autowired
    private IBetOptionService betOptionService;

    @Autowired
    private IBetOrderDetailService betOrderDetailService;

    @Autowired
    private BetTopicsBeanMapper betTopicsBeanMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GameTeamBeanMapper gameTeamBeanMapper;

    @Autowired
    private GameDataBeanMapper gameDataBeanMapper;

    @Autowired
    private GameCampaignBeanMapper gameCampaignBeanMapper;

    @Autowired
    private BetOrderBeanMapper betOrderBeanMapper;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private IGameTypeService gameTypeService;

    @Override
    public JSONArray revenueList(DateDto dateDto,String apiid) {
        List<Long> ids = userService.getUserIds(apiid);
        JSONArray array = new JSONArray();
        Date startDate = dateDto.getStartDate();
        Date endDate = dateDto.getEndDate();
        Calendar rightNow = Calendar.getInstance();
        if (endDate == null) {
            endDate = new Date();
        }
        if (startDate == null) {
            rightNow.setTime(endDate);
            rightNow.add(Calendar.DAY_OF_YEAR,-6);
            startDate = rightNow.getTime();
        }

        while (startDate.compareTo(endDate) != 1) {
            array.add(getRevenue(startDate, ids));
            rightNow.setTime(startDate);
            rightNow.add(Calendar.DAY_OF_YEAR, 1);
            startDate = rightNow.getTime();
        }

        return array;
    }

    @Override
    public IPage<BetOrderBean> getBetOrderPage(Long betId, Long userId, Long optionparam, Page page, BetDetailOrderBy betDetailOrderBy, OrderByComm orderByComm) {
        QueryWrapper<BetOptionBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bet_data_id", betId);
        List<BetOptionBean> list = betOptionService.list(queryWrapper);
        List<Integer> optionList = new ArrayList<>();
        list.forEach(option -> {
            optionList.add(option.getId());
        });
        List<Long> ids = new ArrayList<>();
        if (userId != null) {
            ids.add(userId);
            if(!userService.getUserIds().contains(userId))
                ids.add(-1L);
            else
                ids.add(userId);
        } else {
            ids = userService.getUserIds();
            if(ids.size()==0)ids.add(-1L);
        }

        QueryWrapper<BetOrderBean> betOrderBeanQueryWrapper = new QueryWrapper<>();
        betOrderBeanQueryWrapper.select("*,ifnull(bet_order.amount-bet_order.estimated_reward,0) as rewardamount");
        betOrderBeanQueryWrapper.in("tenant_user_no", ids);
        betOrderBeanQueryWrapper.eq("bet_option_id",optionparam);
        if(betDetailOrderBy==null) {
            betOrderBeanQueryWrapper.orderByDesc("create_time");
        }
        else{
            if(orderByComm!=null)
                betOrderBeanQueryWrapper.last("order by "+betDetailOrderBy+" "+orderByComm);
            else
                betOrderBeanQueryWrapper.last("order by "+betDetailOrderBy);
        }


        IPage<BetOrderBean> result = betOrderService.page(page, betOrderBeanQueryWrapper);

        return result;
    }

    @Override
    public List<BetOrderDetailBean> getBetDetail(Long betOrderId) {
        QueryWrapper<BetOrderDetailBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bet_order_id", betOrderId);
        queryWrapper.orderByDesc("create_time");
        List<BetOrderDetailBean> list = betOrderDetailService.list(queryWrapper);
        list.forEach(order -> {
            order.setBetTopicsBean(betTopicsBeanMapper.selectTopicByOptionId(order.getBetOptionId()));
            if ("Campaign".equals(order.getBetTopicsBean().get(0).getTopicableType())) {
                order.setGameNo(gameCampaignBeanMapper.selectById(order.getBetTopicsBean().get(0).getTopicableId()).getNumber());
            }
        });
        return list;
    }

    @Override
    public IPage<UserBean> getUserList(String userId,IPage<UserBean> page,String apiid) {
        boolean export=false;
        if(httpServletRequest.getAttribute("export")!=null) export="1".equals((String)httpServletRequest.getAttribute("export"));
        if(export) page=null;

        List<Long> ids = new ArrayList<>();
        UserBean userBean2=null;
        Long userId2=null;
        if(StringUtils.isNotBlank(userId)){
            Pattern p=Pattern.compile("[0-9]*");
            Matcher m=p.matcher(userId);
            if(m.matches()){
                userId2=Long.parseLong(userId);
            }else{
                userId2=(userBean2=userMapper.findByUsername2(userId))!=null?userBean2.getId():null;
            }
        }
        if (userId2 != null) {
            if(!userService.getUserIds(apiid).contains(userId2))
                ids.add(-1L);
            else
                ids.add(userId2);
        } else {
            ids = userService.getUserIds(apiid);
            if(ids.size()==0)ids.add(-1L);
        }
        List<UserBean> userbeans=userMapper.selectUserPage(page, ids);
        userbeans.forEach(userBean -> {
            BigDecimal b1=userBean.getOrdertotle();
            BigDecimal b2=userBean.getRewardtotle();
            double bb1=b1.doubleValue();
            double bb2=b2.doubleValue();
            BigDecimal b4=new BigDecimal(0.00);
            if(bb1!=0) {
                BigDecimal b3 = new BigDecimal((bb2 - bb1) / bb1 * 100);
                b4 = b3.setScale(2, BigDecimal.ROUND_HALF_EVEN);
            }
            String ratedata=bb1==0?"0%":b4+"%";

            //   String ratedata=b1.compareTo(new BigDecimal(0)) == 0 ? "0%" :
            //          b1.subtract(b2).divide(b1, 2, BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(100)) + "%";
            userBean.setRewardtotle(b2.subtract(b1));
            userBean.setRatedata(ratedata);
        });

        if(export) {
            ReportExcel reportExcel = new ReportExcel();
            reportExcel.excelExport(userbeans, "用户明细列表", UserBean.class, 1, httpServletResponse, httpServletRequest);
            return null;
        }

        page.setRecords(userbeans);
        return page;
    }

    @Override
    public IPage<BetOrderBean> getUserOrderList(Long userId, Integer gameTypeId, DateDto dateDto, IPage page, UserOrderOrderBy userOrderOrderBy, OrderByComm orderByComm) {
        List<BetOrderBean> pages = betOrderBeanMapper.selectByUserId2(dateDto.getStartDate(), dateDto.getEndDate(), gameTypeId, userId, page,userOrderOrderBy,orderByComm);
        pages.forEach(order -> {
            QueryWrapper<BetOrderDetailBean> wrapper = new QueryWrapper<>();
            wrapper.eq("bet_order_id", order.getId());

            List<BetOrderDetailBean> details = betOrderDetailService.list(wrapper);
//            details.forEach(in->{
//                in.setRollingBall("1".equals(betTopicsBeanMapper.getRollBallByOptionId(in.getBetOptionId()+"")));
//            });
            order.setBetOrderDetails(details);
            order.setBetTopicsBean(betTopicsBeanMapper.selectTopicByOptionId(details.get(0).getBetOptionId()));
            if (order.getBetTopicsBean().size()!=0&&"Campaign".equals(order.getBetTopicsBean().get(0).getTopicableType())) {
                order.setGameNo(gameCampaignBeanMapper.selectById(order.getBetTopicsBean().get(0).getTopicableId()).getNumber());
            }
        });
        page.setRecords(pages);
        return page;
    }

    @Override
    public IPage<BetOrderBean> getOrderPage(String userId, Page page, ScreenForBetOrderDto screenForBetOrderDto, String apiid, OrderOrderBy orderOrderBy, OrderByComm orderByComm) {
        List<Long> userIds = new ArrayList<>();
        Long userId2=null;
        UserBean userBean2=null;
        if(StringUtils.isNotBlank(userId)){
            Pattern p=Pattern.compile("[0-9]*");
            Matcher m=p.matcher(userId);
            if(m.matches()){
                userId2=Long.parseLong(userId);
            }else{
                userId2=(userBean2=userMapper.findByUsername2(userId))!=null?userBean2.getId():null;
            }
        }
        if (userId2 != null) {
            if(!userService.getUserIds(apiid).contains(userId2))
                userIds.add(-1L);
            else
                userIds.add(userId2);
        } else {
            userIds = userService.getUserIds(apiid);
            if(userIds.size()==0)userIds.add(-1L);
        }

//        QueryWrapper<BetOrderBean> queryWrapper = new QueryWrapper<>();
//        queryWrapper.in("tenant_user_no", userIds);
//        queryWrapper.orderByDesc("create_time");
//        加入筛选条件
//        if(StringUtils.isBlank(screenForBetOrderDto.getOrderresult()))screenForBetOrderDto.setOrderresult(null);
//        if(StringUtils.isBlank(screenForBetOrderDto.getOrderstatus()))screenForBetOrderDto.setOrderstatus(null);
        screenForBetOrderDto=(ScreenForBetOrderDto) CommonUtil.setNoParamToNull(screenForBetOrderDto);
        List<BetOrderBean> pages;
        pages = betOrderBeanMapper.screenBetOrder2(page,screenForBetOrderDto,userIds,orderOrderBy,orderByComm);
        pages.forEach(order -> {
            QueryWrapper<BetOrderDetailBean> wrapper = new QueryWrapper<>();
            wrapper.eq("bet_order_id", order.getId());

            List<BetOrderDetailBean> details = betOrderDetailService.list(wrapper);
//            details.forEach(in->{
//                in.setRollingBall("1".equals(betTopicsBeanMapper.getRollBallByOptionId(in.getBetOptionId()+"")));
//            });
            order.setBetOrderDetails(details);
            order.setBetTopicsBean(betTopicsBeanMapper.selectTopicByOptionId(details.get(0).getBetOptionId()));
            if (order.getBetTopicsBean().size()!=0&&"Campaign".equals(order.getBetTopicsBean().get(0).getTopicableType())) {
                order.setGameNo(gameCampaignBeanMapper.selectById(order.getBetTopicsBean().get(0).getTopicableId()).getNumber());
            }
//            //加入渠道来源
//            UserBean userBean=userService.getById(order.getTenantUserNo());
//            if(userBean!=null)
//                order.setChannel(userBean.getApiId());
        });
        page.setRecords(pages);
        return page;
    }

    @Override
    public IPage<BetResult> getBetResult(Integer gameTypeId, DateDto dateDto, String status, IPage page, BetOrderBy betOrderBy, OrderByComm orderType,String hasOrder) {
//    public IPage<BetResult> getBetResult(Integer gameTypeId, DateDto dateDto, String status, IPage page) {
        List<Long> userIds = userService.getUserIds();
        if(userIds.size()==0)userIds.add(-1l);
        if(StringUtils.isBlank(status))status=null;
        if(StringUtils.isBlank(hasOrder))hasOrder="0";
        List<BetTopicsBean> list = betTopicsBeanMapper.selectAllOrderBy2(dateDto.getStartDate(), dateDto.getEndDate(),status, gameTypeId, page, betOrderBy,orderType,userIds,hasOrder);
        //    List<BetTopicsBean> list = betTopicsBeanMapper.selectAll(dateDto.getStartDate(), dateDto.getEndDate(),status, gameTypeId, page);

        List<BetResult> results = new ArrayList<>();
        list.forEach(betTopicsBean -> {
            List<GameTeamBean> gameTeams=gameTeamBeanMapper.selectByDataId(betTopicsBean.getDataId());
            //  for(int i=0;i<gameTeams.size();i++) {
            BetResult betResult = new BetResult();
            betResult.setBetTopics(betTopicsBean);
            betResult.setGameTeams(gameTeams);
            betResult.setStartTime(gameDataBeanMapper.selectById(betTopicsBean.getDataId()).getStartTime());

            List<Integer> optionIds = new ArrayList<>();
//                betTopicsBean.getBetOptions().forEach(bean -> {
//                    optionIds.add(bean.getId());
//                });
            optionIds.add(betTopicsBean.getOptionid());
            QueryWrapper<BetOrderBean> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("ifnull(sum(amount), 0.00) amount");
            queryWrapper.in("bet_option_id", optionIds);
            queryWrapper.in("tenant_user_no", userIds);
            queryWrapper.eq("status", OrderStatus.SETTLED);
            BigDecimal totalAmount = (BigDecimal) betOrderService.getMap(queryWrapper).get("amount");
            betResult.setOrderAmount(totalAmount);

            queryWrapper = new QueryWrapper();
            queryWrapper.select("ifnull(sum(estimated_reward), 0.00) amount");
            queryWrapper.in("bet_option_id", optionIds);
            queryWrapper.in("tenant_user_no", userIds);
            queryWrapper.eq("status", OrderStatus.SETTLED);
            queryWrapper.eq("win_lose", WinLoseStatus.WIN);
            BigDecimal returnAmount = (BigDecimal) betOrderService.getMap(queryWrapper).get("amount");
            betResult.setSettledAmount(returnAmount);
            betResult.setSettledRate(totalAmount.compareTo(new BigDecimal(0)) == 0 ? "0%" :
                    totalAmount.subtract(returnAmount).divide(totalAmount, 2, BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(100)) + "%");

            queryWrapper = new QueryWrapper<>();
            queryWrapper.in("bet_option_id", optionIds);
            queryWrapper.in("tenant_user_no", userIds);
            queryWrapper.eq("status", OrderStatus.SETTLED);

            betResult.setOrderCount(betOrderService.count(queryWrapper));

            queryWrapper = new QueryWrapper<>();
            queryWrapper.select("distinct tenant_user_no");
            queryWrapper.in("bet_option_id", optionIds);
            queryWrapper.in("tenant_user_no", userIds);
            queryWrapper.eq("status", OrderStatus.SETTLED);
            betResult.setUserCount(betOrderService.count(queryWrapper));

            if ("Campaign".equals(betTopicsBean.getTopicableType())) {
                betResult.setGameNo(gameCampaignBeanMapper.selectById(betTopicsBean.getTopicableId()).getNumber());
            }
            // betResult.setTeam(gameTeams.get(i).getAbbr());
            //betResult.setBet_option(optionIds.get(i));

            betResult.setTeam(gameTeams.get(betTopicsBean.getSequence()-1).getAbbr());
            betResult.setBet_option(betTopicsBean.getOptionid());
            results.add(betResult);
            //  }
        });
        page.setRecords(results);
        return page;
    }

    private JSONObject getRevenue(Date date, List<Long> userIds) {
        JSONObject jsonObject = new JSONObject();
        QueryWrapper<BetOrderBean> queryWrapper = new QueryWrapper();
        queryWrapper.select("ifnull(sum(amount), 0.00) amount");
        queryWrapper.eq("date_format(update_time, '%Y-%m-%d')", DateUtil.dateToString(date));
        queryWrapper.in("tenant_user_no", userIds);
        queryWrapper.eq("status", OrderStatus.SETTLED);
        queryWrapper.in("win_lose", WinLoseStatus.WIN,WinLoseStatus.LOSE);

        BigDecimal totalAmount = (BigDecimal) betOrderService.getMap(queryWrapper).get("amount");
        queryWrapper = new QueryWrapper();
        queryWrapper.select("ifnull(sum(estimated_reward), 0.00) amount");
        queryWrapper.eq("date_format(update_time, '%Y-%m-%d')", DateUtil.dateToString(date));
        queryWrapper.in("tenant_user_no", userIds);
        queryWrapper.eq("status", OrderStatus.SETTLED);
        queryWrapper.eq("win_lose", WinLoseStatus.WIN);
        BigDecimal winAmount = (BigDecimal) betOrderService.getMap(queryWrapper).get("amount");

        jsonObject.put("returnAmount", winAmount);

        jsonObject.put("totalAmount", totalAmount);
        jsonObject.put("rewardAmount", totalAmount.subtract(winAmount));
        jsonObject.put("rewardRate", totalAmount.compareTo(new BigDecimal(0)) == 0 ? "0%" :
                totalAmount.subtract(winAmount).divide(totalAmount, 2, BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(100)) + "%");


        queryWrapper = new QueryWrapper();
        queryWrapper.eq("date_format(update_time, '%Y-%m-%d')", DateUtil.dateToString(date));
        queryWrapper.in("tenant_user_no", userIds);
        queryWrapper.eq("status", OrderStatus.SETTLED);
        jsonObject.put("orderCount", betOrderService.count(queryWrapper));

        queryWrapper = new QueryWrapper();
        queryWrapper.select("distinct tenant_user_no");
        queryWrapper.eq("date_format(update_time, '%Y-%m-%d')", DateUtil.dateToString(date));
        queryWrapper.in("tenant_user_no", userIds);
        queryWrapper.eq("status", OrderStatus.SETTLED);
        jsonObject.put("userCount", betOrderService.count(queryWrapper));

        queryWrapper = new QueryWrapper();
        queryWrapper.select("1");
        queryWrapper.in("tenant_user_no", userIds);
        queryWrapper.eq("status", OrderStatus.SETTLED);
        queryWrapper.groupBy("tenant_user_no");
        queryWrapper.having("date_format(min(update_time), '%Y-%m-%d') = {0}", DateUtil.dateToString(date));
        List maps = betOrderService.listMaps(queryWrapper);
        jsonObject.put("newUserCount", betOrderService.listMaps(queryWrapper) == null ? 0 : betOrderService.listMaps(queryWrapper).size());
        jsonObject.put("date", DateUtil.dateToString(date));
        String dateStr= DateUtil.dateToString(date);
//        String dateStr= "2019-4-02";
        //当天总提款和总存款，优惠活动总金额
        List<JSONObject> jsonObjects =moneyRecordBeanMapper.selectTotleGroupByType(dateStr);
        Map<String,Map<String,Integer>> moneyable_types =new HashMap<>();
        for(JSONObject json:jsonObjects) {
            Map<String,Integer> mapin=new HashMap<>();
            mapin.put(json.getString("moneyabletype"),json.getInteger("totle"));
            moneyable_types.put(json.getString("moneyabletype"),mapin);
        }
        for(MoneyAbleType moneyAbleType:MoneyAbleType.values()){
            if(moneyable_types.containsKey(moneyAbleType.toString()))
                jsonObject.put("moneyrecord"+moneyAbleType.toString().toLowerCase(),moneyable_types.get(moneyAbleType.toString()).get(moneyAbleType.toString()));
            else
                jsonObject.put("moneyrecord"+moneyAbleType.toString().toLowerCase(),0);
        }
        return jsonObject;
    }

    @Override
    public List<GameTypeBean> gameTypes() {
        return gameTypeService.list();
    }

    private List<Long> getUserIds(Long apiId) {
        QueryWrapper<UserBean> queryWrapper = new QueryWrapper();
        queryWrapper.select("id");
        if (apiId != null) {
            queryWrapper.eq("api_id", apiId);
        }
        List<Long> ids = new ArrayList<>();
        userService.list(queryWrapper).forEach(userBean -> {
            ids.add(userBean.getId());
        });
        return ids;
    }

    public static void main(String[] args) {
        for(MoneyAbleType moneyAbleType:MoneyAbleType.values()){
            System.out.println(moneyAbleType.toString());
        }
    }
}
