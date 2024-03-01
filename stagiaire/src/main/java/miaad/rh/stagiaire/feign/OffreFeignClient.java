package miaad.rh.stagiaire.feign;


import lombok.Builder;
import miaad.rh.stagiaire.dto.OffreDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("OFFRE-STAGE")
public interface OffreFeignClient {
    @GetMapping("/api/stage/offers/{id}")
    OffreDto getStageById(@PathVariable("id") Long id);
}
