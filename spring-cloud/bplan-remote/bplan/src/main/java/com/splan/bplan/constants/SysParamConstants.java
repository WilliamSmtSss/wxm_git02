package com.splan.bplan.constants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class SysParamConstants {

    public static String PAY_REVIEW_FLAG = "PAY_REVIEW_FLAG";

    public static String BALANCE_GIFT_COIN = "BALANCE_GIFT_COIN";

    public static String ORDER_LIMIT_MAX = "ORDER_LIMIT_MAX";

    public static String ORDER_LIMIT_MIN = "ORDER_LIMIT_MIN";

    public static String NORMAL_COM_PERCENT = "NORMAL_COM_PERCENT";

    public static String ORDER_PROFIT_LIMIT_MAX = "ORDER_PROFIT_LIMIT_MAX";

    public static String LIMIT_CHARGE = "LIMIT_CHARGE";

    public static String LIMIT_WATER = "LIMIT_WATER";

    public static String LIMIT_GIFT = "LIMIT_GIFT";

    public static String LIMIT_EVERYDAY_ACTIVITY = "LIMIT_EVERYDAY_ACTIVITY";

    public static String LIMIT_GIFT_DATE = "LIMIT_GIFT_DATE";

    public static String REDIRECT_URI = "REDIRECT_URI";

    public static String HOT_GAME = "HOT_GAME";

    public static Map<String,String> map = new HashMap<>();

    public static Map<String, String> getMap() {
        return map;
    }


    public static Object get(String param){
        return getMap().get(param);
    }

    public static Integer getInteger(String param){
        return Integer.valueOf(get(param).toString());
    }

    public static String getString(String param){
        return getMap().get(param);
    }

    public static BigDecimal getBigDecimal(String param){
        return new BigDecimal(getMap().get(param));
    }


}
