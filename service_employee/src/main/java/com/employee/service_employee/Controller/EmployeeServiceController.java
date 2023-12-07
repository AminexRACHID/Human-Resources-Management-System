package com.employee.service_employee.Controller;

import com.employee.service_employee.Dto.PasswordDTO;
import com.employee.service_employee.Exception.EmployeeNotFoundException;
import com.employee.service_employee.Feign.AccountFeignClient;
import com.employee.service_employee.Service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/employee")
public class EmployeeServiceController {

    @Autowired
    private EmployeeService employeeService;
    private final AccountFeignClient accountFeignClient;
    //private TrainingRequestService trainingRequestService;

    // Change password :
    @PostMapping("/change-password/{email}")
    public ResponseEntity<String> changePassword(@PathVariable String email, @RequestBody PasswordDTO passwordDTO) {
        try{
            employeeService.changePassword(email, passwordDTO);
            return ResponseEntity.ok("Password changed successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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