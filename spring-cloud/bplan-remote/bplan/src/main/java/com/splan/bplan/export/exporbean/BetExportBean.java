package com.splan.bplan.export.exporbean;

import com.splan.bplan.annotation.ExcelAnnotation;
import lombok.Data;

@Data
public class BetExportBean {
    @ExcelAnnotation(id=1,name={"游戏类型"},width = 5000)
    private String nameEn;

    @ExcelAnnotation(id=1,name={"盘口ID"},width = 5000)
    private String betID;

    @ExcelAnnotation(id=1,name={"比赛ID"},width = 5000)
    private String dataID;

    @ExcelAnnotation(id=1,name={"比赛开始时间"},width = 5000)
    private String startTime;

    @ExcelAnnotation(id=1,name={"比赛队伍"},width = 5000)
    private String teams;

    @ExcelAnnotation(id=1,name={"盘口名称"},width = 5000)
    private String betName;

    @ExcelAnnotation(id=1,name={"结算状态"},width = 5000)
    private String status;

    @ExcelAnnotation(id=1,name={"盘口结果"},width = 5000)
    private String betresult;

    @ExcelAnnotation(id=1,name={"下注额"},width = 5000)
    private String orderAmount;

    @ExcelAnnotation(id=1,name={"返还额"},width = 5000)
    private String returndAmount;

    @ExcelAnnotation(id=1,name={"订单量"},width = 5000)
    private String orderCount;

    @ExcelAnnotation(id=1,name={"下注用户数"},width = 5000)
    private String userCount;

    @ExcelAnnotation(id=1,name={"盈利/亏损"},width = 5000)
    private String settledAmount;

    @ExcelAnnotation(id=1,name={"盈利率"},width = 5000)
    private String settledRate;
}
