package miaad.sgrh.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stagiaires")
public class Stagiaire extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String levelStudies;
    private String linkedin;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] cv;

    private String status;

}
