package com.splan.xbet.admin.back.enums;

public enum BetStatus {
    CHECKED("CHECKED","已结算"),
    DEFAULT("DEFAULT","未结算");
//    CANCELED("CANCELED","未激活");
    private String eName;
    private String cName;
    BetStatus(String eName, String cName){
        this.eName=eName;
        this.cName=cName;
    }

    public String geteName() {
        return eName;
    }

    public String getcName() {
        return cName;
    }
}
