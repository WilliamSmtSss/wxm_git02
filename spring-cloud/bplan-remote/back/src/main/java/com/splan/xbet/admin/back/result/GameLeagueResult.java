package com.splan.xbet.admin.back.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class GameLeagueResult implements Serializable {

    private Integer leagueId;

    private String name;

    private Integer gameCount;
}
