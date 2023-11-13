package miaad.rh.stagiaire.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stagaires")
public class Stagaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "city_name")
    private String city;
    @Column(name = "stadies_level")
    private String levelStadies;
    @Column(name = "linkedIn_name")
    private String linkedin;
    @Column(name = "status")
    private String status;
}
