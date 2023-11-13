package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.Enum.BodyPart;
import com.fitLifeBuddy.Entity.Enum.Equipment;
import com.fitLifeBuddy.Entity.Enum.Level;
import com.fitLifeBuddy.Entity.Enum.TypeExercise;
import com.fitLifeBuddy.Entity.Exercise;
import com.fitLifeBuddy.Entity.Routine;
import com.fitLifeBuddy.Repository.IExerciseRepository;
import com.fitLifeBuddy.Service.IExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        return exerciseRepository.findAll();
    }

    @Override
    public Optional<Exercise> getById(Long id) throws Exception {
        return exerciseRepository.findById(id);
    }

    @Override
    public List<Exercise> findByTitle(String title) throws Exception {
        return exerciseRepository.findByTitle(title);
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
    public List<Exercise> findByEquipment(Equipment equipment) throws Exception {
        return exerciseRepository.findByEquipment(equipment);
    }

    @Override
    public List<Exercise> findByLevel(Level level) throws Exception {
        return exerciseRepository.findByLevel(level);
    }

    @Override
    public List<Routine> findRoutinesByIdExercise(Long idExercise) throws Exception {
        return exerciseRepository.findRoutinesByIdExercise(idExercise);
    }
}
