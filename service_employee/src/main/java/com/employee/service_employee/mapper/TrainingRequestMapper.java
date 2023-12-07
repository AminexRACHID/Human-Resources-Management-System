package com.employee.service_employee.mapper;

import com.employee.service_employee.Dto.TrainingRequestDto;
import com.employee.service_employee.Entity.TrainingRequest;

public class TrainingRequestMapper {

    public static TrainingRequestDto mapToTrainingRequestDTO(TrainingRequest trainingRequest){
        return new TrainingRequestDto(
                trainingRequest.getId(),
                trainingRequest.getRequestedDate(),
                trainingRequest.getStatus(),
                trainingRequest.getEmployeeId(),
                trainingRequest.getFormationId()
        );
    }

    public static TrainingRequest mapToTrainingRequest(TrainingRequestDto trainingRequest){
        return new TrainingRequest(
                trainingRequest.getId(),
                trainingRequest.getRequestedDate(),
                trainingRequest.getStatus(),
                trainingRequest.getEmployeeId(),
                trainingRequest.getFormationId()
        );
    }
}
