package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.*;
import com.fitLifeBuddy.Entity.Enum.DietType;
import com.fitLifeBuddy.Entity.Enum.FoodOrigin;
import com.fitLifeBuddy.Service.IFoodService;
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
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/foods")
@Api(tags = "Food", value = "Service Web RESTFul de Foods")
public class FoodController {
    @Autowired
    private IFoodService foodService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Foods", notes = "Metodo para listar a todos los Foods")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Foods encontrados"),
            @ApiResponse(code = 404, message = "Foods no encontrados")
    })
    public ResponseEntity<List<Food>> findAll(){
        try {
            List<Food> foods = foodService.getAll();
            if (foods.size() > 0)
                return new ResponseEntity<List<Food>>(foods, HttpStatus.OK);
            else
                return new ResponseEntity<List<Food>>(HttpStatus.NOT_FOUND);
        } catch (Exception ex){
            return new ResponseEntity<List<Food>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Food por Id", notes = "Métodos para encontrar un Food por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Food encontrado"),
            @ApiResponse(code = 404, message = "Food no encontrado")
    })
    public ResponseEntity<Food> findById(@PathVariable("id") Long id) {
        try {
            Optional<Food> food = foodService.getById(id);
            if (!food.isPresent())
                return new ResponseEntity<Food>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Food>(food.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Food>(HttpStatus.INTERNAL_SERVER_ERROR);
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
    @ApiOperation(value = "Buscar Food por nameFood", notes = "Métodos para encontrar un Food por su respectivo nameFood")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Food encontrados"),
            @ApiResponse(code = 404, message = "Food no encontrados")
    })
    public ResponseEntity<List<Food>> findByNameFood(@PathVariable("nameFood") String nameFood) {
        try {
            List<Food> foods = foodService.findByNameFood(nameFood);
            if (foods.size() > 0)
                return new ResponseEntity<List<Food>>(foods, HttpStatus.OK);
            else
                return new ResponseEntity<List<Food>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<Food>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("searchByCategoryName/{categoryName}")
    @ApiOperation(value = "Buscar Food por categoryName", notes = "Métodos para encontrar un Food por su respectivo categoryName")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Food encontrados"),
            @ApiResponse(code = 404, message = "Food no encontrados")
    })
    public ResponseEntity<List<Food>> findByCategoryName(@PathVariable("categoryName") String categoryName) {
        try {
            List<Food> foods = foodService.findByCategoryName(categoryName);
            if (foods.size() > 0)
                return new ResponseEntity<List<Food>>(foods, HttpStatus.OK);
            else
                return new ResponseEntity<List<Food>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<Food>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("searchByFoodOrigin/{foodOrigin}")
    @ApiOperation(value = "Buscar Food por foodOrigin", notes = "Métodos para encontrar un Food por su respectivo foodOrigin")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Food encontrados"),
            @ApiResponse(code = 404, message = "Food no encontrados")
    })
    public ResponseEntity<List<Food>> findByFoodOrigin(@PathVariable("foodOrigin") FoodOrigin foodOrigin) {
        try {
            List<Food> foods = foodService.findByFoodOrigin(foodOrigin);
            if (foods.size() > 0)
                return new ResponseEntity<List<Food>>(foods, HttpStatus.OK);
            else
                return new ResponseEntity<List<Food>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<Food>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("searchByDietType/{dietType}")
    @ApiOperation(value = "Buscar Food por dietType", notes = "Métodos para encontrar un Food por su respectivo dietType")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Food encontrados"),
            @ApiResponse(code = 404, message = "Food no encontrados")
    })
    public ResponseEntity<List<Food>> findByDietType(@PathVariable("foodOrigin") DietType dietType) {
        try {
            List<Food> foods = foodService.findByDietType(dietType);
            if (foods.size() > 0)
                return new ResponseEntity<List<Food>>(foods, HttpStatus.OK);
            else
                return new ResponseEntity<List<Food>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<Food>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("searchMealsByIdFood/{idFood}")
    @ApiOperation(value = "Buscar Meals por Food", notes = "Métodos para encontrar un Meal por su respectivo Food")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Meals encontrados"),
            @ApiResponse(code = 404, message = "Meals no encontrados")
    })
    public ResponseEntity<List<Meal>> findMealsByIdFood(@PathVariable("idFood") Long idFood) {
        try {
            List<Meal> meals = foodService.findMealsByIdFood(idFood);
            if (meals.size() > 0)
                return new ResponseEntity<List<Meal>>(meals, HttpStatus.OK);
            else
                return new ResponseEntity<List<Meal>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<Meal>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
