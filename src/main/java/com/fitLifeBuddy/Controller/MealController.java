package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.*;
import com.fitLifeBuddy.Entity.DTO.MealDTO;
import com.fitLifeBuddy.Service.IDailyService;
import com.fitLifeBuddy.Service.IFoodService;
import com.fitLifeBuddy.Service.IMealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/api/meals")
@Api(tags = "Meal", value = "Service Web RESTFul de Meals")
public class MealController {
    private static final Logger logger = LoggerFactory.getLogger(PacientController.class);

    @Autowired
    private IMealService mealService;

    @Autowired
    private IDailyService dailyService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Meals", notes = "Método para listar a todos los Meals")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Meals encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Meal>> findAll(){
        try {
            List<Meal> meals = mealService.getAll();
            return new ResponseEntity<List<Meal>>(meals, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<List<Meal>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Meal por Id", notes = "Métodos para encontrar un Meal por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Meal encontrado"),
            @ApiResponse(code = 404, message = "Meal no encontrado")
    })
    public ResponseEntity<Meal> findById(@PathVariable("id") Long id) {
        try {
            Optional<Meal> meal = mealService.getById(id);
            if (!meal.isPresent())
                return new ResponseEntity<Meal>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Meal>(meal.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Meal>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de Meal", notes = "Metodo que actualiza los datos de Meal")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Meal actualizados"),
            @ApiResponse(code = 404, message = "Meal no encontrado")
    })
    public ResponseEntity<Meal> updateMeal(
            @PathVariable("id") Long id, @Valid @RequestBody Meal meal) {
        try {
            Optional<Meal> mealUp = mealService.getById(id);
            if (!mealUp.isPresent())
                return new ResponseEntity<Meal>(HttpStatus.NOT_FOUND);
            meal.setIdMeal(id);
            mealService.save(meal);
            return new ResponseEntity<Meal>(meal, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Meal>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping(value = "{idDaily}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Meal", notes = "Método que registra Meal en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Meal creado"),
            @ApiResponse(code = 400, message = "Información inválida o incompleta para crear el Meal")
    })
    public ResponseEntity<?> insertMeal(@PathVariable("idDaily") Long idDaily, @Valid @RequestBody MealDTO mealDTO) {
        try {
            Optional<Daily> daily = dailyService.getById(idDaily);
            if (daily.isPresent()){
                Meal meal = new Meal();
                meal.setTimeMeal(mealDTO.getTimeMeal()); // Setea el valor de timeMeal desde el DTO
                meal.setDaily(daily.get()); // Asocia el Daily correspondiente
                Meal mealNew = mealService.save(meal);
                return ResponseEntity.status(HttpStatus.CREATED).body(mealNew);
            } else
                return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de Meal", notes = "Metodo que elimina los datos de Meal")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Meal eliminados"),
            @ApiResponse(code = 404, message = "Meal no encontrado")
    })
    public ResponseEntity<Meal> deleteMeal(@PathVariable("id") Long id) {
        try {
            Optional<Meal> mealDelete = mealService.getById(id);
            if (!mealDelete.isPresent())
                return new ResponseEntity<Meal>(HttpStatus.NOT_FOUND);
            mealService.delete(id);
            return new ResponseEntity<Meal>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Meal>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("searchByNameMeal/{nameMeal}")
    @ApiOperation(value = "Buscar Meal por nameMeal", notes = "Métodos para encontrar Meals por su respectivo nameMeal")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Meals encontrados"),
            @ApiResponse(code = 404, message = "Meals no encontrados")
    })
    public ResponseEntity<List<Meal>> findByNameMeal(@PathVariable("nameMeal") String nameMeal) {
        try {
            List<Meal> meals = mealService.findByNameMeal(nameMeal);
            if (meals.size() > 0)
                return new ResponseEntity<List<Meal>>(meals, HttpStatus.OK);
            else
                return new ResponseEntity<List<Meal>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<Meal>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
    @GetMapping("searchMealFoodsByIdMeal/{idMeal}")
    @ApiOperation(value = "Buscar MealFoods por Meal", notes = "Métodos para encontrar MealFoods por su respectivo Meal")
    @ApiResponses({
            @ApiResponse(code = 201, message = "MealFoods encontrados"),
            @ApiResponse(code = 404, message = "MealFoods no encontrados")
    })
    public ResponseEntity<List<MealFood>> findMealFoodsByIdMeal(@PathVariable("idMeal") Long idMeal) {
        try {
            List<MealFood> mealFoods = mealService.findMealFoodsByIdMeal(idMeal);
            if (mealFoods.size() > 0)
                return new ResponseEntity<List<MealFood>>(mealFoods, HttpStatus.OK);
            else
                return new ResponseEntity<List<MealFood>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<MealFood>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
