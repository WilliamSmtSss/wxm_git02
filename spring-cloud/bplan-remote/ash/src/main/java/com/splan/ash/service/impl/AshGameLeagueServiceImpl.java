package com.splan.ash.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.ash.mappers.AshGameAreasMapper;
import com.splan.ash.mappers.AshGameLeagueMapper;
import com.splan.ash.service.IAshGameAreaService;
import com.splan.ash.service.IAshGameLeagueService;
import com.splan.base.bean.ash.AshGameAreasBean;
import com.splan.base.bean.ash.AshGameLeagueBean;
import org.springframework.stereotype.Service;

@Service
public class AshGameLeagueServiceImpl extends ServiceImpl<AshGameLeagueMapper, AshGameLeagueBean> implements IAshGameLeagueService {
}
