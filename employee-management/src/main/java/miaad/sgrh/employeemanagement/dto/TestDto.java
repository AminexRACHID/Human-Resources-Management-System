package miaad.sgrh.employeemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TestDto implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Date birthDay;
    private String cin;
    private Date hireDate;
    private String service;
    private String post;
    private String role;
    private MultipartFile file;

}