package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.splan.base.bean.ActivityListBean;
import com.splan.base.bean.BetOrderBean;
import com.splan.base.enums.*;
import com.splan.bplan.mappers.ActivityListBeanMapper;
import com.splan.bplan.result.ActiveResult;
import com.splan.bplan.result.SignResult;
import com.splan.bplan.service.IActiveService;
import com.splan.bplan.service.IBetOrderService;
import com.splan.bplan.service.IUserBalanceService;
import com.splan.bplan.thread.ActiveCallable;
import com.splan.bplan.thread.BetFinishCallable;
import com.splan.bplan.thread.CheckActiveCallable;
import com.splan.bplan.thread.OrderActiveCallable;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class ActiveServiceImpl implements IActiveService {

    @Autowired
    private IBetOrderService betOrderService;

    @Autowired
    private ActivityListBeanMapper activityListBeanMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private final boolean flag = true;

    private final String PRE = "activity_";

    private final String DAY = "day_";

    private final String DAY_REWARD = "dayreward_";//每日活跃值领取记录

    private final String USER_DAY_REWARD = "user_day_reward_";//每日活跃值数量

    private final String WEEK = "week_";

    private final String WEEK_REWARD = "weekreward_";//每周活跃值领取记录

    private final String USER_WEEK_REWARD = "user_week_reward_";//每周活跃值数量

    private final String SPLIT = "_";

    private final String ACTIVITY = "activity_list";

    private final String RANK = "activity_rank_";

    public Map<Integer,ActivityListBean> MAP = new ConcurrentHashMap<>();

    private ExecutorService service = Executors.newFixedThreadPool(100);

    public ExecutorService getService() {
        return service;
    }

    @Override
    public void gainActive(Long userId, Integer activeId){
        ActiveCallable activeCallable = new ActiveCallable(this,userId,activeId);
        service.submit(activeCallable);
    }

    @Autowired
    private IUserBalanceService userBalanceService;

    @Override
    public void initEveryDayActive() {
        QueryWrapper<ActivityListBean> queryWrapper = new QueryWrapper<ActivityListBean>();
        queryWrapper.eq("status", Status.ENABLE);
        List<ActivityListBean> list = activityListBeanMapper.selectList(queryWrapper);
        String key = PRE + DAY;

        for (int i = 0; i < list.size(); i++) {
            MAP.put(list.get(i).getId(),list.get(i));
            ActivityListBean activityListBean = list.get(i);
            if (activityListBean.getActivityType().equals(ActivityType.DAY_LIMIT)){
                redisTemplate.opsForValue().set(key + activityListBean.getId(),new Long(0L).toString());
            }else if (activityListBean.getActivityType().equals(ActivityType.DAY_REWARD)){
                redisTemplate.opsForValue().set(PRE + DAY_REWARD + activityListBean.getId(),new Long(0L).toString());//每日活跃值领取记录
            }
        }
        //redisTemplate.opsForHash().putAll(ACTIVITY,map);
        initEveryDayMemberReward();

    }

    @Override
    public boolean initWeekActive() {
        QueryWrapper<ActivityListBean> queryWrapper = new QueryWrapper<ActivityListBean>();
        queryWrapper.eq("status", Status.ENABLE);
        queryWrapper.eq("activity_type",ActivityType.WEEK_REWARD);
        List<ActivityListBean> list = activityListBeanMapper.selectList(queryWrapper);
        for (int i = 0; i < list.size(); i++) {
            redisTemplate.opsForValue().set(PRE+WEEK_REWARD+list.get(i).getId(),new Long(0L).toString());
        }

        initEveryWeekMemberReward();
        return false;
    }

    /**
     * 重置每日会员活跃值
     */
    private void initEveryDayMemberReward(){
        Set<String> keys = redisTemplate.keys(PRE+USER_DAY_REWARD+"*");
        redisTemplate.delete(keys);
        //初始化数据
        //删除签到数据
        /*Set<String> keys2 = redisTemplate.keys(RANK+"*");
        redisTemplate.delete(keys2);*/
    }

    /**
     * 重置每周会员活跃值
     */
    private void initEveryWeekMemberReward(){
        Set<String> keys = redisTemplate.keys(PRE+USER_WEEK_REWARD+"*");
        redisTemplate.delete(keys);
        //初始化数据
    }

    //back false 前台触发 领取任务   true后台触发
    @Override
    public boolean doActive(Long userId, Integer activeId, TriggerType triggerType) {
        if (MAP.size()==0){
            //初始化
            initMap();
        }

        ActivityListBean activityListBean = MAP.get(activeId);

        if (activityListBean==null){
            return false;
        }

        if (!triggerType.equals(activityListBean.getTriggerType())){
            return false;//执行方式不对
        }

        String key = PRE + DAY;

        ValueOperations<String,String> ops = redisTemplate.opsForValue();

        String userDayReward = ops.get(PRE+USER_DAY_REWARD+userId);

        if (StringUtils.isBlank(userDayReward)){
            ops.set(PRE+USER_DAY_REWARD+userId,"0");//初始化 今日积分
            userDayReward = "0";
        }

        String userWeekReward = ops.get(PRE+USER_WEEK_REWARD+userId);
        if (StringUtils.isBlank(userDayReward)){
            ops.set(PRE+USER_WEEK_REWARD+userId,"0");//初始化 每周积分
            userWeekReward = "0";
        }

        if (activityListBean.getActivityType().equals(ActivityType.DAY_LIMIT)){//每日限制任务
            boolean flag = ops.getBit(key + activeId,userId);
            if (!flag){
                //ActivityListBean activityListBean = MAP.get(activeId);
                //未做任务
                boolean checkActivce = checkActive(userId,activeId,triggerType);
                //增加当日积分
                //增加当周积分
                ops.increment(PRE+USER_DAY_REWARD+userId,activityListBean.getActivityValue().longValue());
                ops.increment(PRE+USER_WEEK_REWARD+userId,activityListBean.getActivityValue().longValue());
                ops.setBit(key+activeId ,userId,true);
                return true;
            }
        }else if (activityListBean.getActivityType().equals(ActivityType.DAY_NO_LIMIT)){//无限制
            ops.increment(PRE+USER_DAY_REWARD+userId,activityListBean.getActivityValue().longValue());
            ops.increment(PRE+USER_WEEK_REWARD+userId,activityListBean.getActivityValue().longValue());
            return true;

        }
        //金额操作
        else if (activityListBean.getActivityType().equals(ActivityType.DAY_REWARD)){//每日活跃值现金奖励
            //boolean flag = ops.getBit(PRE+USER_DAY_REWARD+userId)
            boolean flag = ops.getBit(PRE + DAY_REWARD + activityListBean.getId(),userId);//获取领取记录
            if (!flag){
                //未领取奖励处理，判断是否到达
                Integer userDayRewardInt = Integer.valueOf(userDayReward);
                if (userDayRewardInt<activityListBean.getLimitValue()){
                    //未达到领取标准
                    return false;
                }
                ops.setBit(PRE + DAY_REWARD + activityListBean.getId(),userId,true);//设置领取状态
                //增加金额
                userBalanceService.changeCoin(userId,Long.valueOf(activityListBean.getId()),activityListBean.getActivityValue(), MoneyAbleType.EVERYDAY_ACTIVITY);
            }

        }else if (activityListBean.getActivityType().equals(ActivityType.WEEK_REWARD)){//每周活跃值现金奖励
            boolean flag = ops.getBit(PRE + WEEK_REWARD + activityListBean.getId(),userId);//获取领取记录
            if (!flag){
                //未领取奖励处理，判断是否到达
                Integer userWeekRewardInt = Integer.valueOf(userWeekReward);
                if (userWeekRewardInt<activityListBean.getLimitValue()){
                    //未达到领取标准
                    return false;
                }
                ops.setBit(PRE + WEEK_REWARD + activityListBean.getId(),userId,true);//设置领取状态
                userBalanceService.changeCoin(userId,Long.valueOf(activityListBean.getId()),activityListBean.getActivityValue(), MoneyAbleType.EVERYDAY_ACTIVITY);
            }
        }else {
            log.info("未知活跃值活动");
            return false;
        }

        return true;
    }

    @Override
    public SignResult sign(Long userId) {
        String key = PRE + DAY;
        ValueOperations<String,String> ops = redisTemplate.opsForValue();
        boolean flag = ops.getBit(key + 1,userId);
        SignResult signResult = new SignResult();
        if (!flag){
            ops.setBit(key + 1,userId,true);
            /*Long bitcount = bitCount(key + 1);
            ops.set(RANK+userId,bitcount+"");*/
            signResult.setFlag(true);
            //signResult.setRank(bitcount);
        }else {
            signResult.setFlag(false);
        }

        return signResult;
    }


    @Override
    public boolean getReward(Long userId, Integer activeId) {
        return false;
    }

    @Override
    public ActiveResult getActivityList(Long userId) {
        long start = new Date().getTime();
        ActiveResult activeResult = new ActiveResult();
        ValueOperations<String,String> ops = redisTemplate.opsForValue();
        QueryWrapper<ActivityListBean> queryWrapper = new QueryWrapper<ActivityListBean>();
        queryWrapper.eq("status", Status.ENABLE);
        List<ActivityListBean> list = activityListBeanMapper.selectList(queryWrapper);
        for (int i = 0; i < list.size(); i++) {
            /*if (i==0){
                long count = bitCount(PRE + DAY + list.get(i).getId());
                list.get(i).setSignCount(count);
                list.get(i).setRank(getActiveCount(ops.get(RANK+userId)));
            }*/
            boolean flag = ops.getBit(PRE + DAY + list.get(i).getId(),userId);
            list.get(i).setFlag(flag);
        }
        activeResult.setList(list);
        activeResult.setDayActive(getActiveCount(ops.get(PRE+USER_DAY_REWARD+userId)));
        activeResult.setWeekActive(getActiveCount(ops.get(PRE+USER_WEEK_REWARD+userId)));
        long end = new Date().getTime();
        log.info("执行时间"+(end-start));
        return activeResult;
    }

    @Override
    public boolean initMap() {
        QueryWrapper<ActivityListBean> queryWrapper = new QueryWrapper<ActivityListBean>();
        queryWrapper.eq("status", Status.ENABLE);
        List<ActivityListBean> list = activityListBeanMapper.selectList(queryWrapper);
        for (int i = 0; i < list.size(); i++) {
            MAP.put(list.get(i).getId(),list.get(i));
        }
        return false;
    }

    @Override
    public boolean checkActive(Long userId, Integer activeId, TriggerType triggerType) {

        return false;
    }

    @Override
    public boolean afterOrder(Long userId) {
        ValueOperations<String,String> ops = redisTemplate.opsForValue();
        String key = PRE + DAY;
        QueryWrapper<BetOrderBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tenant_user_no",userId);
        //3	每日参与竞猜一次
        //6	每日竞猜流水满50

        //7	每日竞猜一次1.5赔率以上（含）

        //8	每日竞猜流水满150
        //9	每日参与竞猜五次

        //11	每日竞猜三次1.5赔率以上（含）

        //12	每日竞猜流水满300
        queryWrapper.ne("status", OrderStatus.FAIL);
        queryWrapper.apply("to_days(create_time)","to_days(now())");
        queryWrapper.select(" IFNULL(sum(amount),0) as a "," COUNT(id) as b" );
        Map<String,Object> map = betOrderService.getMap(queryWrapper);
        //流水
        Integer a = Integer.valueOf(map.get("a").toString());
        if (ops.getBit(key + 6,userId) && a>=50){
            gain(6,userId);
        }
        if (ops.getBit(key + 8,userId) && a>=150){
            gain(8,userId);
        }
        if (ops.getBit(key + 12,userId) && a>=300){
            gain(12,userId);
        }
        //次数
        Integer b = Integer.valueOf(map.get("b").toString());
        if (ops.getBit(key + 3,userId) && b>=1){
            gain(3,userId);
        }
        if (ops.getBit(key + 9,userId) && b>=5){
            gain(9,userId);
        }

        queryWrapper.ge("odd","1.5");
        Map<String,Object> map2 = betOrderService.getMap(queryWrapper);
        Integer c = Integer.valueOf(map.get("b").toString());
        if (ops.getBit(key + 7,userId) && c>=1){
            gain(7,userId);
        }
        if (ops.getBit(key + 11,userId) && c>=3){
            gain(11,userId);
        }
        return false;
    }

    private void gain(Integer activtyId,Long userId){
        String key = PRE + DAY;
        ValueOperations<String,String> ops = redisTemplate.opsForValue();
        ops.setBit(key + activtyId,userId,true);
        ops.increment(PRE+USER_DAY_REWARD+userId,MAP.get(activtyId).getActivityValue().longValue());
        ops.increment(PRE+USER_WEEK_REWARD+userId,MAP.get(activtyId).getActivityValue().longValue());
    }

    @Override
    public boolean afterCheck(Long userId) {
        if (flag){
            return false;
        }
        ValueOperations<String,String> ops = redisTemplate.opsForValue();
        //5	 每日竞猜正确一次
        //10 每日竞猜正确三次
        String key = PRE + DAY;
        //if (ops.getBit(key + 5,userId) || ops.getBit(key + 10,userId)){
        QueryWrapper<BetOrderBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tenant_user_no",userId);
        queryWrapper.eq("status", OrderStatus.SETTLED);
        queryWrapper.eq("win_lose",WinLoseStatus.WIN);
        queryWrapper.apply("to_days(create_time)","to_days(now())");
        queryWrapper.select(" COUNT(id) as b" );
        Map<String,Object> map2 = betOrderService.getMap(queryWrapper);
        Integer b = Integer.valueOf(map2.get("b").toString());
        if (ops.getBit(key + 5,userId) && b>=1){
            gain(5,userId);
        }
        if (ops.getBit(key + 10,userId) && b>=3){
            gain(10,userId);
        }
        //}
        return false;
    }

    @Override
    public boolean afterOrderAsyn(Long userId) {
        OrderActiveCallable orderActiveCallable = new OrderActiveCallable(this,userId);
        service.submit(orderActiveCallable);
        return false;
    }

    @Override
    public boolean afterCheckAsyn(Long userId) {
        if (flag){
            return false;
        }
        CheckActiveCallable checkActiveCallable = new CheckActiveCallable(this,userId);
        service.submit(checkActiveCallable);
        return false;
    }

    private Integer getActiveCount(String value){
        if (StringUtils.isBlank(value)){
            return 0;
        }else {
            return Integer.valueOf(value);
        }

    }

    private long bitCount(final String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                long result = 0;
                result = connection.bitCount(key.getBytes());
                return result;
            }
        });
    }

    @Override
    public int updateActive(Integer activeId, Integer val) {
        ActivityListBean act=new ActivityListBean();
        act.setId(activeId);
        act.setActivityValue(new BigDecimal(val));
        return activityListBeanMapper.updateById(act);
    }

}
