package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.Enum.CategoryName;
import com.fitLifeBuddy.Entity.Food;
import com.fitLifeBuddy.Entity.MealFood;


import java.util.List;

public interface IFoodService extends CrudService<Food>{
    public List<Food> findByNameFood(String nameFood) throws Exception;
    public List<Food> findByCategoryName(CategoryName categoryName) throws Exception;
    public List<MealFood> findMealFoodsByIdFood(Long idFood) throws Exception;

}
