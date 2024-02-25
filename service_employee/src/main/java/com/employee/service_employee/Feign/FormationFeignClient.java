package com.employee.service_employee.Feign;

import com.employee.service_employee.Dto.FormationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "FORMATION-SERVICE")
public interface FormationFeignClient {

    @GetMapping("/api/miaad/formations/formation/{id}")
    FormationDto getFormationById(@RequestParam("id") Long id);
}
