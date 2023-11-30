package miaad.rh.absence.feign;

import miaad.rh.absence.dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("EMPLOYEE-MANAGEMENT")
public interface EmployeeRestClient {
    @GetMapping("/api/employees/{id}")
    EmployeeDto getEmployeeById(@PathVariable("id") Long employeeId);
}
