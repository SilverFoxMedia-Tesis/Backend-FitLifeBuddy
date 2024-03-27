package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.Daily;
import com.fitLifeBuddy.Entity.Enum.DietType;
import com.fitLifeBuddy.Entity.Enum.Frecuently;
import com.fitLifeBuddy.Entity.Plan;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPlanService extends CrudService<Plan>{
    public List<Daily> findDailiesByIdPlan(Long idPlan) throws  Exception;
    public List<Plan> findByFrecuently(Frecuently frecuently) throws Exception;
    public List<Plan> findByDietType(DietType dietType) throws Exception;
    public List<Plan> findActivedPlanByPacientId(Long pacientId) throws Exception;
}
