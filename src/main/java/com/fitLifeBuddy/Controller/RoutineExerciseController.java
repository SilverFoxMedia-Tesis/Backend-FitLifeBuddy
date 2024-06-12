package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.Exercise;
import com.fitLifeBuddy.Entity.Routine;
import com.fitLifeBuddy.Entity.RoutineExercise;
import com.fitLifeBuddy.Service.IExerciseService;
import com.fitLifeBuddy.Service.IRoutineExerciseService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/routineExercises")
@Api(tags = "RoutineExercise", value = "Service Web RESTFul de RoutineFood")
public class RoutineExerciseController {
    @Autowired
    private IRoutineExerciseService routineExerciseService;

    @Autowired
    private IRoutineService routineService;

    @Autowired
    private IExerciseService exerciseService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar RoutineExercises", notes = "Método para listar a todos los RoutineExercises")
    @ApiResponses({
            @ApiResponse(code = 200, message = "RoutineExercises encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<RoutineExercise>> findAll(){
        try {
            List<RoutineExercise> routineExercises = routineExerciseService.getAll();
            return new ResponseEntity<List<RoutineExercise>>(routineExercises, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<List<RoutineExercise>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar RoutineExercise por Id", notes = "Método para encontrar un RoutineExercise por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "RoutineExercise encontrado"),
            @ApiResponse(code = 404, message = "RoutineExercise no encontrado"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<RoutineExercise> findById(@PathVariable("id") Long id) {
        try {
            Optional<RoutineExercise> routineExercise = routineExerciseService.getById(id);
            if (!routineExercise.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(routineExercise.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de RoutineExercise", notes = "Metodo que actualiza los datos de RoutineExercise")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de RoutineExercise actualizados"),
            @ApiResponse(code = 404, message = "RoutineExercise no encontrado")
    })
    public ResponseEntity<RoutineExercise> updateRoutineExercise(
            @PathVariable("id") Long id, @Valid @RequestBody RoutineExercise routineExercise) {
        try {
            Optional<RoutineExercise> routineExerciseUp = routineExerciseService.getById(id);
            if (!routineExerciseUp.isPresent())
                return new ResponseEntity<RoutineExercise>(HttpStatus.NOT_FOUND);
            routineExercise.setIdRoutineExercise(id);
            routineExerciseService.save(routineExercise);
            return new ResponseEntity<RoutineExercise>(routineExercise, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<RoutineExercise>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping(value = "/{idRoutine}/{idExercise}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de RoutineExercises", notes = "Método que registra RoutineExercises en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "RoutineExercise creado"),
            @ApiResponse(code = 404, message = "RoutineExercise no creado")
    })
    public ResponseEntity<RoutineExercise> insertRoutineExercise(@PathVariable("idRoutine") Long idRoutine, @PathVariable("idExercise") Long idExercise,@Valid @RequestBody RoutineExercise routineExercise) {
        try {
            Optional<Routine> routine = routineService.getById(idRoutine);
            Optional<Exercise> exercise = exerciseService.getById(idExercise);
            if (routine.isPresent() && exercise.isPresent()){
                routineExercise.setRoutine(routine.get());
                routineExercise.setExercise(exercise.get());
                RoutineExercise routineExerciseNew = routineExerciseService.save(routineExercise);
                return ResponseEntity.status(HttpStatus.CREATED).body(routineExerciseNew);

            } else
                return new ResponseEntity<RoutineExercise>(HttpStatus.FAILED_DEPENDENCY);
        } catch (Exception e) {
            return new ResponseEntity<RoutineExercise>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de RoutineExercise", notes = "Metodo que elimina los datos de RoutineExercise")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de RoutineExercise eliminados"),
            @ApiResponse(code = 404, message = "RoutineExercise no encontrado")
    })
    public ResponseEntity<RoutineExercise> deleteRoutineExercise(@PathVariable("id") Long id) {
        try {
            Optional<RoutineExercise> routineExerciseDelete = routineExerciseService.getById(id);
            if (!routineExerciseDelete.isPresent())
                return new ResponseEntity<RoutineExercise>(HttpStatus.NOT_FOUND);
            routineExerciseService.delete(id);
            return new ResponseEntity<RoutineExercise>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<RoutineExercise>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
