package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface IPacientRepository extends JpaRepository<Pacient, Long> {
    @Query("select pa.pacientHistory from Pacient pa where pa.idPacient =: pacientId")
    public PacientHistory findPacientHistoryByIdPacient(@Param("pacientId") Long idPacient);
    @Query("select pa.plan from Pacient pa where pa.idPacient =: pacientId")
    public Plan findPlanByIdPacient(@Param("pacientId") Long idPacient);
    @Query("select pa from Pacient pa where pa.birthDate =:biDay")
    public List<Pacient> find(@Param("biDay")Date birthDate);
    @Query("select pa.healthConditions from Pacient pa where pa.idPacient =:pacientId")
    public List<HealthCondition> findHealthConditionsByIdPacient(@Param("pacientId")Long idPacient);
    @Query("select pa.foodConditions from Pacient pa where pa.idPacient =:pacientId")
    public List<FoodCondition> findFoodConditionsByIdPacient(@Param("pacientId")Long idPacient);
}
