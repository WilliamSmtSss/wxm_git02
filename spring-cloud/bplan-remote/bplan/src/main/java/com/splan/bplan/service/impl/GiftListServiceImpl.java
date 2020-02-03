package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.GiftListBean;
import com.splan.bplan.constants.GiftTaskConstants;
import com.splan.base.enums.Status;
import com.splan.bplan.mappers.GiftListBeanMapper;
import com.splan.bplan.service.IGiftListService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftListServiceImpl extends ServiceImpl<GiftListBeanMapper, GiftListBean> implements IGiftListService {
    @Override
    public List<GiftListBean> getEnableList() {
        QueryWrapper<GiftListBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Status.ENABLE);
        return list(queryWrapper);
    }

    @Override
    public int cacheList() {
        /*List<GiftListBean> list = getEnableList();
        list.forEach((gift) -> GiftTaskConstants.getMap().put(Integer.toString(gift.getId()), gift));
        return list.size();*/
        return 0;
    }
}
