package com.employee.service_employee.Controller;

import com.employee.service_employee.Dto.TrainingRequestDto;
import com.employee.service_employee.Dto.TrainingRequestInfoDto;
import com.employee.service_employee.Dto.TrainingRequestStatusDto;
import com.employee.service_employee.Service.TrainingRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/test/trainingRequest")
public class TrainingRequestController {

    private TrainingRequestService trainingRequestService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitTrainingRequest(@RequestBody TrainingRequestDto trainingRequest) {
        try{
            trainingRequestService.submitTrainingRequest(trainingRequest);
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("message", "Training Request submitted successfully");
            return ResponseEntity.ok(responseMap);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<?> getTrainingRequestInfo() {
        try{
            List<TrainingRequestInfoDto> trainingRequestInfoDtos = trainingRequestService.getTrainingRequestInfo();
            return ResponseEntity.ok(trainingRequestInfoDtos);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/nbrStatus/{id}")
    public ResponseEntity<?> getTrainingRequestInfoStatus(@PathVariable("id") Long id) {
        try{
            TrainingRequestStatusDto trainingRequestInfoDtos = trainingRequestService.getTrainingRequestInfoNbr(id);
            return ResponseEntity.ok(trainingRequestInfoDtos);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/formationByEmployee/{id}")
    public ResponseEntity<?> getTrainingRequestInfoByEmployee(@PathVariable("id") Long id) {
        try{
            List<TrainingRequestInfoDto> trainingRequestInfoDtos = trainingRequestService.getTrainingRequestInfoByEmployee(id);
            return ResponseEntity.ok(trainingRequestInfoDtos);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/accept")
    public ResponseEntity<?> acceptRequest(@RequestBody Long id) {
        TrainingRequestDto trainingRequestDto = trainingRequestService.acceptTrainingRequest(id);
        return ResponseEntity.ok(trainingRequestDto);
    }

    @PutMapping("/reject")
    public ResponseEntity<?> rejectRequest(@RequestBody Long id) {
        TrainingRequestDto trainingRequestDto = trainingRequestService.rejectTrainingRequest(id);
        return ResponseEntity.ok(trainingRequestDto);
    }
}
