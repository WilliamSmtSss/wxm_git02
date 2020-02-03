package com.splan.bplan.mapping;

import java.util.HashMap;
import java.util.Map;

public class MessageMap{


    private static Map<String,String> MAP = new HashMap<>();

    static {
        MAP.put("operation_101","取消");
        MAP.put("operation_102","清盘");
        MAP.put("operation_103","重新结算");

        MAP.put("series_0","全部");
        MAP.put("topic_0","全局");

        MAP.put("reason_1001","比赛改期");
        MAP.put("reason_1002","比赛取消");
        MAP.put("reason_1003","数据有误");
        MAP.put("reason_1004","比赛有风险");

        MAP.put("reason_1005","赛事/盘口/注单出现异常行为");
        MAP.put("reason_1006","赛事改期");
        MAP.put("reason_1007","赛事取消/延迟");
        MAP.put("reason_1008","提前开赛");
        MAP.put("reason_1009","技术错误");
        MAP.put("reason_1010","人员大幅变动");
        MAP.put("reason_1011","错误显示赔率");
        MAP.put("reason_1012","赛果无法得知");
        MAP.put("reason_1013","弃权");

    }

    public static String get(String key,Integer code){
        return MAP.get(key+"_"+code);
    }

    public static void main(String[] args) {
        System.out.println(get("operation",101));
    }
}
