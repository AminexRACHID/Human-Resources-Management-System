package com.employee.service_employee.Service;

import com.employee.service_employee.Dto.*;
import com.employee.service_employee.Entity.TrainingRequest;
import com.employee.service_employee.Exception.EmployeeNotFoundException;
import com.employee.service_employee.Feign.AccountFeignClient;
import com.employee.service_employee.Feign.FormationFeignClient;
import com.employee.service_employee.Repository.TrainingRequestRepository;
import com.employee.service_employee.mapper.TrainingRequestMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TrainingRequestService {
    private TrainingRequestRepository trainingRequestRepository;
    private AccountFeignClient employeeFeignClient;
    private FormationFeignClient formationFeignClient;

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

    public List<TrainingRequestInfoDto> getTrainingRequestInfo() {

            List<TrainingRequest> trainingRequest1 = trainingRequestRepository.getTrainingRequestByStatus();
                List<TrainingRequestInfoDto> trainingInfo = trainingRequest1.stream()
                        .map(training -> {
                            EmployeeDto employeeDto = employeeFeignClient.getEmployeeById(training.getEmployeeId());
                            FormationDto formationDto = formationFeignClient.getFormationById(training.getFormationId());
                            TrainingRequestInfoDto trainingRequestDto = new TrainingRequestInfoDto();
                            trainingRequestDto.setEmployeeDto(employeeDto);
                            trainingRequestDto.setTrainingRequestDto(training);
                            trainingRequestDto.setFormationDto(formationDto);
                            return trainingRequestDto;
                        }).collect(Collectors.toList());

                return trainingInfo;
    }

    public List<TrainingRequestInfoDto> getTrainingRequestInfoByEmployee(Long id) {

        List<TrainingRequest> trainingRequest1 = trainingRequestRepository.getTrainingRequestByEmployee(id);
        List<TrainingRequestInfoDto> trainingInfo = trainingRequest1.stream()
                .map(training -> {
                    EmployeeDto employeeDto = employeeFeignClient.getEmployeeById(training.getEmployeeId());
                    FormationDto formationDto = formationFeignClient.getFormationById(training.getFormationId());
                    TrainingRequestInfoDto trainingRequestDto = new TrainingRequestInfoDto();
                    trainingRequestDto.setEmployeeDto(employeeDto);
                    trainingRequestDto.setTrainingRequestDto(training);
                    trainingRequestDto.setFormationDto(formationDto);
                    return trainingRequestDto;
                }).collect(Collectors.toList());

        return trainingInfo;
    }

    public TrainingRequestStatusDto getTrainingRequestInfoNbr(Long id) {

        List<TrainingRequest> trainingRequests = trainingRequestRepository.getTrainingRequestByEmployee(id);

        long nbrPending = trainingRequests.stream()
                .filter(trainingRequest -> "Pending".equals(trainingRequest.getStatus()))
                .count();

        long nbrReject = trainingRequests.stream()
                .filter(trainingRequest -> "Rejected".equals(trainingRequest.getStatus()))
                .count();

        long nbrAccepted = trainingRequests.stream()
                .filter(trainingRequest -> "Accepted".equals(trainingRequest.getStatus()))
                .count();

        TrainingRequestStatusDto requestStatusDto = new TrainingRequestStatusDto();
        requestStatusDto.setAccepted(nbrAccepted);
        requestStatusDto.setRejected(nbrReject);
        requestStatusDto.setPending(nbrPending);


        return requestStatusDto;
    }

    public TrainingRequestDto acceptTrainingRequest(Long id){
        TrainingRequest trainingRequest = trainingRequestRepository.findTrainingRequestById(id);
        trainingRequest.setStatus("Accepted");
        trainingRequestRepository.save(trainingRequest);
        return TrainingRequestMapper.mapToTrainingRequestDTO(trainingRequest);
    }

    public TrainingRequestDto rejectTrainingRequest(Long id){
        TrainingRequest trainingRequest = trainingRequestRepository.findTrainingRequestById(id);
        trainingRequest.setStatus("Rejected");
        trainingRequestRepository.save(trainingRequest);
        return TrainingRequestMapper.mapToTrainingRequestDTO(trainingRequest);
    }

}