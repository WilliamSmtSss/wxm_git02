package com.splan.xbet.frontback.frontback.rest;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.back.*;
import com.splan.base.enums.CheckStatus;
import com.splan.base.enums.back.NoticeStatus;
import com.splan.base.enums.back.NoticeType;
import com.splan.base.enums.back.ProductType;
import com.splan.base.param.backremote.BackProductOrderParam;
import com.splan.base.param.backremote.BackProductTrialParam;
import com.splan.base.result.backremote.BackProductInfoResult;
import com.splan.base.result.backremote.BackProductTrialResult;
import com.splan.base.service.v2.FrontBackService;
import com.splan.xbet.frontback.frontback.mappers.*;
import com.splan.xbet.frontback.frontback.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.security.krb5.internal.PAData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class FrontBackResource implements FrontBackService {

    @Autowired
    private BackProductTrialMapper backProductTrialMapper;

    @Autowired
    private BackProductOrderMapper backProductOrderMapper;

    @Autowired
    private BackProductInfoMapper backProductInfoMapper;

    @Autowired
    private BackMessageMapper backMessageMapper;

    @Autowired
    private BackNoticeMapper backNoticeMapper;

    @Override
    public String addTrial(BackProductTrialParam backProductTrialParam) {
        BackProductTrial backProductTrial= (BackProductTrial) CommonUtil.changeParamtoBean(backProductTrialParam,new BackProductTrial());
        int x=backProductTrialMapper.insert(backProductTrial);
        backProductTrial.setStatus(CheckStatus.UNCHECK);
        BackMessage i=new BackMessage();
        i.setMsgType(NoticeType.service_try.toString());
        i.setApiId(backProductTrialParam.getBusinessName());
        i.setMsgId(backProductTrial.getId());
        backMessageMapper.insert(i);
        return x+"";
    }

    @Override
    public String selTrial(BackProductTrialParam backProductTrialParam) {
        BackProductTrial backProductTrial=backProductTrialMapper.sel1(backProductTrialParam.getSysId(), null);
        BackProductTrialResult backProductTrialResult=new BackProductTrialResult();
        if(backProductTrial!=null) {
            backProductTrialResult = (BackProductTrialResult) CommonUtil.changeParamtoBean(backProductTrial, new BackProductTrialResult());
        }
        return JSON.toJSONString(backProductTrialResult);
    }

    @Override
    public String addOrder(BackProductOrderParam backProductOrderParam) {
        BackProductOrder backProductOrder= (BackProductOrder) CommonUtil.changeParamtoBean(backProductOrderParam,new BackProductOrder());
        List<String> serviceIds= Arrays.asList(backProductOrderParam.getServiceId().split("[,|，]"));
        int x=0;
        for(String serviceId:serviceIds) {
            backProductOrder.setServiceId(serviceId);
            backProductOrder.setStatus(CheckStatus.UNCHECK);
            x = backProductOrderMapper.insert(backProductOrder);
            BackMessage i=new BackMessage();
            i.setMsgType(NoticeType.service_try.toString());
            i.setApiId(backProductOrderParam.getBusinessName());
            i.setMsgId(backProductOrder.getId());
            backMessageMapper.insert(i);
        }
        return x+"";
    }

    @Override
    public String selOrder(BackProductOrderParam backProductOrderParam) {
        Page page=new Page();
        page.setCurrent(backProductOrderParam.getCurrent());
        page.setSize(backProductOrderParam.getSize());
        List<BackProductInfoResult> list=new ArrayList<>();
        List<BackProductInfo> list1=backProductInfoMapper.getList(page,backProductOrderParam.getBusinessName());
        for(BackProductInfo backProductInfo:list1){
            BackProductInfoResult backProductInfoResult=new BackProductInfoResult();
            backProductInfoResult= (BackProductInfoResult) CommonUtil.changeParamtoBean(backProductInfo,new BackProductInfoResult());
            if(backProductInfo.getServiceStart()!=null && backProductInfo.getServiceEnd()!=null)
                backProductInfoResult.setLeftTime(CommonUtil.differentDays(backProductInfo.getServiceStart(),backProductInfo.getServiceEnd()));
            list.add(backProductInfoResult);
        }
        page.setRecords(list);
        return JSON.toJSONString(page);
    }

    @Override
    public String addMessage(Map<String, Object> requestMap) {
        Integer msgId=Integer.parseInt(requestMap.get("msgId")+"");
        String msgType=requestMap.get("msgType").toString();
        String apiId=requestMap.get("apiId").toString();
        BackMessage i=new BackMessage();
        i.setApiId(apiId);
        i.setMsgType(msgType);
        i.setMsgId(msgId);
        return backMessageMapper.insert(i)+"";
    }

    @Override
    public String selNotice(Map<String, Object> requestMap) {
        BackNotice q=CommonUtil.chgParamtoBean(requestMap,new BackNotice());
        Page page=new Page();
        page.setCurrent(Integer.parseInt(requestMap.get("current").toString()));
        page.setSize(Integer.parseInt(requestMap.get("size").toString()));
        List<String> statuss=new ArrayList<>();
        statuss.add(NoticeStatus.Release.toString());
        q.setStatuss(statuss);
        List<BackNotice> list=backNoticeMapper.pageList(page,q);
        for(BackNotice e:list){
            e.setProductName(ProductType.getCName(e.getProduct()));
            e.setTypeName(NoticeType.getCName(e.getType()));
            e.setStatusName(NoticeStatus.getCName(e.getStatus()));
        }
        return JSON.toJSONString(page.setRecords(list));
    }

    public static void main(String[] args) {
        BackProductTrial backProductTrial=new BackProductTrial();
        backProductTrial.setBusinessName("111");
        BackProductTrialResult backProductTrialResult= (BackProductTrialResult) new BackProductTrial();
        System.out.println(backProductTrialResult.getBusinessName());
    }

}
