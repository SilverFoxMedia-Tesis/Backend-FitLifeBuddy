package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.PacientHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPacientHistoryRepository extends JpaRepository<PacientHistory, Long> {
    @Query("SELECT ph FROM PacientHistory ph WHERE ph.pacient.idPacient = :pacientId ORDER BY ph.idPacientHistory DESC")
    Optional<PacientHistory> findLatestByPacientId(Long pacientId);
}
