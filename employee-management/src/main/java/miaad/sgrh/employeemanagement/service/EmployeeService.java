package miaad.sgrh.employeemanagement.service;

import miaad.sgrh.employeemanagement.dto.EmployeeDto;
import miaad.sgrh.employeemanagement.entity.Document;
import miaad.sgrh.employeemanagement.entity.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto, MultipartFile file);
    EmployeeDto getEmployeeById(Long employeeId);
    EmployeeDto getEmployeeByCin(String employeeCin);

    EmployeeDto getEmployeeByEmail(String employeeEmail);
    List<EmployeeDto> getEmployeesByPost(String employeePost);
    List<EmployeeDto> getEmployeesByService(String employeeService);

    List<EmployeeDto> getAllEmployees();
    EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee);
    void deleteEmployees(Long employeeId);

    @Query("SELECT e FROM Employee e WHERE e.lastName = :lastName AND e.firstName = :firstName")
    EmployeeDto getEmployeeByLastNameAndFirstName(String lastName, String firstName);

    Document uploadDocument(Employee employee, MultipartFile file);

    Document getEmployeeDocument(Long employeeId, Long documentId);

    List<Document> getAllDocumentsByEmployee(Long employeeId);

    void createAdminAuto(EmployeeDto employeeDto);
}
