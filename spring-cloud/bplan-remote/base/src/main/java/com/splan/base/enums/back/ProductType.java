package com.splan.base.enums.back;

public enum ProductType {

    embeds("嵌入式"),
    data("赔率数据");

    private String name;

    ProductType(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public static String getCName(String eName){
        for(ProductType e:ProductType.values()){
            if(e.toString().equals(eName))
                return e.getName();
        }
        return "";
    }

}
