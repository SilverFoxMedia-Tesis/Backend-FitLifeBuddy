package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.Routine;
import com.fitLifeBuddy.Entity.RoutineExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoutineRepository extends JpaRepository<Routine, Long> {
    @Query("select re from RoutineExercise re where re.exercise.idExercise = :routineId")
    public List<RoutineExercise> findRoutineExercisesByIdRoutine(@Param("routineId") Long idRoutine);
}
