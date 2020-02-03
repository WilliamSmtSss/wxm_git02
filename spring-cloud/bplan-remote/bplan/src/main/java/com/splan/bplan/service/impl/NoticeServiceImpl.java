package com.splan.bplan.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.NoticeBean;
import com.splan.base.bean.UserBean;
import com.splan.base.enums.MsgType;
import com.splan.base.enums.NoticeAction;
import com.splan.base.enums.Status;
import com.splan.bplan.dto.MessageOrderTemplate;
import com.splan.bplan.filter.BigDecimalValueFilter;
import com.splan.bplan.mappers.NoticeBeanMapper;
import com.splan.bplan.service.INoticeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeBeanMapper, NoticeBean> implements INoticeService {


    @Override
    public List<NoticeBean> noticeList(String apiId) {
        QueryWrapper<NoticeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Status.ENABLE);
        queryWrapper.eq("action", NoticeAction.notice);
        if (StringUtils.isNotBlank(apiId)){
            queryWrapper.eq("api_id",apiId);
        }else {
            queryWrapper.isNull("api_id");
        }
        //queryWrapper.gt("deadline_time",new Date());
        queryWrapper.orderByDesc("create_time");
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<NoticeBean> personNoticeList(Page<NoticeBean> page,UserBean userBean) {
        QueryWrapper<NoticeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Status.ENABLE);
        queryWrapper.eq("action", NoticeAction.message);
        queryWrapper.eq("user_id",userBean.getId());
        queryWrapper.orderByAsc("is_read");
        queryWrapper.orderByDesc("create_time");

        return baseMapper.selectPage(page,queryWrapper);
    }

    @Override
    public IPage<NoticeBean> personNoticeList(Page<NoticeBean> page, UserBean userBean, NoticeAction noticeAction) {
        QueryWrapper<NoticeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Status.ENABLE);
        queryWrapper.eq("action", noticeAction);
        if (noticeAction.equals(NoticeAction.message)){
            queryWrapper.eq("user_id",userBean.getId());
        }else {
            queryWrapper.isNull("user_id");
        }

        queryWrapper.eq("action",noticeAction);
        queryWrapper.orderByAsc("is_read");
        queryWrapper.orderByDesc("create_time");

        return baseMapper.selectPage(page,queryWrapper);
    }

    @Override
    public Integer sendNotice(String title, String content, Long referenceId,Long userId) {
        NoticeBean noticeBean = new NoticeBean();
        noticeBean.setAction(NoticeAction.message);
        //noticeBean.setContent("\""+content+"\"");
        noticeBean.setContent(content);
        noticeBean.setReferenceId(referenceId);
        noticeBean.setUserId(userId);
        noticeBean.setTitle(title);
        noticeBean.setStatus(Status.ENABLE);
        save(noticeBean);
        return null;
    }

    @Override
    public Integer sendNotice(String title, String content, Long referenceId, Long userId, MsgType msgType) {
        NoticeBean noticeBean = new NoticeBean();
        noticeBean.setAction(NoticeAction.message);
        //noticeBean.setContent("\""+content+"\"");
        noticeBean.setContent(content);
        noticeBean.setReferenceId(referenceId);
        noticeBean.setUserId(userId);
        noticeBean.setTitle(title);
        noticeBean.setStatus(Status.ENABLE);
        noticeBean.setMsgType(msgType);
        save(noticeBean);
        return null;
    }

    @Override
    public Integer sendNotice(String title, String orderNo, BigDecimal amount, String comment,Date orderDate,String vsDetail, Long referenceId, Long userId) {
        MessageOrderTemplate messageOrderTemplate = new MessageOrderTemplate();
        messageOrderTemplate.setAmount(amount);
        messageOrderTemplate.setComment(comment);
        messageOrderTemplate.setOrderNO(orderNo);
        messageOrderTemplate.setOrderDate(orderDate);
        messageOrderTemplate.setVsDetail(vsDetail);
        SerializeConfig config = new SerializeConfig();
        String content = JSON.toJSONString(messageOrderTemplate,config,new BigDecimalValueFilter());
        sendNotice(title,content,referenceId,userId);
        return null;
    }

    @Override
    public Integer sendNotice(String title, String orderNo, BigDecimal amount, String comment, Date orderDate, String vsDetail, Long referenceId, Long userId, MsgType msgType) {
        MessageOrderTemplate messageOrderTemplate = new MessageOrderTemplate();
        messageOrderTemplate.setAmount(amount);
        messageOrderTemplate.setComment(comment);
        messageOrderTemplate.setOrderNO(orderNo);
        messageOrderTemplate.setOrderDate(orderDate);
        messageOrderTemplate.setVsDetail(vsDetail);
        SerializeConfig config = new SerializeConfig();
        String content = JSON.toJSONString(messageOrderTemplate,config,new BigDecimalValueFilter());
        sendNotice(title,content,referenceId,userId,msgType);
        return null;
    }


    @Override
    public IPage<NoticeBean> businessNoticeList(Page page) {
        JSONObject sysUser = (JSONObject) SecurityUtils.getSubject().getPrincipal();
        Integer apiId = sysUser.getInteger("userId");
        List<Integer> apiIds = new ArrayList<>();
        apiIds.add(apiId);
        apiIds.add(0);
        QueryWrapper<NoticeBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", Status.ENABLE);
        queryWrapper.in("api_id", apiIds);
        IPage<NoticeBean> pages = page(page, queryWrapper);
        pages.getRecords().forEach(notice -> {
            notice.setMessage(notice.getContent());
            notice.setContent(null);
        });
        return pages;
    }

    @Override
    public Integer readAllMessage(UserBean userBean) {
        UpdateWrapper<NoticeBean> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("status", Status.ENABLE);
        updateWrapper.eq("is_read",0);
        updateWrapper.eq("user_id",userBean.getId());
        updateWrapper.eq("action",NoticeAction.message);
        updateWrapper.set("is_read",1);
        update(updateWrapper);
        return null;
    }
}
