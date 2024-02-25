package com.formations.service_formation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ServiceFormationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceFormationApplication.class, args);
	}

}
