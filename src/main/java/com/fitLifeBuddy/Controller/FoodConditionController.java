package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.Enum.TypeFoodCondition;
import com.fitLifeBuddy.Entity.FoodCondition;
import com.fitLifeBuddy.Entity.Pacient;
import com.fitLifeBuddy.Service.IFoodConditionService;
import com.fitLifeBuddy.Service.IPacientService;
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
@RequestMapping("/api/foodConditions")
@Api(tags = "FoodCondition", value = "Service Web RESTFul de FoodConditions")
public class FoodConditionController {

    @Autowired
    private IFoodConditionService foodConditionService;

    @Autowired
    private IPacientService pacientService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar FoodConditions", notes = "Metodo para listar a todos los FoodConditions")
    @ApiResponses({
            @ApiResponse(code = 200, message = "FoodConditions encontrados o no existen FoodConditions"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<FoodCondition>> findAll(){
        try {
            List<FoodCondition> foodConditions = foodConditionService.getAll();
            return new ResponseEntity<List<FoodCondition>>(foodConditions, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<List<FoodCondition>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar FoodCondition por Id", notes = "Método para encontrar un FoodCondition por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "FoodCondition encontrado"),
            @ApiResponse(code = 404, message = "FoodCondition no encontrado"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<FoodCondition> findById(@PathVariable("id") Long id) {
        try {
            Optional<FoodCondition> foodCondition = foodConditionService.getById(id);
            if (!foodCondition.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(foodCondition.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de FoodCondition", notes = "Metodo que actualiza los datos de FoodCondition")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de FoodCondition actualizados"),
            @ApiResponse(code = 404, message = "FoodCondition no encontrado")
    })
    public ResponseEntity<FoodCondition> updateFoodCondition(
            @PathVariable("id") Long id, @Valid @RequestBody FoodCondition foodCondition) {
        try {
            Optional<FoodCondition> foodConditionUp = foodConditionService.getById(id);
            if (!foodConditionUp.isPresent())
                return new ResponseEntity<FoodCondition>(HttpStatus.NOT_FOUND);
            foodCondition.setIdFoodCondition(id);
            foodConditionService.save(foodCondition);
            return new ResponseEntity<FoodCondition>(foodCondition, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<FoodCondition>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping(value = "{idPacient}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de FoodCondition", notes = "Método que registra FoodConditions en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "FoodCondition creado"),
            @ApiResponse(code = 404, message = "FoodCondition no creado")
    })
    public ResponseEntity<FoodCondition> insertFoodCondition(@PathVariable("idPacient") Long idPacient ,@Valid @RequestBody FoodCondition foodCondition) {
        try {
            Optional<Pacient> pacient = pacientService.getById(idPacient);
            if (pacient.isPresent()){
                foodCondition.setPacient(pacient.get());
                FoodCondition foodConditionNew = foodConditionService.save(foodCondition);
                return ResponseEntity.status(HttpStatus.CREATED).body(foodConditionNew);
            }else
                return new ResponseEntity<FoodCondition>(HttpStatus.FAILED_DEPENDENCY);

        } catch (Exception e) {
            return new ResponseEntity<FoodCondition>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de FoodCondition", notes = "Metodo que elimina los datos de FoodCondition")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de FoodCondition eliminados"),
            @ApiResponse(code = 404, message = "FoodCondition no encontrado")
    })
    public ResponseEntity<FoodCondition> deleteFoodCondition(@PathVariable("id") Long id) {
        try {
            Optional<FoodCondition> foodConditionDelete = foodConditionService.getById(id);
            if (!foodConditionDelete.isPresent())
                return new ResponseEntity<FoodCondition>(HttpStatus.NOT_FOUND);
            foodConditionService.delete(id);
            return new ResponseEntity<FoodCondition>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<FoodCondition>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("searchByNameFoodCondition/{nameFoodCondition}")
    @ApiOperation(value = "Buscar FoodCondition por nameFoodCondition", notes = "Método para encontrar un FoodCondition por su respectivo nameFoodCondition")
    @ApiResponses({
            @ApiResponse(code = 200, message = "FoodCondition encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<FoodCondition>> findByNameFoodCondition(@PathVariable("nameFoodCondition") String nameFoodCondition) {
        try {
            List<FoodCondition> foodConditions = foodConditionService.findByNameFoodCondition(nameFoodCondition);
            return new ResponseEntity<>(foodConditions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("searchByTypeFoodCondition/{typeFoodCondition}")
    @ApiOperation(value = "Buscar FoodCondition por typeFoodCondition", notes = "Métodos para encontrar un FoodCondition por su respectivo typeFoodCondition")
    @ApiResponses({
            @ApiResponse(code = 200, message = "FoodCondition encontrados o no existen FoodConditions de este tipo"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<FoodCondition>> findByTypeFoodCondition(@PathVariable("typeFoodCondition") TypeFoodCondition typeFoodCondition) {
        try {
            List<FoodCondition> foodConditions = foodConditionService.findByTypeFoodCondition(typeFoodCondition);
            return new ResponseEntity<List<FoodCondition>>(foodConditions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<FoodCondition>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
