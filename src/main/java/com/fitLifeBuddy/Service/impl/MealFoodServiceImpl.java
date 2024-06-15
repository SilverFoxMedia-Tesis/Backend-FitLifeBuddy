package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.MealFood;
import com.fitLifeBuddy.Repository.IMealFoodRepository;
import com.fitLifeBuddy.Service.IMealFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MealFoodServiceImpl implements IMealFoodService {
    @Autowired
    private IMealFoodRepository mealFoodRepository;

    @Override
    @Transactional
    public MealFood save(MealFood mealFood) throws Exception {
        return mealFoodRepository.save(mealFood);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        mealFoodRepository.deleteById(id);
    }

    @Override
    public List<MealFood> getAll() throws Exception {
        return mealFoodRepository.findAll().stream()
                .sorted((mf1, mf2) -> mf1.getIdMealFood().compareTo(mf2.getIdMealFood()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MealFood> getById(Long id) throws Exception {
        return mealFoodRepository.findById(id);
    }
}
