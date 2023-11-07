package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.AttachedModem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IAttachedModemRepository extends JpaRepository<AttachedModem, Long> {
    public AttachedModem findByClientCode(Long clientCode);
    public List<AttachedModem> findByPlan(String plan);
    @Query("select at from AttachedModem at where at.establishmentDate=:estaDate")
    public List<AttachedModem> find(@Param("estaDate")Date establishmentDate);
}
