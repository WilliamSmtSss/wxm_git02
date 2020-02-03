package com.splan.xbet.frontback.frontback.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.back.BackProductTrial;
import com.splan.base.enums.CheckStatus;
import com.splan.base.enums.ResultStatus;
import com.splan.base.enums.back.NoticeType;
import com.splan.base.http.CommonResult;
import com.splan.base.param.backremote.BackProductTrialParam;
import com.splan.base.result.backremote.BackProductTrialResult;
import com.splan.base.utils.ResultUtil;
import com.splan.xbet.frontback.frontback.mappers.BackMessageMapper;
import com.splan.xbet.frontback.frontback.mappers.BackProductTrialMapper;
import com.splan.xbet.frontback.frontback.service.ServiceTrialService;
import com.splan.xbet.frontback.frontback.utils.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceTrialServiceImpl implements ServiceTrialService {

    @Autowired
    private BackProductTrialMapper backProductTrialMapper;

    @Autowired
    private BackMessageMapper backMessageMapper;

    @Override
    public CommonResult<Page<List<BackProductTrialResult>>> list(BackProductTrialParam backProductTrialParam) {
        Page page=new Page();
        page.setCurrent(backProductTrialParam.getCurrent());
        page.setSize(backProductTrialParam.getSize());
        List<BackProductTrial> list=backProductTrialMapper.getList(page,backProductTrialParam);
        page.setRecords(list);
        return ResultUtil.returnSuccess(page);
    }

    @Override
    public CommonResult add(BackProductTrialParam backProductTrialParam) {
        BackProductTrial backProductTrial= (BackProductTrial) CommonUtil.changeParamtoBean(backProductTrialParam,new BackProductTrial());
        backProductTrial.setStatus(CheckStatus.PASS);
        return ResultUtil.returnSuccess(backProductTrialMapper.insert(backProductTrial));
    }

    @Override
    public CommonResult check(BackProductTrialParam backProductTrialParam) {
        try{
            if(null == backProductTrialParam.getId())
                return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
            if(backProductTrialParam.getStatus() == CheckStatus.PASS) {
                if (StringUtils.isBlank(backProductTrialParam.getTrialAdmin()) || StringUtils.isBlank(backProductTrialParam.getTrialPassword()))
                    return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
            }
            BackProductTrial update= (BackProductTrial) CommonUtil.changeParamtoBean(backProductTrialParam,new BackProductTrial());
            backMessageMapper.updateByMsgId(backProductTrialParam.getId()+"", NoticeType.service_try.toString());
            return ResultUtil.returnSuccess(backProductTrialMapper.updateById(update));
        }catch (Exception e){
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
        }
    }
    @Override
    public CommonResult del(BackProductTrialParam backProductTrialParam) {
        return null;
    }

    @Override
    public CommonResult edit(BackProductTrialParam backProductTrialParam) {
        try {
            if(null == backProductTrialParam.getId())
                return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
            BackProductTrial update= (BackProductTrial) CommonUtil.changeParamtoBean(backProductTrialParam,new BackProductTrial());
            return ResultUtil.returnSuccess(backProductTrialMapper.updateById(update));
        }catch (Exception e) {
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
        }
    }

}
