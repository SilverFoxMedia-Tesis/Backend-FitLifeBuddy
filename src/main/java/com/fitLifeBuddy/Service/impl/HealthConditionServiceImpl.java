package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.Enum.TypeHealthCondition;
import com.fitLifeBuddy.Entity.HealthCondition;
import com.fitLifeBuddy.Repository.IHealthConditionRepository;
import com.fitLifeBuddy.Service.IHealthConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HealthConditionServiceImpl implements IHealthConditionService {
    @Autowired
    private IHealthConditionRepository healthConditionRepository;

    @Override
    @Transactional
    public HealthCondition save(HealthCondition healthCondition) throws Exception {
        return healthConditionRepository.save(healthCondition);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        healthConditionRepository.findById(id);

    }

    @Override
    public List<HealthCondition> getAll() throws Exception {
        return healthConditionRepository.findAll();
    }

    @Override
    public Optional<HealthCondition> getById(Long id) throws Exception {
        return healthConditionRepository.findById(id);
    }

    @Override
    public List<HealthCondition> findByNameHealthCondition(String nameHealthCondition) throws Exception {
        return healthConditionRepository.findByNameHealthCondition(nameHealthCondition);
    }

    @Override
    public List<HealthCondition> findByTypeHealthCondition(TypeHealthCondition typeHealthCondition) throws Exception {
        return healthConditionRepository.findByTypeHealthCondition(typeHealthCondition);
    }
}
