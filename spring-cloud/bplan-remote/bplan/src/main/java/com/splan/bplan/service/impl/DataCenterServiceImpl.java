package com.splan.bplan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.*;
import com.splan.base.enums.AccessType;
import com.splan.base.enums.KaliPayType;
import com.splan.base.enums.OrderStatus;
import com.splan.bplan.mappers.PayOrderBeanMapper;
import com.splan.bplan.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DataCenterServiceImpl implements DataCenterService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IPayOrderService payOrderService;

    @Autowired
    private IUserBalanceService userBalanceService;

    @Autowired
    private IBetOrderService betOrderService;

    @Autowired
    private IUserOnlineService iUserOnlineService;

    @Override
    public JSONObject getRegisterInfo(JSONObject jsonObject) {
        JSONObject jsonObjectResult = new JSONObject();

        Integer total = userService.count();
        jsonObjectResult.put("total",total);//总注册

        QueryWrapper<UserBean> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("to_days(create_time)","to_days(now())");

        queryWrapper.isNull("be_invite_code");
        Integer normal = userService.count(queryWrapper);
        jsonObjectResult.put("normal",normal);//总注册

        return jsonObjectResult;
    }

    @Override
    public JSONObject getDepositInfo() {
        QueryWrapper<PayOrderBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("access_type", AccessType.DEPOSIT);
        queryWrapper.eq("status", OrderStatus.SETTLED);
        queryWrapper.select("ifnull(sum(amount), 0.00) amount");
        BigDecimal totalAmount = (BigDecimal) payOrderService.getMap(queryWrapper).get("amount");

        queryWrapper.eq("date_format(create_time, '%Y-%m-%d')", getFormatDate(new Date()));
        BigDecimal dayAmount = (BigDecimal) payOrderService.getMap(queryWrapper).get("amount");

        queryWrapper.like("channel", "AliPay");
        BigDecimal dayAliAmount = (BigDecimal) payOrderService.getMap(queryWrapper).get("amount");

        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("access_type", AccessType.DEPOSIT);
        queryWrapper.eq("status", OrderStatus.SETTLED);
        queryWrapper.eq("date_format(create_time, '%Y-%m-%d')", getFormatDate(new Date()));
        queryWrapper.eq("channel", KaliPayType.VISA);
        queryWrapper.select("ifnull(sum(amount), 0.00) amount");
        BigDecimal dayVISAAmount = (BigDecimal) payOrderService.getMap(queryWrapper).get("amount");

        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("access_type", AccessType.DEPOSIT);
        queryWrapper.eq("status", OrderStatus.SETTLED);
        queryWrapper.select("ifnull(sum(amount), 0.00) amount");

        List<Map<String, Object>> weekAmount = getWeekAmount(new Date(), queryWrapper);

        //查询三种支付类型的今日充值额
        queryWrapper=new QueryWrapper<>();
        queryWrapper.select("channel,ifnull(sum(amount),0.00) as amount");
        queryWrapper.groupBy("channel");
//        queryWrapper.apply("to_days(create_time) = to_days(now())");
//        queryWrapper.eq("access_type", AccessType.DEPOSIT);
//        queryWrapper.eq("status", OrderStatus.SETTLED);
        System.err.println(queryWrapper.toString());
        List<Map<String,Object>> channelsAmount=payOrderService.listMaps(queryWrapper);
        Map<String,Map<String,Object>> mapMap=new HashMap<>();
        channelsAmount.forEach(stringObjectMap -> {
            mapMap.put((String) stringObjectMap.get("channel"),stringObjectMap);
        });
         JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalAmount", totalAmount);
        jsonObject.put("dayAmount", dayAmount);
        jsonObject.put("dayAliAmount", dayAliAmount);
        jsonObject.put("dayVISAAmount", dayVISAAmount);
        jsonObject.put("weekAmount", weekAmount);
        jsonObject.put("channelsAmount",channelsAmount);
        for(KaliPayType e:KaliPayType.values()){
            if(mapMap.containsKey(e.getEnName())){
                jsonObject.put(e.getEnName(),mapMap.get(e.getEnName()).get("amount"));
            }else{
                jsonObject.put(e.getEnName(),0);
            }
        }

        return jsonObject;
    }

    @Override
    public IPage<PayOrderBean> getDepositList(Page page, Date startDate, Date endDate) {
        QueryWrapper<PayOrderBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("access_type", AccessType.DEPOSIT);
        queryWrapper.eq("status", OrderStatus.SETTLED);
        queryWrapper.groupBy("to_days(create_time)");
        if (startDate != null) {
            queryWrapper.ge("create_time", startDate);
        }
        if (endDate != null) {
            queryWrapper.le("date_format(create_time, '%Y-%m-%d')", endDate);
        }
        queryWrapper.select("ifnull(sum(amount), 0.00) amount", "max(create_time) create_time");
        return payOrderService.pageMaps(page, queryWrapper);
    }

    @Override
    public JSONObject getWithdrawInfo() {
        QueryWrapper<PayOrderBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("access_type", AccessType.WITHDRAW);
        queryWrapper.eq("status", OrderStatus.SETTLED);
        queryWrapper.select("ifnull(sum(amount), 0.00) amount");
        BigDecimal totalAmount = (BigDecimal) payOrderService.getMap(queryWrapper).get("amount");

        queryWrapper.eq("date_format(create_time, '%Y-%m-%d')", getFormatDate(new Date()));
        BigDecimal dayAmount = getPayOrderAmount(queryWrapper);

        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("access_type", AccessType.WITHDRAW);
        queryWrapper.eq("status", OrderStatus.SETTLED);
        queryWrapper.select("ifnull(sum(amount), 0.00) amount");
        List<Map<String, Object>> weekAmount = getWeekAmount(new Date(), queryWrapper);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalAmount", totalAmount);
        jsonObject.put("dayAmount", dayAmount);
        jsonObject.put("weekAmount", weekAmount);
        List<String> types=new ArrayList<>();
        types.add("Agent");
        types.add("SuperAgent");
        jsonObject.put("todayAgentamount",payOrderService.getTodayamountByUserType(types));
        types.clear();;
        types.add("Normal");
        jsonObject.put("todayNormalamount",payOrderService.getTodayamountByUserType(types));
        return jsonObject;
    }

    @Override
    public IPage<PayOrderBean> getWithdrawList(Page page, Date startDate, Date endDate) {
        QueryWrapper<PayOrderBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("access_type", AccessType.WITHDRAW);
        queryWrapper.eq("status", OrderStatus.SETTLED);
        queryWrapper.groupBy("to_days(create_time)");
        if (startDate != null) {
            queryWrapper.ge("create_time", startDate);
        }
        if (endDate != null) {
            queryWrapper.le("date_format(create_time, '%Y-%m-%d')", endDate);
        }
        queryWrapper.select("ifnull(sum(amount), 0.00) amount", "max(create_time) create_time");
        return payOrderService.pageMaps(page, queryWrapper);
    }

    @Override
    public JSONObject getBalanceInfo() {
        QueryWrapper<UserBalanceBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("sum(coin) amount", "max(create_time) create_time");
        BigDecimal totalAmount = (BigDecimal) userBalanceService.getMap(queryWrapper).get("amount");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalAmount", totalAmount);
        return jsonObject;
    }

    @Override
    public IPage<UserBalanceBean> getBalanceList(Page page, Date startDate, Date endDate) {
        QueryWrapper<UserBalanceBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.groupBy("to_days(create_time)");
        if (startDate != null) {
            queryWrapper.ge("create_time", startDate);
        }
        if (endDate != null) {
            queryWrapper.le("date_format(create_time, '%Y-%m-%d')", endDate);
        }
        queryWrapper.select("ifnull(sum(coin), 0.00) amount", "max(create_time) create_time");
        return userBalanceService.pageMaps(page, queryWrapper);

    }

    private BigDecimal getPayOrderAmount(QueryWrapper queryWrapper) {
        List<PayOrderBean> list = payOrderService.list(queryWrapper);
        BigDecimal amount = new BigDecimal(0.00);
        list.forEach(order -> amount.add(order.getAmount()));
        return amount;
    }

    private BigDecimal getBalanceAmount(QueryWrapper queryWrapper) {
        List<UserBalanceBean> list = userBalanceService.list(queryWrapper);
        BigDecimal amount = new BigDecimal(0.00);
        list.forEach(userBalance -> amount.add(userBalance.getCoin()));
        return amount;
    }

    private String getFormatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        return simpleDateFormat.format(date);
    }

    private List<Map<String, Object>> getWeekAmount(Date date, QueryWrapper queryWrapper) {
        Calendar calendar = Calendar.getInstance();
        List<Map<String, Object>> list = new ArrayList<>();
        for(int i = 0; i < 7; i++) {
            queryWrapper.last("and date_format(create_time, '%Y-%m-%d') = '" + getFormatDate(date) + "'");
            Map<String, Object> map = new HashMap<>();
            map.put("amount", payOrderService.getMap(queryWrapper).get("amount"));
            map.put("date", getFormatDate(date));
            list.add(map);
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            date = calendar.getTime();
        }
        return list;
    }

    @Override
    public JSONObject getOrderInfo() {
        QueryWrapper<BetOrderBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",OrderStatus.SETTLED);
        queryWrapper.select("sum(amount) amount", "max(create_time) create_time");
        BigDecimal totalAmount = (BigDecimal)betOrderService.getMap(queryWrapper).get("amount");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalAmount", totalAmount);
        jsonObject.put("TodayOrderAmount",betOrderService.getTodayOrderAmount());
        jsonObject.put("YestTodayOrderAmount",betOrderService.getYestTodayOrderAmount());
        return jsonObject;
    }

    @Override
    public IPage<BetOrderBean> getOrderList(Page page, Date startDate, Date endDate) {
        QueryWrapper<BetOrderBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.groupBy("to_days(create_time)");
        if (startDate != null) {
            queryWrapper.ge("create_time", startDate);
        }
        if (endDate != null) {
            queryWrapper.le("date_format(create_time, '%Y-%m-%d')", endDate);
        }
        queryWrapper.eq("status",OrderStatus.SETTLED);
        queryWrapper.select("ifnull(sum(amount), 0.00) amount", "max(create_time) create_time");
        return betOrderService.pageMaps(page, queryWrapper);
    }

    @Override
    public JSONObject getProfitInfo() {
        QueryWrapper<BetOrderBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",OrderStatus.SETTLED);
        queryWrapper.select("(sum(t.amount)-sum(t.estimated_reward)) as amount", "max(create_time) create_time");
        BigDecimal totalAmount = (BigDecimal)betOrderService.getMap(queryWrapper).get("amount");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalAmount", totalAmount);
        jsonObject.put("TodayProfitAmount",betOrderService.getTodayProfitAmount());
        jsonObject.put("YestTodayProfitAmount",betOrderService.getYestTodayProfitAmount());
        return jsonObject;
    }

    @Override
    public IPage<BetOrderBean> getProfitList(Page page, Date startDate, Date endDate) {
        QueryWrapper<BetOrderBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.groupBy("to_days(create_time)");
        if (startDate != null) {
            queryWrapper.ge("create_time", startDate);
        }
        if (endDate != null) {
            queryWrapper.le("date_format(create_time, '%Y-%m-%d')", endDate);
        }
        queryWrapper.eq("status",OrderStatus.SETTLED);
        queryWrapper.select("(sum(t.amount)-sum(t.estimated_reward)) as amount", "max(create_time) create_time");
        return betOrderService.pageMaps(page, queryWrapper);
    }

    @Override
    public JSONObject getOnlineInfo() {
        QueryWrapper<UserOnlineBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("max(online) as amount");
        BigDecimal totalAmount = (BigDecimal)iUserOnlineService.getMap(queryWrapper).get("amount");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("maxonline", totalAmount);
        return jsonObject;
    }

    @Override
    public IPage<UserOnlineBean> getOnlineList(Page page, Date startDate, Date endDate) {
        {
            QueryWrapper<UserOnlineBean> queryWrapper = new QueryWrapper<>();
            queryWrapper.groupBy("to_days(create_time)");
            if (startDate != null) {
                queryWrapper.ge("create_time", startDate);
            }
            if (endDate != null) {
                queryWrapper.le("date_format(create_time, '%Y-%m-%d')", endDate);
            }
            queryWrapper.select("online,create_time");
            return iUserOnlineService.pageMaps(page, queryWrapper);
        }
    }

//    private List<Map<String,Object>> getTodayDataByType(QueryWrapper queryWrapper){
//            List<Map<String,Object>> list=new ArrayList<>();
//
//    }

}
