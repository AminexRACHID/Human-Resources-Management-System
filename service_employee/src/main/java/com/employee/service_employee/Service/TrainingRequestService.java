package com.employee.service_employee.Service;

import com.employee.service_employee.Dto.TrainingRequestDto;
import com.employee.service_employee.Entity.TrainingRequest;
import com.employee.service_employee.Exception.EmployeeNotFoundException;
import com.employee.service_employee.Repository.TrainingRequestRepository;
import com.employee.service_employee.mapper.TrainingRequestMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class TrainingRequestService {
    private TrainingRequestRepository trainingRequestRepository;

    public void submitTrainingRequest(TrainingRequestDto trainingRequest) {
        try {
            TrainingRequest trainingRequest1 = trainingRequestRepository.getTrainingRequest(trainingRequest.getFormationId(), trainingRequest.getEmployeeId());
            if (trainingRequest1 != null){
                throw new EmployeeNotFoundException("You have already submit a request for this formation");
            }
            LocalDate currentDate = LocalDate.now();
            trainingRequest.setRequestedDate(currentDate);
            trainingRequest.setStatus("Pending");
            trainingRequestRepository.save(TrainingRequestMapper.mapToTrainingRequest(trainingRequest));
        } catch (Exception e){
            throw new EmployeeNotFoundException(e.getMessage());
        }
    }
}