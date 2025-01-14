package miaad.sgrh.employeemanagement.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import miaad.sgrh.employeemanagement.controller.AccountController;
import miaad.sgrh.employeemanagement.controller.EmployeeController;
import miaad.sgrh.employeemanagement.dto.EmployeeDto;
import miaad.sgrh.employeemanagement.entity.Account;
import miaad.sgrh.employeemanagement.entity.Document;
import miaad.sgrh.employeemanagement.entity.Employee;
import miaad.sgrh.employeemanagement.exception.RessourceNotFoundException;
import miaad.sgrh.employeemanagement.feign.AbsenceFeignClient;
import miaad.sgrh.employeemanagement.feign.TrainingRequestFeignClient;
import miaad.sgrh.employeemanagement.mapper.EmployeeMapper;
import miaad.sgrh.employeemanagement.repository.AccountRepository;
import miaad.sgrh.employeemanagement.repository.DocumentRepository;
import miaad.sgrh.employeemanagement.repository.EmployeeRepository;
import miaad.sgrh.employeemanagement.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private AccountRepository accountRepository;
    private DocumentRepository documentRepository;
    private AccountController accountController;
    private TrainingRequestFeignClient trainingRequestFeignClient;
    private AbsenceFeignClient absenceFeignClient;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto, MultipartFile file) {
        if (employeeRepository.existsByEmail(employeeDto.getEmail())) {
            throw new RessourceNotFoundException("Email is not unique. Please choose a different email address.");
        }
        if (employeeRepository.existsByCin(employeeDto.getCin())) {
            throw new RessourceNotFoundException("CIN is not unique. Please choose a different CIN.");
        }

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        String password = employee.getLastName() + employee.getCin();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Account account = new Account();
        account.setLogin(employee.getEmail());
        account.setPassword(passwordEncoder.encode(password));
        account.setConfirmation(true);
        account.setRole(employeeDto.getRole());

        employee.setAccount(account);
        account.setEmployee(employee);

        Employee savedEmployee = employeeRepository.save(employee);
        accountRepository.save(account);

        if (file != null) {
            try {
                uploadDocument(employee, file);
            } catch (Exception e) {
                throw new RessourceNotFoundException("Failed to update file with id: " + employee.getId());
            }
        }



        return EmployeeMapper.mapToEmployeeDTO(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RessourceNotFoundException("Employee not exists with given id: "+ employeeId));
        Account account = accountController.getaccounte(employee.getEmail());
        EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDTO(employee);
        employeeDto.setRole(account.getRole());
        return employeeDto;
    }

    @Override
    public EmployeeDto getEmployeeByCin(String employeeCin) {
        Employee employee = employeeRepository.findEmployeeByCin(employeeCin);

        if (employee == null) {
            throw new RessourceNotFoundException("Employee not exists with given cin: " + employeeCin);
        }

        return EmployeeMapper.mapToEmployeeDTO(employee);
    }

    @Override
    public EmployeeDto getEmployeeByEmail(String employeeEmail) {
        Employee employee = employeeRepository.findEmployeeByEmail(employeeEmail);

        if (employee == null) {
            throw new RessourceNotFoundException("Employee not exists with given email: " + employeeEmail);
        }

        return EmployeeMapper.mapToEmployeeDTO(employee);
    }

    @Override
    public List<EmployeeDto> getEmployeesByPost(String employeePost) {
        List<Employee> employees = employeeRepository.findEmployeesByPost(employeePost);

        if (employees == null) {
            throw new RessourceNotFoundException("Employee not exists with given Post: " + employeePost);
        }

        return employees.stream().map((employee) -> EmployeeMapper.mapToEmployeeDTO(employee))
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> getEmployeesByService(String employeeService) {
        List<Employee> employees = employeeRepository.findEmployeesByService(employeeService);

        if (employees == null) {
            throw new RessourceNotFoundException("Employee not exists with given Service: " + employeeService);
        }

        return employees.stream().map((employee) -> EmployeeMapper.mapToEmployeeDTO(employee))
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map((employee) -> EmployeeMapper.mapToEmployeeDTO(employee))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RessourceNotFoundException("Employee not exists with given id: "+ employeeId));

        Account account = accountRepository.findAccountByLogin(employee.getEmail());


        if (updatedEmployee.getFirstName() != null) employee.setFirstName(updatedEmployee.getFirstName());
        if (updatedEmployee.getLastName() != null) employee.setLastName(updatedEmployee.getLastName());
        if (updatedEmployee.getEmail() != null){
//            if (employeeRepository.existsByEmail(updatedEmployee.getEmail())) {
//                throw new RessourceNotFoundException("Email is not unique. Please choose a different email address.");
//            }
            employee.setEmail(updatedEmployee.getEmail());
            account.setLogin(updatedEmployee.getEmail());
        }
        if (updatedEmployee.getBirthDay() != null) employee.setBirthDay(updatedEmployee.getBirthDay());
        if (updatedEmployee.getHireDate() != null) employee.setHireDate(updatedEmployee.getHireDate());
        if (updatedEmployee.getService() != null) employee.setService(updatedEmployee.getService());
        if (updatedEmployee.getPost() != null) employee.setPost(updatedEmployee.getPost());
        if (updatedEmployee.getCin() != null){
            employee.setCin(updatedEmployee.getCin());
        }
        if (updatedEmployee.getRole() != null) account.setRole(updatedEmployee.getRole());

        Employee updatedEmployeeObj = employeeRepository.save(employee);
        accountRepository.save(account);
        return EmployeeMapper.mapToEmployeeDTO(updatedEmployeeObj);
    }
    @Transactional
    @Override
    public void deleteEmployees(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RessourceNotFoundException("Employee not exists with given id: "+ employeeId));

        try{
            absenceFeignClient.deleteAbsenceByCollaborateurId(employee.getId());
            absenceFeignClient.deleteDemandeAbsenceByCollaborateurId(employee.getId());
            trainingRequestFeignClient.deleteTrainingRequestsByEmployeeId(employee.getId());
            accountRepository.deleteByLogin(employee.getEmail());
            documentRepository.deleteByEmployee_Id(employeeId);
            employeeRepository.deleteById(employeeId);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public EmployeeDto getEmployeeByLastNameAndFirstName(String lastName, String firstName) {
        Employee employee = employeeRepository.findByLastNameAndFirstName(lastName, firstName);

        if (employee == null) {
            throw new RessourceNotFoundException("Employee not found with Name: " + firstName + " " + lastName);
        }

        return EmployeeMapper.mapToEmployeeDTO(employee);
    }

    // Documents
    @Override
    public Document uploadDocument(Employee employee, MultipartFile file) {
        Document document = new Document();
        document.setDocumentName(file.getOriginalFilename());
        try {
            document.setContent(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        document.setEmployee(employee);

        return documentRepository.save(document);
    }
    @Override
    public Document getEmployeeDocument(Long employeeId, Long documentId) {
        EmployeeDto employee = getEmployeeById(employeeId);
        if (employee != null) {
            return documentRepository.findById(documentId).orElse(null);
        }
        return null;
    }

    @Override
    public List<Document> getAllDocumentsByEmployee(Long employeeId) {

        return documentRepository.findByEmployeeId(employeeId);
    }


    @Transactional
    @Override
    public void createAdminAuto(EmployeeDto employeeDto) {
        if (employeeRepository.existsByEmail(employeeDto.getEmail()) || employeeRepository.existsByCin(employeeDto.getCin())) {

        }else {

            Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
            String password = "adminmiaad2023";
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Account account = new Account();
            account.setLogin(employee.getEmail());
            account.setPassword(passwordEncoder.encode(password));
            account.setConfirmation(true);
            account.setRole(employeeDto.getRole());

            employee.setAccount(account);
            account.setEmployee(employee);
            Document document = new Document();
            document.setDocumentName("");
            document.setContent(new byte[0]);

            document.setEmployee(employee);

            documentRepository.save(document);
            employeeRepository.save(employee);
            accountRepository.save(account);
        }


    }
}
