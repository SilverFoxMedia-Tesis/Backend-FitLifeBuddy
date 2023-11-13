package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.Enum.DietType;
import com.fitLifeBuddy.Entity.Enum.FoodOrigin;
import com.fitLifeBuddy.Entity.Food;
import com.fitLifeBuddy.Entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFoodRepository extends JpaRepository<Food,Long> {
    public List<Food> findByNameFood(String nameFood);
    public List<Food> findByCategoryName(String categoryName);
    public List<Food> findByFoodOrigin(FoodOrigin foodOrigin);
    public List<Food> findByDietType(DietType dietType);
    @Query("select f.meals from Food f where f.idFood = : foodId")
    public List<Meal> findMealsByIdFood(@Param("foodId")Long idFood);

}
