package com.splan.base.param.backremote;

import com.splan.base.enums.CheckStatus;
import com.splan.base.param.PageParam;
import lombok.Data;

import java.util.List;

@Data
public class BackProductTrialParam extends PageParam {

     Integer id;

     Integer sysId;

     String businessName;

     String phone;

     String trialAdmin;

     String trialPassword;

     CheckStatus status;

     String searchText;

     List<CheckStatus> statuses;
}
