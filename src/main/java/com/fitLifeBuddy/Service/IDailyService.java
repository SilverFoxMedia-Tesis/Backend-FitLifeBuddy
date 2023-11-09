package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.Daily;
import com.fitLifeBuddy.Entity.Exercise;
import com.fitLifeBuddy.Entity.Meal;


import java.util.Date;
import java.util.List;

public interface IDailyService extends CrudService<Daily>{
    public List<Daily> findByDate(Date date) throws Exception;
    public List<Meal> findMealsByIdDaily(Long idDaily) throws Exception;
    public List<Exercise> findExercisesByIdDaily(Long idDaily) throws Exception;
}
