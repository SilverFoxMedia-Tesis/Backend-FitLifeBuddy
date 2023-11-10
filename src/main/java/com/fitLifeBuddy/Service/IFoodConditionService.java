package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.Enum.TypeFoodCondition;
import com.fitLifeBuddy.Entity.FoodCondition;

import java.util.List;

public interface IFoodConditionService extends CrudService<FoodCondition>{
    public List<FoodCondition> findByNameFoodCondition(String nameFoodCondition) throws Exception;
    public List<FoodCondition> findByTypeFoodCondition(TypeFoodCondition typeFoodCondition) throws Exception;

}
