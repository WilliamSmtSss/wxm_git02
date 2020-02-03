package com.splan.xbet.admin.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.base.bean.NoticeBean;
import com.splan.base.bean.UserBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface INoticeService extends IService<NoticeBean> {

//    List<NoticeBean> noticeList(String apiId);
//
//    IPage<NoticeBean> personNoticeList(Page<NoticeBean> page, UserBean userBean);
//
//    Integer sendNotice(String title, String content, Long referenceId, Long userId);
//
//    Integer sendNotice(String title, String orderNo, BigDecimal amount, String comment, Date orderDate, String vsDetail, Long referenceId, Long userId);


    IPage<NoticeBean> businessNoticeList(Page page);

//    Integer readAllMessage(UserBean userBean);
}
