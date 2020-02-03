package com.splan.bplan.mapping;

import java.util.HashMap;
import java.util.Map;

public class AreaMap {

    private static Map<Integer,String> MAP = new HashMap<>();

    static {
        MAP.put(1,"中国");
        MAP.put(2,"东南亚");
        MAP.put(3,"南美");
        MAP.put(4,"北美");
        MAP.put(5,"欧洲");
        MAP.put(6,"独联体");

    }

    public static String get(Integer key){
        return MAP.get(key);
    }
}
