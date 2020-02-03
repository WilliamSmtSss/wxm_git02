package com.splan.bplan.export.exporbean;

import com.splan.bplan.annotation.ExcelAnnotation;
import lombok.Data;

import java.util.List;

@Data
public class OrderExportBean {
    @ExcelAnnotation(id=1,name={"用户ID"},width = 5000)
    private String userid;

    @ExcelAnnotation(id=1,name={"下注盘口"},width = 5000)
    private List<String> betname;

    @ExcelAnnotation(id=1,name={"类型"},width = 5000)
    private String type;

    @ExcelAnnotation(id=1,name={"下注额"},width = 5000)
    private String amount;

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
    private String rewardtotles;

}
