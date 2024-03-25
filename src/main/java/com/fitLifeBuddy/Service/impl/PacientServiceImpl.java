package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.*;
import com.fitLifeBuddy.Repository.IPacientRepository;
import com.fitLifeBuddy.Service.IPacientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PacientServiceImpl implements IPacientService {
    @Autowired
    private IPacientRepository pacientRepository;

    @Override
    @Transactional
    public Pacient save(Pacient pacient) throws Exception {
        return pacientRepository.save(pacient);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        pacientRepository.deleteById(id);
    }

    @Override
    public List<Pacient> getAll() throws Exception {
        return pacientRepository.findAll();
    }

    @Override
    public Optional<Pacient> getById(Long id) throws Exception {
        return pacientRepository.findById(id);
    }

    @Override
    public PacientHistory findPacientHistoryByIdPacient(Long idPacient) throws Exception {
        return pacientRepository.findPacientHistoryByIdPacient(idPacient);
    }

    @Override
    public List<Plan> findPlanByIdPacient(Long idPacient) throws Exception {
        return pacientRepository.findPlanByIdPacient(idPacient);
    }

    @Override
    public List<Pacient> find(Date birthDate) throws Exception {
        return pacientRepository.find(birthDate);
    }

    @Override
    public List<HealthCondition> findHealthConditionsByIdPacient(Long idPacient) throws Exception {
        return pacientRepository.findHealthConditionsByIdPacient(idPacient);
    }

    @Override
    public List<FoodCondition> findFoodConditionsByIdPacient(Long idPacient) throws Exception {
        return pacientRepository.findFoodConditionsByIdPacient(idPacient);
    }
}
