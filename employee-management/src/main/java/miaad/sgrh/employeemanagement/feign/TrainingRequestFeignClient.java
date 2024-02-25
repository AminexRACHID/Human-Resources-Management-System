package miaad.sgrh.employeemanagement.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "EMPLOYEE-SERVICE")
public interface TrainingRequestFeignClient {

    @DeleteMapping("/test/trainingRequest/deleteByEmployeeId/{employeeId}")
    ResponseEntity<?> deleteTrainingRequestsByEmployeeId(@PathVariable Long employeeId);

}

