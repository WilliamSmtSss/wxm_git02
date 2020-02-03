package com.splan.bplan.utils;

import com.splan.bplan.service.IPayService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PayServiceFactory implements ApplicationContextAware {
    private static Map<String, IPayService> payServiceMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, IPayService> map = applicationContext.getBeansOfType(IPayService.class);
        payServiceMap = new HashMap<>();
        map.forEach((key, value) -> {
            payServiceMap.put(value.getPayType(), value);
        });
    }

    public static <T extends IPayService> T getPayService(String payType) {
        return (T)payServiceMap.get(payType);
    }
}
