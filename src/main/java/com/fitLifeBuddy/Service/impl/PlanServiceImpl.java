package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.Daily;
import com.fitLifeBuddy.Entity.Enum.DietType;
import com.fitLifeBuddy.Entity.Enum.Frecuently;
import com.fitLifeBuddy.Entity.Plan;
import com.fitLifeBuddy.Repository.IPlanRepository;
import com.fitLifeBuddy.Service.IPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PlanServiceImpl implements IPlanService {
    @Autowired
    private IPlanRepository planRepository;

    @Override
    @Transactional
    public Plan save(Plan plan) throws Exception {
        return planRepository.save(plan);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        planRepository.deleteById(id);

    }

    @Override
    public List<Plan> getAll() throws Exception {
        return planRepository.findAll();
    }

    @Override
    public Optional<Plan> getById(Long id) throws Exception {
        return planRepository.findById(id);
    }
    @Override
    public List<Daily> findDailiesByIdPlan(Long idPlan) throws Exception {
        return planRepository.findDailiesByIdPlan(idPlan);
    }

    @Override
    public List<Plan> findByFrecuently(Frecuently frecuently) throws Exception {
        return planRepository.findByFrecuently(frecuently);
    }

    @Override
    public List<Plan> findByDietType(DietType dietType) throws Exception {
        return planRepository.findByDietType(dietType);
    }
}
