package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.Enum.TypeFoodCondition;
import com.fitLifeBuddy.Entity.FoodCondition;
import com.fitLifeBuddy.Repository.IFoodConditionRepository;
import com.fitLifeBuddy.Service.IFoodConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FoodConditionServiceImpl implements IFoodConditionService {
    @Autowired
    private IFoodConditionRepository foodConditionRepository;

    @Override
    public FoodCondition save(FoodCondition foodCondition) throws Exception {
        return foodConditionRepository.save(foodCondition);
    }

    @Override
    public void delete(Long id) throws Exception {
        foodConditionRepository.deleteById(id);

    }

    @Override
    public List<FoodCondition> getAll() throws Exception {
        return foodConditionRepository.findAll().stream()
                .sorted((fc1, fc2) -> fc1.getIdFoodCondition().compareTo(fc2.getIdFoodCondition()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FoodCondition> getById(Long id) throws Exception {
        return foodConditionRepository.findById(id);
    }

    @Override
    public List<FoodCondition> findByNameFoodCondition(String nameFoodCondition) throws Exception {
        return foodConditionRepository.findByNameFoodCondition(nameFoodCondition);
    }

    @Override
    public List<FoodCondition> findByTypeFoodCondition(TypeFoodCondition typeFoodCondition) throws Exception {
        return foodConditionRepository.findByTypeFoodCondition(typeFoodCondition);
    }
}
