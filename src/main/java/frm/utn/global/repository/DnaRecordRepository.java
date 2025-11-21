package frm.utn.global.repository;

import frm.utn.global.entity.DnaRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DnaRecordRepository extends JpaRepository<DnaRecord, Long> {

    Optional<DnaRecord> findByHash(String hash);

    @Query("SELECT COUNT(d) FROM DnaRecord d WHERE d.isMutant = true")
    long countMutants();

    @Query("SELECT COUNT(d) FROM DnaRecord d WHERE d.isMutant = false")
    long countHumans();

    List<DnaRecord> findByDna(String dna);
}