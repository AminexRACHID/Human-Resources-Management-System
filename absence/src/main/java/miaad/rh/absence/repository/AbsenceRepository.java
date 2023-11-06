package miaad.rh.absence.repository;

import miaad.rh.absence.entity.Absence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {
}
