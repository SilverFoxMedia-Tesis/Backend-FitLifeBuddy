package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.Daily;
import com.fitLifeBuddy.Entity.Enum.Frecuently;
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
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Plan actualizados"),
            @ApiResponse(code = 404, message = "Plan no encontrado")
    })
    public ResponseEntity<Plan> updatePlan(
            @PathVariable("id") Long id, @Valid @RequestBody Plan plan) {
        try {
            Optional<Plan> planUp = planService.getById(id);
            if (!planUp.isPresent())
                return new ResponseEntity<Plan>(HttpStatus.NOT_FOUND);
            plan.setIdPlan(id);
            planService.save(plan);
            return new ResponseEntity<Plan>(plan, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Plan>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping(value = "{idPacient}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Plan", notes = "Método que registra Plans en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Plan creado"),
            @ApiResponse(code = 404, message = "Plan no creado")
    })
    public ResponseEntity<Plan> insertPlan(@PathVariable("idPacient") Long idPacient ,@Valid @RequestBody Plan plan) {
        try {
            Optional<Pacient> pacient = pacientService.getById(idPacient);
            if (pacient.isPresent()){
                plan.setPacient(pacient.get());
                Plan planNew = planService.save(plan);
                return ResponseEntity.status(HttpStatus.CREATED).body(planNew);
            }else
                return new ResponseEntity<Plan>(HttpStatus.FAILED_DEPENDENCY);

        } catch (Exception e) {
            return new ResponseEntity<Plan>(HttpStatus.INTERNAL_SERVER_ERROR);

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
