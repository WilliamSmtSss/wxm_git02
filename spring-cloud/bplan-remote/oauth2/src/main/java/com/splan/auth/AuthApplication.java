package com.splan.auth;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author keets
 * @date 2016/12/5
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//@MapperScan(basePackages = {"com.splan.auth.dao.mapper"})
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}