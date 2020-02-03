package com.splan.bplan.mappers;

import com.splan.base.base.SuperMapper;
import com.splan.base.bean.ProxyConfig;
import com.splan.base.bean.UserBalanceBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProxyConfigMapper extends SuperMapper<ProxyConfig> {

    @Select({"select a.*,b.client_secret from proxy_config as a LEFT JOIN open_api.oauth_client_details as b on a.client_id=b.client_id where a.client_id=#{clientId}"})
    ProxyConfig selectProxyConfigByClientId(String clientId);

    @Select({"select * from proxy_config where client_id=#{clientId}"})
    ProxyConfig getProxyConfigByClientId(String clientId);

    @Select("select client_id from proxy_config")
    List<String> getclientids();

    @Select("select * from proxy_config where virtual_client_id=#{virtualClientId}")
    List<ProxyConfig> getProxyConfigByVirtual(String virtualClientId);

    @Select({"SELECT b.*,c.client_secret FROM user_account AS a LEFT JOIN proxy_config AS b ON a.api_id=b.client_id LEFT JOIN open_api.oauth_client_details AS c ON b.client_id=c.client_id WHERE a.id=#{userId} AND a.api_id IS NOT NULL "})
    ProxyConfig getProxyConfigByUserId(Long userId);
}