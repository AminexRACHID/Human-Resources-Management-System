package miaad.sgrh.employeemanagement.repository;

import miaad.sgrh.employeemanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findEmployeeByEmail(String employeeEmail);
    Employee findEmployeeById(Long id);
    Employee findEmployeeByCin(String employeeCin);
    List<Employee> findEmployeesByPost(String employeePost);
    List<Employee> findEmployeesByService(String employeeService);
    boolean existsByEmail(String email);
    boolean existsByCin(String cin);

    @Query("SELECT e FROM Employee e WHERE e.lastName = :lastName AND e.firstName = :firstName")
    Employee findByLastNameAndFirstName(@Param("lastName") String lastName, @Param("firstName") String firstName);
}
