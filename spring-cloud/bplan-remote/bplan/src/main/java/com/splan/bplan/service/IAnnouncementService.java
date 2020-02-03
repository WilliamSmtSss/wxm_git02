package com.splan.bplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.base.bean.AnnouncementBean;

import java.util.List;

public interface IAnnouncementService extends IService<AnnouncementBean> {

    List<AnnouncementBean> noticeList(String apiId);


}
