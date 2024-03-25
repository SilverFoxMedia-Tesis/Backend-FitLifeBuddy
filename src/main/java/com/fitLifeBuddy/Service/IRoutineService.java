package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.Routine;
import com.fitLifeBuddy.Entity.RoutineExercise;


import java.util.List;

public interface IRoutineService extends CrudService<Routine> {
    public List<RoutineExercise> findRoutineExercisesByIdRoutine(Long idRoutine) throws Exception;
}
