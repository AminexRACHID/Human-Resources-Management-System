package miaad.sgrh.employeemanagement.mapper;

import miaad.sgrh.employeemanagement.dto.EmployeeDto;
import miaad.sgrh.employeemanagement.entity.Employee;

public class EmployeeMapper {
    public static EmployeeDto mapToEmployeeDTO(Employee employee){
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getBirthDay(),
                employee.getCin(),
                employee.getHireDate(),
                employee.getService(),
                employee.getPost()
        );
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto){
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail(),
                employeeDto.getBirthDay(),
                employeeDto.getCin(),
                employeeDto.getHireDate(),
                employeeDto.getService(),
                employeeDto.getPost()
        );
    }
}
