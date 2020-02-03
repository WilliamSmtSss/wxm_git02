package com.splan.bplan.result;

import com.splan.base.bean.ActivityListBean;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ActiveResult implements Serializable {

    private List<ActivityListBean> list;

    private int dayActive;

    private int weekActive;
}
