package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.Nutritionist;
import com.fitLifeBuddy.Entity.Pacient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INutritionistRepository extends JpaRepository<Nutritionist, Long> {
    @Query("select n.pacients from Nutritionist n where n.idNutritionist = :nutritionistId")
    List<Pacient> findPacientsByIdNutritionist(@Param("nutritionistId")Long idNutritionist);
}
