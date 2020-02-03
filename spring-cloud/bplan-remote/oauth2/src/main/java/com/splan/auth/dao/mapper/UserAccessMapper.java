package com.splan.auth.dao.mapper;

import com.splan.auth.entity.UserAccess;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;


public interface UserAccessMapper {

    List<UserAccess> securitySelectByUserId(@Param("userId") UUID userId);

    List<UserAccess> securitySelectByUserIdWithFakeDoc(@Param("userId") UUID userId);

    int securityInsertRecord(UserAccess record);

    int securityUpdateRecord(UserAccess record);

    int deleteByUserId(@Param("userId") UUID userId);

}
