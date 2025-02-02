package miaad.sgrh.offrestage.repository;

import miaad.sgrh.offrestage.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {
    List<Stage> findStagesByTitle(String title);
    Stage findStagesById(Long id);

    @Query("SELECT s FROM Stage s WHERE LOWER(s.title) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Stage> findByTitleContainingIgnoreCase(@Param("searchTerm") String searchTerm);
}
