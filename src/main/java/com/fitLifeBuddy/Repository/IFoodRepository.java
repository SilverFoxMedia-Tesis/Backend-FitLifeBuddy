package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.Enum.CategoryName;
import com.fitLifeBuddy.Entity.Food;
import com.fitLifeBuddy.Entity.MealFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFoodRepository extends JpaRepository<Food,Long> {
    @Query("select f from Food f where f.nameFood = :foodName")
    public List<Food> findByNameFood(@Param("foodName") String nameFood);
    @Query("select f from Food f where f.categoryName = :nameCategory")
    public List<Food> findByCategoryName(@Param("nameCategory") CategoryName categoryName);
    @Query("select mf from MealFood mf where mf.food.idFood = :foodId")
    public List<MealFood> findMealFoodsByIdFood(@Param("foodId")Long idFood);

}
