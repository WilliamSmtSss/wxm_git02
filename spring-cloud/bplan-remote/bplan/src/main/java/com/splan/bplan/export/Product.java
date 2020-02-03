package com.splan.bplan.export;

import com.splan.bplan.annotation.ExcelAnnotation;
import lombok.Data;

import java.util.Date;

/**
 * Created by chengcheng on 2017/12/5.
 */
@Data
public class Product {

    @ExcelAnnotation(id=1,name={"产品名称","商品名称"},width = 5000)
    private String name;
    @ExcelAnnotation(id=2,name={"产品价格","商品价格"},width = 5000)
    private double price;
    @ExcelAnnotation(id=3,name={"生产日期"},width = 5000)
    private Date date;

}