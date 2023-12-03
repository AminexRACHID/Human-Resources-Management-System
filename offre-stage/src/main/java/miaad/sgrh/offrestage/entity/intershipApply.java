package miaad.sgrh.offrestage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "intershipApply")
public class intershipApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    @Column(name = "stage_id", nullable = false)
    private Long stageId;

    @Column(name = "stagiaire_id", nullable = false)
    private Long stagiaireId;
}
