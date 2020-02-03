package com.splan.xbet.frontback.frontback.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.back.BackProductInfo;
import com.splan.base.enums.ResultStatus;
import com.splan.base.http.CommonResult;
import com.splan.base.param.backremote.BackProductInfoParam;
import com.splan.base.result.backremote.BackProductInfoResult;
import com.splan.base.utils.ResultUtil;
import com.splan.xbet.frontback.frontback.mappers.BackProductInfoMapper;
import com.splan.xbet.frontback.frontback.service.ServiceManagerService;
import com.splan.xbet.frontback.frontback.utils.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceManagerServiceImpl implements ServiceManagerService {

    @Autowired
    private BackProductInfoMapper backProductInfoMapper;

    @Override
    public CommonResult<Page<List<BackProductInfoResult>>> list(BackProductInfoParam backProductInfoParam) {
        Page page=new Page();
        page.setCurrent(backProductInfoParam.getCurrent());
        page.setSize(backProductInfoParam.getSize());
        List<BackProductInfoResult> list=new ArrayList<>();
        List<BackProductInfo> list1=backProductInfoMapper.getPageList(page,backProductInfoParam.getCheckStatus());
        for(BackProductInfo p:list1){
            BackProductInfoResult backProductInfoResult= (BackProductInfoResult) CommonUtil.changeParamtoBean(p,new BackProductInfoResult());
            list.add(backProductInfoResult);
        }
        page.setRecords(list);
        return ResultUtil.returnSuccess(page);
    }

    @Override
    public CommonResult add(BackProductInfoParam backProductInfoParam) {
        BackProductInfo backProductInfo= (BackProductInfo)CommonUtil.changeParamtoBean(backProductInfoParam,new BackProductInfo());
        return ResultUtil.returnSuccess(backProductInfoMapper.insert(backProductInfo));
    }

    @Override
    public CommonResult edit(BackProductInfoParam backProductInfoParam) {
        if(null == backProductInfoParam.getId())
            ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
        BackProductInfo update= (BackProductInfo) CommonUtil.changeParamtoBean(backProductInfoParam,new BackProductInfo());
        return ResultUtil.returnSuccess(backProductInfoMapper.updateById(update));
    }

    @Override
    public CommonResult del(BackProductInfoParam backProductInfoParam) {
        return null;
    }

}
