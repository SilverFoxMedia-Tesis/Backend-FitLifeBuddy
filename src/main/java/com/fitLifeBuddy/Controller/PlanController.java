package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.DTO.PlanDTO;
import com.fitLifeBuddy.Entity.Daily;
import com.fitLifeBuddy.Entity.Enum.DietType;
import com.fitLifeBuddy.Entity.Enum.Frecuently;
import com.fitLifeBuddy.Entity.Enum.Status;
import com.fitLifeBuddy.Entity.Pacient;
import com.fitLifeBuddy.Entity.Plan;
import com.fitLifeBuddy.Service.IPacientService;
import com.fitLifeBuddy.Service.IPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/plans")
@Api(tags = "Plan", value = "Service Web RESTFul de Plans")
public class PlanController {
    @Autowired
    private IPlanService planService;

    @Autowired
    private IPacientService pacientService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Plans", notes = "Metodo para listar a todos los Plans")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Plans encontrados"),
            @ApiResponse(code = 404, message = "Plans no encontrados")
    })
    public ResponseEntity<List<Plan>> findAll(){
        try {
            List<Plan> plans = planService.getAll();
            if (plans.size() > 0)
                return new ResponseEntity<List<Plan>>(plans, HttpStatus.OK);
            else
                return new ResponseEntity<List<Plan>>(HttpStatus.NOT_FOUND);
        } catch (Exception ex){
            return new ResponseEntity<List<Plan>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Plan por Id", notes = "Métodos para encontrar un Plan por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Plan encontrado"),
            @ApiResponse(code = 404, message = "Plan no encontrado")
    })
    public ResponseEntity<Plan> findById(@PathVariable("id") Long id) {
        try {
            Optional<Plan> plan = planService.getById(id);
            if (!plan.isPresent())
                return new ResponseEntity<Plan>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Plan>(plan.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Plan>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de Plan", notes = "Metodo que actualiza los datos de Plan")
    public ResponseEntity<Plan> updatePlan(
            @PathVariable("id") Long id, @Valid @RequestBody PlanDTO planDTO) {
        try {
            Optional<Plan> planOptional = planService.getById(id);
            if (!planOptional.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Plan plan = planOptional.get();
            // Aplica cambios solo si el DTO proporciona un valor no nulo
            if (planDTO.getFrecuently() != null) plan.setFrecuently(planDTO.getFrecuently());
            if (planDTO.getDietType() != null) plan.setDietType(planDTO.getDietType());
            if (planDTO.getTmb() != null) plan.setTmb(planDTO.getTmb());
            if (planDTO.getCalorieDeficit() != null) plan.setCalorieDeficit(planDTO.getCalorieDeficit());
            if (planDTO.getCarbohydrates_g() != null) plan.setCarbohydrates_g(planDTO.getCarbohydrates_g());
            if (planDTO.getProteins_g() != null) plan.setProteins_g(planDTO.getProteins_g());
            if (planDTO.getFats_g() != null) plan.setFats_g(planDTO.getFats_g());
            if (planDTO.getWeightLose() != null) plan.setWeightLose(planDTO.getWeightLose());
            if (planDTO.getStatus() != null) plan.setStatus(planDTO.getStatus());

            Plan updatedPlan = planService.save(plan);
            return ResponseEntity.ok(updatedPlan);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping(value = "{idPacient}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Plan", notes = "Método que registra Plans en BD")
    public ResponseEntity<Plan> insertPlan(
            @PathVariable("idPacient") Long idPacient, @Valid @RequestBody PlanDTO planDTO) {

        try {
            Optional<Pacient> pacientOptional = pacientService.getById(idPacient);
            if (!pacientOptional.isPresent()) {
                return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
            }

            // Crear una nueva entidad Plan y aplicar los detalles del DTO
            Plan plan = new Plan();
            plan.setFrecuently(planDTO.getFrecuently());
            plan.setDietType(planDTO.getDietType());
            plan.setTmb(planDTO.getTmb());
            plan.setCalorieDeficit(planDTO.getCalorieDeficit());
            plan.setCarbohydrates_g(planDTO.getCarbohydrates_g());
            plan.setProteins_g(planDTO.getProteins_g());
            plan.setFats_g(planDTO.getFats_g());
            plan.setWeightLose(planDTO.getWeightLose());
            plan.setStatus(planDTO.getStatus() != null ? planDTO.getStatus() : Status.ACTIVED); // Defaulting to ACTIVED if not set

            // Asignar el paciente obtenido al nuevo plan
            plan.setPacient(pacientOptional.get());

            // Actualizar el estado de planes existentes si es necesario
            List<Plan> existingPlans = planService.findActivedPlanByPacientId(idPacient);
            for (Plan existingPlan : existingPlans) {
                existingPlan.setStatus(Status.CANCELED);
                planService.save(existingPlan);
            }

            // Guardar el nuevo plan
            Plan newPlan = planService.save(plan);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPlan);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de Plan", notes = "Metodo que elimina los datos de Plan")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Plan eliminados"),
            @ApiResponse(code = 404, message = "Plan no encontrado")
    })
    public ResponseEntity<Plan> deletePlan(@PathVariable("id") Long id) {
        try {
            Optional<Plan> planDelete = planService.getById(id);
            if (!planDelete.isPresent())
                return new ResponseEntity<Plan>(HttpStatus.NOT_FOUND);
            planService.delete(id);
            return new ResponseEntity<Plan>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Plan>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("searchDailiesByIdPlan/{idPlan}")
    @ApiOperation(value = "Buscar Dailies por Plan", notes = "Métodos para encontrar Dailies por su respectivo Plan")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Dailies encontrados"),
            @ApiResponse(code = 404, message = "Dailies no encontrados")
    })
    public ResponseEntity<List<Daily>> findDailiesByIdPlan(@PathVariable("idPlan") Long idPlan) {
        try {
            List<Daily> dailies = planService.findDailiesByIdPlan(idPlan);
            if (dailies.size() > 0)
                return new ResponseEntity<List<Daily>>(dailies, HttpStatus.OK);
            else
                return new ResponseEntity<List<Daily>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<Daily>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("searchByFrecuently/{frecuently}")
    @ApiOperation(value = "Buscar Plans por frecuently", notes = "Métodos para encontrar Plans por su respectivo frecuently")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Plans encontrados"),
            @ApiResponse(code = 404, message = "Plans no encontrados")
    })
    public ResponseEntity<List<Plan>> findByFrecuently(@PathVariable("frecuently") Frecuently frecuently) {
        try {
            List<Plan> plans = planService.findByFrecuently(frecuently);
            if (plans.size() > 0)
                return new ResponseEntity<List<Plan>>(plans, HttpStatus.OK);
            else
                return new ResponseEntity<List<Plan>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<Plan>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("searchByDietType/{dietType}")
    @ApiOperation(value = "Buscar Plans por dietType", notes = "Métodos para encontrar Plans por su respectivo dietType")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Plans encontrados"),
            @ApiResponse(code = 404, message = "Plans no encontrados")
    })
    public ResponseEntity<List<Plan>> findByDietType(@PathVariable("dietType") DietType dietType) {
        try {
            List<Plan> plans = planService.findByDietType(dietType);
            if (plans.size() > 0)
                return new ResponseEntity<List<Plan>>(plans, HttpStatus.OK);
            else
                return new ResponseEntity<List<Plan>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<Plan>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
