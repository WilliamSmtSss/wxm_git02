package com.splan.xbet.back.backforbusiness;

import com.splan.xbet.back.backforbusiness.ssh.SSHConnection;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan(basePackages = {"com.splan.xbet.back.backforbusiness.mappers"})
@EnableSwagger2
@EnableFeignClients(basePackages = {"com.splan.base.service.v2","com.splan.base.service"})
@EnableDiscoveryClient
public class BackforbusinessApplication {

    public static void main(String[] args) {
        try {
//            new SSHConnection();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        SpringApplication.run(BackforbusinessApplication.class, args);
    }

}
