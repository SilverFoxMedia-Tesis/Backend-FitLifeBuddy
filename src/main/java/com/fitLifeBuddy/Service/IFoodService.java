package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.Enum.DietType;
import com.fitLifeBuddy.Entity.Enum.FoodOrigin;
import com.fitLifeBuddy.Entity.Food;
import com.fitLifeBuddy.Entity.Meal;


import java.util.List;

public interface IFoodService extends CrudService<Food>{
    public List<Food> findByNameFood(String nameFood) throws Exception;
    public List<Food> findByCategoryName(String categoryName) throws Exception;
    public List<Food> findByFoodOrigin(FoodOrigin foodOrigin) throws Exception;
    public List<Food> findByDietType(DietType dietType) throws Exception;
    public List<Meal> findMealsByIdFood(Long idFood) throws Exception;

}
