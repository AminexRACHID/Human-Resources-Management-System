package miaad.sgrh.employeemanagement.controller;

import lombok.AllArgsConstructor;
import miaad.sgrh.employeemanagement.dto.EmployeeDto;
import miaad.sgrh.employeemanagement.entity.Document;
import miaad.sgrh.employeemanagement.entity.Employee;
import miaad.sgrh.employeemanagement.exception.RessourceNotFoundException;
import miaad.sgrh.employeemanagement.mapper.EmployeeMapper;
import miaad.sgrh.employeemanagement.service.EmployeeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public void deleteEmplyee(@PathVariable("id") Long employeeId) {

        employeeService.deleteEmployees(employeeId);
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

    //Documents
    // curl -o document.pdf -X GET http://localhost:8080/api/employees/{employeeId}/documents/{documentId}
    // http://localhost:8080/api/employees/1/documents
    // Post -> body -> form data -> key value == file , value == select file
    @PostMapping("/{employeeId}/documents")
    public ResponseEntity<Document> uploadDocument(@PathVariable Long employeeId, @RequestParam("file") MultipartFile file) {
        EmployeeDto employeedto = employeeService.getEmployeeById(employeeId);
        Employee employee = EmployeeMapper.mapToEmployee(employeedto);

        if (employee != null) {
            Document document = employeeService.uploadDocument(employee, file);
            return new ResponseEntity<>(document, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // http://localhost:8080/api/employees/{employeeId}/documents/{documentId}
    @GetMapping("/{employeeId}/documents/{documentId}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long employeeId, @PathVariable Long documentId) {
        Document document = employeeService.getEmployeeDocument(employeeId, documentId);

        if (document != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);

            return new ResponseEntity<>(document.getContent(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{employeeId}/documents")
    public ResponseEntity<?> getAllDocumentsByEmployee(@PathVariable Long employeeId) {

        try {
            List<Document> documents = employeeService.getAllDocumentsByEmployee(employeeId);

            if (documents != null) {
                Document document = documents.get(0);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);

                return new ResponseEntity<>(document.getContent(), headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
