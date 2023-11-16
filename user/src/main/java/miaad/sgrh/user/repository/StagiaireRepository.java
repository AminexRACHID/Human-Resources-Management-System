package miaad.sgrh.user.repository;

import miaad.sgrh.user.dto.StagiaireDto;
import miaad.sgrh.user.dto.UserDto;
import miaad.sgrh.user.entity.Stagiaire;
import miaad.sgrh.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StagiaireRepository extends JpaRepository<Stagiaire,Long> {
    Stagiaire findStagiaireByEmail(String email);

    @Query("SELECT s FROM Stagiaire s WHERE s.status = :status")
    List<Stagiaire> getStagiaireByStatus(String status);
}
