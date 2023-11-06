package miaad.sgrh.employeemanagement.controller;

import lombok.AllArgsConstructor;
import miaad.sgrh.employeemanagement.dto.EmployeeDto;
import miaad.sgrh.employeemanagement.exception.RessourceNotFoundException;
import miaad.sgrh.employeemanagement.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;
    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDto employeeDto){
        try{
            EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
            return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long employeeId){
        try{
            EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
            return ResponseEntity.ok(employeeDto);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable("id") Long employeeId,@RequestBody EmployeeDto updatedEmployeeDto){
        try{
            EmployeeDto updatedEmployee = employeeService.updateEmployee(employeeId, updatedEmployeeDto);
            return ResponseEntity.ok(updatedEmployee);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteEmplyee(@PathVariable("id") Long employeeId){
        try{
            employeeService.deleteEmployee(employeeId);
            return ResponseEntity.ok("Employee deleted successfully.");
        } catch (RessourceNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search/email/{email}")
    public ResponseEntity<?> getEmployeeByEmail(@PathVariable("email") String employeeEmail){
        try{
            EmployeeDto employeeDto = employeeService.getEmployeeByEmail(employeeEmail);
            return ResponseEntity.ok(employeeDto);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search/post/{post}")
    public ResponseEntity<?> getEmployeeByPost(@PathVariable("post") String employeePost){
        try{
            List<EmployeeDto> employeesDto = employeeService.getEmployeesByPost(employeePost);
            return ResponseEntity.ok(employeesDto);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search/service/{service}")
    public ResponseEntity<?> getEmployeeByService(@PathVariable("service") String employeesService){
        try{
            List<EmployeeDto> employeesDto = employeeService.getEmployeesByService(employeesService);
            return ResponseEntity.ok(employeesDto);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search/cin/{cin}")
    public ResponseEntity<?> getEmployeeByCin(@PathVariable("cin") String employeeCin){
        try{
            EmployeeDto employeeDto = employeeService.getEmployeeByCin(employeeCin);
            return ResponseEntity.ok(employeeDto);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //  GET http://localhost:8080/api/employees/search/name?lastName=rachi&firstName=Amin
    @GetMapping("/search/name")
    public ResponseEntity<?> getEmployeeByLastNameAndFirstName(
            @RequestParam("lastName") String lastName,
            @RequestParam("firstName") String firstName){
        try{
            EmployeeDto employee = employeeService.getEmployeeByLastNameAndFirstName(lastName, firstName);
            return ResponseEntity.ok(employee);
        } catch (RessourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
