package com.splan.xbet.admin.back.listener;

import com.splan.xbet.admin.back.constants.Constants;
import com.splan.xbet.admin.back.netty.utils.SpringUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ShiroListener implements SessionListener {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void onStart(Session session) {
        if (redisTemplate == null) {
            //由于启动期间注入失败，只能运行期间注入，这段代码可以删除
            redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
            RedisSerializer redisSerializer=new StringRedisSerializer();
            redisTemplate.setStringSerializer(redisSerializer);
        }
        Map<String,String> map=new HashMap<>();
        if(null!=redisTemplate.opsForValue().get(Constants.SHIRO_SESSION)){
            map=(HashMap<String,String>)redisTemplate.opsForValue().get(Constants.SHIRO_SESSION);
        }
        map.put(session.getId()+"","");
        redisTemplate.opsForValue().set(Constants.SHIRO_SESSION,map);

    }

    @Override
    public void onStop(Session session) {
        if (redisTemplate == null) {
            //由于启动期间注入失败，只能运行期间注入，这段代码可以删除
            redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
            RedisSerializer redisSerializer=new StringRedisSerializer();
            redisTemplate.setStringSerializer(redisSerializer);
        }
        if(null!=redisTemplate.opsForValue().get(Constants.SHIRO_SESSION)){
            Map<String,String> map=(HashMap<String,String>)redisTemplate.opsForValue().get(Constants.SHIRO_SESSION);
            map.remove(session.getId()+"");
            redisTemplate.opsForValue().set(Constants.SHIRO_SESSION,map);
        }
    }

    @Override
    public void onExpiration(Session session) {
        if (redisTemplate == null) {
            //由于启动期间注入失败，只能运行期间注入，这段代码可以删除
            redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
            RedisSerializer redisSerializer=new StringRedisSerializer();
            redisTemplate.setStringSerializer(redisSerializer);
        }
        if(null!=redisTemplate.opsForValue().get(Constants.SHIRO_SESSION)){
            Map<String,String> map=(HashMap<String,String>)redisTemplate.opsForValue().get(Constants.SHIRO_SESSION);
            map.remove(session.getId()+"");
            redisTemplate.opsForValue().set(Constants.SHIRO_SESSION,map);
        }
    }

}
