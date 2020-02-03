package com.splan.base.enums;

public enum Language {
    ENGLISH("ENGLISH","英语","en"),
    SIMPLIFIED_CHINESE("SIMPLIFIED_CHINESE","简体中文","zh"),
    TRADITIONAL_CHINESE("TRADITIONAL_CHINESE","繁体中文","tc"),
    VI("VI","越南语","vi")
    ;
    //    CANCELED("CANCELED","未激活");
    private String eName;

    private String cName;

    private String prefix;

    Language(String eName, String cName){
        this.eName = eName;
        this.cName = cName;
    }

    Language(String eName, String cName,String prefix){
        this.eName = eName;
        this.cName = cName;
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String geteName() {
        return eName;
    }

    public String getcName() {
        return cName;
    }

    public  static String getCnameByeName(String eName){
        for(Language p:Language.values()){
            if(p.geteName().equals(eName)){
                return p.getcName();
            }
        }
        return "";
    }
}
