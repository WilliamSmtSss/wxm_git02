package com.splan.xbet.admin.back.enums;

public enum OrderStatus {
    FAIL("FAIL","失败"),
    UNSETTLED("UNSETTLED","未结算"),
    SETTLED("SETTLED","结算");
    private String eName;
    private String cName;
    OrderStatus(String eName, String cName){
        this.eName=eName;
        this.cName=cName;
    }

    public String geteName() {
        return eName;
    }

    public String getcName() {
        return cName;
    }

    public static String getCname(String eName){
        for(OrderStatus orderStatus:OrderStatus.values()){
            if(eName.equals(orderStatus.geteName())){
                return orderStatus.getcName();
            }
        }
        return "";
    }
}
