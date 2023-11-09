package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.Food;
import com.fitLifeBuddy.Entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IMealRepository extends JpaRepository<Meal, Long> {
    public List<Meal> findByNameMeal(String nameMeal);

    @Query("select m.foods from Meal m where m.idMeal =: mealId")
    public List<Food> findFoodsByIdMeal(@Param("mealId")Long idMeal);
}
