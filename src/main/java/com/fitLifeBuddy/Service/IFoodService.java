package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.Enum.DietType;
import com.fitLifeBuddy.Entity.Enum.FoodOrigin;
import com.fitLifeBuddy.Entity.Food;
import com.fitLifeBuddy.Entity.Meal;


import java.util.List;

public interface IFoodService extends CrudService<Food>{
    public List<Food> findByNameFood(String nameFood);
    public List<Food> findByCategoryName(String categoryName);
    public List<Food> findByFoodOrigin(FoodOrigin foodOrigin);
    public List<Food> findByDietType(DietType dietType);
    public List<Meal> findMealsByIdFood(Long idFood);

}
