package miaad.rh.stagiaire.feign;
import miaad.rh.stagiaire.dto.StagaireDto;
import miaad.rh.stagiaire.dto.StagiaireDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("USER-SERVICE")
public interface StagaireRestClient {

    @GetMapping("/manage/stagiaire/search/emails/{email}")
    StagiaireDto getStagiaireByEmails(@PathVariable("email") String email);
}
