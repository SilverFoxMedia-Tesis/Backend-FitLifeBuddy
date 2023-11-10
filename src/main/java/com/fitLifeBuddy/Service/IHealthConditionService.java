package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.Enum.TypeHealthCondition;
import com.fitLifeBuddy.Entity.HealthCondition;

import java.util.List;

public interface IHealthConditionService extends CrudService<HealthCondition>{

    public List<HealthCondition> findByNameHealthCondition(String nameHealthCondition) throws Exception;
    public List<HealthCondition> findByTypeHealthCondition(TypeHealthCondition typeHealthCondition) throws Exception;

}
