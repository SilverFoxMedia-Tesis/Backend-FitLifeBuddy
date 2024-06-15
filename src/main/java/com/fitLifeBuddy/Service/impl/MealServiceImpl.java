package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.Enum.TimeMeal;
import com.fitLifeBuddy.Entity.Food;
import com.fitLifeBuddy.Entity.Meal;
import com.fitLifeBuddy.Entity.MealFood;
import com.fitLifeBuddy.Repository.IMealRepository;
import com.fitLifeBuddy.Service.IMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MealServiceImpl implements IMealService {
    @Autowired
    private IMealRepository mealRepository;

    @Override
    @Transactional
    public Meal save(Meal meal) throws Exception {
        return mealRepository.save(meal);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        mealRepository.deleteById(id);

    }

    @Override
    public List<Meal> getAll() throws Exception {
        return mealRepository.findAll().stream()
                .sorted((m1, m2) -> m1.getIdMeal().compareTo(m2.getIdMeal()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Meal> getById(Long id) throws Exception {
        return mealRepository.findById(id);
    }

    @Override
    public List<MealFood> findMealFoodsByIdMeal(Long idMeal) throws Exception {
        return mealRepository.findMealFoodsByIdMeal(idMeal);
    }

    @Override
    public List<Meal> findMealsByPlanIdAndTimeMeal(Long idPlan, TimeMeal timeMeal) throws Exception {
        List<Meal> allMealsInPlan = mealRepository.findMealsByPlanIdAndTimeMeal(idPlan, timeMeal);
        Collections.shuffle(allMealsInPlan);  // Mezclar la lista
        return allMealsInPlan.stream().limit(3).collect(Collectors.toList());  // Tomar los primeros 3
    }
}
