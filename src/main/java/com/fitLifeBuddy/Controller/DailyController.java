package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.*;
import com.fitLifeBuddy.Service.IDailyService;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/dailies")
@Api(tags = "Daily" ,value = "Service Web RESTFul de Dailies")
public class DailyController {
    @Autowired
    private IDailyService dailyService;

    @Autowired
    private IPlanService planService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Dailies", notes = "Metodo para listar a todos los Dailies")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Dailies encontrados"),
            @ApiResponse(code = 404, message = "Dailies no encontrados")
    })
    public ResponseEntity<List<Daily>> findAll(){
        try {
            List<Daily> dailies = dailyService.getAll();
            if (dailies.size() > 0)
                return new ResponseEntity<List<Daily>>(dailies, HttpStatus.OK);
            else
                return new ResponseEntity<List<Daily>>(HttpStatus.NOT_FOUND);
        } catch (Exception ex){
            return new ResponseEntity<List<Daily>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Daily por Id", notes = "Métodos para encontrar un Daily por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Daily encontrado"),
            @ApiResponse(code = 404, message = "Daily no encontrado")
    })
    public ResponseEntity<Daily> findById(@PathVariable("id") Long id) {
        try {
            Optional<Daily> daily = dailyService.getById(id);
            if (!daily.isPresent())
                return new ResponseEntity<Daily>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Daily>(daily.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Daily>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de Daily", notes = "Metodo que actualiza los datos de Daily")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Daily actualizados"),
            @ApiResponse(code = 404, message = "Daily no encontrado")
    })
    public ResponseEntity<Daily> updateDaily(
            @PathVariable("id") Long id, @Valid @RequestBody Daily daily) {
        try {
            Optional<Daily> dailyUp = dailyService.getById(id);
            if (!dailyUp.isPresent())
                return new ResponseEntity<Daily>(HttpStatus.NOT_FOUND);
            daily.setIdDaily(id);
            dailyService.save(daily);
            return new ResponseEntity<Daily>(daily, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Daily>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping(value = "{idPlan}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Daily", notes = "Método que registra Dailies en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Daily creado"),
            @ApiResponse(code = 404, message = "Daily no creado")
    })
    public ResponseEntity<Daily> insertDaily(@PathVariable("idPlan") Long idPlan ,@Valid @RequestBody Daily daily) {
        try {
            Optional<Plan> plan = planService.getById(idPlan);
            if (plan.isPresent()){
                daily.setPlan(plan.get());
                Daily dailyNew = dailyService.save(daily);
                return ResponseEntity.status(HttpStatus.CREATED).body(dailyNew);
            }else
                return new ResponseEntity<Daily>(HttpStatus.FAILED_DEPENDENCY);

        } catch (Exception e) {
            return new ResponseEntity<Daily>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de Daily", notes = "Metodo que elimina los datos de Daily")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Daily eliminados"),
            @ApiResponse(code = 404, message = "Daily no encontrado")
    })
    public ResponseEntity<Daily> deleteDaily(@PathVariable("id") Long id) {
        try {
            Optional<Daily> dailyDelete = dailyService.getById(id);
            if (!dailyDelete.isPresent())
                return new ResponseEntity<Daily>(HttpStatus.NOT_FOUND);
            dailyService.delete(id);
            return new ResponseEntity<Daily>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Daily>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("searchByDate/{date}")
    @ApiOperation(value = "Buscar Daily por date", notes = "Métodos para encontrar un Daily por su respectivo date")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Dailies encontrados"),
            @ApiResponse(code = 404, message = "Dailies no encontrados")
    })
    public ResponseEntity<List<Daily>> findByDate(@PathVariable("date") Date date) {
        try {
            List<Daily> dailies = dailyService.findByDate(date);
            if (dailies.size() > 0)
                return new ResponseEntity<List<Daily>>(dailies, HttpStatus.OK);
            else
                return new ResponseEntity<List<Daily>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<Daily>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("searchMealsByIdDaily/{idDaily}")
    @ApiOperation(value = "Buscar Meals por Daily", notes = "Métodos para encontrar un Meal por su respectivo Daily")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Meals encontrados"),
            @ApiResponse(code = 404, message = "Meals no encontrados")
    })
    public ResponseEntity<List<Meal>> findMealsByIdDaily(@PathVariable("idDaily") Long idDaily) {
        try {
            List<Meal> meals = dailyService.findMealsByIdDaily(idDaily);
            if (meals.size() > 0)
                return new ResponseEntity<List<Meal>>(meals, HttpStatus.OK);
            else
                return new ResponseEntity<List<Meal>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<Meal>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("searchRoutinesByIdDaily/{idDaily}")
    @ApiOperation(value = "Buscar Routines por Daily", notes = "Métodos para encontrar un Routine por su respectivo Daily")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Routines encontrados"),
            @ApiResponse(code = 404, message = "Routines no encontrados")
    })
    public ResponseEntity<List<Routine>> findRoutinesByIdDaily(@PathVariable("idDaily") Long idDaily) {
        try {
            List<Routine> routines = dailyService.findRoutinesByIdDaily(idDaily);
            if (routines.size() > 0)
                return new ResponseEntity<List<Routine>>(routines, HttpStatus.OK);
            else
                return new ResponseEntity<List<Routine>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<Routine>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
