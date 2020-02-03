package com.splan.xbet.frontback.frontback.task;

import com.splan.xbet.frontback.frontback.utils.CommonUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Task {

    static final String suff="http://66cb92e0.ngrok.io/frontBack/login/getInfo";

    @Scheduled(cron = "0 */1 * * * ?")
    private void health(){
//        String result = CommonUtil.post(suff,null);
//        System.out.println("1");
    }

}
