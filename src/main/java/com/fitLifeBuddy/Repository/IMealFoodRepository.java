package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.MealFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMealFoodRepository extends JpaRepository<MealFood, Long> {
}
