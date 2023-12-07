package miaad.rh.administration.config;

import miaad.rh.administration.service.AttestationGeneratorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public AttestationGeneratorService attestationGeneratorService() {
        return new AttestationGeneratorService();
    }
}
