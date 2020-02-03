package com.splan.ash.controller;

import com.splan.base.service.IAshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private IAshService ashService;

    @GetMapping("/test")
    public String getTest(){
        int x = ashService.saveGames();
        return x+"";
    }

    @GetMapping("/test1")
    public String getTest1(Long gameId){
        int x = ashService.saveGameAreas(gameId);
        return x+"";
    }

    @GetMapping("/test2")
    public String getTest2(Long gameId,Long areaId){
        int x = ashService.saveGameLeagues(gameId,areaId);
        return x+"";
    }
}
