package com.splan.bplan.filter;

import com.alibaba.fastjson.serializer.ValueFilter;

import java.math.BigDecimal;

public class BigDecimalValueFilter implements ValueFilter {

    /*public Object process(Object object,String name,Object value){

        if(null != value&&value instanceof BigDecimal){

            String formatValue = String.format("%.2f",((BigDecimal)value).doubleValue());

            return formatValue;

        }
        return value;
    }*/

    @Override
    public Object process(Object o, String name, Object value) {
        //o是待转换的对象，name是字段名，value是字段值
        if (null != value && value instanceof BigDecimal) {
            return ((BigDecimal) value).setScale( 2,BigDecimal.ROUND_HALF_DOWN ).toString();
        }
        return value;
    }

}
