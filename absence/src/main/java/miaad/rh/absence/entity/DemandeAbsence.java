package miaad.rh.absence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "demandesAbsences")
public class DemandeAbsence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_colaborateur", nullable = false)
    private Long colaborateurId;
    @Column(name = "is_employee")
    private boolean employee;
    @Column(name = "Date_absence")
    private Date absenceDate;
    @Column(name = "Duration_absence")
    private String duration;
    @Column(name = "Nature_absence")
    private String absenceNature;
    @Column(name = "justification_absence")
    private String justifie;
    @Column(name = "justification_detailles", columnDefinition = "LONGBLOB")
    @Lob
    private byte[] justification;
}
