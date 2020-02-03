package com.splan.xbet.back.backforbusiness.listener;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.splan.xbet.back.backforbusiness.contantes.Constants;
import com.splan.xbet.back.backforbusiness.contantes.RedisContant;
import com.splan.xbet.back.backforbusiness.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Set;

@WebListener
@Component
public class WebListen implements ServletContextListener {
//    @Autowired
//    StringRedisTemplate redisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.err.println("contextInitialized...");
//        Set<String> keys = redisTemplate.keys("*:"+ BussinessBackCacheMapper.class.getName() + "*");
//        Set<String> shiroKeys = redisTemplate.keys(Constants.SHIRO_SESSION+"*");
//        keys.addAll(shiroKeys);
//        if (!CollectionUtils.isEmpty(keys)) {
//            redisTemplate.delete(keys);
//        }

        if (redisTemplate == null) {
            //由于启动期间注入失败，只能运行期间注入，这段代码可以删除
            redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
            RedisSerializer redisSerializer=new StringRedisSerializer();
            redisTemplate.setStringSerializer(redisSerializer);
        }
        Set<String> shiroKeys = redisTemplate.keys(Constants.SHIRO_SESSION+"*");
        if (!CollectionUtils.isEmpty(shiroKeys)) {
            redisTemplate.delete(shiroKeys);
        }

        Set<String> emailKeys = redisTemplate.keys(RedisContant.EMAIL+"*");
        if (!CollectionUtils.isEmpty(emailKeys)) {
            redisTemplate.delete(emailKeys);
        }

        Set<String> msgKeys = redisTemplate.keys(RedisContant.MSG+"*");
        if (!CollectionUtils.isEmpty(msgKeys)) {
            redisTemplate.delete(msgKeys);
        }

    }

}
