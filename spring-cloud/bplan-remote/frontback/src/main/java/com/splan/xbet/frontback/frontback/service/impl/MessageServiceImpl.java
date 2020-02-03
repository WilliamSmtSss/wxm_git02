package com.splan.xbet.frontback.frontback.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.back.BackMessage;
import com.splan.base.enums.back.NoticeType;
import com.splan.base.http.CommonResult;
import com.splan.base.utils.ResultUtil;
import com.splan.xbet.frontback.frontback.mappers.BackMessageMapper;
import com.splan.xbet.frontback.frontback.service.MessageService;
import com.splan.xbet.frontback.frontback.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private BackMessageMapper backMessageMapper;

    @Override
    public CommonResult<Page<BackMessage>> list(Map<String, Object> requestParam) {
        BackMessage s= CommonUtil.chgParamtoBean(requestParam,new BackMessage());
        Page page=new Page();
        page.setCurrent(Integer.parseInt(requestParam.get("current")+""));
        page.setSize(Integer.parseInt(requestParam.get("size")+""));
        List<BackMessage> list=backMessageMapper.pageList(page,s);
        for(BackMessage e:list){
            e.setMsgTypeName(NoticeType.getCName(e.getMsgType()));
        }
        page.setRecords(list);
        return ResultUtil.returnSuccess(page);
    }


}
