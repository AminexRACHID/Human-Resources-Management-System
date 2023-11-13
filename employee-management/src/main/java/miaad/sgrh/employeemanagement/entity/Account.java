package miaad.sgrh.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String password;
    private String role;
    private boolean confirmation;

    @OneToOne(cascade = CascadeType.ALL)
    private Employee employee;

//    @OneToOne(cascade = CascadeType.ALL)
//    private User user;

    @Column(name = "user_id", nullable = true, unique = true)
    private Long userId;

}
