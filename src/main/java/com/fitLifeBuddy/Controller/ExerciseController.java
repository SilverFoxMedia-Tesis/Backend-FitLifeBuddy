package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.Enum.BodyPart;
import com.fitLifeBuddy.Entity.Enum.TypeExercise;
import com.fitLifeBuddy.Entity.Exercise;
import com.fitLifeBuddy.Entity.RoutineExercise;
import com.fitLifeBuddy.Service.IExerciseService;
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
@RequestMapping("/api/exercises")
@Api(tags = "Exercise", value = "Service Web RESTFul de Exercises")
public class ExerciseController {
    @Autowired
    private IExerciseService exerciseService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Exercises", notes = "Método para listar a todos los Exercises")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exercises encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Exercise>> findAll() {
        try {
            List<Exercise> exercises = exerciseService.getAll();
            return new ResponseEntity<List<Exercise>>(exercises, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<List<Exercise>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Exercise por Id", notes = "Método para encontrar un Exercise por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exercise encontrado o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Exercise>> findById(@PathVariable("id") Long id) {
        try {
            Optional<Exercise> exercise = exerciseService.getById(id);
            if (!exercise.isPresent()) {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            }
            List<Exercise> result = new ArrayList<>();
            result.add(exercise.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de Exercise", notes = "Metodo que actualiza los datos de Exercise")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Exercise actualizados"),
            @ApiResponse(code = 404, message = "Exercise no encontrado")
    })
    public ResponseEntity<Exercise> updateExercise(
            @PathVariable("id") Long id, @Valid @RequestBody Exercise exercise) {
        try {
            Optional<Exercise> exerciseUp = exerciseService.getById(id);
            if (!exerciseUp.isPresent())
                return new ResponseEntity<Exercise>(HttpStatus.NOT_FOUND);
            exercise.setIdExercise(id);
            exerciseService.save(exercise);
            return new ResponseEntity<Exercise>(exercise, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Exercise>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Exercise", notes = "Método que registra Exercises en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Exercise creado"),
            @ApiResponse(code = 404, message = "Exercise no creado")
    })
    public ResponseEntity<Exercise> insertExercise(@Valid @RequestBody Exercise exercise) {
        try {
            Exercise exerciseNew = exerciseService.save(exercise);
            return ResponseEntity.status(HttpStatus.CREATED).body(exerciseNew);

        } catch (Exception e) {
            return new ResponseEntity<Exercise>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de Exercise", notes = "Metodo que elimina los datos de Exercise")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Exercise eliminados"),
            @ApiResponse(code = 404, message = "Exercise no encontrado")
    })
    public ResponseEntity<Exercise> deleteExercise(@PathVariable("id") Long id) {
        try {
            Optional<Exercise> exerciseDelete = exerciseService.getById(id);
            if (!exerciseDelete.isPresent())
                return new ResponseEntity<Exercise>(HttpStatus.NOT_FOUND);
            exerciseService.delete(id);
            return new ResponseEntity<Exercise>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Exercise>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("searchByWorkout/{workout}")
    @ApiOperation(value = "Buscar Exercise por Workout", notes = "Método para encontrar un Workout por su respectivo title")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Workout encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Exercise>> findByWorkout(@PathVariable("workout") String workout) {
        try {
            List<Exercise> exercises = exerciseService.findByWorkout(workout);
            return new ResponseEntity<List<Exercise>>(exercises, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<Exercise>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("searchByTypeExercise/{typeExercise}")
    @ApiOperation(value = "Buscar Exercise por typeExercise", notes = "Métodos para encontrar un Exercise por su respectivo typeExercise")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Exercise encontrados"),
            @ApiResponse(code = 404, message = "Exercise no encontrados")
    })
    public ResponseEntity<List<Exercise>> findByTypeExercise(@PathVariable("typeExercise") TypeExercise typeExercise) {
        try {
            List<Exercise> exercises = exerciseService.findByTypeExercise(typeExercise);
            if (exercises.size() > 0)
                return new ResponseEntity<List<Exercise>>(exercises, HttpStatus.OK);
            else
                return new ResponseEntity<List<Exercise>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<Exercise>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("searchByBodyPart/{bodyPart}")
    @ApiOperation(value = "Buscar Exercise por title", notes = "Métodos para encontrar un Exercise por su respectivo bodyPart")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Exercise encontrados"),
            @ApiResponse(code = 404, message = "Exercise no encontrados")
    })
    public ResponseEntity<List<Exercise>> findByBodyPart(@PathVariable("bodyPart") BodyPart bodyPart) {
        try {
            List<Exercise> exercises = exerciseService.findByBodyPart(bodyPart);
            if (exercises.size() > 0)
                return new ResponseEntity<List<Exercise>>(exercises, HttpStatus.OK);
            else
                return new ResponseEntity<List<Exercise>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<Exercise>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @GetMapping("searchRoutineExercisesByIdExercise/{idExercise}")
    @ApiOperation(value = "Buscar RoutineExercise por Exercise", notes = "Métodos para encontrar un RoutineExercise por su respectivo Exercise")
    @ApiResponses({
            @ApiResponse(code = 201, message = "RoutineExercise encontrados"),
            @ApiResponse(code = 404, message = "RoutineExercise no encontrados")
    })
    public ResponseEntity<List<RoutineExercise>> findRoutineExercisesByIdExercise(@PathVariable("idExercise") Long idExercise) {
        try {
            List<RoutineExercise> routineExercises = exerciseService.findRoutineExercisesByIdExercise(idExercise);
            if (routineExercises.size() > 0)
                return new ResponseEntity<List<RoutineExercise>>(routineExercises, HttpStatus.OK);
            else
                return new ResponseEntity<List<RoutineExercise>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<RoutineExercise>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
