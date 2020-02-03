package com.splan.auth.rest;

import com.splan.auth.dao.mapper.ClientSecretMapper;
import com.splan.auth.dto.ApiClientDTO;
import com.splan.auth.service.ClientSecretService;
import com.splan.base.bean.OauthClientDetailsBean;
import com.splan.base.service.OauthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * @author keets
 * @date 2017/9/18
 */
//@Path("/")
@RestController
@RequestMapping("/")
public class ClientSecretResource implements OauthClientDetailsService {

    @Autowired
    private ClientSecretService clientSecretService;

    @Autowired
    private ClientSecretMapper clientSecretMapper;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/clients")
    public Response createClient(ApiClientDTO apiClientDTO) {
        clientSecretService.createClientSecret(apiClientDTO);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/clients/{clientId}")
    public Response getClient(@PathParam("clientId") String clientId) {
        ApiClientDTO apiClientDTO = clientSecretService.getClientSecretByClientId(clientId);
        return Response.ok(apiClientDTO).build();
    }


    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/clients/{clientId}")
    public Response updateClient(@PathParam("clientId") String clientId, ApiClientDTO apiClientDTO) {
        clientSecretService.updateClientSecret(apiClientDTO);
        return Response.ok().build();
    }

    @Override
    @GetMapping("/saveClient")
    public int saveClient(String clientId, String clientSecret,String ipWhitelist) {
        return clientSecretMapper.backSave(clientId,clientSecret,ipWhitelist);
    }

    @Override
    @GetMapping("/updateClient")
    public int updateClient(String clientId,String clientSecret,String ipWhitelist) {
        return clientSecretMapper.backUpdate(clientId,clientSecret,ipWhitelist);
    }

    @Override
    @GetMapping("/getClient")
    public Map<String,Object> getClient2(String clientId) {
//        Map<String,Object> param=new HashMap<>();
//        Map<String,Object> result=new HashMap<>();
//        param.put("clientId",clientId);
//        List<ClientSecret> list=clientSecretMapper.selectByParams(param);
//        if(list!=null && list.size()!=0) {
//            ClientSecret clientSecret = list.get(0);
//            result.put("clientSecret", clientSecret.getClientSecret());
//        }
//        return result;

        Map<String,Object> result=new HashMap<>();
        OauthClientDetailsBean bean=clientSecretMapper.backGet(clientId);
        if(null!=bean) {
            result.put("clientSecret", bean.getClientSecret());
            result.put("ipWhitelist", bean.getIpWhitelist());
        }
        else {
            result.put("clientSecret", "");
            result.put("ipWhitelist","");
        }
        return result;
    }

}
