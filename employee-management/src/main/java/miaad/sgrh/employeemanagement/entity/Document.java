package miaad.sgrh.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String documentName;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] content;

    @ManyToOne
    private Employee employee;

    @PrePersist
    protected void onCreate() {
        creationDate = new Date();
    }
}
