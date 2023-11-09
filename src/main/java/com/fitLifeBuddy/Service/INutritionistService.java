package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.Nutritionist;
import com.fitLifeBuddy.Entity.Pacient;

import java.util.List;

public interface INutritionistService extends CrudService<Nutritionist>{
    List<Pacient> findPacientsByIdNutritionist(Long idNutritionist) throws Exception;
}
