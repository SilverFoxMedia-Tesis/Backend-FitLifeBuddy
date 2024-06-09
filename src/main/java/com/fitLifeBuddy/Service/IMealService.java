package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.Enum.TimeMeal;
import com.fitLifeBuddy.Entity.Food;
import com.fitLifeBuddy.Entity.Meal;
import com.fitLifeBuddy.Entity.MealFood;


import java.util.List;

public interface IMealService extends CrudService<Meal>{
    public List<MealFood> findMealFoodsByIdMeal(Long idMeal) throws Exception;
    public List<Meal> findMealsByPlanIdAndTimeMeal(Long idPlan, TimeMeal timeMeal) throws Exception;


}
