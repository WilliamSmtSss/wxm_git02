package com.splan.xbet.admin.back.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BetOrderResult implements Serializable,Comparable<BetOrderResult> {

    private String mobile;

    private Integer amount;

    private Date createTime;

    @Override
    public int compareTo(BetOrderResult o) {
        if (this.createTime.before(o.getCreateTime())){
            return 1;
        }
        return -1;
    }
}
