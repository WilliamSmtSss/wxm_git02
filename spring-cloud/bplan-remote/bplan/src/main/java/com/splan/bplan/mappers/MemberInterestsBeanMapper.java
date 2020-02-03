package com.splan.bplan.mappers;

import com.splan.base.base.SuperMapper;
import com.splan.base.bean.MemberInterestsBean;
import com.splan.bplan.config.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;

//@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
@CacheNamespace
public interface MemberInterestsBeanMapper extends SuperMapper<MemberInterestsBean> {


}