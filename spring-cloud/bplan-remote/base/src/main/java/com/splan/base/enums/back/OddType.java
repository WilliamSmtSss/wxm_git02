package com.splan.base.enums.back;

import com.splan.base.enums.Language;

public enum OddType {
    EP("EP","欧赔"),
    HK("HK","港赔");
    private String eName;
    private String cName;

    OddType(String eName,String cName){
        this.eName=eName;
        this.cName=cName;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public  static String getCnameByeName(String eName){
        for(OddType p:OddType.values()){
            if(p.geteName().equals(eName)){
                return p.getcName();
            }
        }
        return "";
    }

}
