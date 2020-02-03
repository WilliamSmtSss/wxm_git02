package com.splan.bplan.service;

import com.splan.base.enums.TriggerType;
import com.splan.bplan.result.ActiveResult;
import com.splan.bplan.result.SignResult;

/**
 * 活跃系统
 */
public interface IActiveService {

    /**
     * 后台调用
     * @param userId
     * @param activeId
     */
    void gainActive(Long userId, Integer activeId);

    /**
     * 重置每日活跃
     */
    void initEveryDayActive();

    /**
     * 重置每周活跃
     * @return
     */
    boolean initWeekActive();

    /**
     * 执行任务
     * @param triggerType 是否后台任务
     * @return
     */
    boolean doActive(Long userId, Integer activeId, TriggerType triggerType);

    SignResult sign(Long userId);



    /**
     * 获得任务奖励
     * @param userId
     * @param activeId
     * @return
     */
    boolean getReward(Long userId,Integer activeId);

    /**
     * 获取活跃值
     * @param userId
     * @return
     */
    ActiveResult getActivityList(Long userId);

    /**
     * 初始化活跃值参数
     * @return
     */
    boolean initMap();


    /**
     * 检查任务是否做过
     * @param userId
     * @param activeId
     * @param triggerType
     * @return
     */
    boolean checkActive(Long userId, Integer activeId, TriggerType triggerType);

    /**
     * 下单后触发任务
     * @param userId
     * @return
     */
    boolean afterOrder(Long userId);

    /**
     * 结算触发任务
     * @param userId
     * @return
     */
    boolean afterCheck(Long userId);


    /**
     * 下单后触发任务
     * @param userId
     * @return
     */
    boolean afterOrderAsyn(Long userId);

    /**
     * 结算触发任务
     * @param userId
     * @return
     */
    boolean afterCheckAsyn(Long userId);

    int updateActive(Integer activeId,Integer val);
}
