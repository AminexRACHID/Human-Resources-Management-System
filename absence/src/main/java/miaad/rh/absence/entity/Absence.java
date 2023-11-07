package miaad.rh.absence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "absences")
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_collaborateur", nullable = false)
    private Long collaborateurId;
    @Column(name = "Date_absence")
    private Date absenceDate;
    @Column(name = "Nature_absence")
    private String absenceNature;
    @Column(name = "justification_absence")
    private String justifie;
    @Column(name = "details_justification")
    private String justification;
}