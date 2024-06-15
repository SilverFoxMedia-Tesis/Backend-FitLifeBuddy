package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.Enum.BodyPart;
import com.fitLifeBuddy.Entity.Enum.TypeExercise;
import com.fitLifeBuddy.Entity.Exercise;
import com.fitLifeBuddy.Entity.RoutineExercise;
import com.fitLifeBuddy.Repository.IExerciseRepository;
import com.fitLifeBuddy.Service.IExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ExerciseServiceImpl implements IExerciseService {
    @Autowired
    private IExerciseRepository exerciseRepository;

    @Override
    @Transactional
    public Exercise save(Exercise exercise) throws Exception {
        return exerciseRepository.save(exercise);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        exerciseRepository.deleteById(id);

    }

    @Override
    public List<Exercise> getAll() throws Exception {
        return exerciseRepository.findAll().stream()
                .sorted((e1, e2) -> e1.getIdExercise().compareTo(e2.getIdExercise()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Exercise> getById(Long id) throws Exception {
        return exerciseRepository.findById(id);
    }

    @Override
    public List<Exercise> findByWorkout(String workout) throws Exception {
        return exerciseRepository.findByWorkout(workout);
    }

    @Override
    public List<Exercise> findByTypeExercise(TypeExercise typeExercise) throws Exception {
        return exerciseRepository.findByTypeExercise(typeExercise);
    }

    @Override
    public List<Exercise> findByBodyPart(BodyPart bodyPart) throws Exception {
        return exerciseRepository.findByBodyPart(bodyPart);
    }

    @Override
    public List<RoutineExercise> findRoutineExercisesByIdExercise(Long idExercise) throws Exception {
        return exerciseRepository.findRoutineExercisesByIdExercise(idExercise);
    }
}
