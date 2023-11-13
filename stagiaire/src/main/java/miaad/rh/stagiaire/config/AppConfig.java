package miaad.rh.stagiaire.config;

import miaad.rh.stagiaire.service.PDFGeneratorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public PDFGeneratorService pdfGeneratorService() {
        return new PDFGeneratorService();
    }
}