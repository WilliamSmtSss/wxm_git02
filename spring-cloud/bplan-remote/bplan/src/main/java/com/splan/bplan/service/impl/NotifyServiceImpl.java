package com.splan.bplan.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.splan.bplan.http.*;
import com.splan.bplan.service.INoticeService;
import com.splan.bplan.service.INotifyService;
import com.splan.bplan.service.ISeriesGameService;
import com.splan.bplan.utils.JsonUtil;
import com.splan.bplan.utils.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotifyServiceImpl implements INotifyService {

    private static final String SERIES = "series";

    private static final String CAMPAIGN = "campaign";

    private static final String TOPIC = "topic";

    private static final String NOTIFICATION = "notification";

    private static final String CANCEL_ORDER = "cancel_order";

    @Value("${bplan.encrypt.privatekey}")
    private String privateKey;

    @Autowired
    private ISeriesGameService seriesGameService;


    @Override
    public String process(String data,String encrypted,String method) {
        log.info("===================================================="+data);
        log.info("=======encrypted======    "+encrypted);


        JSONObject jsonObject = JSON.parseObject(data);
        //String encrypted = jsonObject.getString("encrypted");
        try {
            String mode = RSAUtil.privateDecrypt(encrypted,privateKey);
            log.info("=======decrypted======    "+mode);
            BaseGameNotify baseGameNotify = JsonUtil.stringToObject(mode, BaseGameNotify.class);
            String data2 = jsonObject.getString("data");
            if (baseGameNotify.getType().equals(SERIES)){
                processSeries(data2,baseGameNotify,method);
            }else if (baseGameNotify.getType().equals(CAMPAIGN)){
                processCampaign(data2,baseGameNotify,method);
            }else if (baseGameNotify.getType().equals(TOPIC)){
                processTopic(data2,baseGameNotify,method);
            }else if (baseGameNotify.getType().equals(NOTIFICATION)){
                processNotification(data2,baseGameNotify,method);
            }else if (baseGameNotify.getType().equals(CANCEL_ORDER)){
                processCancelOrder(data2,baseGameNotify,method);
            }

        } catch (Exception e) {
            log.error(e.toString());
        }
        return null;
    }

    private String processCancelOrder(String data2, BaseGameNotify baseGameNotify, String method) {
        CancelOrderNotify cancelOrderNotify = JsonUtil.stringToObject(data2, CancelOrderNotify.class);
        return seriesGameService.cancelOrder(cancelOrderNotify,baseGameNotify,method);
    }


    private String processSeries(String data,BaseGameNotify baseGameNotify,String method ){
        SeriesGameNotify seriesGameNotify = JsonUtil.stringToObject(data, SeriesGameNotify.class);
        return seriesGameService.saveSeries(seriesGameNotify,baseGameNotify,method);
    }

    private String processCampaign(String data,BaseGameNotify baseGameNotify ,String method){
        return "";
    }

    private String processTopic(String data,BaseGameNotify baseGameNotify ,String method){
        BetGameNotify betGameNotify = JsonUtil.stringToObject(data,BetGameNotify.class);

        return seriesGameService.saveTopic(betGameNotify,baseGameNotify,method);
    }

    private String processNotification(String data, BaseGameNotify baseGameNotify, String method) {
        NotificationNotify notificationNotify = JsonUtil.stringToObject(data,NotificationNotify.class);
        return seriesGameService.saveNotification(notificationNotify,baseGameNotify,method);
    }



}
