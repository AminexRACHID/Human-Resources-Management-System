package miaad.sgrh.employeemanagement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.List;

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
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthDay;
    @Column(name = "cin", nullable = false, unique = true)
    private String cin;
    @Column(name = "hire_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date hireDate;
    private String service;
    private String post;

    @Transient
    @OneToOne(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Account account;

    @Transient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Document> documents;

    public Employee(Long id, String firstName, String lastName, String email, Date birthDay, String cin, Date hireDate, String service, String post) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDay = birthDay;
        this.cin = cin;
        this.hireDate = hireDate;
        this.service = service;
        this.post = post;
    }
}
