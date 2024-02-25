package com.formations.service_formation.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "EMPLOYEE-SERVICE")
public interface TrainingRequestFeignClient {

    @DeleteMapping("/test/trainingRequest/deleteByFormationId/{formationId}")
    ResponseEntity<?> deleteTrainingRequestsByFormationId(@PathVariable Long formationId);

}
