package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.BetExampleBean;
import com.splan.base.bean.SysParamBean;
import com.splan.bplan.constants.SysParamConstants;
import com.splan.bplan.mappers.BetExampleBeanMapper;
import com.splan.bplan.mappers.SysParamBeanMapper;
import com.splan.bplan.service.IBetExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BetExampleImpl extends ServiceImpl<BetExampleBeanMapper,BetExampleBean> implements IBetExampleService {

    @Autowired
    private BetExampleBeanMapper betExampleBeanMapper;

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private SysParamBeanMapper sysParamBeanMapper;


    @Override
    public int cacheBetExample() {
        List<BetExampleBean> all = betExampleBeanMapper.selectList(null);
        for (int i = 0; i < all.size(); i++) {
            String key = all.get(i).getTopicableType()+"_"+all.get(i).getCategory()+"_"+all.get(i).getSupport();
            redis.opsForValue().set(key,all.get(i).getExplainText2());
        }
        return all.size();
    }

    @Override
    public int cacheParam() {
        List<SysParamBean> all = sysParamBeanMapper.selectList(null);
        all.forEach((e)-> SysParamConstants.getMap().put(e.getParamName(),e.getParamValue()));
        return all.size();
    }

    @Override
    public BetExampleBean selectBySupport(String topicableType, Integer category, Integer support, Integer gameType) {
        return betExampleBeanMapper.selectBySupport(topicableType, category, support, gameType);
    }
}
