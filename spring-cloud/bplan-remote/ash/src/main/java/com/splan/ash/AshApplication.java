package com.splan.ash;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"com.splan.ash.mappers"})
@SpringBootApplication
public class AshApplication {

	public static void main(String[] args) {
		SpringApplication.run(AshApplication.class, args);
	}

}
