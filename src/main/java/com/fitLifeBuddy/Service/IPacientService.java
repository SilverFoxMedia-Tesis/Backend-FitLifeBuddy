package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.*;

import java.util.Date;
import java.util.List;

public interface IPacientService extends CrudService<Pacient>{

    public PacientHistory findPacientHistoryByIdPacient(Long idPacient)throws Exception;
    public Plan findPlanByIdPacient(Long idPacient)throws Exception;
    public List<Pacient> find(Date birthDate)throws Exception;

    public List<HealthCondition> findHealthConditionsByIdPacient(Long idPacient)throws Exception;

    public List<FoodCondition> findFoodConditionsByIdPacient(Long idPacient) throws Exception;
}
