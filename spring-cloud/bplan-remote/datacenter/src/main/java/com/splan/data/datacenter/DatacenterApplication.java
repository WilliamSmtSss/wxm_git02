package com.splan.data.datacenter;

import com.splan.data.datacenter.ssh.SSHConnection;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan(basePackages = {"com.splan.data.datacenter.mapper"})
@EnableSwagger2
public class DatacenterApplication {

    public static void main(String[] args) {
        try {
//            new SSHConnection();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        SpringApplication.run(DatacenterApplication.class, args);
    }

}
