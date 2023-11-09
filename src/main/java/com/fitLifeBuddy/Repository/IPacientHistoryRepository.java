package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.PacientHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IPacientHistoryRepository extends JpaRepository<PacientHistory, Long> {
    public List<PacientHistory> findByMtrans(String mtrans);
}
