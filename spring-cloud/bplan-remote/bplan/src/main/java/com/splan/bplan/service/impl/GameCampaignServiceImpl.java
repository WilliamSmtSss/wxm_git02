package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.GameCampaignBean;
import com.splan.bplan.mappers.GameCampaignBeanMapper;
import com.splan.bplan.service.IGameCampaignService;
import org.springframework.stereotype.Service;

@Service
public class GameCampaignServiceImpl extends ServiceImpl<GameCampaignBeanMapper, GameCampaignBean> implements IGameCampaignService {
}
