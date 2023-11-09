package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.Food;
import com.fitLifeBuddy.Entity.Meal;


import java.util.List;

public interface IMealService extends CrudService<Meal>{
    public List<Meal> findByNameMeal(String nameMeal) throws Exception;
    public List<Food> findFoodsByIdMeal(Long idMeal) throws Exception;

}
