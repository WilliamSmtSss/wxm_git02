package com.splan.xbet.frontback.frontback;

import com.splan.xbet.frontback.frontback.ssh.SSHConnection;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan(basePackages = {"com.splan.xbet.frontback.frontback.mappers"})
@EnableSwagger2
@EnableFeignClients(basePackages = {"com.splan.base.service.v2"})
@EnableDiscoveryClient
@EnableScheduling
public class FrontbackApplication {

    public static void main(String[] args) {
        try {
//            new SSHConnection();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        SpringApplication.run(FrontbackApplication.class, args);
    }

}
