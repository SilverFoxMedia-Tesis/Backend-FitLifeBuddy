package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.*;
import com.fitLifeBuddy.Entity.Enum.CategoryName;
import com.fitLifeBuddy.Service.IFoodService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/foods")
@Api(tags = "Food", value = "Service Web RESTFul de Foods")
public class FoodController {

    private static final Logger logger = LoggerFactory.getLogger(PacientController.class);
    @Autowired
    private IFoodService foodService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Foods", notes = "Método para listar a todos los Foods")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Foods encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Food>> findAll() {
        logger.info("Iniciando la búsqueda de todos los Foods.");
        try {
            List<Food> foods = foodService.getAll();
            if (foods.size() > 0) {
                logger.info("Foods encontrados: {}", foods.size());
            } else {
                logger.warn("No se encontraron Foods.");
            }
            return new ResponseEntity<>(foods, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error al listar los Foods.", ex);
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Food por Id", notes = "Método para encontrar un Food por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Food encontrado"),
            @ApiResponse(code = 404, message = "Food no encontrado"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<Food> findById(@PathVariable("id") Long id) {
        try {
            Optional<Food> food = foodService.getById(id);
            if (!food.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(food.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de Food", notes = "Metodo que actualiza los datos de Food")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Food actualizados"),
            @ApiResponse(code = 404, message = "Food no encontrado")
    })
    public ResponseEntity<Food> updateFood(
            @PathVariable("id") Long id, @Valid @RequestBody Food food) {
        try {
            Optional<Food> foodUp = foodService.getById(id);
            if (!foodUp.isPresent())
                return new ResponseEntity<Food>(HttpStatus.NOT_FOUND);
            food.setIdFood(id);
            foodService.save(food);
            return new ResponseEntity<Food>(food, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Food>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Food", notes = "Método que registra Foods en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Food creado"),
            @ApiResponse(code = 404, message = "Food no creado")
    })
    public ResponseEntity<Food> insertFood(@Valid @RequestBody Food food) {
        try {
            Food foodNew = foodService.save(food);
            return ResponseEntity.status(HttpStatus.CREATED).body(foodNew);

        } catch (Exception e) {
            return new ResponseEntity<Food>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de Food", notes = "Metodo que elimina los datos de Food")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Food eliminados"),
            @ApiResponse(code = 404, message = "Food no encontrado")
    })
    public ResponseEntity<Food> deleteFood(@PathVariable("id") Long id) {
        try {
            Optional<Food> foodDelete = foodService.getById(id);
            if (!foodDelete.isPresent())
                return new ResponseEntity<Food>(HttpStatus.NOT_FOUND);
            foodService.delete(id);
            return new ResponseEntity<Food>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Food>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("searchByNameFood/{nameFood}")
    @ApiOperation(value = "Buscar Food por nameFood", notes = "Método para encontrar un Food por su respectivo nameFood")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Food encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Food>> findByNameFood(@PathVariable("nameFood") String nameFood) {
        try {
            List<Food> foods = foodService.findByNameFood(nameFood);
            return new ResponseEntity<>(foods, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("searchByCategoryName/{categoryName}")
    @ApiOperation(value = "Buscar Food por categoryName", notes = "Método para encontrar un Food por su respectivo categoryName")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Food encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Food>> findByCategoryName(@PathVariable("categoryName") CategoryName categoryName) {
        try {
            List<Food> foods = foodService.findByCategoryName(categoryName);
            return new ResponseEntity<>(foods, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("searchMealFoodByIdFood/{idFood}")
    @ApiOperation(value = "Buscar MealFoods por Food", notes = "Método para encontrar MealFoods por su respectivo Food")
    @ApiResponses({
            @ApiResponse(code = 200, message = "MealFoods encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<MealFood>> findMealFoodsByIdFood(@PathVariable("idFood") Long idFood) {
        try {
            List<MealFood> mealFoods = foodService.findMealFoodsByIdFood(idFood);
            return new ResponseEntity<>(mealFoods, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
