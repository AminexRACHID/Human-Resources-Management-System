package miaad.sgrh.employeemanagement;

import miaad.sgrh.employeemanagement.dto.EmployeeDto;
import miaad.sgrh.employeemanagement.service.EmployeeService;
import miaad.sgrh.employeemanagement.serviceImpl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.sql.Date;
import java.text.SimpleDateFormat;

@SpringBootApplication
@EnableFeignClients
public class EmployeeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementApplication.class, args);
	}

//	@Autowired
//	private EmployeeService employeeService;

//	@Override
//	public void run(String... args) throws Exception {
//		// Création automatique de l'employé lors du démarrage de l'application
//		EmployeeDto employeeDto = new EmployeeDto();
//		employeeDto.setFirstName("admin");
//		employeeDto.setLastName("miaad");
//		employeeDto.setEmail("sgrhmiaaddss@gmail.com");
//		employeeDto.setBirthDay(Date.valueOf("2020-01-01"));
//		employeeDto.setCin("miaad");
//		employeeDto.setHireDate(Date.valueOf("2024-01-01"));
//		employeeDto.setService("Informatique");
//		employeeDto.setPost("Intelligence Artificielle");
//		employeeDto.setRole("Admin");
//
//		employeeService.createEmployee(employeeDto, null);
//	}

	@Bean
	public CommandLineRunner createEmployee(EmployeeService employeeService) {
		return args -> {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			EmployeeDto employeeDto = new EmployeeDto();
			employeeDto.setFirstName("admin");
			employeeDto.setLastName("miaad");
			employeeDto.setEmail("sgrhmiaad2025@gmail.com");
			employeeDto.setBirthDay(Date.valueOf("2020-01-01"));
			employeeDto.setCin("admin2025");
			employeeDto.setHireDate(Date.valueOf("2024-01-01"));
			employeeDto.setService("Informatique");
			employeeDto.setPost("Intelligence Artificielle");
			employeeDto.setRole("Admin");

			employeeService.createAdminAuto(employeeDto);
		};
	}

}
