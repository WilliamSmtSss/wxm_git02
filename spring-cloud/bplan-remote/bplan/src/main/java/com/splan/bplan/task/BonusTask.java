package com.splan.bplan.task;

import com.splan.bplan.mappers.UserOnlineMapper;
import com.splan.bplan.service.IActiveService;
import com.splan.bplan.service.ITaskLogService;
import com.splan.bplan.service.IUserGiftTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 佣金计算任务
 */
@Component
@Slf4j
public class BonusTask{

    @Autowired
    private ITaskLogService taskLogService;

    @Autowired
    private IActiveService activeService;

    @Autowired
    private IUserGiftTaskService userGiftTaskService;

    @Autowired
    private UserOnlineMapper userOnlineMapper;

    //@Autowired
    /**
     * 每天14点
     */
    /*@Scheduled(cron="0 0 14 * * ?")
    private void process(){
        taskLogService.settleUserBonus();
        log.info("佣金计算完成");
    }

    *//**
     * 每月1号0点
     *//*
    @Scheduled(cron="0 0 0 1 * ?")
    private void monthProcess(){
        taskLogService.monthBonus();
        log.info("每月彩金计算完成");
    }*/

   /* @Scheduled(cron="0 0 0 * * ?")
    private void everyDayProcess(){
        initActive();
    }*/

    /*@Scheduled(cron="0 0 0 ? * MON")
    private void everyWeekProcess(){
        activeService.initWeekActive();
        TaskLog taskLog = new TaskLog();
        taskLog.setTaskType(TaskType.EVERY_WEEK_ACTIVITY_INIT);
        taskLog.setTaskCount(0);
        taskLog.setDetail("重置每周活跃");
        taskLog.setStatus(TaskStatus.FINISH);
        taskLogService.save(taskLog);
        log.info("重置每周活跃");
    }

    *//**
     * 重置每日活跃度
     *//*
    private void initActive() {
        activeService.initEveryDayActive();
        TaskLog taskLog = new TaskLog();
        taskLog.setTaskType(TaskType.EVERY_DAY_ACTIVITY_INIT);
        taskLog.setTaskCount(0);
        taskLog.setDetail("重置每日活跃");
        taskLog.setStatus(TaskStatus.FINISH);
        taskLogService.save(taskLog);
        log.info("重置每日活跃");
    }*/

    /**
     * 处理过期任务
     */
//    @Scheduled(cron = "0 */1 * * * *" )
//    private void overdueGift() {
//        System.out.println("每分钟执行一次");
//    }

//    /**
//     * 每天零点插入useronline
//     */
//    @Scheduled(cron = "0 1 0 ? * *")
//    private void insertUserOnline(){
////        System.out.println("haha");
//        UserOnlineBean u=new UserOnlineBean();
//        u.setOnline(0);
//        userOnlineMapper.insert(u);
//    }

//    /**
//     * 每5分钟更新当天useronline
//     */
//    @Scheduled(cron = "0 */1 * ? * *")
//    private void updateUserOnline(){
//        List<UserOnlineBean> list=userOnlineMapper.selectUserOnlineToday();
//        Integer nowOnlineUsers=NettySocketHolder.getOnLineUserNow();
//        nowOnlineUsers=13;
//        if(list.size()!=0){
//            UserOnlineBean userOnlineBean=list.get(0);
//            if(nowOnlineUsers>userOnlineBean.getOnline()) {
//                userOnlineBean.setOnline(nowOnlineUsers);
//                userOnlineMapper.updateById(userOnlineBean);
//            }
//        }else{
//            UserOnlineBean u=new UserOnlineBean();
//            u.setOnline(0);
//            userOnlineMapper.insert(u);
//        }
//    }

}
