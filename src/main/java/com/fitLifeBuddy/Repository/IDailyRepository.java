package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.Daily;
import com.fitLifeBuddy.Entity.Exercise;
import com.fitLifeBuddy.Entity.Meal;
import com.fitLifeBuddy.Entity.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IDailyRepository extends JpaRepository<Daily, Long> {
    @Query("select d from Daily d where d.date = :dateDaily")
    public List<Daily> findByDate(@Param("dateDaily") Date date);
    @Query("select d.meals from Daily d where d.idDaily = :dailyId")
    public List<Meal> findMealsByIdDaily(@Param("dailyId")Long idDaily);
    @Query("select d.routines from Daily d where d.idDaily = :dailyId")
    public List<Routine> findRoutinesByIdDaily(@Param("dailyId")Long idDaily);

}
