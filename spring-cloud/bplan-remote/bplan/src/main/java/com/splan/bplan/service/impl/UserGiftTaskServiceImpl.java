package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.GiftListBean;
import com.splan.base.bean.UserBean;
import com.splan.base.bean.UserGiftTaskBean;
import com.splan.base.enums.GiftStatus;
import com.splan.bplan.mappers.UserGiftTaskMapper;
import com.splan.bplan.service.IGiftListService;
import com.splan.bplan.service.IUserGiftTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserGiftTaskServiceImpl extends ServiceImpl<UserGiftTaskMapper, UserGiftTaskBean> implements IUserGiftTaskService {

    @Autowired
    private IGiftListService giftListService;

    @Autowired
    private UserGiftTaskMapper userGiftTaskMapper;

    @Override
    public boolean register(UserBean user) {
        List<GiftListBean> giftList = giftListService.getEnableList();
        giftList.forEach(gift -> {
            UserGiftTaskBean userGiftTaskBean = new UserGiftTaskBean();
            userGiftTaskBean.setGiftId(gift.getId());
            userGiftTaskBean.setUserId(user.getId());
            if (gift.getId() == 1) {
                userGiftTaskBean.setGiftStatus(GiftStatus.FINISHED);
                userGiftTaskBean.setTaskValue(new BigDecimal(1));
            } else {
                userGiftTaskBean.setGiftStatus(GiftStatus.UNFINISHED);
                userGiftTaskBean.setTaskValue(new BigDecimal(0));
            }
            save(userGiftTaskBean);
        });
        return true;
    }

    @Override
    public boolean finishTask(long userId, GiftListBean giftListBean) {
        QueryWrapper<UserGiftTaskBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("gift_id", giftListBean.getId());
        UserGiftTaskBean userGiftTaskBean = getOne(queryWrapper);
        if (GiftStatus.UNFINISHED.equals(userGiftTaskBean.getGiftStatus())) {
            userGiftTaskBean.setGiftStatus(GiftStatus.FINISHED);
            userGiftTaskBean.setTaskValue(giftListBean.getActivityValue());
            updateById(userGiftTaskBean);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int overdueTask() {
        UpdateWrapper<UserGiftTaskBean> updateWrapper = new UpdateWrapper<>();
        return 0;
    }

}
