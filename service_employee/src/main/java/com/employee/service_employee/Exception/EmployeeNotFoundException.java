package com.employee.service_employee.Exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(Long employeeId) {
        super("Employee not found with ID: " + employeeId);
    }

    public EmployeeNotFoundException(String message) {
        super(message);
    }

}
