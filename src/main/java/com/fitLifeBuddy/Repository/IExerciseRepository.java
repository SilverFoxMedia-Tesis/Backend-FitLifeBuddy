package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.Enum.BodyPart;
import com.fitLifeBuddy.Entity.Enum.Equipment;
import com.fitLifeBuddy.Entity.Enum.Level;
import com.fitLifeBuddy.Entity.Enum.TypeExercise;
import com.fitLifeBuddy.Entity.Exercise;
import com.fitLifeBuddy.Entity.Meal;
import com.fitLifeBuddy.Entity.Routine;
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
    public List<Exercise> findByEquipment(Equipment equipment);
    public List<Exercise> findByLevel(Level level);

    @Query("select e.routines from Exercise e where e.idExercise = : exerciseId")
    public List<Routine> findRoutinesByIdExercise(@Param("exerciseId")Long idExercise);

}
