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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/routines")
@Api(tags = "Routine", value = "Service Web RESTful de Routines")
public class RoutineController {
    @Autowired
    private IRoutineService routineService;

    @Autowired
    private IDailyService dailyService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Routines", notes = "Método para listar a todos los Routines")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Routines encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Routine>> findAll(){
        try {
            List<Routine> routines = routineService.getAll();
            return new ResponseEntity<List<Routine>>(routines, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<List<Routine>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Routine por Id", notes = "Método para encontrar un Routine por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Routine encontrado"),
            @ApiResponse(code = 404, message = "Routine no encontrado"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<Routine> findById(@PathVariable("id") Long id) {
        try {
            Optional<Routine> routine = routineService.getById(id);
            if (!routine.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(routine.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<?> insertRoutine(@PathVariable("idDaily") Long idDaily) {
        try {
            Optional<Daily> daily = dailyService.getById(idDaily);
            if (daily.isPresent()){
                Routine routine = new Routine();
                routine.setDaily(daily.get()); // Asocia el Daily encontrado
                Routine routineNew = routineService.save(routine);
                return ResponseEntity.status(HttpStatus.CREATED).body(routineNew);
            } else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Cambio a NOT_FOUND (404) para indicar que Daily no se encontró

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

    @GetMapping("searchRoutineExercisesByIdRoutine/{idRoutine}")
    @ApiOperation(value = "Buscar RoutineExercises por Routine", notes = "Método para encontrar RoutineExercises por su respectivo Routine")
    @ApiResponses({
            @ApiResponse(code = 200, message = "RoutineExercises encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<RoutineExercise>> findRoutineExercisesByIdRoutine(@PathVariable("idRoutine") Long idRoutine) {
        try {
            List<RoutineExercise> routineExercises = routineService.findRoutineExercisesByIdRoutine(idRoutine);
            return new ResponseEntity<>(routineExercises, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
