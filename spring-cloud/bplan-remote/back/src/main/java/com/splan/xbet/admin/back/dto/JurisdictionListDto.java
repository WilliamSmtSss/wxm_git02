package com.splan.xbet.admin.back.dto;

import com.splan.base.bean.SysPermission;
import lombok.Data;

import java.io.Serializable;
@Data
public class JurisdictionListDto implements Serializable {
    Integer id;
    String permission_name;
    public  JurisdictionListDto(SysPermission sysPermission){
        id=sysPermission.getId();
        permission_name=sysPermission.getPermissionName();
    }
}
