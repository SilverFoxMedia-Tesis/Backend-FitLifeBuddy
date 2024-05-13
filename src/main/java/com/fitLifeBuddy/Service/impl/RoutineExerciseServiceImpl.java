package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.RoutineExercise;
import com.fitLifeBuddy.Repository.IRoutineExerciseRepository;
import com.fitLifeBuddy.Service.IRoutineExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoutineExerciseServiceImpl implements IRoutineExerciseService {
    @Autowired
    private IRoutineExerciseRepository routineExerciseRepository;

    @Override
    public RoutineExercise save(RoutineExercise routineExercise) throws Exception {
        return routineExerciseRepository.save(routineExercise);
    }

    @Override
    public void delete(Long id) throws Exception {
        routineExerciseRepository.deleteById(id);

    }

    @Override
    public List<RoutineExercise> getAll() throws Exception {
        return routineExerciseRepository.findAll();
    }

    @Override
    public Optional<RoutineExercise> getById(Long id) throws Exception {
        return routineExerciseRepository.findById(id);
    }
}
