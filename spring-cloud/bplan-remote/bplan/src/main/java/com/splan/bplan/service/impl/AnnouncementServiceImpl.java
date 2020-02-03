package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.AnnouncementBean;
import com.splan.bplan.mappers.AnnouncementBeanMapper;
import com.splan.bplan.service.IAnnouncementService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementBeanMapper, AnnouncementBean> implements IAnnouncementService {


    @Override
    public List<AnnouncementBean> noticeList(String apiId) {
        return baseMapper.getNotice(apiId);
    }


}
