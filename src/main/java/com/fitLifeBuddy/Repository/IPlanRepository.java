package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.Daily;
import com.fitLifeBuddy.Entity.Enum.DietType;
import com.fitLifeBuddy.Entity.Enum.Frecuently;
import com.fitLifeBuddy.Entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IPlanRepository extends JpaRepository<Plan, Long> {

    @Query("select da.dailies from Plan da where da.idPlan = :planId")
    public List<Daily> findDailiesByIdPlan(@Param("planId") Long idPlan);
    public List<Plan> findByFrecuently(Frecuently frecuently);
    public List<Plan> findByDietType(DietType dietType);
    @Query("SELECT p FROM Plan p WHERE p.pacient.idPacient = :pacientId AND p.status = com.fitLifeBuddy.Entity.Enum.Status.ACTIVED")
    public List<Plan> findActivedPlanByPacientId(@Param("pacientId") Long pacientId);
}
