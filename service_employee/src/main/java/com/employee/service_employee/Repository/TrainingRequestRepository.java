package com.employee.service_employee.Repository;

import com.employee.service_employee.Entity.TrainingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TrainingRequestRepository extends JpaRepository<TrainingRequest, Long> {

    @Query("SELECT t FROM TrainingRequest t WHERE t.employeeId = :employeeId AND t.formationId = :formationId")
    TrainingRequest getTrainingRequest(Long formationId, Long employeeId);

}
