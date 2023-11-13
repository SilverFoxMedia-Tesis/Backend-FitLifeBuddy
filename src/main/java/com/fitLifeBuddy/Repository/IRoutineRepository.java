package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.Exercise;
import com.fitLifeBuddy.Entity.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoutineRepository extends JpaRepository<Routine, Long> {
    @Query("select r.exercises from Routine r where r.idRoutine =: routineId")
    public List<Exercise> findFoodsByIdRoutine(@Param("routineId") Long idRoutine);
}
