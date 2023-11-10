package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.Enum.TypeFoodCondition;
import com.fitLifeBuddy.Entity.FoodCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFoodConditionRepository extends JpaRepository<FoodCondition, Long> {
    public List<FoodCondition> findByNameFoodCondition(String nameFoodCondition);
    public List<FoodCondition> findByTypeFoodCondition(TypeFoodCondition typeFoodCondition);
}
