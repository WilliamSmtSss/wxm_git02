package com.splan.bplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.base.bean.GiftListBean;
import com.splan.base.bean.UserBean;
import com.splan.base.bean.UserGiftTaskBean;

public interface IUserGiftTaskService extends IService<UserGiftTaskBean> {
    boolean register(UserBean user);
    boolean finishTask(long userId, GiftListBean giftListBean);
    int overdueTask();
}
