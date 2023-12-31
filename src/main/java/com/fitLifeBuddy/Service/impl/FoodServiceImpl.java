package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.Enum.DietType;
import com.fitLifeBuddy.Entity.Enum.FoodOrigin;
import com.fitLifeBuddy.Entity.Food;
import com.fitLifeBuddy.Entity.Meal;
import com.fitLifeBuddy.Repository.IFoodRepository;
import com.fitLifeBuddy.Service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FoodServiceImpl implements IFoodService {
    @Autowired
    private IFoodRepository foodRepository;

    @Override
    @Transactional
    public Food save(Food food) throws Exception {
        return foodRepository.save(food);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        foodRepository.deleteById(id);

    }

    @Override
    public List<Food> getAll() throws Exception {
        return foodRepository.findAll();
    }

    @Override
    public Optional<Food> getById(Long id) throws Exception {
        return foodRepository.findById(id);
    }

    @Override
    public List<Food> findByNameFood(String nameFood) {
        return foodRepository.findByNameFood(nameFood);
    }

    @Override
    public List<Food> findByCategoryName(String categoryName) {
        return foodRepository.findByCategoryName(categoryName);
    }

    @Override
    public List<Food> findByFoodOrigin(FoodOrigin foodOrigin) {
        return foodRepository.findByFoodOrigin(foodOrigin);
    }

    @Override
    public List<Food> findByDietType(DietType dietType) {
        return foodRepository.findByDietType(dietType);
    }

    @Override
    public List<Meal> findMealsByIdFood(Long idFood) {
        return foodRepository.findMealsByIdFood(idFood);
    }
}
