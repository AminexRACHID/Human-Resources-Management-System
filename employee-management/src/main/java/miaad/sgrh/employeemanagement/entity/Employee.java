package miaad.sgrh.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "birth_day")
    @DateTimeFormat(fallbackPatterns = "dd/MM/yyyy")
    private Date birthDay;
    @Column(name = "cin", nullable = false, unique = true)
    private String cin;
    @Column(name = "hire_date")
    @DateTimeFormat(fallbackPatterns = "dd/MM/yyyy")
    private Date hireDate;

    private String service;
    private String post;

}
