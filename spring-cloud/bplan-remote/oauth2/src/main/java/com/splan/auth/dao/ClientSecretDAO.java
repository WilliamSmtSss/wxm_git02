package com.splan.auth.dao;

import com.splan.auth.entity.ClientSecret;
import com.splan.auth.entity.ClientSecretStatus;
import com.splan.base.bean.OauthClientDetailsBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.UUID;


@Mapper
public interface ClientSecretDAO {

    int create(ClientSecret clientSecret);

    String getScope(String clientId, String clientSecret);

    List<ClientSecret> get(ClientSecret clientSecret);

    int updateStatusByTenantId(UUID tenantId, ClientSecretStatus status);

    int updateStatusByClientId(String clientId, ClientSecretStatus status);

    int update(ClientSecret clientSecret);

    ClientSecret getByClientId(String clientId);

    OauthClientDetailsBean getWhiteList(String clientId);
}
