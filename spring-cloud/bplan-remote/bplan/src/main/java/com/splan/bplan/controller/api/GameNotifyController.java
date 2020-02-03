package com.splan.bplan.controller.api;

import com.splan.bplan.service.INotifyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@RestController
//@RequestMapping("/notifyapi/notifiedLeague")
@RequestMapping("/notifyapi/notifiedLeague")
public class GameNotifyController extends BaseController{

    @Value("${bplan.encrypt.privatekey}")
    private String privateKey;

    @Autowired
    private INotifyService notifyService;


    /*@PostMapping("/notify")
    @PutMapping("/notify")*/
    @RequestMapping(method = {RequestMethod.POST,RequestMethod.PUT},value = "/notify")
    @ResponseBody
    public ResponseEntity<String> notifyData(HttpServletRequest request, String data,String encrypted){

        if (data == null){
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
                data = IOUtils.toString(reader);
            } catch (IOException e) {
                log.error(e.toString());
            }
        }

        notifyService.process(data,encrypted,request.getMethod());
        return new ResponseEntity<String>(HttpStatus.OK);
    }



}
