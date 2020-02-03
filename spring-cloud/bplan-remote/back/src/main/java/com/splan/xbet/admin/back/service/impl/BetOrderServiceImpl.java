package com.splan.xbet.admin.back.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.BetOrderBean;
import com.splan.xbet.admin.back.mappers.BetOrderBeanMapper;
import com.splan.xbet.admin.back.mappers.BetOrderDetailBeanMapper;
import com.splan.xbet.admin.back.mappers.UserMapper;
import com.splan.xbet.admin.back.service.IBetOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class BetOrderServiceImpl extends ServiceImpl<BetOrderBeanMapper, BetOrderBean> implements IBetOrderService {
//
//    @Autowired
//    private BetOrderBeanMapper betOrderBeanMapper;
//
//    @Autowired
//    private BetOrderDetailBeanMapper betOrderDetailBeanMapper;
//
//    @Autowired
//    private IMoneyRecordService moneyRecordService;
//
//    @Autowired
//    private UserBalanceBeanMapper userBalanceBeanMapper;
//
//    @Autowired
//    private BetSettleRecordMapper betSettleRecordMapper;
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Autowired
//    private MemberInterestsBeanMapper memberInterestsBeanMapper;
//
//    @Autowired
//    private IBetOrderService betOrderService;
//
//    @Autowired
//    private IActiveService activeService;
//
//    @Autowired
//    private INoticeService noticeService;
//
//    @Autowired
//    private ProxyConfigMapper proxyConfigMapper;
//
//    @Autowired
//    private ProxyLogMapper proxyLogMapper;
//
//    private final String BATCH_URL = "/user/batchnotifyorder";
//
//    private final String SINGLE_URL = "/user/notifyorder";
//
//    private final String ROLLBACK_URL = "/user/notifyrollback";
//
//    private ExecutorService service = Executors.newFixedThreadPool(9);
//
//    @Override
//    public CommonResult<IPage<BetOrderBean>> selectByType(UserBean userBean,Integer type, String orderDate, Page page) {
//        String orderStatus = OrderStatus.FAIL.name();
//        if (type==0){
//            orderStatus = "'"+OrderStatus.UNSETTLED.name()+"'";
//        }else {
//            //orderStatus = "'"+OrderStatus.SETTLED.name()+"','"+OrderStatus.FAIL.name()+"'";
//            orderStatus = "'"+OrderStatus.SETTLED.name()+"'";
//        }
//        List<BetOrderBean> list = betOrderBeanMapper.selectByType(page,orderStatus,orderDate,userBean.getId());
//        page.setRecords(list);
//        return ResultUtil.returnSuccess(page);
//
//    }
//
//    /**
//     * 结算结果
//     * @param betOptionBean
//     * @return
//     */
//    @Override
//    //@Transactional(isolation = Isolation.SERIALIZABLE,rollbackFor = Exception.class)
//    public Integer checkResult(List<BetOptionBean> betOptionBeans) {
//        for (int x = 0; x < betOptionBeans.size(); x++) {
//            BetOptionBean betOptionBean = betOptionBeans.get(x);
//            synchronized ("result:"+betOptionBean.getId().toString().intern()){
//                /**
//                 * 1.胜利 2.失败 3.取消
//                 */
//                log.info("开始结算"+betOptionBean.getId());
//                WinLoseStatus winLoseStatus = WinLoseStatus.NOTOPEN;
//                if (betOptionBean.getBetResult().intValue()==1){
//                    winLoseStatus = WinLoseStatus.WIN;
//
//                }else if(betOptionBean.getBetResult().intValue()==2){
//                    winLoseStatus = WinLoseStatus.LOSE;
//                }else if (betOptionBean.getBetResult().intValue()==3){
//                    winLoseStatus = WinLoseStatus.CANCEL;
//                }
//                List<BetOrderDetailBean> betOrderDetailBeans = betOrderDetailBeanMapper.selectByBetOptionId(betOptionBean.getId(),OrderStatus.UNSETTLED);
//                BetSettleRecord betSettleRecord = new BetSettleRecord();
//                betSettleRecord.setBetDataId(betOptionBean.getBetDataId());
//                betSettleRecord.setBetOptionId(betOptionBean.getId());
//                betSettleRecord.setStatus(OrderStatus.UNSETTLED);
//                betSettleRecord.setWinLose(winLoseStatus);
//
//                betSettleRecordMapper.insert(betSettleRecord);
//
//                for (int i = 0; i < betOrderDetailBeans.size(); i++) {
//
//                    if (betOptionBean.getBetResult().intValue()==1){
//                        winSettle(betOrderDetailBeans.get(i));
//
//                    }else if(betOptionBean.getBetResult().intValue()==2){
//                        loseSettle(betOrderDetailBeans.get(i));
//                        //置为失败
//                    }else if (betOptionBean.getBetResult().intValue()==3){
//                        cancleSettle(betOrderDetailBeans.get(i));
//                        //退钱
//                    }
//
//                }
//                betSettleRecord.setSettleCount(betOrderDetailBeans.size());
//                betSettleRecord.setStatus(OrderStatus.SETTLED);
//                betSettleRecordMapper.updateById(betSettleRecord);
//                log.info("结算完成"+betOptionBean.getId()+"=="+betOrderDetailBeans.size());
//                syncOrder(betOptionBean);
//                log.info("同步到运营方"+betOptionBean.getId());
//            }
//        }
//        return betOptionBeans.size();
//
//    }
//
//    /**
//     * 历史未结算的订单 补充
//     * @return
//     */
//    @Override
//    public Integer checkHistoryResult() {
//
//        List<BetOrderDetailBean> betOrderDetailBeanList = betOrderDetailBeanMapper.selectByYesterday();
//        if (betOrderDetailBeanList.size()>0){
//            for (int i = 0; i < betOrderDetailBeanList.size(); i++) {
//                BetOrderDetailBean e = betOrderDetailBeanList.get(i);
//                if (e.getBetResult().intValue() == 1) {
//                    winSettle(e);
//
//                } else if (e.getBetResult().intValue() == 2) {
//                    loseSettle(e);
//                    //置为失败
//                } else if (e.getBetResult().intValue() == 3) {
//                    cancleSettle(e);
//                    //退钱
//                }
//            }
//        }
//        return betOrderDetailBeanList.size();
//    }
//
//    @Override
//    public Integer syncToOrder() {
//        List<BetOrderBean> list = betOrderBeanMapper.selectByYesterday();
//        postOrder(list);
//        return list.size();
//    }
//
//
//    private void postOrder(List<BetOrderBean> list){
//        if (list.size()==0){
//            return;
//        }
//        try {
//            Map<String,List<BetOrderBean>> map = CollectionTools.classify(list,"apiId");
//            map.forEach((key,value)->{
//                try{
//                    ProxyConfig proxyConfig = proxyConfigMapper.selectProxyConfigByClientId(key);
//                    if (null!=proxyConfig && StringUtils.isNotBlank(proxyConfig.getUrl())){
//                        //if (value.size()<50){
//                        if (proxyConfig.getPushConfig()==0){
//                            postBatch(key,value,proxyConfig);
//                        }else {
//                            log.info("单个推送");
//                            service.submit(new Runnable() {
//                                @Override
//                                public void run() {
//                                    singlePost(key,value,proxyConfig);
//                                }
//                            });
//
//                        }
//
//                    }else {
//                        log.error("商户号："+key+"未配置推送地址");
//                    }
//                }catch (Exception e){
//                    log.error("推送失败");
//                }
//
//
//            });
//        } catch (Exception e) {
//            log.error("转换失败");
//        }
//    }
//
//    /**
//     * 同步到运营方
//     */
//    private void syncOrder(BetOptionBean betOptionBean){
//        List<BetOrderBean> list = betOrderBeanMapper.selectByOptionId(betOptionBean.getId());
//        postOrder(list);
//
//    }
//
//    /**
//     * 单个推送
//     * @param key
//     * @param value
//     * @param proxyConfig
//     * @return
//     */
//    private String singlePost(String key,List<BetOrderBean> value,ProxyConfig proxyConfig){
//
//        //List<SimpleBetNotifyDto> list1 = new ArrayList<>();
//        for (int i = 0; i < value.size(); i++) {
//            log.info(value.get(i).toString()+"单个推送");
//            SimpleBetNotifyDto betNotifyDto = new SimpleBetNotifyDto();
//            betNotifyDto.setClientId(key);
//            betNotifyDto.setOdd(value.get(i).getOdd());
//            betNotifyDto.setCheckodd(value.get(i).getCheckOdd());
//            betNotifyDto.setExtraId(value.get(i).getExtraId());
//            betNotifyDto.setOrderno(value.get(i).getTenantOrderNo());
//            betNotifyDto.setRewardCoin(value.get(i).getEstimatedReward());
//            betNotifyDto.setStatus(value.get(i).getWinLose());
//            betNotifyDto.setOrderCoin(value.get(i).getAmount());
//            if (value.get(i).getCameo().equals("单注")){
//                betNotifyDto.setOrderType("1");
//            }else {
//                betNotifyDto.setOrderType(value.get(i).getCameo().replace("串1",""));
//            }
//
//            betNotifyDto.setTimestamp(getTimeStamp());
//            String sign = SignUtil.getSign(betNotifyDto,proxyConfig.getClientSecret());
//            betNotifyDto.setSign(sign);
//            String data = JSON.toJSONString(betNotifyDto);
//            CommonResult<SingleBetResult> commonResult = OKHttpUtil.httpPostOperate(proxyConfig.getUrl()+SINGLE_URL,data,true);
//            if (commonResult.isSuccess()) {
//                value.get(i).setSyncFlag(SyncStatus.BET_SYNC);
//                betOrderBeanMapper.updateById(value.get(i));
//            }
//
//        }
//        return null;
//    }
//
//    /**
//     * 回滚推送
//     * @param key
//     * @param value
//     * @param proxyConfig
//     * @return
//     */
//    private String rollbackPost(List<BetOrderBean> value){
//
//        //List<SimpleBetNotifyDto> list1 = new ArrayList<>();
//        for (int i = 0; i < value.size(); i++) {
//            ProxyConfig proxyConfig = proxyConfigMapper.getProxyConfigByUserId(value.get(i).getTenantUserNo());
//            if (proxyConfig!=null) {
//
//                log.info(value.get(i).toString() + "回滚推送");
//                RollbackBetNotifyDto betNotifyDto = new RollbackBetNotifyDto();
//                betNotifyDto.setClientId(proxyConfig.getClientId());
//                UserBean userBean = userMapper.selectById(value.get(i).getTenantUserNo());
//                betNotifyDto.setExtraId(userBean.getExtraId());
//                betNotifyDto.setOrderno(value.get(i).getTenantOrderNo());
//                betNotifyDto.setRollbackCoin(new BigDecimal(value.get(i).getAmount()));
//                betNotifyDto.setOrderCoin(value.get(i).getAmount());
//                betNotifyDto.setTimestamp(getTimeStamp());
//                String sign = SignUtil.getSign(betNotifyDto, proxyConfig.getClientSecret());
//                betNotifyDto.setSign(sign);
//                String data = JSON.toJSONString(betNotifyDto);
//                CommonResult<SingleBetResult> commonResult = OKHttpUtil.httpPostOperate(proxyConfig.getUrl() + ROLLBACK_URL, data, true);
//                if (commonResult.isSuccess()) {
//                    value.get(i).setSyncFlag(SyncStatus.SYNC);
//                    betOrderBeanMapper.updateById(value.get(i));
//                }
//            }
//
//        }
//        return null;
//    }
//
//    /**
//     * 批量推送
//     * @param key
//     * @param value
//     * @param proxyConfig
//     * @return
//     */
//    private String postBatch(String key,List<BetOrderBean> value,ProxyConfig proxyConfig){
//        BetBatchNotifyDto betBatchNotifyDto = new BetBatchNotifyDto();
//        betBatchNotifyDto.setClientId(key);
//        betBatchNotifyDto.setTimestamp(getTimeStamp());
//        betBatchNotifyDto.setTotal(value.size());
//        String batchOrderNo = OrderNoGeneratorUtil.getOrderNoByTime(key+"batch");
//        betBatchNotifyDto.setSerialNo(batchOrderNo);
//        if (proxyConfig.getEncode()==0){
//            String sign = SignUtil.getSign(betBatchNotifyDto,proxyConfig.getClientSecret());
//            betBatchNotifyDto.setSign(sign);
//        }
//        List<BetNotifyDto> list1 = new ArrayList<>();
//        for (int i = 0; i < value.size(); i++) {
//            BetNotifyDto betNotifyDto = new BetNotifyDto();
//            betNotifyDto.setClientId(key);
//            betNotifyDto.setOdd(value.get(i).getOdd());
//            betNotifyDto.setCheckodd(value.get(i).getCheckOdd());
//            betNotifyDto.setExtraId(value.get(i).getExtraId());
//            betNotifyDto.setOrderno(value.get(i).getTenantOrderNo());
//            betNotifyDto.setRewardCoin(value.get(i).getEstimatedReward());
//            betNotifyDto.setStatus(value.get(i).getWinLose());
//            betNotifyDto.setOrderCoin(value.get(i).getAmount());
//            betNotifyDto.setOrderType(value.get(i).getCameo());
//            list1.add(betNotifyDto);
//
//        }
//        betBatchNotifyDto.setList(list1);
//        String data = JSON.toJSONString(betBatchNotifyDto);
//        CommonResult<BatchBetResult> commonResult = OKHttpUtil.httpPostOperate(proxyConfig.getUrl()+BATCH_URL,data,true);
//        ProxyLog proxyLog = new ProxyLog();
//        proxyLog.setClientId(key);
//        proxyLog.setUrl(proxyConfig.getUrl());
//        proxyLog.setParam(data);
//        proxyLog.setRetries(0);
//        proxyLog.setType("batch");
//        if (commonResult.isSuccess()){
//            value.forEach((e)->{
//                e.setSyncFlag(SyncStatus.BET_SYNC);
//                betOrderBeanMapper.updateById(e);
//            });
//            proxyLog.setStatus("success");
//            proxyLog.setResponse(commonResult.toString());
//        }else {
//            proxyLog.setStatus("fail");
//        }
//        proxyLogMapper.insert(proxyLog);
//
//        return null;
//    }
//
//
//    /**
//     * 胜利结算
//     */
//    private void winSettle(BetOrderDetailBean betOrderDetailBean){
//
//        betOrderDetailBean.setWinLose(WinLoseStatus.WIN);
//        betOrderDetailBean.setStatus(OrderStatus.SETTLED);
//        betOrderDetailBeanMapper.updateById(betOrderDetailBean);
//
//        if (betOrderDetailBean.getOrderType()==Boolean.TRUE){
//            if (betOrderDetailBean.getBetOrderBean().getStatus().equals(OrderStatus.UNSETTLED)){
//                //未结算状态
//                QueryWrapper<BetOrderDetailBean> queryWrapper = new QueryWrapper<>();
//                queryWrapper.eq("bet_order_id",betOrderDetailBean.getBetOrderBean().getId())
//                        .eq("status",OrderStatus.UNSETTLED);
//                Integer count = betOrderDetailBeanMapper.selectCount(queryWrapper);
//                if (count==0){//最后一个结算,更新主表数据
//                    betOrderDetailBean.getBetOrderBean().setWinLose(WinLoseStatus.WIN);
//                    betOrderDetailBean.getBetOrderBean().setStatus(OrderStatus.SETTLED);
//                    /**
//                     * 增加处理 是否有取消的盘口
//                     * 重新计算预计返回
//                     */
//                    queryWrapper = new QueryWrapper<>();
//                    queryWrapper.eq("bet_order_id",betOrderDetailBean.getBetOrderBean().getId())
//                            .eq("win_lose",WinLoseStatus.WIN);
//                    List<BetOrderDetailBean> list = betOrderDetailBeanMapper.selectList(queryWrapper);
//                    BigDecimal odds = new BigDecimal(1);
//                    for (int i = 0; i < list.size(); i++) {
//                        odds = odds.multiply(list.get(i).getOdd());
//                    }
//                    odds = odds.setScale(2,BigDecimal.ROUND_HALF_DOWN);
//                    betOrderDetailBean.getBetOrderBean().setOdd(odds);
//                    betOrderDetailBean.getBetOrderBean().setEstimatedReward(odds.multiply(new BigDecimal(betOrderDetailBean.getBetOrderBean().getAmount())).setScale(2,BigDecimal.ROUND_HALF_DOWN));
//
//                    betOrderBeanMapper.updateById(betOrderDetailBean.getBetOrderBean());
//                    process(betOrderDetailBean.getBetOrderBean(),AlgorithmType.ADD,true);
//                    activeService.afterCheck(betOrderDetailBean.getBetOrderBean().getTenantUserNo());//每日竞猜正确一次
//                    noticeService.sendNotice("投注正确",betOrderDetailBean.getBetOrderBean().getTenantOrderNo(),betOrderDetailBean.getBetOrderBean().getEstimatedReward(),"",betOrderDetailBean.getBetOrderBean().getCreateTime(),null,betOrderDetailBean.getBetOrderBean().getId(),betOrderDetailBean.getBetOrderBean().getTenantUserNo(),MsgType.win);
//                }
//            }
//        }else {
//            betOrderDetailBean.getBetOrderBean().setWinLose(WinLoseStatus.WIN);
//            betOrderDetailBean.getBetOrderBean().setStatus(OrderStatus.SETTLED);
//            betOrderBeanMapper.updateById(betOrderDetailBean.getBetOrderBean());
//            process(betOrderDetailBean.getBetOrderBean(),AlgorithmType.ADD,true);
//            activeService.afterCheck(betOrderDetailBean.getBetOrderBean().getTenantUserNo());//每日竞猜正确一次
//            noticeService.sendNotice("投注正确",betOrderDetailBean.getBetOrderBean().getTenantOrderNo(),betOrderDetailBean.getBetOrderBean().getEstimatedReward(),"",betOrderDetailBean.getBetOrderBean().getCreateTime(),null,betOrderDetailBean.getBetOrderBean().getId(),betOrderDetailBean.getBetOrderBean().getTenantUserNo(),MsgType.win);
//        }
//
//
//    }
//
//    private String getTimeStamp(){
//        Date date = new Date();
//        //设置要获取到什么样的时间
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        //获取String类型的时间
//        String createdate = sdf.format(date);
//        return createdate;
//    }
//
//    /**
//     * 失败结算
//     */
//    private void loseSettle(BetOrderDetailBean betOrderDetailBean){
//
//        betOrderDetailBean.setWinLose(WinLoseStatus.LOSE);
//        betOrderDetailBean.setStatus(OrderStatus.SETTLED);
//        betOrderDetailBeanMapper.updateById(betOrderDetailBean);
//        if (betOrderDetailBean.getOrderType()==Boolean.TRUE){
//            if (betOrderDetailBean.getBetOrderBean().getStatus().equals(OrderStatus.UNSETTLED)){
//                betOrderDetailBean.getBetOrderBean().setWinLose(WinLoseStatus.LOSE);
//                betOrderDetailBean.getBetOrderBean().setStatus(OrderStatus.SETTLED);
//                betOrderDetailBean.getBetOrderBean().setEstimatedReward(new BigDecimal(0));
//                betOrderBeanMapper.updateById(betOrderDetailBean.getBetOrderBean());
//
//            }
//        }else {
//            betOrderDetailBean.getBetOrderBean().setWinLose(WinLoseStatus.LOSE);
//            betOrderDetailBean.getBetOrderBean().setStatus(OrderStatus.SETTLED);
//            betOrderDetailBean.getBetOrderBean().setEstimatedReward(new BigDecimal(0));
//            betOrderBeanMapper.updateById(betOrderDetailBean.getBetOrderBean());
//        }
//        if(betOrderDetailBean.getBetOrderBean().getCheckOdd().compareTo(new BigDecimal(1.5))>-1){
//            UserBalanceBean userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(betOrderDetailBean.getBetOrderBean().getTenantUserNo());
//            //增加累计下注额
//            userBalanceBean.setOrderCoin(userBalanceBean.getOrderCoin().add(new BigDecimal(betOrderDetailBean.getBetOrderBean().getAmount())));
//            userBalanceBeanMapper.updateById(userBalanceBean);
//        }
//
//    }
//
//    /**
//     * 取消结算
//     */
//    private void cancleSettle(BetOrderDetailBean betOrderDetailBean){
//        cancleSettle(betOrderDetailBean,"盘口取消");
//    }
//
//    private void cancleSettle(BetOrderDetailBean betOrderDetailBean,String title){
//        betOrderDetailBean.setWinLose(WinLoseStatus.CANCEL);
//        betOrderDetailBean.setStatus(OrderStatus.SETTLED);
//        betOrderDetailBean.setCancelReason("盘口取消");
//        betOrderDetailBeanMapper.updateById(betOrderDetailBean);
//        if (betOrderDetailBean.getOrderType()==Boolean.TRUE){
//            if (betOrderDetailBean.getBetOrderBean().getStatus().equals(OrderStatus.UNSETTLED)){
//                //取消
//                QueryWrapper<BetOrderDetailBean> queryWrapper = new QueryWrapper<>();
//                queryWrapper.eq("bet_order_id",betOrderDetailBean.getBetOrderBean().getId())
//                        .ne("win_lose",WinLoseStatus.CANCEL);
//                Integer count = betOrderDetailBeanMapper.selectCount(queryWrapper);
//                if (count==0){//全部取消
//                    betOrderDetailBean.getBetOrderBean().setWinLose(WinLoseStatus.CANCEL);
//                    betOrderDetailBean.getBetOrderBean().setStatus(OrderStatus.SETTLED);
//                    betOrderDetailBean.getBetOrderBean().setEstimatedReward(new BigDecimal(betOrderDetailBean.getBetOrderBean().getAmount()));
//                    betOrderDetailBean.getBetOrderBean().setFailResult(title);
//                    betOrderBeanMapper.updateById(betOrderDetailBean.getBetOrderBean());
//                    process(betOrderDetailBean.getBetOrderBean(),AlgorithmType.ADD,false);
//                    noticeService.sendNotice("退款",betOrderDetailBean.getBetOrderBean().getTenantOrderNo(),betOrderDetailBean.getBetOrderBean().getEstimatedReward(),title,betOrderDetailBean.getBetOrderBean().getUpdateTime(),null,betOrderDetailBean.getBetOrderBean().getId(),betOrderDetailBean.getBetOrderBean().getTenantUserNo(),MsgType.refund);
//                }else {
//                    //重新计算赔率
//                    //取消
//                    QueryWrapper<BetOrderDetailBean> queryWrapper2 = new QueryWrapper<>();
//                    queryWrapper.eq("bet_order_id",betOrderDetailBean.getBetOrderBean().getId())
//                            .eq("status",OrderStatus.UNSETTLED);
//                    Integer count2 = betOrderDetailBeanMapper.selectCount(queryWrapper2);
//                    if (count2==0){
//                        //重新结算
//                        betOrderDetailBean.getBetOrderBean().setWinLose(WinLoseStatus.WIN);
//                        betOrderDetailBean.getBetOrderBean().setStatus(OrderStatus.SETTLED);
//                        /**
//                         * 增加处理 是否有取消的盘口
//                         * 重新计算预计返回
//                         */
//                        queryWrapper = new QueryWrapper<>();
//                        queryWrapper.eq("bet_order_id",betOrderDetailBean.getBetOrderBean().getId())
//                                .eq("win_lose",WinLoseStatus.WIN);
//                        List<BetOrderDetailBean> list = betOrderDetailBeanMapper.selectList(queryWrapper);
//                        BigDecimal odds = new BigDecimal(1);
//                        for (int i = 0; i < list.size(); i++) {
//                            odds = odds.multiply(list.get(i).getOdd());
//                        }
//                        odds = odds.setScale(2,BigDecimal.ROUND_HALF_DOWN);
//                        betOrderDetailBean.getBetOrderBean().setOdd(odds);
//                        betOrderDetailBean.getBetOrderBean().setEstimatedReward(odds.multiply(new BigDecimal(betOrderDetailBean.getBetOrderBean().getAmount())).setScale(2,BigDecimal.ROUND_HALF_DOWN));
//
//                        betOrderBeanMapper.updateById(betOrderDetailBean.getBetOrderBean());
//                        process(betOrderDetailBean.getBetOrderBean(),AlgorithmType.ADD,true);
//                        activeService.afterCheck(betOrderDetailBean.getBetOrderBean().getTenantUserNo());
//                        noticeService.sendNotice("投注正确",betOrderDetailBean.getBetOrderBean().getTenantOrderNo(),betOrderDetailBean.getBetOrderBean().getEstimatedReward(),"",betOrderDetailBean.getBetOrderBean().getCreateTime(),null,betOrderDetailBean.getBetOrderBean().getId(),betOrderDetailBean.getBetOrderBean().getTenantUserNo(),MsgType.win);
//                    }
//                }
//
//            }
//        }else {
//            betOrderDetailBean.getBetOrderBean().setWinLose(WinLoseStatus.CANCEL);
//            betOrderDetailBean.getBetOrderBean().setStatus(OrderStatus.SETTLED);
//            betOrderDetailBean.getBetOrderBean().setEstimatedReward(new BigDecimal(betOrderDetailBean.getBetOrderBean().getAmount()));
//            betOrderDetailBean.getBetOrderBean().setFailResult(title);
//            betOrderBeanMapper.updateById(betOrderDetailBean.getBetOrderBean());
//            process(betOrderDetailBean.getBetOrderBean(),AlgorithmType.ADD,false);
//            noticeService.sendNotice("退款",betOrderDetailBean.getBetOrderBean().getTenantOrderNo(),betOrderDetailBean.getBetOrderBean().getEstimatedReward(),title,betOrderDetailBean.getBetOrderBean().getUpdateTime(),null,betOrderDetailBean.getBetOrderBean().getId(),betOrderDetailBean.getBetOrderBean().getTenantUserNo(),MsgType.refund);
//        }
//    }
//
//
//    /**
//     * 资金操作
//     * @param betOrderBean
//     * @param userId
//     * @param amount
//     * @param algorithmType
//     */
//    private void process(BetOrderBean betOrderBean, AlgorithmType algorithmType,boolean flag){
//        //synchronized (betOrderBean.getTenantUserNo().toString().intern()){
//            UserBalanceBean userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(betOrderBean.getTenantUserNo());
//            BigDecimal from = userBalanceBean.getCoin();
//            int x = changeCoin(userBalanceBean,betOrderBean,algorithmType,flag);
//            while (x==0){
//                userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(betOrderBean.getTenantUserNo());
//                from = userBalanceBean.getCoin();
//                x = changeCoin(userBalanceBean,betOrderBean,algorithmType,flag);
//            }
//            MoneyAbleType moneyAbleType = MoneyAbleType.BET_REWARD;
//            if (!flag){
//                moneyAbleType = MoneyAbleType.BET_CANCEL;
//            }
//            moneyRecordService.createRecord(betOrderBean.getId(),userBalanceBean.getId(), moneyAbleType,from,betOrderBean.getEstimatedReward(), algorithmType,"结算获得",userBalanceBean.getUserId());
//        //}
//
//        if (flag){
//            memberGrow(betOrderBean.getTenantUserNo());
//        }
//
//
//    }
//
//
//    /**
//     * 资金回滚操作
//     * 适用比赛结果判断错误情况下
//     * @param betOrderBean
//     * @param userId
//     * @param amount
//     * @param algorithmType
//     */
//    private void unprocess(BetOrderBean betOrderBean, AlgorithmType algorithmType,boolean flag){
//        UserBalanceBean userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(betOrderBean.getTenantUserNo());
//        BigDecimal from = userBalanceBean.getCoin();
//        int x = unchangeCoin(userBalanceBean,betOrderBean,algorithmType,flag);
//        while (x==0){
//            userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(betOrderBean.getTenantUserNo());
//            from = userBalanceBean.getCoin();
//            x = unchangeCoin(userBalanceBean,betOrderBean,algorithmType,flag);
//        }
//        MoneyAbleType moneyAbleType = MoneyAbleType.BET_RECONFIRM;
//        BigDecimal es = betOrderBean.getEstimatedReward();
//        if (betOrderBean.getWinLose().equals(WinLoseStatus.CANCEL)){
//            es = new BigDecimal(betOrderBean.getAmount());
//        }
//        moneyRecordService.createRecord(betOrderBean.getId(),userBalanceBean.getId(), moneyAbleType,from,es, algorithmType,"盘口结果回滚",userBalanceBean.getUserId());
//
//    }
//
//    /**
//     * 会员激活
//     */
//    @Transactional(rollbackFor = Exception.class)
//    private void memberGrow(Long userId){
//        try{
//            UserBean userBean = userMapper.selectById(userId);
//            if (userBean!=null && !userBean.getStatus().equals(Status.DISABLE) && userBean.getMemberStatus().equals(Status.LOCK) && userBean.getMemberLevel()>0){
//                //会员权益待激活状态
//                MemberInterestsBean memberInterestsBean = memberInterestsBeanMapper.selectById(userBean.getMemberLevel());
//                Integer orderCoin = memberInterestsBean.getStayOrderCoin();//保级下注额
//                //查询历史订单
//                QueryWrapper<BetOrderBean> queryWrapper = new QueryWrapper<>();
//                queryWrapper.apply("date_format(create_time,'%Y-%m')","date_format(now(),'%Y-%m')");
//                queryWrapper.ge("odd",1.5);
//                queryWrapper.eq("status", OrderStatus.SETTLED);
//                queryWrapper.eq("tenant_user_no",userId);
//                queryWrapper.select("sum(amount) as a");
//                //queryWrapper.groupBy("tenant_user_no");
//                Map<String,Object> m = betOrderService.getMap(queryWrapper);
//                Integer sum = Integer.valueOf(m.get("a").toString());
//                if (sum>orderCoin){
//                    //重新激活会员权限
//                    userBean.setMemberStatus(Status.ENABLE);
//                    userMapper.updateById(userBean);
//                }
//
//            }
//        }catch (Exception e){
//            log.error("会员权益升级错误"+e.toString());
//        }
//
//    }
//
//    /**
//     * 更新金额，回滚用
//     * @param userBalanceBean
//     * @param totalAmount
//     * @param flag true 订单正确
//     * @return
//     */
//    private int unchangeCoin(UserBalanceBean userBalanceBean,BetOrderBean betOrderBean, AlgorithmType algorithmType,boolean flag){
//
//        log.info("当前版本号:"+userBalanceBean.getVersion());
//        BigDecimal from = userBalanceBean.getCoin();
//        if (algorithmType.equals(AlgorithmType.SUB)){
//            if (betOrderBean.getWinLose().equals(WinLoseStatus.WIN)){
//                userBalanceBean.setAvailableCoin(userBalanceBean.getAvailableCoin().subtract(betOrderBean.getEstimatedReward()));
//                userBalanceBean.setCoin(userBalanceBean.getCoin().subtract(betOrderBean.getEstimatedReward()));
//            }else if (betOrderBean.getWinLose().equals(WinLoseStatus.CANCEL)){
//                userBalanceBean.setAvailableCoin(userBalanceBean.getAvailableCoin().subtract(new BigDecimal(betOrderBean.getAmount())));
//                userBalanceBean.setCoin(userBalanceBean.getCoin().subtract(new BigDecimal(betOrderBean.getAmount())));
//            }
//
//        }
//        //会员系统减少
//        if (flag && betOrderBean.getOdd().compareTo(new BigDecimal(1.5))>-1){
//            //减少累计下注额
//            userBalanceBean.setOrderCoin(userBalanceBean.getOrderCoin().subtract(new BigDecimal(betOrderBean.getAmount())));
//        }
//        return userBalanceBeanMapper.updateById(userBalanceBean);
//    }
//
//    /**
//     * 更新金额
//     * @param userBalanceBean
//     * @param totalAmount
//     * @param flag true 订单正确
//     * @return
//     */
//    private int changeCoin(UserBalanceBean userBalanceBean,BetOrderBean betOrderBean, AlgorithmType algorithmType,boolean flag){
//
//        log.info("当前版本号:"+userBalanceBean.getVersion());
//        BigDecimal from = userBalanceBean.getCoin();
//        if (algorithmType.equals(AlgorithmType.ADD)){
//            userBalanceBean.setAvailableCoin(userBalanceBean.getAvailableCoin().add(betOrderBean.getEstimatedReward()));
//            userBalanceBean.setCoin(userBalanceBean.getCoin().add(betOrderBean.getEstimatedReward()));
//        }
//        //会员系统增加
//        if (flag && betOrderBean.getOdd().compareTo(new BigDecimal(1.5))>0){
//            //增加累计下注额
//            userBalanceBean.setOrderCoin(userBalanceBean.getOrderCoin().add(new BigDecimal(betOrderBean.getAmount())));
//        }
//        return userBalanceBeanMapper.updateById(userBalanceBean);
//    }
//
//
//    /**
//     * 滚动显示
//     * @param orderDate
//     * @param page
//     * @return
//     */
//    @Override
//    public CommonResult<List<BetOrderResult>> rollOrderList() {
//        List<BetOrderResult> list = betOrderBeanMapper.selectRollOrderList();
//        /**
//         *
//         */
//        int max = 100;
//        int size = list.size();
//        for (int i = 0; i < list.size(); i++) {
//            list.get(i).setMobile(RandomValue.masaike(list.get(i).getMobile()));
//        }
//        if (size<100){
//            for (int i = 0; i < 100-size; i++) {
//                BetOrderResult betOrderResult = new BetOrderResult();
//                betOrderResult.setMobile(RandomValue.getTel());
//                betOrderResult.setAmount(RandomValue.getOrderNum(1,200));
//                betOrderResult.setCreateTime(RandomValue.randomDate(new Date()));
//                list.add(betOrderResult);
//            }
//        }
//        Collections.sort(list);
//
//        return ResultUtil.returnSuccess(list);
//    }
//
//
//    @Override
//    public CommonResult<IPage<BetOrderBean>> selectAll(JSONObject jsonObject, Page page) {
//        List<BetOrderBean> list = betOrderBeanMapper.selectByDate(page,jsonObject);
//        page.setRecords(list);
//        return ResultUtil.returnSuccess(page);
//    }
//
//    @Override
//    public BigDecimal getTodayOrderAmount() {
//        return betOrderBeanMapper.getTodayOrderAmount();
//    }
//
//    @Override
//    public BigDecimal getYestTodayOrderAmount() {
//        return betOrderBeanMapper.getYestTodayOrderAmount();
//    }
//
//    @Override
//    public BigDecimal getTodayProfitAmount() {
//        return betOrderBeanMapper.getTodayProfitAmount();
//    }
//
//    @Override
//    public BigDecimal getYestTodayProfitAmount() {
//        return betOrderBeanMapper.getYestTodayProfitAmount();
//    }
//
//    @Override
//    public Integer cancelOrders(String[] orders) {
//        for (int i = 0; i < orders.length; i++) {
//            BetOrderBean betOrderBean = betOrderBeanMapper.selectByOrderNo(orders[i]);
//            if (betOrderBean.getStatus().equals(OrderStatus.UNSETTLED)){
//                for (int j = 0; j < betOrderBean.getBetOrderDetails().size(); j++) {
//                    betOrderBean.getBetOrderDetails().get(j).setWinLose(WinLoseStatus.CANCEL);
//                    betOrderBean.getBetOrderDetails().get(j).setStatus(OrderStatus.SETTLED);
//                    betOrderDetailBeanMapper.updateById(betOrderBean.getBetOrderDetails().get(j));
//                }
//                betOrderBean.setWinLose(WinLoseStatus.CANCEL);
//                betOrderBean.setStatus(OrderStatus.SETTLED);
//                betOrderBean.setEstimatedReward(new BigDecimal(betOrderBean.getAmount()));
//                betOrderBean.setFailResult("订单异常");
//                betOrderBeanMapper.updateById(betOrderBean);
//                process(betOrderBean,AlgorithmType.ADD,false);
//                noticeService.sendNotice("退款",betOrderBean.getTenantOrderNo(),betOrderBean.getEstimatedReward(),"盘口取消",betOrderBean.getUpdateTime(),null,betOrderBean.getId(),betOrderBean.getTenantUserNo(),MsgType.refund);
//            }
//
//        }
//        syncToOrder();
//        return null;
//    }
//
//    /**
//     * 回滚操作
//     * @param betTopicsBean
//     * @return
//     */
//    @Override
//    public Integer recheckResult(BetTopicsBean betTopicsBean) {
//        log.info("进入重新结算"+betTopicsBean.getId());
//        List<BetOrderBean> list = new ArrayList<>();
//        List<BetOrderDetailBean> betOrderDetailBeans = betOrderDetailBeanMapper.selectByBetDataId(betTopicsBean.getId());
//        for (int i = 0; i < betOrderDetailBeans.size(); i++) {
//            BetOrderDetailBean betOrderDetailBean = betOrderDetailBeans.get(i);
//            if (betOrderDetailBean.getOrderType()==Boolean.FALSE){
//
//                if (betOrderDetailBean.getWinLose().equals(WinLoseStatus.WIN)){
//                    unprocess(betOrderDetailBean.getBetOrderBean(),AlgorithmType.SUB,true);
//                    betOrderDetailBean.getBetOrderBean().setRollbackLockCoin(betOrderDetailBean.getBetOrderBean().getRollbackCoin());
//                }else if (betOrderDetailBean.getWinLose().equals(WinLoseStatus.LOSE)){
//                    BigDecimal es = new BigDecimal(betOrderDetailBean.getBetOrderBean().getAmount()).multiply(betOrderDetailBean.getBetOrderBean().getCheckOdd());
//                    betOrderDetailBean.getBetOrderBean().setEstimatedReward(es);
//                    betOrderDetailBean.getBetOrderBean().setRollbackLockCoin(BigDecimal.ZERO);
//                }else if (betOrderDetailBean.getWinLose().equals(WinLoseStatus.CANCEL)){
//                    BigDecimal es = new BigDecimal(betOrderDetailBean.getBetOrderBean().getAmount()).multiply(betOrderDetailBean.getBetOrderBean().getCheckOdd());
//                    betOrderDetailBean.getBetOrderBean().setEstimatedReward(es);
//                    betOrderDetailBean.getBetOrderBean().setRollbackLockCoin(new BigDecimal(betOrderDetailBean.getBetOrderBean().getAmount()));
//                    unprocess(betOrderDetailBean.getBetOrderBean(),AlgorithmType.SUB,false);
//                }
//                if (betOrderDetailBean.getBetOrderBean().getSyncFlag()!=null && SyncStatus.BET_SYNC.equals(betOrderDetailBean.getBetOrderBean().getSyncFlag())){
//                    list.add(betOrderDetailBean.getBetOrderBean());//同步数据订单
//                }
//                betOrderDetailBean.setStatus(OrderStatus.UNSETTLED);
//                betOrderDetailBean.setWinLose(WinLoseStatus.NOTOPEN);
//                betOrderDetailBean.getBetOrderBean().setWinLose(WinLoseStatus.NOTOPEN);
//                betOrderDetailBean.getBetOrderBean().setStatus(OrderStatus.UNSETTLED);
//                betOrderDetailBean.getBetOrderBean().setSyncFlag(SyncStatus.SYNC);
//
//                betOrderDetailBeanMapper.updateById(betOrderDetailBean);
//                betOrderBeanMapper.updateById(betOrderDetailBean.getBetOrderBean());
//
//            }
//
//        }
//
//        //rollbackPost(list);//回滚推送
//        return betOrderDetailBeans.size();
//    }
}
