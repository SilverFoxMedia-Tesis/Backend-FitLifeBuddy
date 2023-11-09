package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.Nutritionist;
import com.fitLifeBuddy.Entity.Pacient;
import com.fitLifeBuddy.Repository.INutritionistRepository;
import com.fitLifeBuddy.Service.INutritionistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class NutriotionistServiceImpl implements INutritionistService {
    @Autowired
    private INutritionistRepository nutritionistRepository;

    @Override
    @Transactional
    public Nutritionist save(Nutritionist nutritionist) throws Exception {
        return nutritionistRepository.save(nutritionist);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        nutritionistRepository.deleteById(id);

    }

    @Override
    public List<Nutritionist> getAll() throws Exception {
        return nutritionistRepository.findAll();
    }

    @Override
    public Optional<Nutritionist> getById(Long id) throws Exception {
        return nutritionistRepository.findById(id);
    }

    @Override
    public List<Pacient> findPacientsByIdNutritionist(Long idNutritionist) throws Exception {
        return nutritionistRepository.findPacientsByIdNutritionist(idNutritionist);
    }
}
