package com.splan.xbet.frontback.frontback.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.back.BackMessage;
import com.splan.base.bean.back.BackProductOrder;
import com.splan.base.enums.CheckStatus;
import com.splan.base.enums.ResultStatus;
import com.splan.base.enums.back.NoticeType;
import com.splan.base.http.CommonResult;
import com.splan.base.param.backremote.BackProductOrderParam;
import com.splan.base.result.backremote.BackProductOrderResult;
import com.splan.base.utils.ResultUtil;
import com.splan.xbet.frontback.frontback.mappers.BackMessageMapper;
import com.splan.xbet.frontback.frontback.mappers.BackProductOrderMapper;
import com.splan.xbet.frontback.frontback.service.ServiceOrderService;
import com.splan.xbet.frontback.frontback.utils.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ServiceOrderServiceImpl implements ServiceOrderService {

    @Autowired
    private BackProductOrderMapper backProductOrderMapper;

    @Autowired
    private BackMessageMapper backMessageMapper;

    @Override
    public CommonResult<Page<List<BackProductOrderResult>>> list(BackProductOrderParam backProductOrderParam) {
        Page page=new Page();
        page.setCurrent(backProductOrderParam.getCurrent());
        page.setSize(backProductOrderParam.getSize());
        List<BackProductOrderResult> list=new ArrayList<>();
        List<BackProductOrder> list1=backProductOrderMapper.getPageList(page,backProductOrderParam);
        Date nowTime=new Date();
        for(BackProductOrder p:list1){
            BackProductOrderResult backProductOrderResult= (BackProductOrderResult) CommonUtil.changeParamtoBean(p,new BackProductOrderResult());
            if(null!=backProductOrderResult.getServiceStartTime() && null !=backProductOrderResult.getServiceEndTime()) {
                if (nowTime.compareTo(backProductOrderResult.getServiceStartTime()) >= 0 && nowTime.compareTo(backProductOrderResult.getServiceEndTime()) <= 0) {
                    backProductOrderResult.setPassStatus("生效中");
                    if (CommonUtil.differentDays(nowTime, backProductOrderResult.getServiceEndTime()) <= 7) {
                        backProductOrderResult.setPassStatus("即将到期");
                    }
                }else if(nowTime.compareTo(backProductOrderResult.getServiceStartTime()) < 0){
                    backProductOrderResult.setPassStatus("未生效");
                }else{
                    backProductOrderResult.setPassStatus("已过期");
                }
            }else{
                backProductOrderResult.setPassStatus("已关闭");
            }
            list.add(backProductOrderResult);
        }
        page.setRecords(list);
        return ResultUtil.returnSuccess(page);
    }

    @Override
    public CommonResult add(BackProductOrderParam backProductOrderParam) {
        backProductOrderParam.setStatus(CheckStatus.PASS);
        return ResultUtil.returnSuccess(backProductOrderMapper.insert((BackProductOrder) CommonUtil.changeParamtoBean(backProductOrderParam,new BackProductOrder())));
    }

    @Override
    public CommonResult edit(BackProductOrderParam backProductOrderParam) {
        if(null == backProductOrderParam.getId())
            ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
        BackProductOrder update= (BackProductOrder) CommonUtil.changeParamtoBean(backProductOrderParam,new BackProductOrder());
        return ResultUtil.returnSuccess(backProductOrderMapper.updateById(update));
    }

    @Override
    public CommonResult del(BackProductOrderParam backProductOrderParam) {
        return null;
    }

    @Override
    public CommonResult check(BackProductOrderParam backProductOrderParam) {

        if(null==backProductOrderParam.getId())
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);

        if(backProductOrderParam.getStatus() == CheckStatus.PASS) {
            if (StringUtils.isBlank(backProductOrderParam.getServiceStartTime()) || StringUtils.isBlank(backProductOrderParam.getServiceStartTime()))
                return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
        }
        BackProductOrder update= (BackProductOrder) CommonUtil.changeParamtoBean(backProductOrderParam,new BackProductOrder());
        backMessageMapper.updateByMsgId(backProductOrderParam.getId()+"", NoticeType.service_open.toString());
        return ResultUtil.returnSuccess(backProductOrderMapper.updateById(update));
    }

}
