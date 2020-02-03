package com.splan.auth.dao.mapper;

import com.splan.auth.entity.ClientSecret;
import com.splan.base.base.SuperMapper;
import com.splan.base.bean.OauthClientDetailsBean;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface ClientSecretMapper extends SuperMapper<OauthClientDetailsBean> {

    String getScope(@Param("clientId") String clientId, @Param("clientSecret") String clientSecret);


    int insert(ClientSecret record);

    List<ClientSecret> selectByParams(Map map);

    int updateByParams(Map map);

    int updateStatus(Map map);

    ClientSecret getByClientId(@Param("clientId") String clientId);

    @Insert("insert into oauth_client_details (client_id,client_secret,ip_whitelist) values (#{clientId},#{clientSecret},#{ipWhitelist})")
    int backSave(@Param("clientId") String clientId, @Param("clientSecret") String clientSecret,@Param("ipWhitelist") String ipWhitelist);

    @Update("update oauth_client_details set client_secret=#{clientSecret},ip_whitelist=#{ipWhitelist} where client_id=#{clientId}")
    int backUpdate(@Param("clientId") String clientId, @Param("clientSecret") String clientSecret,@Param("ipWhitelist") String ipWhitelist);

    @Select("select ifnull(client_id,'') as client_id,ifnull(client_secret,'') as client_secret,ifnull(ip_whitelist,'') as ip_whitelist from oauth_client_details where client_id=#{clientId}")
    OauthClientDetailsBean backGet(@Param("clientId") String clientId);

    @Select("select client_id,ip_whitelist from oauth_client_details where client_id=#{clientId}")
    OauthClientDetailsBean getWhiteList(@Param("clientId") String clientId);
    
}
