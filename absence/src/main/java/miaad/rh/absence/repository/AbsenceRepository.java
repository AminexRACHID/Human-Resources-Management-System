package miaad.rh.absence.repository;

import miaad.rh.absence.entity.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    List<Absence> findByAbsenceNature(String justifiée);
    List<Absence> findByColaborateurId(Long id);
}
