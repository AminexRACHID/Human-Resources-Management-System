package miaad.rh.stagiaire.feign;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("STAGAIRE-SERVICE")

public interface StagaireRestClient {
}
