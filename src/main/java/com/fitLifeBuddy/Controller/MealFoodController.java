package com.fitLifeBuddy.Controller;


import com.fitLifeBuddy.Entity.MealFood;
import com.fitLifeBuddy.Service.IMealFoodService;
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
@RequestMapping("/api/mealFoods")
@Api(tags = "MealFood", value = "Service Web RESTFul de MealFood")
public class MealFoodController {
    @Autowired
    private IMealFoodService mealFoodService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar MealFoods", notes = "Metodo para listar a todos los MealFoods")
    @ApiResponses({
            @ApiResponse(code = 201, message = "MealFoods encontrados"),
            @ApiResponse(code = 404, message = "MealFoods no encontrados")
    })
    public ResponseEntity<List<MealFood>> findAll(){
        try {
            List<MealFood> mealFoods = mealFoodService.getAll();
            if (mealFoods.size() > 0)
                return new ResponseEntity<List<MealFood>>(mealFoods, HttpStatus.OK);
            else
                return new ResponseEntity<List<MealFood>>(HttpStatus.NOT_FOUND);
        } catch (Exception ex){
            return new ResponseEntity<List<MealFood>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar MealFood por Id", notes = "Métodos para encontrar un MealFood por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "MealFood encontrado"),
            @ApiResponse(code = 404, message = "MealFood no encontrado")
    })
    public ResponseEntity<MealFood> findById(@PathVariable("id") Long id) {
        try {
            Optional<MealFood> mealFood = mealFoodService.getById(id);
            if (!mealFood.isPresent())
                return new ResponseEntity<MealFood>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<MealFood>(mealFood.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<MealFood>(HttpStatus.INTERNAL_SERVER_ERROR);
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de MealFoods", notes = "Método que registra MealFoods en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "MealFood creado"),
            @ApiResponse(code = 404, message = "MealFood no creado")
    })
    public ResponseEntity<MealFood> insertMealFood(@Valid @RequestBody MealFood mealFood) {
        try {
            MealFood mealFoodNew = mealFoodService.save(mealFood);
            return ResponseEntity.status(HttpStatus.CREATED).body(mealFoodNew);
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
}