package com.splan.bplan.result;

import com.splan.base.bean.MemberInterestsBean;
import com.splan.base.enums.Status;
import lombok.Data;

import java.io.Serializable;

@Data
public class MemberResult extends MemberInterestsBean implements Serializable {

    private Integer memberLevel;

    private Status memberStatus;

    private String levelName;

    private String userName;

    private String period;//有效期

    private Integer monthOrderCoin;//当前保级下注额

    private Integer orderCoin;//累计下注额

}
