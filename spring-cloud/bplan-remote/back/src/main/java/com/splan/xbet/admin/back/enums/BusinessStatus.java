package com.splan.xbet.admin.back.enums;

public enum BusinessStatus {
    ENABLE("1","激活"),
    UNABLE("0","未激活");
    private String eName;
    private String cName;
    BusinessStatus(String eName, String cName){
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
