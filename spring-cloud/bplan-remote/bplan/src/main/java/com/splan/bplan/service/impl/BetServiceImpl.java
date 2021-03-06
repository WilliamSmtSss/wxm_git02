package com.splan.bplan.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.splan.base.bean.*;
import com.splan.base.enums.*;
import com.splan.bplan.constants.MapKeyComparator;
import com.splan.bplan.constants.SysParamConstants;
import com.splan.bplan.dto.*;
import com.splan.bplan.filter.BigDecimalValueFilter;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.http.DataCenterConfig;
import com.splan.bplan.http.DataResult;
import com.splan.bplan.mappers.*;
import com.splan.bplan.result.GameResult;
import com.splan.bplan.service.*;
import com.splan.bplan.thread.OrderCallable;
import com.splan.bplan.thread.OrderCameoCallable;
import com.splan.bplan.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class BetServiceImpl implements IBetService {

    @Value("${bplan.encrypt.privatekey}")
    private String privateKey;

    @Autowired
    private UserBalanceBeanMapper userBalanceBeanMapper;

    @Autowired
    private BetOptionBeanMapper betOptionBeanMapper;

    @Autowired
    private BetOrderBeanMapper betOrderBeanMapper;

    @Autowired
    private DataCenterConfig dataCenterConfig;

    @Autowired
    private IMoneyRecordService moneyRecordService;

    @Autowired
    private BetTopicsBeanMapper betTopicsBeanMapper;

    @Autowired
    private GameCampaignBeanMapper gameCampaignBeanMapper;

    @Autowired
    private GameDataBeanMapper gameDataBeanMapper;

    @Autowired
    private BetOrderDetailBeanMapper betOrderDetailBeanMapper;

    @Autowired
    private SysParamBeanMapper sysParamBeanMapper;

    @Autowired
    private IActiveService activeService;

    @Autowired
    private INoticeService noticeService;

    @Autowired
    private IBetOrderDetailService betOrderDetailService;

    @Autowired
    private IGameTeamService gameTeamService;

    @Autowired
    private IBetOptionService betOptionService;

    @Autowired
    private IBusinessConfigService businessConfigService;

    private ExecutorService service = Executors.newFixedThreadPool(9);

    private final String PRE = "BET";

    private boolean checkLimit(UserBalanceBean userBalanceBean,BetTopicsBean betTopicsBean,OrderDto orderDto,BetOptionBean betOptionBean){
        if (userBalanceBean.getLimitOrderCoin().intValue()!=0 && orderDto.getAmount()>userBalanceBean.getLimitOrderCoin().intValue()){
            return false;
        }
        Integer userBetLimit = Integer.valueOf(SysParamConstants.getMap().get(SysParamConstants.ORDER_LIMIT_MAX));
        Integer userSingleBetProfitLimit = Integer.valueOf(SysParamConstants.getMap().get(SysParamConstants.ORDER_PROFIT_LIMIT_MAX));

        if (betTopicsBean.getUserBetLimit()!=null){
            userBetLimit = betTopicsBean.getUserBetLimit();
        }
        if (betTopicsBean.getUserSingleBetProfitLimit()!=null){
            userSingleBetProfitLimit = betTopicsBean.getUserSingleBetProfitLimit();
        }
        if (betOptionBean.getUserSingleBetProfitLimit()!=null){
            userSingleBetProfitLimit = betOptionBean.getUserSingleBetProfitLimit().intValue();
        }
        BigDecimal estiAmount = orderDto.getOdd().subtract(new BigDecimal(1)).multiply(new BigDecimal(orderDto.getAmount()));

        BigDecimal orderAmount = betOrderBeanMapper.getUserOrderSum(orderDto.getBetOptionId(),userBalanceBean.getUserId());
        BigDecimal totalOrder = estiAmount.add(orderAmount);
        /*if (totalOrder.compareTo(new BigDecimal(betTopicsBean.getUserSingleBetProfitLimit()).multiply(new BigDecimal(2)))>-1){
            return false;
        }*/
        /**
         * 累计下注金额控制
         */
        BigDecimal totalLimt = new BigDecimal(betTopicsBean.getUserSingleBetProfitLimit()).multiply(new BigDecimal(2));

        if (betTopicsBean.getTopicHandicapProfitLimit()!=null){
            totalLimt = new BigDecimal(betTopicsBean.getTopicHandicapProfitLimit());
        }

        if (totalOrder.compareTo(totalLimt)>-1){
            return false;
        }

        if (orderDto.getAmount()<userBetLimit && estiAmount.compareTo(new BigDecimal(userSingleBetProfitLimit))<1){
            return true;
        }
        return false;
    }

    /**
     * 下单
     * @param user
     * @param orderDtoList
     * @param confirm true确定  false未确认
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ,rollbackFor = Exception.class)
    public CommonResult<List<BetOrderBean>> order(UserBean user, OrderBetDto orderBetDto) {
        /**
         * 1.纠正 赔率
         * 2.判断 余额是否充足
         * 3.预扣冻结
         * 4.增加订单
         * 5.增加流水
         * 6.发送下注
         * 7.扣钱
         */
        try{
            List<OrderDto> orderDtoList = orderBetDto.getOrders();

            BigDecimal totalAmount = new BigDecimal(0);

            UserBalanceBean userBalanceBean = null;

            List<BetOrderBean> resultList = new ArrayList<>();
            long startTime = new Date().getTime();
            for (int i = 0; i < orderDtoList.size(); i++) {
                if (orderDtoList.get(i).getAmount()<=0){//金额不能为负数
                    return ResultUtil.returnError(ResultStatus.BET_OUT_LIMIT);//金额不能为负数
                }
                BetOptionBean betOptionBean = betOptionBeanMapper.selectById(orderDtoList.get(i).getBetOptionId());

                if (betOptionBean==null){
                    return ResultUtil.returnError(ResultStatus.BET_NOT_EXIST);//盘口不存在
                }

                BetTopicsBean betTopicsBean = betTopicsBeanMapper.selectById(betOptionBean.getBetDataId());
                if (betTopicsBean==null){
                    return ResultUtil.returnError(ResultStatus.BET_NOT_EXIST);//盘口不存在
                }
                if (!betTopicsBean.getStatus().equals("default")){
                    return ResultUtil.returnError(ResultStatus.BET_CANCEL);//盘口取消或者 已结算
                }

                if (!betTopicsBean.isSwitchStatus() || !betTopicsBean.isFinalSwitch()){
                    return ResultUtil.returnError(ResultStatus.BET_LOCK);
                }

                if (betOptionBean.getBetResult()!=null && betOptionBean.getBetResult().intValue()!=0){
                    return ResultUtil.returnError(ResultStatus.BET_FINISH);//盘口结束
                }
                if (!orderBetDto.isConfirm() && !new BigDecimal(betOptionBean.getOdd()).equals(orderDtoList.get(i).getOdd())){
                    return ResultUtil.returnError(ResultStatus.ODD_IS_CHANGE);//赔率变化  返回前端重新接受
                }

                orderDtoList.get(i).setOdd(new BigDecimal(betOptionBean.getOdd()));
                totalAmount.add(new BigDecimal(orderDtoList.get(i).getAmount()));

                BigDecimal amount = new BigDecimal(orderDtoList.get(i).getAmount());
                /**
                 * id锁  冻结金额
                 */
                BigDecimal from = new BigDecimal(0);
                userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(user.getId());
                from = userBalanceBean.getAvailableCoin();
                if (userBalanceBean.getAvailableCoin().compareTo(amount)<0){
                    return ResultUtil.returnError(ResultStatus.NOT_ENOUGH_MONEY);//余额不足
                }

                if (!checkLimit(userBalanceBean,betTopicsBean,orderDtoList.get(i),betOptionBean)){
                    return ResultUtil.returnError(ResultStatus.BET_OUT_LIMIT);//超出下注额
                }

                int retry = 0;

                int x = frozen(userBalanceBean,amount);
                while (x==0 && retry<10){
                    userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(user.getId());
                    from = userBalanceBean.getCoin();
                    x = frozen(userBalanceBean,amount);
                }
                if (retry==10){
                    return ResultUtil.returnError(ResultStatus.NOT_ENOUGH_MONEY);
                }

                /**
                 * 发送给b端请求
                 * 成功 释放 冻结金额
                 * 失败 返回 coin金额 a
                 */

                String orderNo = null;
                if (StringUtils.isBlank(user.getApiId())){
                    orderNo = OrderNoGeneratorUtil.getOrderNoByTime("SG");
                }else {
                    orderNo = OrderNoGeneratorUtil.getOrderNoByTime(user.getApiId());
                }

                BetOrderDto betOrderDto = new BetOrderDto();
                betOrderDto.setBetOptionId(orderDtoList.get(i).getBetOptionId());
                betOrderDto.setAmount(orderDtoList.get(i).getAmount());
                betOrderDto.setOdd(orderDtoList.get(i).getOdd());
                betOrderDto.setTenantCustomerNo(user.getId());
                betOrderDto.setTenantOrderNo(orderNo);

                betOrderDto.setOdd(betOrderDto.getOdd().setScale(2,BigDecimal.ROUND_HALF_DOWN));


                Integer dataId = betTopicsBean.getDataId();

                GameResult gameDataBean = gameDataBeanMapper.selectGameWithTeamCampainsResultById(dataId);

                BetOrderBean betOrderBean = processOrder(orderDtoList.get(i),userBalanceBean,orderNo,dataId,orderBetDto.getCameo(),gameDataBean,orderBetDto.getIp(),betOptionBean,betTopicsBean,user);// 生成订单
                resultList.add(betOrderBean);
                //异步提交
                service.submit(new OrderCallable(this,moneyRecordService,activeService,noticeService,betOrderDto,betOrderBean,userBalanceBean,amount,from));

            }

            return ResultUtil.returnSuccess(resultList);
        }catch (Exception e){
            log.error(e.toString());
            return ResultUtil.returnError(ResultStatus.UNKNOWN_ERROR);
        }

    }

    @Override
    public BetOrderBean asynSendOrder(BetOrderDto betOrderDto,BetOrderBean betOrderBean,UserBalanceBean userBalanceBean,BigDecimal amount,BigDecimal from){
        boolean flag = false;//请求成功
        if (betOrderBean.getInfluenceAmount()>0) {
            Map<String, Object> datamap = new TreeMap<>();
            //datamap.put("amount", betOrderDto.getAmount());
            datamap.put("amount", betOrderBean.getInfluenceAmount());
            datamap.put("bet_option_id", betOrderDto.getBetOptionId());
            datamap.put("odd", betOrderDto.getOdd());
            datamap.put("tenant_customer_no", betOrderDto.getTenantCustomerNo());
            datamap.put("tenant_order_no", betOrderDto.getTenantOrderNo());
            String sign = null;
            try {
                sign = sign(datamap);
            } catch (Exception e) {
                log.error(e.toString() + "加签失败");
            }
            betOrderDto.setAmount(betOrderBean.getInfluenceAmount());
            BetOrderSignDto betOrderSignDto = new BetOrderSignDto();
            betOrderSignDto.setBetOrder(betOrderDto);
            betOrderSignDto.setSign(sign);
            SerializeConfig config = new SerializeConfig();
            config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
            String data = JSON.toJSONString(betOrderSignDto, config, new BigDecimalValueFilter());

            log.info("发送数据====" + data);
            DataResult result = OKHttpUtil.httpPost(dataCenterConfig.getUrl(), data, dataCenterConfig.getApiKey(), dataCenterConfig.getApiVersion());
            flag = result.isSuccess();//请求成功
            if (flag){
                //添加流水记录
                moneyRecordService.createRecord(betOrderBean.getId(),userBalanceBean.getId(), MoneyAbleType.BET,from,amount,AlgorithmType.SUB,result.getResult(),userBalanceBean.getUserId());
                //activeService.afterCheck(userBalanceBean.getUserId());
                betOrderBean.setFailResult(result.getResult());
                betOrderBean.setStatus(OrderStatus.UNSETTLED);
                betOrderBean.getBetOrderDetails().stream().forEach((e)->{
                    e.setStatus(OrderStatus.UNSETTLED);
                    e.setExtra(null);
                    betOrderDetailBeanMapper.updateById(e);
                });
            }else {
                betOrderBean.setStatus(OrderStatus.FAIL);
                betOrderBean.setFailResult(result.getResult());
                betOrderBean.getBetOrderDetails().stream().forEach((e)->{
                    e.setStatus(OrderStatus.FAIL);
                    e.setExtra(null);
                    betOrderDetailBeanMapper.updateById(e);
                });
                String comment = getChinese(betOrderBean.getFailResult());
                noticeService.sendNotice("退款",betOrderDto.getTenantOrderNo(),amount,"系统拒绝:"+comment,betOrderBean.getCreateTime(),null,betOrderBean.getId(),userBalanceBean.getUserId(),MsgType.refund);
            }
        }else {
            flag = true;
            //添加流水记录
            moneyRecordService.createRecord(betOrderBean.getId(),userBalanceBean.getId(), MoneyAbleType.BET,from,amount,AlgorithmType.SUB,"试玩",userBalanceBean.getUserId());
            //activeService.afterCheck(userBalanceBean.getUserId());
            betOrderBean.setFailResult("试玩");
            betOrderBean.setStatus(OrderStatus.UNSETTLED);
            betOrderBean.getBetOrderDetails().stream().forEach((e)->{
                e.setStatus(OrderStatus.UNSETTLED);
                e.setExtra(null);
                betOrderDetailBeanMapper.updateById(e);
            });
        }


        betOrderBeanMapper.updateById(betOrderBean);

        /**
         * id锁 释放金额
         */

        int y = unfrozen(userBalanceBean,amount,flag);
        while (y==0){
            //更新失败 重新读取信息
            userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(userBalanceBean.getUserId());
            y = unfrozen(userBalanceBean,amount,flag);
        }
        return betOrderBean;


    }

    @Override
    public BetOrderBean synSendCameoOrder(BetCameoOrderDto betOrderDto,BetOrderBean betOrderBean,UserBalanceBean userBalanceBean,BigDecimal amount,BigDecimal from){
        boolean flag = false;//请求成功
        if (betOrderBean.getInfluenceAmount()>0) {
            SerializeConfig config = new SerializeConfig();
            config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
            Map<String, Object> datamap = new TreeMap<>();
            //datamap.put("amount", betOrderDto.getAmount());
            datamap.put("amount",betOrderBean.getInfluenceAmount());
            datamap.put("tenant_customer_no", betOrderDto.getTenantCustomerNo());
            datamap.put("tenant_order_no", betOrderDto.getTenantOrderNo());
            datamap.put("bet_option_cameo_orders_attributes", cameoAttributes(betOrderDto.getBetOptionCameoOrdersAttributes()));
            String sign = null;
            try {
                sign = sign(datamap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            betOrderDto.setAmount(betOrderBean.getInfluenceAmount());
            BetCameoOrderSignDto betOrderSignDto = new BetCameoOrderSignDto();
            betOrderSignDto.setCameoOrder(betOrderDto);
            betOrderSignDto.setSign(sign);
            String data = JSON.toJSONString(betOrderSignDto, config, new BigDecimalValueFilter());

            DataResult result = OKHttpUtil.httpPost(dataCenterConfig.getCameoUrl(), data, dataCenterConfig.getApiKey(), dataCenterConfig.getApiVersion());
            flag = result.isSuccess();//请求成功

            if (flag) {
                betOrderBean.setStatus(OrderStatus.UNSETTLED);
                betOrderBean.setFailResult(result.getResult());
                betOrderBean.getBetOrderDetails().stream().forEach((e) -> {
                    e.setStatus(OrderStatus.UNSETTLED);
                    e.setExtra(null);
                    betOrderDetailBeanMapper.updateById(e);
                });
                //添加流水记录
                moneyRecordService.createRecord(betOrderBean.getId(), userBalanceBean.getId(), MoneyAbleType.BET, from, amount, AlgorithmType.SUB, result.getResult(), userBalanceBean.getUserId());
                //activeService.afterCheck(userBalanceBean.getUserId());
            } else {
                betOrderBean.setStatus(OrderStatus.FAIL);
                betOrderBean.setFailResult(result.getResult());
                betOrderBean.getBetOrderDetails().stream().forEach((e) -> {
                    e.setStatus(OrderStatus.FAIL);
                    e.setExtra(null);
                    betOrderDetailBeanMapper.updateById(e);
                });
                String comment = getChinese(betOrderBean.getFailResult());
                noticeService.sendNotice("退款", betOrderDto.getTenantOrderNo(), amount, "系统拒绝:" + comment, betOrderBean.getCreateTime(), null, betOrderBean.getId(), userBalanceBean.getUserId(), MsgType.refund);
            }
            betOrderBeanMapper.updateById(betOrderBean);
        }else {
            flag = true;
            betOrderBean.setStatus(OrderStatus.UNSETTLED);
            betOrderBean.setFailResult("试玩");
            betOrderBean.getBetOrderDetails().stream().forEach((e) -> {
                e.setStatus(OrderStatus.UNSETTLED);
                e.setExtra(null);
                betOrderDetailBeanMapper.updateById(e);
            });
            //添加流水记录
            moneyRecordService.createRecord(betOrderBean.getId(), userBalanceBean.getId(), MoneyAbleType.BET, from, amount, AlgorithmType.SUB, "试玩", userBalanceBean.getUserId());
        }
        /**
         * id锁 释放金额
         */

        //log.info(userBalanceBean.getUserId() + "unfrozen获得锁" + Thread.currentThread().getName());
        int y = unfrozen(userBalanceBean, amount, flag);
        while (y == 0) {
            //更新失败 重新读取信息
            userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(userBalanceBean.getUserId());
            y = unfrozen(userBalanceBean, amount, flag);
        }
        log.info(userBalanceBean.getUserId() + "unfrozen释放锁" + Thread.currentThread().getName());


        return betOrderBean;
    }

    private String sign(Map<String,Object> map) throws Exception {
        Map<String, Object> resultMap = sortMapByKey(map);
        StringBuffer str = new StringBuffer();
        String strr = "";
        for (Map.Entry<String, Object> entry : resultMap.entrySet()){
            str.append(entry.getKey()+"="+entry.getValue());
            str.append("&");
        }
        if (str.length()>1){
            strr = str.toString().substring(0,str.toString().length()-1);
        }
        log.info(strr);

        String md5 = MD5Util.crypt(strr);
        String signstr = RSASignature.sign(md5,privateKey);
        return signstr;

    }


    private String cameoAttributes(List<OrderDto> list){
        Collections.sort(list);
        StringBuffer str = new StringBuffer();
        String strr = "";
        str.append("[");
        for (int i = 0; i < list.size(); i++) {
            str.append("bet_option_id="+list.get(i).getBetOptionId());
            str.append("&");
            str.append("odd="+list.get(i).getOdd());
            str.append("&");
        }
        if (str.length()>1){
            strr = str.toString().substring(0,str.toString().length()-1);
        }
        strr+="]";
        return strr;
    }

    public Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, Object> sortMap = new TreeMap<String, Object>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }



    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ,rollbackFor = Exception.class)
    public CommonResult<List<BetOrderBean>> cameoOrder(UserBean user, OrderBetDto orderBetDto) {
        /**
         * 1.纠正 赔率
         * 2.判断 余额是否充足
         * 3.预扣冻结
         * 4.增加订单
         * 5.增加流水
         * 6.发送下注
         * 7.扣钱
         */
        try {

            List<OrderDto> orderDtoList = orderBetDto.getOrders();

            BigDecimal totalAmount = new BigDecimal(orderBetDto.getTotalAmount());

            BigDecimal totalOdds = new BigDecimal(1);

            UserBalanceBean userBalanceBean = null;

            List<BetOrderBean> resultList = new ArrayList<>();
            long startTime = new Date().getTime();

            Integer[] dataIds = new Integer[orderDtoList.size()];
            if (CollectionUtils.isEmpty(orderDtoList)) {
                return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
            }
            for (int i = 0; i < orderDtoList.size(); i++) {
                BetOptionBean betOptionBean = betOptionBeanMapper.selectById(orderDtoList.get(i).getBetOptionId());
                //orderDtoList.get(i).setOdd(betOptionBean.getOdd());
                if (betOptionBean == null) {
                    return ResultUtil.returnError(ResultStatus.BET_NOT_EXIST);//盘口不存在
                }
                BetTopicsBean betTopicsBean = betTopicsBeanMapper.selectById(betOptionBean.getBetDataId());
                if (betTopicsBean == null) {
                    return ResultUtil.returnError(ResultStatus.BET_NOT_EXIST);//盘口不存在
                }
                if (!betTopicsBean.getStatus().equals("default")) {
                    return ResultUtil.returnError(ResultStatus.BET_CANCEL);//盘口取消或者 已结算
                }

                if (!betTopicsBean.isSwitchStatus() || !betTopicsBean.isFinalSwitch()){
                    return ResultUtil.returnError(ResultStatus.BET_LOCK);
                }

                if (betOptionBean.getBetResult() != null && betOptionBean.getBetResult().intValue() != 0) {
                    return ResultUtil.returnError(ResultStatus.BET_FINISH);//盘口结束
                }
                if (!orderBetDto.isConfirm() && !new BigDecimal(betOptionBean.getOdd()).equals(orderDtoList.get(i).getOdd())) {
                    return ResultUtil.returnError(ResultStatus.ODD_IS_CHANGE);//赔率变化  返回前端重新接受
                }
                if (!betTopicsBean.isCameoOpening()) {
                    return ResultUtil.returnError(ResultStatus.BET_NOT_ALLOW_CAMEO);//不接受串关
                }
                orderDtoList.get(i).setOdd(new BigDecimal(betOptionBean.getOdd()));
                totalOdds = totalOdds.multiply(orderDtoList.get(i).getOdd());//计算赔率
                dataIds[i] = betTopicsBean.getDataId();

            }
            totalOdds = totalOdds.setScale(2, BigDecimal.ROUND_HALF_DOWN);
            orderBetDto.setOdds(totalOdds);
            /**
             * id锁  冻结金额
             */
            BigDecimal from = new BigDecimal(0);
            userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(user.getId());
            from = userBalanceBean.getAvailableCoin();
            if (userBalanceBean.getAvailableCoin().compareTo(totalAmount) < 0) {
                return ResultUtil.returnError(ResultStatus.NOT_ENOUGH_MONEY);//余额不足
            }
            int retry = 0;
            int x = frozen(userBalanceBean, totalAmount);
            while (x == 0 && retry<10) {
                userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(user.getId());
                from = userBalanceBean.getAvailableCoin();
                x = frozen(userBalanceBean, totalAmount);
                retry++;
            }
            if (retry==10){
                return ResultUtil.returnError(ResultStatus.NOT_ENOUGH_MONEY);
            }

            /**
             * 发送给b端请求
             * 成功 释放 冻结金额
             * 失败 返回 coin金额 a
             */

            String orderNo = null;
            if (StringUtils.isBlank(user.getApiId())){
                orderNo = OrderNoGeneratorUtil.getOrderNoByTime("SG");
            }else {
                orderNo = OrderNoGeneratorUtil.getOrderNoByTime(user.getApiId());
            }
            BetCameoOrderDto betOrderDto = new BetCameoOrderDto();
            betOrderDto.setAmount(totalAmount.intValue());

            betOrderDto.setTenantCustomerNo(user.getId().intValue());
            betOrderDto.setTenantOrderNo(orderNo);
            List<OrderDto> betOptionCameoList = BeanUtil.deepCopy(orderDtoList);

            for (int i = 0; i < betOptionCameoList.size(); i++) {
                betOptionCameoList.get(i).setExtra(null);
                betOptionCameoList.get(i).setAmount(null);
                betOptionCameoList.get(i).setOdd(betOptionCameoList.get(i).getOdd().setScale(2,BigDecimal.ROUND_HALF_DOWN));
            }
            betOrderDto.setBetOptionCameoOrdersAttributes(betOptionCameoList);

            BetOrderBean betOrderBean = processCameoOrder(orderBetDto, userBalanceBean, orderNo,  dataIds, orderBetDto.getCameo(),orderBetDto.getIp(),user);// 生成订单

            resultList.add(betOrderBean);
            //异步提交
            service.submit(new OrderCameoCallable(this,moneyRecordService,activeService,noticeService,betOrderDto,betOrderBean,userBalanceBean,totalAmount,from));

            return ResultUtil.returnSuccess(resultList);
        }catch (Exception e){
            log.error(e.toString());
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }
    }

    private Integer getInfluenceAmount(Integer amount ,String apiId){
        BusinessConfigBean businessConfigBean = businessConfigService.selectByApiId(apiId);
        int influenceAmount = amount;
        if (businessConfigBean!=null && businessConfigBean.getCoefficient()!=null){
            influenceAmount = amount*businessConfigBean.getCoefficient()/100;
            if (influenceAmount==0 && businessConfigBean.getCoefficient()!=0){
                influenceAmount = 1;
            }
        }
        return influenceAmount;
    }

    public static void main(String[] args) {
        Integer x = 22;
        System.out.println(x*10/100);
        System.out.println(x*3/100);
    }

    /**
     * 生成订单
     * @return
     */
    private BetOrderBean processOrder(OrderDto orderDto,UserBalanceBean userBalanceBean,String orderNo,Integer dataId,String cameo,GameResult gameDataBean,String ip,BetOptionBean betOptionBean,BetTopicsBean betTopicsBean,UserBean userBean){
        //BusinessConfigBean businessConfigBean = businessConfigService.selectByApiId(userBean.getApiId());
        int influenceAmount = getInfluenceAmount(orderDto.getAmount(),userBean.getApiId());
        BetOrderBean betOrderBean = new BetOrderBean();
        betOrderBean.setOrderType(Boolean.FALSE);
        betOrderBean.setBetOptionId(orderDto.getBetOptionId());
        betOrderBean.setCheckOdd(orderDto.getOdd());
        betOrderBean.setOdd(orderDto.getOdd());
        BigDecimal estimatedReward = new BigDecimal(orderDto.getAmount()).multiply(orderDto.getOdd());//预计获得
        betOrderBean.setEstimatedReward(estimatedReward);
        betOrderBean.setAccountCoin(userBalanceBean.getCoin());
        betOrderBean.setAmount(orderDto.getAmount());//实际金额
        betOrderBean.setInfluenceAmount(influenceAmount);
        betOrderBean.setRollbackLockCoin(new BigDecimal(orderDto.getAmount()));
        betOrderBean.setTenantUserNo(userBalanceBean.getUserId());
        betOrderBean.setTenantOrderNo(orderNo);
        betOrderBean.setWinLose(WinLoseStatus.NOTOPEN);
        betOrderBean.setCameo("单注");
        betOrderBean.setIp(ip);
        /*if (result.length()>100){
            result = result.substring(0,100);
        }
        betOrderBean.setFailResult(result);*/
        /*if (flag){
            betOrderBean.setStatus(OrderStatus.UNSETTLED);
        }else {
            betOrderBean.setEstimatedReward(new BigDecimal(betOrderBean.getAmount()));//失败返回金额
            betOrderBean.setStatus(OrderStatus.FAIL);
        }*/
        betOrderBean.setStatus(OrderStatus.CONFIRM);
        betOrderBean.setDataId(dataId);

        betOrderBean.setHedge(Boolean.FALSE);
        betOrderBeanMapper.insert(betOrderBean);

        /**
         * 插入明细详细
         */

        BetOrderDetailBean betOrderDetailBean = new BetOrderDetailBean();
        betOrderDetailBean.setBetOptionId(orderDto.getBetOptionId());
        betOrderDetailBean.setBetOrderId(betOrderBean.getId());
        betOrderDetailBean.setOdd(orderDto.getOdd());
        betOrderDetailBean.setWinLose(WinLoseStatus.NOTOPEN);
        betOrderDetailBean.setOrderType(Boolean.FALSE);
        betOrderDetailBean.setDataId(dataId);
        betOrderDetailBean.setGameId(gameDataBean.getGameId());

        /*if (flag){
            betOrderDetailBean.setStatus(OrderStatus.UNSETTLED);
        }else {
            betOrderDetailBean.setStatus(OrderStatus.FAIL);
        }*/
        betOrderDetailBean.setStatus(OrderStatus.CONFIRM);
        //JSONObject jsonObject = JSON.parseObject(orderDto.getExtra());
        //String vsDetail = jsonObject.getString("groupName")+" "+jsonObject.getJSONObject("curentTeams").getString("mark");
        //String vsDetail = orderDto.getExtra();
        String vsDetail = betTopicsBean.getGroupName()+" "+betOptionBean.getName();
        String vs = getVs(gameDataBean.getTeams());

        betOrderDetailBean.setVs(vs);
        betOrderDetailBean.setVsDetail(vsDetail);
        betOrderDetailBeanMapper.insert(betOrderDetailBean);
        List<BetOrderDetailBean> betOrderDetailBeanList = new ArrayList<>();
        betOrderDetailBean.setExtra(orderDto.getExtra());
        betOrderDetailBeanList.add(betOrderDetailBean);

        betOrderBean.setBetOrderDetails(betOrderDetailBeanList);


        return betOrderBean;
    }

    /*private String getVs(JSONObject jsonObject){
        JSONArray jsonArray = jsonObject.getJSONArray("teams");
        if (jsonArray.size()>0){
            String vs = jsonArray.getJSONObject(0).getString("abbr") + " VS " + jsonArray.getJSONObject(1).getString("abbr");
            return vs;
        }else {
            return "";
        }

    }*/

    private String getVs(List<GameTeamBean> list){
        if (list.size()>0){
            String vs = list.get(0).getAbbr() + " VS " + list.get(1).getAbbr();
            return vs;
        }else {
            return "";
        }

    }


    /**
     * 生成串投订单
     * @return
     */
    private BetOrderBean processCameoOrder(OrderBetDto orderBetDto,UserBalanceBean userBalanceBean,String orderNo,Integer[] dataIds,String cameo,String ip,UserBean userBean){
        int influenceAmount = getInfluenceAmount(orderBetDto.getTotalAmount(),userBean.getApiId());
        BetOrderBean betOrderBean = new BetOrderBean();
        //betOrderBean.setBetOptionId(orderBetDto.getBetOptionId());
        betOrderBean.setOrderType(Boolean.TRUE);
        betOrderBean.setCheckOdd(orderBetDto.getOdds());
        betOrderBean.setOdd(orderBetDto.getOdds());
        BigDecimal estimatedReward = new BigDecimal(orderBetDto.getTotalAmount()).multiply(orderBetDto.getOdds());//预计获得
        betOrderBean.setEstimatedReward(estimatedReward);
        betOrderBean.setAccountCoin(userBalanceBean.getCoin());
        betOrderBean.setAmount(orderBetDto.getTotalAmount());
        betOrderBean.setInfluenceAmount(influenceAmount);
        betOrderBean.setRollbackLockCoin(new BigDecimal(orderBetDto.getTotalAmount()));
        betOrderBean.setTenantUserNo(userBalanceBean.getUserId());
        betOrderBean.setTenantOrderNo(orderNo);
        betOrderBean.setCameo(orderBetDto.getOrders().size()+"串1");
        betOrderBean.setWinLose(WinLoseStatus.NOTOPEN);
        /*if (result.length()>100){
            result = result.substring(0,100);
        }
        betOrderBean.setFailResult(result);
        if (flag){
            betOrderBean.setStatus(OrderStatus.UNSETTLED);
        }else {
            betOrderBean.setStatus(OrderStatus.FAIL);
        }*/
        betOrderBean.setStatus(OrderStatus.CONFIRM);

        betOrderBean.setHedge(Boolean.FALSE);
        betOrderBean.setIp(ip);
        betOrderBeanMapper.insert(betOrderBean);

        /**
         * 插入串单详细
         */
        List<BetOrderDetailBean> list = new ArrayList<>();
        for (int i = 0; i < orderBetDto.getOrders().size(); i++) {
            BetOrderDetailBean betOrderDetailBean = new BetOrderDetailBean();
            betOrderDetailBean.setBetOptionId(orderBetDto.getOrders().get(i).getBetOptionId());
            betOrderDetailBean.setBetOrderId(betOrderBean.getId());
            betOrderDetailBean.setOdd(orderBetDto.getOrders().get(i).getOdd());
            betOrderDetailBean.setWinLose(WinLoseStatus.NOTOPEN);

            /*if (flag){
                betOrderDetailBean.setStatus(OrderStatus.UNSETTLED);
            }else {
                betOrderDetailBean.setStatus(OrderStatus.FAIL);
            }*/
            betOrderDetailBean.setStatus(OrderStatus.CONFIRM);
            betOrderDetailBean.setDataId(dataIds[i]);
            GameResult gameResult = gameDataBeanMapper.selectGameWithTeamCampainsResultById(dataIds[i]);
            betOrderDetailBean.setGameId(gameResult.getGameId());
            betOrderDetailBean.setOrderType(Boolean.TRUE);
            /*JSONObject jsonObject = JSON.parseObject(orderBetDto.getOrders().get(i).getExtra());
            String vsDetail = jsonObject.getString("groupName")+" "+jsonObject.getJSONObject("curentTeams").getString("mark");*/
            String vsDetail = betOptionService.getVsDetail(orderBetDto.getOrders().get(i).getBetOptionId());
            String vs = getVs(gameResult.getTeams());
            betOrderDetailBean.setVsDetail(vsDetail);
            //String vs = getVs(jsonObject);
            betOrderDetailBean.setVs(vs);
            betOrderDetailBeanMapper.insert(betOrderDetailBean);
            betOrderDetailBean.setExtra(orderBetDto.getOrders().get(i).getExtra());
            list.add(betOrderDetailBean);
        }
        betOrderBean.setBetOrderDetails(list);


        return betOrderBean;
    }

    /**
     * 冻结金额
     * @param userBalanceBean
     * @param totalAmount
     * @param flag
     * @return
     */
    private int frozen(UserBalanceBean userBalanceBean,BigDecimal totalAmount){
        return userBalanceBeanMapper.frozenBalance(userBalanceBean.getUserId(),totalAmount);
    }

    /**
     * 释放金额
     * @param userBalanceBean
     * @param totalAmount
     * @param flag
     * @return
     */
    private int unfrozen(UserBalanceBean userBalanceBean,BigDecimal totalAmount,boolean flag){
        userBalanceBean.setFrozenCoin(userBalanceBean.getFrozenCoin().subtract(totalAmount));//解除冻结
        if (!flag){
            //失败则返回金额 1.可用金额
            userBalanceBean.setAvailableCoin(userBalanceBean.getAvailableCoin().add(totalAmount));
            //userBalanceBean.setCoin(userBalanceBean.getCoin().add(totalAmount));
        }else{
            //成功后解除限额limit
            //userBalanceBean.setCoin(userBalanceBean.getCoin().add(totalAmount));
            BigDecimal limit = userBalanceBean.getLimitCoin().subtract(totalAmount);//解除限额
            if (limit.compareTo(BigDecimal.ZERO)<1){
                limit = BigDecimal.ZERO;
            }
            userBalanceBean.setLimitCoin(limit);
            //扣除总额
            userBalanceBean.setCoin(userBalanceBean.getCoin().subtract(totalAmount));

        }

        userBalanceBean.setUpdateTime(new Date());
        return userBalanceBeanMapper.updateById(userBalanceBean);
    }

    private String getChinese(String comment){
        Pattern pattern = Pattern.compile("[^\u4E00-\u9FA5]");
        //[\u4E00-\u9FA5]是unicode2的中文区间
        Matcher matcher = pattern.matcher(comment);
        return matcher.replaceAll("");
    }






}


