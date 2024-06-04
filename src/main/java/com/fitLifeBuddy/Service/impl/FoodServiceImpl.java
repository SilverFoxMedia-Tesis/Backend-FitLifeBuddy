package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.Enum.CategoryName;
import com.fitLifeBuddy.Entity.Food;
import com.fitLifeBuddy.Entity.MealFood;
import com.fitLifeBuddy.Repository.IFoodRepository;
import com.fitLifeBuddy.Service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Food> findByCategoryName(CategoryName categoryName) {
        List<Food> allFoodsInCategory = foodRepository.findByCategoryName(categoryName);
        Collections.shuffle(allFoodsInCategory);  // Mezclar la lista
        return allFoodsInCategory.stream().limit(5).collect(Collectors.toList());  // Tomar los primeros 5
    }

    @Override
    public List<MealFood> findMealFoodsByIdFood(Long idFood) {
        return foodRepository.findMealFoodsByIdFood(idFood);
    }
}
