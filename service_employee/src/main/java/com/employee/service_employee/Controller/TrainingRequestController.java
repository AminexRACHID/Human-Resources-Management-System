package com.employee.service_employee.Controller;

import com.employee.service_employee.Dto.TrainingRequestDto;
import com.employee.service_employee.Service.TrainingRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/trainingRequest")
public class TrainingRequestController {

    private TrainingRequestService trainingRequestService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitTrainingRequest(@RequestBody TrainingRequestDto trainingRequest) {
        try{
            trainingRequestService.submitTrainingRequest(trainingRequest);
            return ResponseEntity.ok("Training Request submitted successfully");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
