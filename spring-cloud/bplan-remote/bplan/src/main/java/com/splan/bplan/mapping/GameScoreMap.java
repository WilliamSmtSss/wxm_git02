package com.splan.bplan.mapping;

import java.util.HashMap;
import java.util.Map;

public class GameScoreMap {


    private static Map<String,String> MAP = new HashMap<>();

    static {
        MAP.put("3_1","0:2");
        MAP.put("3_2","1:2");
        MAP.put("3_3","2:0");
        MAP.put("3_4","2:1");

        MAP.put("5_1","0:3");
        MAP.put("5_2","1:3");
        MAP.put("5_3","2:3");
        MAP.put("5_4","3:0");
        MAP.put("5_5","3:1");
        MAP.put("5_6","3:2");

    }

    public static String get(Integer bo,Integer se){
        return MAP.get(bo+"_"+se);
    }

}
