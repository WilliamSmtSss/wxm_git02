package com.splan.xbet.admin.back.enums;

public enum ProductSkin {
    LIGHT_COLOUR(1,"浅色"),
    DEEP_COLOUR(0,"深色");
    //    CANCELED("CANCELED","未激活");
    private Integer eName;
    private String cName;
    ProductSkin(Integer eName, String cName){
        this.eName=eName;
        this.cName=cName;
    }

    public Integer geteName() {
        return eName;
    }

    public String getcName() {
        return cName;
    }

    public  static String getCnameByeName(Integer eName){
        for(ProductSkin p:ProductSkin.values()){
            if(p.geteName()==eName){
                return p.getcName();
            }
        }
        return "";
    }
}
