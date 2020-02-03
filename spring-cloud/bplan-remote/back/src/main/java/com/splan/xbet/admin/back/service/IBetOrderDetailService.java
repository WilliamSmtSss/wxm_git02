package com.splan.xbet.admin.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.base.bean.BetOrderDetailBean;

import java.util.List;

public interface IBetOrderDetailService extends IService<BetOrderDetailBean> {

    List<BetOrderDetailBean> selectByDataId(Integer dataId);
}
