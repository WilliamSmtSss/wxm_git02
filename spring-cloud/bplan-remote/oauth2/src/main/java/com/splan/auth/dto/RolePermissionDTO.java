package com.splan.auth.dto;

import com.splan.auth.entity.Permission;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Created by keets on 2017/11/22.
 */
@Data
public class RolePermissionDTO {

    private Long relationId;

    private UUID roleId;

    private String name;

    private Timestamp updateTime;

    private String description;

    private List<Permission> permissions;
}
