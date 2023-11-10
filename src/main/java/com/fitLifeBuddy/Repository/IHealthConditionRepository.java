package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.Enum.TypeHealthCondition;
import com.fitLifeBuddy.Entity.HealthCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHealthConditionRepository extends JpaRepository<HealthCondition, Long> {
    public List<HealthCondition> findByNameHealthCondition(String nameHealthCondition);
    public List<HealthCondition> findByTypeHealthCondition(TypeHealthCondition typeHealthCondition);
}
