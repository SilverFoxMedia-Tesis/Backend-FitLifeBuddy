package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.Enum.TimeMeal;
import com.fitLifeBuddy.Entity.Food;
import com.fitLifeBuddy.Entity.Meal;
import com.fitLifeBuddy.Entity.MealFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IMealRepository extends JpaRepository<Meal, Long> {

    @Query("select mf from MealFood mf where mf.meal.idMeal =:mealId")
    public List<MealFood> findMealFoodsByIdMeal(@Param("mealId")Long idMeal);

    @Query("select m from Meal m where m.daily.plan.idPlan = :idPlan and m.timeMeal = :timeMeal")
    public List<Meal> findMealsByPlanIdAndTimeMeal(@Param("idPlan") Long idPlan, @Param("timeMeal") TimeMeal timeMeal);
}
