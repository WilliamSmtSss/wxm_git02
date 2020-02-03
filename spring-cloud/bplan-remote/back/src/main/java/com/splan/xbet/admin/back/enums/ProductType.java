package com.splan.xbet.admin.back.enums;

public enum ProductType {
    X("X","X电竞"),
    R("R","RISE"),
    API("API","API文档");
    //    CANCELED("CANCELED","未激活");
    private String eName;
    private String cName;
    ProductType(String eName, String cName){
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
