package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.Enum.BodyPart;
import com.fitLifeBuddy.Entity.Enum.Equipment;
import com.fitLifeBuddy.Entity.Enum.Level;
import com.fitLifeBuddy.Entity.Enum.TypeExercise;
import com.fitLifeBuddy.Entity.Exercise;
import com.fitLifeBuddy.Entity.Routine;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IExerciseService extends CrudService<Exercise>{
    public List<Exercise> findByTitle(String title) throws Exception;
    public List<Exercise> findByTypeExercise(TypeExercise typeExercise) throws Exception;
    public List<Exercise> findByBodyPart(BodyPart bodyPart) throws Exception;
    public List<Exercise> findByEquipment(Equipment equipment) throws Exception;
    public List<Exercise> findByLevel(Level level) throws Exception;
    public List<Routine> findRoutinesByIdExercise(Long idExercise) throws  Exception;

}
