package miaad.sgrh.employeemanagement.service;

import miaad.sgrh.employeemanagement.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);
    EmployeeDto getEmployeeById(Long employeeId);
    EmployeeDto getEmployeeByCin(String employeeCin);

    EmployeeDto getEmployeeByEmail(String employeeEmail);
    List<EmployeeDto> getEmployeesByPost(String employeePost);
    List<EmployeeDto> getEmployeesByService(String employeeService);

    List<EmployeeDto> getAllEmployees();
    EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee);
    void deleteEmployee(Long employeeId);

    EmployeeDto getEmployeeByLastNameAndFirstName(String lastName, String firstName);

}
