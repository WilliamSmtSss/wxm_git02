package com.splan.auth.dao.mapper;

import com.splan.base.bean.ProxyConfig;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProxyConfigMapper{

    @Select({"select a.*,b.client_secret from proxy_config as a LEFT JOIN oauth_client_details as b on a.client_id=b.client_id where a.client_id=#{clientId}"})
    ProxyConfig selectProxyConfigByClientId(String clientId);

    @Select({"select * from proxy_config where client_id=#{clientId}"})
    ProxyConfig getProxyConfigByClientId(String clientId);

    @Select("select client_id from proxy_config")
    List<String> getclientids();

    @Select("select * from proxy_config where virtual_client_id=#{virtualClientId}")
    List<ProxyConfig> getProxyConfigByVirtual(String virtualClientId);

    @Select({"SELECT b.*,c.client_secret FROM user_account AS a LEFT JOIN proxy_config AS b ON a.api_id=b.client_id LEFT JOIN open_api.oauth_client_details AS c ON b.client_id=c.client_id WHERE a.id=#{userId} AND a.api_id IS NOT NULL "})
    ProxyConfig getProxyConfigByUserId(Long userId);

//    @Insert("insert into proxy_config ('client_id','virtual_client_id','url','encode','type','status','redirect_uri','has_extra','push_config') values (#{clientId},'')")
    @Insert("insert into proxy_config (client_id,virtual_client_id,url,encode,type,status,redirect_uri,has_extra,push_config) values (#{clientId},'','',0,'1','ENABLE','1',0,0)")
    int save(String clientId);
}