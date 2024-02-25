package com.employee.service_employee.Controller;

import com.employee.service_employee.Dto.PasswordDTO;
import com.employee.service_employee.Exception.EmployeeNotFoundException;
import com.employee.service_employee.Feign.AccountFeignClient;
import com.employee.service_employee.Service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/test/employee")
public class EmployeeServiceController {

    @Autowired
    private EmployeeService employeeService;
    private final AccountFeignClient accountFeignClient;
    //private TrainingRequestService trainingRequestService;

    // Change password :
    @PostMapping("/change-password/{email}")
    public ResponseEntity<?> changePassword(@PathVariable String email, @RequestBody PasswordDTO passwordDTO) {
        Map<String, String> responseMap = new HashMap<>();

        try{
            employeeService.changePassword(email, passwordDTO);
            responseMap.put("message", "Password changed successfully.");
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            responseMap.put("message", "Password incorrect.");
            return ResponseEntity.badRequest().body(responseMap);
        }

    }
    //Training request :
        /*@PostMapping("/submit")
        public ResponseEntity<TrainingRequest> submitTrainingRequest(@RequestBody TrainingRequest trainingRequest) {
            // Save the training request
            try {
                TrainingRequest savedRequest = trainingRequestService.submitTrainingRequest(trainingRequest);
                return ResponseEntity.ok(savedRequest);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }*/

}