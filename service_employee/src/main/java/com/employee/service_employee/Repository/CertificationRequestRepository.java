package com.employee.service_employee.Repository;

import com.employee.service_employee.Entity.CertificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CertificationRequestRepository extends JpaRepository<CertificationRequest, Long> {
    @Query("SELECT c FROM CertificationRequest c WHERE c.employeeId = :employeeId AND c.certificationId = :certificationId")
    CertificationRequest getCertificationRequest(Long certificationId, Long employeeId);
}
