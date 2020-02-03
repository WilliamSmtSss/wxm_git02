package com.splan.xbet.admin.back.result;

import com.splan.base.bean.SysPermission;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class JurisdictionResult implements Serializable {
    private String menuName;
    private List<SysPermission> sysPermissions;
}
