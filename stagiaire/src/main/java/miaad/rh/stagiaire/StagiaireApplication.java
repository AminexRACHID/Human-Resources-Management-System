package miaad.rh.stagiaire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StagiaireApplication {

	public static void main(String[] args) {
		SpringApplication.run(StagiaireApplication.class, args);
	}

}
