package com.splan.bplan.constants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class GiftTaskConstants {
    public static Map<String,Object> map = new HashMap<>();

    public static Map<String, Object> getMap() {
        return map;
    }


    public static Object get(String param){
        return getMap().get(param);
    }

    public static Integer getInteger(String param){
        return Integer.valueOf(get(param).toString());
    }

    public static String getString(String param){
        return getMap().get(param).toString();
    }

    public static BigDecimal getBigDecimal(String param){
        return new BigDecimal(getMap().get(param).toString());
    }
}
