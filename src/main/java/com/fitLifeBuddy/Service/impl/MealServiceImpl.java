package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.Food;
import com.fitLifeBuddy.Entity.Meal;
import com.fitLifeBuddy.Repository.IMealRepository;
import com.fitLifeBuddy.Service.IMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        return mealRepository.findAll();
    }

    @Override
    public Optional<Meal> getById(Long id) throws Exception {
        return mealRepository.findById(id);
    }

    @Override
    public List<Meal> findByNameMeal(String nameMeal) throws Exception {
        return mealRepository.findByNameMeal(nameMeal);
    }

    @Override
    public List<Food> findFoodsByIdMeal(Long idMeal) throws Exception {
        return mealRepository.findFoodsByIdMeal(idMeal);
    }
}
