package com.employee.service_employee.Repository;

import com.employee.service_employee.Entity.TrainingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainingRequestRepository extends JpaRepository<TrainingRequest, Long> {

    @Query("SELECT t FROM TrainingRequest t WHERE t.employeeId = :employeeId AND t.formationId = :formationId")
    TrainingRequest getTrainingRequest(Long formationId, Long employeeId);

    @Query("SELECT t FROM TrainingRequest t WHERE t.employeeId = :employeeId")
    List<TrainingRequest> getTrainingRequestByEmployee(Long employeeId);

    TrainingRequest findTrainingRequestById(Long id);

    @Query("SELECT t FROM TrainingRequest t WHERE t.status = 'Pending'")
    List<TrainingRequest> getTrainingRequestByStatus();

    List<TrainingRequest> findByFormationId(Long formationId);
    List<TrainingRequest> findByEmployeeId(Long employeeId);


}
