package com.employee.service_employee.Controller;

import com.employee.service_employee.Dto.CertificationRequestDto;
import com.employee.service_employee.Service.CertificationRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/test/certificationRequest")
public class CertificationRequestController {

    CertificationRequestService certificationRequestService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitCertificationRequest(@RequestBody CertificationRequestDto certificationRequestDto) {
        try{
            certificationRequestService.submitCertificationRequest(certificationRequestDto);
            return ResponseEntity.ok("Certification Request submitted successfully");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
