package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.*;
import com.fitLifeBuddy.Service.IDailyService;
import com.fitLifeBuddy.Service.IExerciseService;
import com.fitLifeBuddy.Service.IRoutineService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/routines")
@Api(tags = "Routine", value = "Service Web RESTful de Routines")
public class RoutineController {
    @Autowired
    private IRoutineService routineService;

    @Autowired
    private IDailyService dailyService;

    @Autowired
    private IExerciseService exerciseService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Meals", notes = "Metodo para listar a todos los Meals")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Meals encontrados"),
            @ApiResponse(code = 404, message = "Meals no encontrados")
    })
    public ResponseEntity<List<Routine>> findAll(){
        try {
            List<Routine> routines = routineService.getAll();
            if (routines.size() > 0)
                return new ResponseEntity<List<Routine>>(routines, HttpStatus.OK);
            else
                return new ResponseEntity<List<Routine>>(HttpStatus.NOT_FOUND);
        } catch (Exception ex){
            return new ResponseEntity<List<Routine>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Routine por Id", notes = "Métodos para encontrar un Routine por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Routine encontrado"),
            @ApiResponse(code = 404, message = "Routine no encontrado")
    })
    public ResponseEntity<Routine> findById(@PathVariable("id") Long id) {
        try {
            Optional<Routine> routine = routineService.getById(id);
            if (!routine.isPresent())
                return new ResponseEntity<Routine>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Routine>(routine.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Routine>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de Routine", notes = "Metodo que actualiza los datos de Routine")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Routine actualizados"),
            @ApiResponse(code = 404, message = "Routine no encontrado")
    })
    public ResponseEntity<Routine> updateRoutine(
            @PathVariable("id") Long id, @Valid @RequestBody Routine routine) {
        try {
            Optional<Routine> routineUp = routineService.getById(id);
            if (!routineUp.isPresent())
                return new ResponseEntity<Routine>(HttpStatus.NOT_FOUND);
            routine.setIdRoutine(id);
            routineService.save(routine);
            return new ResponseEntity<Routine>(routine, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Routine>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping(value = "{idDaily}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Routine", notes = "Método que registra Routine en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Routine creado"),
            @ApiResponse(code = 404, message = "Routine no creado")
    })
    public ResponseEntity<Routine> insertRoutine(@PathVariable("idDaily") Long idDaily, @RequestParam List<Long> idExercises, @Valid @RequestBody Routine routine) {
        try {
            Optional<Daily> daily = dailyService.getById(idDaily);

            if (daily.isPresent()) {
                routine.setDaily(daily.get());

                Set<Exercise> exercises = new HashSet<>();
                for (Long idExercise : idExercises) {
                    Optional<Exercise> exercise = exerciseService.getById(idExercise);
                    exercise.ifPresent(exercises::add);
                }

                routine.setExercises(exercises);

                Routine routineNew = routineService.save(routine);
                return ResponseEntity.status(HttpStatus.CREATED).body(routineNew);
            } else {
                return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de Routine", notes = "Metodo que elimina los datos de Routine")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Routine eliminados"),
            @ApiResponse(code = 404, message = "Routine no encontrado")
    })
    public ResponseEntity<Routine> deleteRoutine(@PathVariable("id") Long id) {
        try {
            Optional<Routine> routineDelete = routineService.getById(id);
            if (!routineDelete.isPresent())
                return new ResponseEntity<Routine>(HttpStatus.NOT_FOUND);
            routineService.delete(id);
            return new ResponseEntity<Routine>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Routine>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("searchExercisesByIdRoutine/{idRoutine}")
    @ApiOperation(value = "Buscar Exercises por Routine", notes = "Métodos para encontrar Exercises por su respectivo Routine")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Exercises encontrados"),
            @ApiResponse(code = 404, message = "Exercises no encontrados")
    })
    public ResponseEntity<List<Exercise>> findFoodsByIdRoutine(@PathVariable("idRoutine") Long idRoutine) {
        try {
            List<Exercise> exercises = routineService.findFoodsByIdRoutine(idRoutine);
            if (exercises.size() > 0)
                return new ResponseEntity<List<Exercise>>(exercises, HttpStatus.OK);
            else
                return new ResponseEntity<List<Exercise>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<Exercise>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
