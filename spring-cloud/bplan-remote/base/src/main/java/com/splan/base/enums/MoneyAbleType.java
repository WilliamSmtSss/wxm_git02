package com.splan.base.enums;

public enum MoneyAbleType {

    BET("下注"),
    BET_REWARD("下注胜利"),
    BET_CANCEL("比赛取消"),
    BET_RECONFIRM("比赛结果错误，重新计算"),
    RECHARGE("充值"),
    ARTIFICIAL("人工"),
    ACTIVITY("活动"),
    WATER("返水"),
    WITHDRAW("取款"),
    EVERYDAY_ACTIVITY("活跃值"),
    TRANSFER("转账"),
    CHA_ACTIVITY("活动2")
    ;

    private String name;

    MoneyAbleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public  static String getCnameByeName(String eName){
        for(MoneyAbleType p:MoneyAbleType.values()){
            if(p.toString().equals(eName)){
                return p.getName();
            }
        }
        return "";
    }
}
