package com.employee.service_employee.mapper;

import com.employee.service_employee.Dto.CertificationRequestDto;
import com.employee.service_employee.Entity.CertificationRequest;

public class CertificationRequestMapper {

    public static CertificationRequestDto mapToCertificationRequestDTO(CertificationRequest certificationRequest){
        return new CertificationRequestDto(
                certificationRequest.getId(),
                certificationRequest.getRequestedDate(),
                certificationRequest.getStatus(),
                certificationRequest.getEmployeeId(),
                certificationRequest.getCertificationId()
        );
    }

    public static CertificationRequest mapToCertificationRequest(CertificationRequestDto certificationRequest){
        return new CertificationRequest(
                certificationRequest.getId(),
                certificationRequest.getRequestedDate(),
                certificationRequest.getStatus(),
                certificationRequest.getEmployeeId(),
                certificationRequest.getCertificationId()
        );
    }
}
