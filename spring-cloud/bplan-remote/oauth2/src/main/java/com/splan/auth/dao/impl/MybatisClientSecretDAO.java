package com.splan.auth.dao.impl;

import com.splan.auth.dao.ClientSecretDAO;
import com.splan.auth.dao.mapper.ClientSecretMapper;
import com.splan.auth.entity.ClientSecret;
import com.splan.auth.entity.ClientSecretStatus;
import com.splan.base.bean.OauthClientDetailsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class MybatisClientSecretDAO implements ClientSecretDAO {

    @Autowired
    private ClientSecretMapper mapper;

    @Override
    public int create(ClientSecret clientSecret) {
        return mapper.insert(clientSecret);
    }

    @Override
    public String getScope(String clientId, String clientSecret) {
        return mapper.getScope(clientId,clientSecret);
    }

    @Override
    public List<ClientSecret> get(ClientSecret clientSecret) {
        Map<String, Object> params = new HashMap<>();
        params.put("clientId", clientSecret.getClientId());
        return mapper.selectByParams(params);
    }

    @Override
    public int updateStatusByTenantId(UUID tenantId, ClientSecretStatus status) {
        Map<String, Object> params = new HashMap<>();
        params.put("tenantId", tenantId);
        params.put("status", status.toString());

        return mapper.updateStatus(params);
    }

    @Override
    public int updateStatusByClientId(String clientId, ClientSecretStatus status) {
        Map<String, Object> params = new HashMap<>();
        params.put("clientId", clientId);
        params.put("status", status.toString());

        return mapper.updateStatus(params);
    }

    @Override
    public int update(ClientSecret clientSecret) {
        Map<String, Object> params = new HashMap<>();
        params.put("clientId", clientSecret.getClientId());
        params.put("clientSecret", clientSecret.getClientSecret());
        params.put("tenantId", clientSecret.getTenantId());
        params.put("purpose", clientSecret.getPurpose());
        params.put("status", clientSecret.getStatus().toString());

        return mapper.updateByParams(params);
    }

    @Override
    public ClientSecret getByClientId(String clientId) {
        return mapper.getByClientId(clientId);
    }

    @Override
    public OauthClientDetailsBean getWhiteList(String clientId) {
        return mapper.getWhiteList(clientId);
    }
}
