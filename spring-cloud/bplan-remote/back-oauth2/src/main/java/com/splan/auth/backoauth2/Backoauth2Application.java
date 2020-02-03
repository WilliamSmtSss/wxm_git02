package com.splan.auth.backoauth2;

import com.splan.auth.backoauth2.ssh.SSHConnection;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan(basePackages = {"com.splan.auth.backoauth2.mappers"})
@EnableDiscoveryClient
public class Backoauth2Application {

    public static void main(String[] args) {
        try {
//            new SSHConnection();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        SpringApplication.run(Backoauth2Application.class, args);
    }

}
