package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.Routine;
import com.fitLifeBuddy.Entity.RoutineExercise;
import com.fitLifeBuddy.Repository.IRoutineRepository;
import com.fitLifeBuddy.Service.IRoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RoutineServiceImpl implements IRoutineService {
    @Autowired
    private IRoutineRepository routineRepository;

    @Override
    @Transactional
    public Routine save(Routine routine) throws Exception {
        return routineRepository.save(routine);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        routineRepository.deleteById(id);
    }

    @Override
    public List<Routine> getAll() throws Exception {
        return routineRepository.findAll().stream()
                .sorted((r1, r2) -> r1.getIdRoutine().compareTo(r2.getIdRoutine()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Routine> getById(Long id) throws Exception {
        return routineRepository.findById(id);
    }

    @Override
    public List<RoutineExercise> findRoutineExercisesByIdRoutine(Long idRoutine) throws Exception {
        return routineRepository.findRoutineExercisesByIdRoutine(idRoutine);
    }
}
