package com.splan.base.enums;

public enum CheckStatus {
    UNABLE,
    UNCHECK,
    PASS,
    REFUSE;

    public static CheckStatus getCheckStatus(String s){
        for(CheckStatus c:CheckStatus.values()){
            if(s.equals(c.toString()))
                return c;
        }
        return null;
    }

}
