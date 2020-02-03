package com.splan.xbet.admin.back.enums;

public enum WalletType {
    CHECKED(1,"子钱包"),
    DEFAULT(2,"共享钱包");
    //    CANCELED("CANCELED","未激活");
    private Integer eName;
    private String cName;
    WalletType(Integer eName, String cName){
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
        for(WalletType p:WalletType.values()){
            if(p.geteName()==eName){
                return p.getcName();
            }
        }
        return "";
    }
}
