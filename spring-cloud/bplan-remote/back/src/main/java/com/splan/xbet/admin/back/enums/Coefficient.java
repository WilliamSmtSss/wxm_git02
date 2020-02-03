package com.splan.xbet.admin.back.enums;

public enum Coefficient {
    COEFFICIENT1(0,"0%"),
    COEFFICIENT2(10,"10%"),
    COEFFICIENT3(20,"20%"),
    COEFFICIENT4(30,"30%"),
    COEFFICIENT5(40,"40%"),
    COEFFICIENT6(50,"50%"),
    COEFFICIENT7(60,"60%"),
    COEFFICIENT8(70,"70%"),
    COEFFICIENT9(80,"80%"),
    COEFFICIENT10(90,"90%"),
    COEFFICIENT11(100,"100%");
    //    CANCELED("CANCELED","未激活");
    private Integer eName;
    private String cName;
    Coefficient(Integer eName, String cName){
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
        for(Coefficient p:Coefficient.values()){
            if(p.geteName()==eName){
                return p.getcName();
            }
        }
        return "";
    }
}
