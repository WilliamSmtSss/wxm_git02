package com.splan.xbet.admin.back.enums;

public enum BusRole {
    BUS(3,"商户"),
    BUS_DOWN(4,"下级商户");
    //    CANCELED("CANCELED","未激活");
    private Integer eName;
    private String cName;
    BusRole(Integer eName, String cName){
        this.eName=eName;
        this.cName=cName;
    }

    public Integer geteName() {
        return eName;
    }

    public String getcName() {
        return cName;
    }

}
