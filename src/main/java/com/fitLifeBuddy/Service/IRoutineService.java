package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.Exercise;
import com.fitLifeBuddy.Entity.Routine;


import java.util.List;

public interface IRoutineService extends CrudService<Routine> {
    public List<Exercise> findFoodsByIdRoutine(Long idRoutine) throws Exception;
}
