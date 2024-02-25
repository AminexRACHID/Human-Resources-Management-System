package com.employee.service_employee.Feign;

import com.employee.service_employee.Dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "EMPLOYEE-MANAGEMENT")
public interface AccountFeignClient {
    //1
    @GetMapping("/api/account/{email}")
    String getPassword(@RequestParam("email") String email);

    //2
    @PostMapping("/api/account/changePassword")
    ResponseEntity<String> changePassword(@RequestParam("email") String email,@RequestBody String newPass);

    @GetMapping("/api/employees/{id}")
    EmployeeDto getEmployeeById(@RequestParam("id") Long id);
}