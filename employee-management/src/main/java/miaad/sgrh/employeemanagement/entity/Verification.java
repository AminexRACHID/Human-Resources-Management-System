package miaad.sgrh.employeemanagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Table(name = "verification")
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    @Column(name = "expiry_date")
    private Timestamp expiryDate;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    public Verification(Account account, String token) {
        this.account =account;
        this.token = token;
    }
}
