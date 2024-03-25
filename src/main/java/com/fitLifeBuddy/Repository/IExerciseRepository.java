package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.Enum.BodyPart;
import com.fitLifeBuddy.Entity.Enum.TypeExercise;
import com.fitLifeBuddy.Entity.Exercise;
import com.fitLifeBuddy.Entity.RoutineExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IExerciseRepository extends JpaRepository<Exercise, Long> {
    public List<Exercise> findByTitle(String title);
    public List<Exercise> findByTypeExercise(TypeExercise typeExercise);
    public List<Exercise> findByBodyPart(BodyPart bodyPart);

    @Query("select re from RoutineExercise re where re.exercise.idExercise = :exerciseId")
    public List<RoutineExercise> findRoutineExercisesByIdExercise(@Param("exerciseId")Long idExercise);

}
