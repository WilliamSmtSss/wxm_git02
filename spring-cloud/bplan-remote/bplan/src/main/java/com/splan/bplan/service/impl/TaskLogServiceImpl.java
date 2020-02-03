package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.BetOrderBean;
import com.splan.base.bean.MemberInterestsBean;
import com.splan.base.bean.TaskLog;
import com.splan.base.bean.UserBean;
import com.splan.bplan.constants.SysParamConstants;
import com.splan.base.enums.*;
import com.splan.bplan.mappers.MemberInterestsBeanMapper;
import com.splan.bplan.mappers.TaskLogMapper;
import com.splan.bplan.mappers.UserMapper;
import com.splan.bplan.service.IBetOrderService;
import com.splan.bplan.service.ITaskLogService;
import com.splan.bplan.service.IUserBalanceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TaskLogServiceImpl extends ServiceImpl<TaskLogMapper, TaskLog> implements ITaskLogService {


    @Autowired
    private IBetOrderService betOrderService;

    @Autowired
    private IUserBalanceService userBalanceService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MemberInterestsBeanMapper memberInterestsBeanMapper;

    @Autowired
    private TaskLogMapper taskLogMapper;

    @Override
    public void settleUserBonus() {

        QueryWrapper<BetOrderBean> queryWrapper = new QueryWrapper<>();
        //queryWrapper.eq("to_days(create_time)","to_days(now())");
        queryWrapper.le("TO_DAYS( NOW( ) ) - TO_DAYS( create_time)",1);
        queryWrapper.ge("odd",1.75);
        queryWrapper.ne("status", OrderStatus.FAIL);
        queryWrapper.eq("bonus_check",0);
        queryWrapper.groupBy("tenant_user_no");
        queryWrapper.select("sum(amount) as a","tenant_user_no");
        List<Map<String,Object>> unList = betOrderService.listMaps(queryWrapper);

        TaskLog taskLog = new TaskLog();
        taskLog.setStatus(TaskStatus.START);
        taskLog.setTaskType(TaskType.BONUS);
        /*taskLog.setDetail("已结算"+sum+"个用户");
        taskLog.setTaskCount(sum);*/
        save(taskLog);

        int sum = 0;
        for (int i = 0; i < unList.size(); i++) {
            Map<String,Object> m = unList.get(i);
            BigDecimal amount = new BigDecimal(m.get("a").toString());
            BigDecimal brokerageCoin = amount.multiply(SysParamConstants.getBigDecimal(SysParamConstants.NORMAL_COM_PERCENT));
            Long userId = Long.valueOf(m.get("tenant_user_no").toString());

            //查询上家
            UserBean userBean = userMapper.selectById(userId);

            //会员返水
            memberWater(userBean,amount,taskLog);

            //邀请返利
            if (userBean!=null && !userBean.getStatus().equals(Status.DISABLE) && StringUtils.isNotBlank(userBean.getBeInviteCode())){
                //上家
                QueryWrapper<UserBean> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("invite_code",userBean.getBeInviteCode());
                UserBean userBean1 = userMapper.selectOne(queryWrapper1);
                if (userBean1!=null && !userBean1.getStatus().equals(Status.DISABLE) && userBean1.getLevel().equals(Level.Normal)){
                    userBalanceService.changeCoin(userBean1.getId(),Long.valueOf(taskLog.getId()),brokerageCoin, MoneyAbleType.WATER);
                    sum++;
                }

            }
            //会员等级返利

        }
        UpdateWrapper<BetOrderBean> updateWrapper = new UpdateWrapper<>();
        //updateWrapper.eq("to_days(create_time)","to_days(now())");
        updateWrapper.le("TO_DAYS( NOW( ) ) - TO_DAYS( create_time)",1);
        updateWrapper.ge("odd",1.75);
        updateWrapper.ne("status", OrderStatus.FAIL);
        updateWrapper.eq("bonus_check",0);
        updateWrapper.set("bonus_check",1);
        betOrderService.update(updateWrapper);

        taskLog.setStatus(TaskStatus.FINISH);
        taskLog.setDetail("已结算"+sum+"个用户");
        taskLog.setTaskCount(sum);
        updateById(taskLog);

    }

    @Override
    public void monthBonus() {

        QueryWrapper<TaskLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_type",TaskType.MONTH_GIFT);
        queryWrapper.apply("DATE_FORMAT( create_time, '%Y-%m' )","DATE_FORMAT( CURDATE( ) , '%Y-%m' )");
        Integer taskCount = count(queryWrapper);
        if (taskCount==0){
            TaskLog taskLog = new TaskLog();
            taskLog.setStatus(TaskStatus.FINISH);
            taskLog.setTaskType(TaskType.MONTH_GIFT);
            taskLog.setTaskCount(0);
            DateFormat bf = new SimpleDateFormat("yyyy-MM");
            //2017-04-19 星期三 下午 20:17:38
            Date date = new Date();//创建时间
            String format = bf.format(date);//格式化 bf.format(date);
            taskLog.setDetail(format+"会员权益发放");
            save(taskLog);
            taskLogMapper.monthTask();
        }else {
            log.info("当月会员奖励已发放，请勿重复执行");
        }
        //每月


    }

    private void memberWater(UserBean userBean,BigDecimal amount,TaskLog taskLog){
        if (userBean!=null && !userBean.getStatus().equals(Status.DISABLE) && userBean.getMemberStatus().equals(Status.ENABLE)){
            MemberInterestsBean memberInterestsBean = memberInterestsBeanMapper.selectById(userBean.getMemberLevel());
            BigDecimal brokerageCoin = amount.multiply(memberInterestsBean.getWater());
            if (brokerageCoin.compareTo(BigDecimal.ONE)>-1){
                userBalanceService.changeCoin(userBean.getId(),Long.valueOf(taskLog.getId()),brokerageCoin, MoneyAbleType.WATER);
            }

        }

    }


}
