package com.splan.bplan.export.exporbean;

import com.splan.bplan.annotation.ExcelAnnotation;
import lombok.Data;

import java.util.List;

@Data
public class UserDetailExportBean {
    @ExcelAnnotation(id=1,name={"盘口名称"},width = 5000)
    private List<String> betname;

    @ExcelAnnotation(id=1,name={"订单类型"},width = 5000)
    private String ordertype;

    @ExcelAnnotation(id=1,name={"下注额"},width = 5000)
    private String orderamount;

    @ExcelAnnotation(id=1,name={"结算赔率"},width = 5000)
    private String odd;

    @ExcelAnnotation(id=1,name={"下注订单ID"},width = 5000)
    private String orderid;

    @ExcelAnnotation(id=1,name={"下注时间"},width = 5000)
    private String ordertime;

    @ExcelAnnotation(id=1,name={"订单状态"},width = 5000)
    private String orderstatus;

    @ExcelAnnotation(id=1,name={"订单结果"},width = 5000)
    private String orderresult;

    @ExcelAnnotation(id=1,name={"盈利/亏损"},width = 5000)
    private String rewardtotle;
}
