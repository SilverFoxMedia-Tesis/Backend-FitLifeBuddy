package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.RoutineExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoutineExerciseRepository extends JpaRepository<RoutineExercise, Long> {
}
