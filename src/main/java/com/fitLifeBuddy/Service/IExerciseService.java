package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.Enum.BodyPart;
import com.fitLifeBuddy.Entity.Enum.TypeExercise;
import com.fitLifeBuddy.Entity.Exercise;
import com.fitLifeBuddy.Entity.RoutineExercise;

import java.util.List;

public interface IExerciseService extends CrudService<Exercise>{
    public List<Exercise> findByTitle(String title) throws Exception;
    public List<Exercise> findByTypeExercise(TypeExercise typeExercise) throws Exception;
    public List<Exercise> findByBodyPart(BodyPart bodyPart) throws Exception;
    public List<RoutineExercise> findRoutineExercisesByIdExercise(Long idExercise) throws  Exception;

}
