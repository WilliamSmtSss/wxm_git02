package com.splan.xbet.admin.back.task;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.splan.base.bean.XExportBean;
import com.splan.xbet.admin.back.config.Myconfig;
import com.splan.xbet.admin.back.constants.Constants;
import com.splan.xbet.admin.back.mappers.XBetCacheMapper;
import com.splan.xbet.admin.back.mappers.XExportMapper;
import com.splan.xbet.admin.back.netty.utils.SpringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Component
public class Task {

    @Autowired
    private XExportMapper xExportMapper;

    @Autowired
    private RedisTemplate redisTemplate;


// 清除过期的EXCEL文件
    @Scheduled(cron = "0 0 0 * * ?")
    private void clearExpireFile(){
        List<XExportBean> list=xExportMapper.getExpires();
        List<String> urls=new ArrayList<>();
        List<Integer> ids=new ArrayList<>();
        String url="";
        for(XExportBean xExportBean:list){
            if(StringUtils.isNotBlank(xExportBean.getUrl())) {
                url = xExportBean.getUrl();
                url = url.substring(url.lastIndexOf("/") + 1);
                urls.add(url);
            }
        };
        File file=new File(Myconfig.getProfile());
        if(file.isDirectory()){
            File[] files=file.listFiles();
            for(File f:files){
                if(urls.contains(f.getName())){
                    f.delete();
                }
            }
        }
        xExportMapper.delete(new UpdateWrapper<XExportBean>().in("id",ids));
    }

//    清除sql缓存
    @Scheduled(cron = "0 0 0 1 * ?")
//    @Scheduled(cron = "0 0/1 * * * ?")
    private void clearSqlCache(){
        if (redisTemplate == null) {
            //由于启动期间注入失败，只能运行期间注入，这段代码可以删除
            redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
            RedisSerializer redisSerializer=new StringRedisSerializer();
            redisTemplate.setStringSerializer(redisSerializer);
        }
        Set<String> keys = redisTemplate.keys("*:"+ XBetCacheMapper.class.getName()+"*");
        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
        }

    }

    public static void main(String[] args) {
        String url="/back/excelTemp/cc52c3c4-daad-4670-9541-a80ccb4a15ca.xls";
        System.out.println(url.lastIndexOf("/"));
        url=url.substring(url.lastIndexOf("\\\\/")+1);
        System.out.println(url);
    }

}
