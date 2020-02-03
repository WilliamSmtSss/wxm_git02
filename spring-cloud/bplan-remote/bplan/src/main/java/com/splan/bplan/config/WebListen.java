package com.splan.bplan.config;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.splan.bplan.mappers.BussinessBackCacheMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Set;

@WebListener
@Component
public class WebListen implements ServletContextListener {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.err.println("contextInitialized...");
        Set<String> keys = redisTemplate.keys("*:"+ BussinessBackCacheMapper.class.getName() + "*");
        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }

}
