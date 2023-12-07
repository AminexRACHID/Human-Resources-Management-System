package com.employee.service_employee.Service;

import com.employee.service_employee.Dto.CertificationRequestDto;
import com.employee.service_employee.Dto.TrainingRequestDto;
import com.employee.service_employee.Entity.CertificationRequest;
import com.employee.service_employee.Entity.TrainingRequest;
import com.employee.service_employee.Exception.EmployeeNotFoundException;
import com.employee.service_employee.Repository.CertificationRequestRepository;
import com.employee.service_employee.mapper.CertificationRequestMapper;
import com.employee.service_employee.mapper.TrainingRequestMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class CertificationRequestService {

    CertificationRequestRepository certificationRequestRepository;

    public void submitCertificationRequest(CertificationRequestDto certificationRequestDto) {
        try {
            CertificationRequest certificationRequest = certificationRequestRepository.getCertificationRequest(certificationRequestDto.getCertificationId(), certificationRequestDto.getEmployeeId());
            if (certificationRequest != null){
                throw new EmployeeNotFoundException("You have already submit a request for this Certification");
            }
            LocalDate currentDate = LocalDate.now();
            certificationRequestDto.setRequestedDate(currentDate);
            certificationRequestDto.setStatus("Pending");
            certificationRequestRepository.save(CertificationRequestMapper.mapToCertificationRequest(certificationRequestDto));
        } catch (Exception e){
            throw new EmployeeNotFoundException(e.getMessage());
        }
    }

}
