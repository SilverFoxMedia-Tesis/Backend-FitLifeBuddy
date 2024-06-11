package com.fitLifeBuddy.Controller;


import com.fitLifeBuddy.Entity.Enum.CategoryName;
import com.fitLifeBuddy.Entity.Food;
import com.fitLifeBuddy.Entity.Meal;
import com.fitLifeBuddy.Entity.MealFood;
import com.fitLifeBuddy.Service.IFoodService;
import com.fitLifeBuddy.Service.IMealFoodService;
import com.fitLifeBuddy.Service.IMealService;
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
@RequestMapping("/api/mealFoods")
@Api(tags = "MealFood", value = "Service Web RESTFul de MealFood")
public class MealFoodController {
    @Autowired
    private IMealFoodService mealFoodService;

    @Autowired
    private IMealService mealService;

    @Autowired
    private IFoodService foodService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar MealFoods", notes = "Método para listar a todos los MealFoods")
    @ApiResponses({
            @ApiResponse(code = 200, message = "MealFoods encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<MealFood>> findAll(){
        try {
            List<MealFood> mealFoods = mealFoodService.getAll();
            return new ResponseEntity<List<MealFood>>(mealFoods, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<List<MealFood>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar MealFood por Id", notes = "Método para encontrar un MealFood por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "MealFood encontrado o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<MealFood>> findById(@PathVariable("id") Long id) {
        try {
            Optional<MealFood> mealFood = mealFoodService.getById(id);
            if (!mealFood.isPresent()) {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            }
            List<MealFood> result = new ArrayList<>();
            result.add(mealFood.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de MealFood", notes = "Metodo que actualiza los datos de MealFood")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de MealFood actualizados"),
            @ApiResponse(code = 404, message = "MealFood no encontrado")
    })
    public ResponseEntity<MealFood> updateMealFood(
            @PathVariable("id") Long id, @Valid @RequestBody MealFood mealFood) {
        try {
            Optional<MealFood> mealFoodUp = mealFoodService.getById(id);
            if (!mealFoodUp.isPresent())
                return new ResponseEntity<MealFood>(HttpStatus.NOT_FOUND);
            mealFood.setIdMealFood(id);
            mealFoodService.save(mealFood);
            return new ResponseEntity<MealFood>(mealFood, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<MealFood>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping(value = "/{idMeal}/{idFood}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de MealFoods", notes = "Método que registra MealFoods en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "MealFood creado"),
            @ApiResponse(code = 404, message = "MealFood no creado")
    })
    public ResponseEntity<MealFood> insertMealFood(@PathVariable("idMeal") Long idMeal, @PathVariable("idFood") Long idFood,@Valid @RequestBody MealFood mealFood) {
        try {
            Optional<Meal> meal = mealService.getById(idMeal);
            Optional<Food> food = foodService.getById(idFood);
            if (meal.isPresent() && food.isPresent()){
                mealFood.setMeal(meal.get());
                mealFood.setFood(food.get());
                MealFood mealFoodNew = mealFoodService.save(mealFood);
                return ResponseEntity.status(HttpStatus.CREATED).body(mealFoodNew);
            } else
                return new ResponseEntity<MealFood>(HttpStatus.FAILED_DEPENDENCY);

        } catch (Exception e) {
            return new ResponseEntity<MealFood>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de MealFood", notes = "Metodo que elimina los datos de MealFood")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de MealFood eliminados"),
            @ApiResponse(code = 404, message = "MealFood no encontrado")
    })
    public ResponseEntity<MealFood> deleteMealFood(@PathVariable("id") Long id) {
        try {
            Optional<MealFood> mealFoodDelete = mealFoodService.getById(id);
            if (!mealFoodDelete.isPresent())
                return new ResponseEntity<MealFood>(HttpStatus.NOT_FOUND);
            mealFoodService.delete(id);
            return new ResponseEntity<MealFood>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<MealFood>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/suggestions/{idMealFood}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Obtener sugerencias de Food por categoría", notes = "Método para obtener una lista de alimentos de la misma categoría que el alimento actual en MealFood")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sugerencias encontradas"),
            @ApiResponse(code = 404, message = "MealFood no encontrado")
    })
    public ResponseEntity<List<Food>> getFoodSuggestions(@PathVariable("idMealFood") Long idMealFood) {
        try {
            Optional<MealFood> mealFood = mealFoodService.getById(idMealFood);
            if (!mealFood.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            CategoryName categoryName = mealFood.get().getFood().getCategoryName();
            List<Food> foodSuggestions = foodService.findByCategoryName(categoryName);

            return new ResponseEntity<>(foodSuggestions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
