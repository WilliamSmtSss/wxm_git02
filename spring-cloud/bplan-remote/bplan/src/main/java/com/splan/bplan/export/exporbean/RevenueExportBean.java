package com.splan.bplan.export.exporbean;

import com.splan.bplan.annotation.ExcelAnnotation;
import lombok.Data;

@Data
public class RevenueExportBean {
    @ExcelAnnotation(id=1,name={"日期"},width = 5000)
    private String time;

    @ExcelAnnotation(id=1,name={"盈利/亏损"},width = 5000)
    private String reward;

    @ExcelAnnotation(id=1,name={"投注额"},width = 5000)
    private String orderamount;

    @ExcelAnnotation(id=1,name={"返还额"},width = 5000)
    private String reamount;

    @ExcelAnnotation(id=1,name={"盈利率"},width = 5000)
    private String rate;

    @ExcelAnnotation(id=1,name={"下注用户数"},width = 5000)
    private String usercount;

    @ExcelAnnotation(id=1,name={"新增下注用户数"},width = 5000)
    private String addusercount;

    @ExcelAnnotation(id=1,name={"下注订单数"},width = 5000)
    private String ordercount;

    @ExcelAnnotation(id=1,name={"来源"},width = 5000)
    private String channel;

}
