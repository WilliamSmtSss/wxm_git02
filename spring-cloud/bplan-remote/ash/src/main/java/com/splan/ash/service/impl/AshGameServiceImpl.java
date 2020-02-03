package com.splan.ash.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.ash.mappers.AshGamesMapper;
import com.splan.ash.service.IAshGameService;
import com.splan.base.bean.ash.AshGamesBean;
import org.springframework.stereotype.Service;

@Service
public class AshGameServiceImpl extends ServiceImpl<AshGamesMapper, AshGamesBean> implements IAshGameService {
}
