package com.splan.ash.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.ash.mappers.AshGameAreasMapper;
import com.splan.ash.service.IAshGameAreaService;
import com.splan.base.bean.ash.AshGameAreasBean;
import org.springframework.stereotype.Service;

@Service
public class AshGameAreaServiceImpl extends ServiceImpl<AshGameAreasMapper, AshGameAreasBean> implements IAshGameAreaService {
}
