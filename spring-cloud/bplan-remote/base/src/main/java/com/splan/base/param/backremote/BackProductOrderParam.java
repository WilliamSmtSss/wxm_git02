package com.splan.base.param.backremote;

import com.splan.base.enums.CheckStatus;
import com.splan.base.param.PageParam;
import lombok.Data;

import java.util.List;

@Data
public class BackProductOrderParam extends PageParam {

    Integer id;

    Integer sysId;

    String businessName;

    String phone;

    String apptype;

    String datatype;

    String game;

    String serviceName;

    String serviceStartTime;

    String serviceEndTime;

    CheckStatus status;

    String businessNameOrPhone;

    String searchText;

    String serviceId;

    List<CheckStatus> statuses;

}
