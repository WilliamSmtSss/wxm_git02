package com.splan.base.service;

public interface IAshService {

    int saveGames();

    int saveGameAreas(Long gameId);

    int saveGameLeagues(Long gameId,Long areaId);
}
