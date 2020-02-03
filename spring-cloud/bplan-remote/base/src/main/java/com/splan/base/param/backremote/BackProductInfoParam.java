package com.splan.base.param.backremote;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.back.BackProductInfo;
import com.splan.base.enums.CheckStatus;
import com.splan.base.param.PageParam;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BackProductInfoParam extends PageParam {

     Integer id;

//     @NotEmpty
     String serviceName;
     @NotEmpty
     String dataType;

     String serviceType;
     @NotEmpty
     String appType;
//     @NotEmpty
     String serviceDescribe;
     @NotNull
     boolean status;

     CheckStatus checkStatus;

}
