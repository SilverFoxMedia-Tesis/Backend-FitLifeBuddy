package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.Daily;
import com.fitLifeBuddy.Entity.Enum.Status;
import com.fitLifeBuddy.Entity.Exercise;
import com.fitLifeBuddy.Entity.Meal;
import com.fitLifeBuddy.Entity.Routine;
import com.fitLifeBuddy.Repository.IDailyRepository;
import com.fitLifeBuddy.Service.IDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DailyServiceImpl implements IDailyService {
    @Autowired
    private IDailyRepository dailyRepository;

    @Override
    @Transactional
    public Daily save(Daily daily) throws Exception {
        return dailyRepository.save(daily);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        dailyRepository.deleteById(id);
    }

    @Override
    public List<Daily> getAll() throws Exception {
        return dailyRepository.findAll().stream()
                .sorted((d1, d2) -> d1.getIdDaily().compareTo(d2.getIdDaily()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Daily> getById(Long id) throws Exception {
        return dailyRepository.findById(id);
    }

    @Override
    public List<Daily> findByDateAndPatientId(Date date, Long idPacient) throws Exception {
        return dailyRepository.findByDateAndPatientId(date, idPacient);
    }

    @Override
    public List<Daily> findByDate(Date date) throws Exception {
        return dailyRepository.findByDate(date);
    }

    @Override
    public List<Meal> findMealsByIdDaily(Long idDaily) throws Exception {
        return dailyRepository.findMealsByIdDaily(idDaily);
    }

    @Override
    public List<Routine> findRoutinesByIdDaily(Long idDaily) throws Exception {
        return dailyRepository.findRoutinesByIdDaily(idDaily);
    }

    @Override
    public List<Daily> findDailyByDateAndStatusUnfilled(Date date, Long idPacient) throws Exception {
        return dailyRepository.findByDateAndStatus(date, Status.UNFILLED, idPacient );
    }

    @Override
    public List<Daily> findUnfilledDailiesUntilToday(Date dateDaily, Status status, Long idPlan) throws Exception {
        return dailyRepository.findUnfilledDailiesUntilToday(dateDaily, Status.UNFILLED, idPlan);
    }
}
