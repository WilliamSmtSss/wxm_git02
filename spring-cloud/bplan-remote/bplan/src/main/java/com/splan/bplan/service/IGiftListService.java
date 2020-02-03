package com.splan.bplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.base.bean.GiftListBean;

import java.util.List;

public interface IGiftListService extends IService<GiftListBean> {
    List<GiftListBean> getEnableList();
    int cacheList();
}
