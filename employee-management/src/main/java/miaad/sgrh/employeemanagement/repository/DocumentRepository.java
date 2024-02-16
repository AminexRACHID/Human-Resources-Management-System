package miaad.sgrh.employeemanagement.repository;

import miaad.sgrh.employeemanagement.entity.Document;
import miaad.sgrh.employeemanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    //List<Document> findByEmployeeId(Long employeeId);
    @Query("SELECT d FROM Document d WHERE d.employee.id = :id")
    List<Document> findByEmployeeId(@Param("id") Long employeeId);

    @Modifying
    @Query("DELETE FROM Document d WHERE d.employee.id = :id")
    void deleteByEmployee_Id(@Param("id") Long employeeId);
}
