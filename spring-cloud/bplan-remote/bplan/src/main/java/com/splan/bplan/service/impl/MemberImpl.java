package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.MemberInterestsBean;
import com.splan.bplan.mappers.MemberInterestsBeanMapper;
import com.splan.bplan.service.IMemberService;
import org.springframework.stereotype.Service;

@Service
public class MemberImpl extends ServiceImpl<MemberInterestsBeanMapper, MemberInterestsBean> implements IMemberService {
    @Override
    public int update(MemberInterestsBean update) {
        return baseMapper.updateById(update);
    }
}
