package com.splan.ash.service.impl;

import com.splan.ash.service.IAshGameAreaService;
import com.splan.ash.service.IAshGameLeagueService;
import com.splan.ash.service.IAshGameService;
import com.splan.ash.util.PandoraUtil;
import com.splan.base.bean.ash.AshGameAreasBean;
import com.splan.base.bean.ash.AshGameLeagueBean;
import com.splan.base.bean.ash.AshGamesBean;
import com.splan.base.http.PandoraListResult;
import com.splan.base.service.IAshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AshServiceImpl implements IAshService {


    @Autowired
    private IAshGameService ashGameService;

    @Autowired
    private IAshGameAreaService ashGameAreaService;

    @Autowired
    private IAshGameLeagueService ashGameLeagueService;

    @Override
    public int saveGames() {
        String url = "api/v1/games";
        Map<String, Object> data = new HashMap<>();
        data.put("limit",10);
        data.put("offset",0);
        PandoraListResult<AshGamesBean> ss = PandoraUtil.httpListGet(url,data,AshGamesBean.class);
        if (ss.getResult()!=null && ss.getResult().getTotal()>0){
            ashGameService.saveOrUpdateBatch(ss.getResult().getItems());
            return ss.getResult().getTotal().intValue();
        }

        return 0;
    }

    @Override
    public int saveGameAreas(Long gameId) {

        String url = "api/v1/game/areas";
        Map<String, Object> data = new HashMap<>();
        data.put("limit",20);
        data.put("offset",0);
        data.put("game_id",gameId);
        PandoraListResult<AshGameAreasBean> ss = PandoraUtil.httpListGet(url,data, AshGameAreasBean.class);
        if (ss.getResult()!=null && ss.getResult().getTotal()>0){
            ashGameAreaService.saveOrUpdateBatch(ss.getResult().getItems());
            return ss.getResult().getTotal().intValue();
        }
        return 0;
    }

    @Override
    public int saveGameLeagues(Long gameId, Long areaId) {
        String url = "api/v1/game/leagues";
        Map<String, Object> data = new HashMap<>();
        data.put("limit",100);
        data.put("offset",0);
        data.put("game_id",gameId);
        if (areaId!=null){
            data.put("area_id",areaId);
        }
        PandoraListResult<AshGameLeagueBean> ss = PandoraUtil.httpListGet(url,data, AshGameLeagueBean.class);
        if (ss.getResult()!=null && ss.getResult().getTotal()>0){
            ashGameLeagueService.saveOrUpdateBatch(ss.getResult().getItems());
            return ss.getResult().getTotal().intValue();
        }
        return 0;
    }
}
