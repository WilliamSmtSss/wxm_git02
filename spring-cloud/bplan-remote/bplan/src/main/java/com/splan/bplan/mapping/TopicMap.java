package com.splan.bplan.mapping;

import java.util.HashMap;
import java.util.Map;

public class TopicMap {

    private static Map<Integer,String> MAP = new HashMap<>();

    static {
        MAP.put(1,"平均击杀最多队伍");
        MAP.put(2,"平均耗时最长队伍");
        MAP.put(3,"单场助攻最多");
        MAP.put(4,"单场阵亡最低队伍");
        MAP.put(5,"单场击杀最多队伍");
        MAP.put(6,"赢得耗时最长比赛队伍");
        MAP.put(7,"赢得耗时最短比赛队伍");

    }

    public static String get(Integer key){
        return MAP.get(key);
    }
}
