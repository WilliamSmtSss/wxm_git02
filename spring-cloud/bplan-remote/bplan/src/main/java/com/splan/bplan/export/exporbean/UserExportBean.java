package com.splan.bplan.export.exporbean;

import com.splan.bplan.annotation.ExcelAnnotation;
import lombok.Data;

@Data
public class UserExportBean {
    @ExcelAnnotation(id=1,name={"用户ID"},width = 5000)
    private String userid;

    @ExcelAnnotation(id=1,name={"用户下注额"},width = 5000)
    private String useramount;

    @ExcelAnnotation(id=1,name={"盈利/亏损"},width = 5000)
    private String rewardtotles;

    @ExcelAnnotation(id=1,name={"盈利率"},width = 5000)
    private String ratedata;

    @ExcelAnnotation(id=1,name={"用户状态"},width = 5000)
    private String userstatus;

    @ExcelAnnotation(id=1,name={"风控原因"},width = 5000)
    private String riskreason;

    @ExcelAnnotation(id=1,name={"风控建议"},width = 5000)
    private String riskadvise;
}
