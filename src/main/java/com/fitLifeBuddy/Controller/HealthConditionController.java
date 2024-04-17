package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.Enum.TypeFoodCondition;
import com.fitLifeBuddy.Entity.Enum.TypeHealthCondition;
import com.fitLifeBuddy.Entity.FoodCondition;
import com.fitLifeBuddy.Entity.HealthCondition;
import com.fitLifeBuddy.Entity.Pacient;
import com.fitLifeBuddy.Service.IHealthConditionService;
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
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/healthConditions")
@Api(tags = "HealthCondition", value = "Service Web RESTFul de HealthConditions")
public class HealthConditionController {
    @Autowired
    IHealthConditionService healthConditionService;

    @Autowired
    IPacientService pacientService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar HealthConditions", notes = "Método para listar a todos los HealthConditions")
    @ApiResponses({
            @ApiResponse(code = 200, message = "HealthConditions encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<HealthCondition>> findAll(){
        try {
            List<HealthCondition> healthConditions = healthConditionService.getAll();
            return new ResponseEntity<List<HealthCondition>>(healthConditions, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<List<HealthCondition>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar HealthCondition por Id", notes = "Métodos para encontrar un HealthCondition por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "HealthCondition encontrado"),
            @ApiResponse(code = 404, message = "HealthCondition no encontrado"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<HealthCondition> findById(@PathVariable("id") Long id) {
        try {
            Optional<HealthCondition> healthCondition = healthConditionService.getById(id);
            if (!healthCondition.isPresent())
                return new ResponseEntity<HealthCondition>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<HealthCondition>(healthCondition.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<HealthCondition>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de HealthCondition", notes = "Metodo que actualiza los datos de HealthCondition")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de HealthCondition actualizados"),
            @ApiResponse(code = 404, message = "HealthCondition no encontrado")
    })
    public ResponseEntity<HealthCondition> updateFoodCondition(
            @PathVariable("id") Long id, @Valid @RequestBody HealthCondition healthCondition) {
        try {
            Optional<HealthCondition> healthConditionUp = healthConditionService.getById(id);
            if (!healthConditionUp.isPresent())
                return new ResponseEntity<HealthCondition>(HttpStatus.NOT_FOUND);
            healthCondition.setIdHealthCondition(id);
            healthConditionService.save(healthCondition);
            return new ResponseEntity<HealthCondition>(healthCondition, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<HealthCondition>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping(value = "{idPacient}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de HealthCondition", notes = "Método que registra HealthConditions en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "HealthCondition creado"),
            @ApiResponse(code = 404, message = "HealthCondition no creado")
    })
    public ResponseEntity<HealthCondition> insertHealthCondition(@PathVariable("idPacient") Long idPacient ,@Valid @RequestBody HealthCondition healthCondition) {
        try {
            Optional<Pacient> pacient = pacientService.getById(idPacient);
            if (pacient.isPresent()){
                healthCondition.setPacient(pacient.get());
                HealthCondition healthConditionNew = healthConditionService.save(healthCondition);
                return ResponseEntity.status(HttpStatus.CREATED).body(healthConditionNew);
            }else
                return new ResponseEntity<HealthCondition>(HttpStatus.FAILED_DEPENDENCY);

        } catch (Exception e) {
            return new ResponseEntity<HealthCondition>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de HealthCondition", notes = "Metodo que elimina los datos de HealthCondition")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de HealthCondition eliminados"),
            @ApiResponse(code = 404, message = "HealthCondition no encontrado")
    })
    public ResponseEntity<HealthCondition> deleteHealthCondition(@PathVariable("id") Long id) {
        try {
            Optional<HealthCondition> healthConditionDelete = healthConditionService.getById(id);
            if (!healthConditionDelete.isPresent())
                return new ResponseEntity<HealthCondition>(HttpStatus.NOT_FOUND);
            healthConditionService.delete(id);
            return new ResponseEntity<HealthCondition>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<HealthCondition>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("searchByNameHealthCondition/{nameHealthCondition}")
    @ApiOperation(value = "Buscar HealthCondition por nameHealthCondition", notes = "Métodos para encontrar un HealthCondition por su respectivo nameHealthCondition")
    @ApiResponses({
            @ApiResponse(code = 200, message = "HealthCondition encontrados o no existen HealthConditions con ese nombre"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<HealthCondition>> findByNameHealthCondition(@PathVariable("nameHealthCondition") String nameHealthCondition) {
        try {
            List<HealthCondition> healthConditions = healthConditionService.findByNameHealthCondition(nameHealthCondition);
            return new ResponseEntity<List<HealthCondition>>(healthConditions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<HealthCondition>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("searchByTypeHealthCondition/{typeHealthCondition}")
    @ApiOperation(value = "Buscar HealthCondition por typeHealthCondition", notes = "Métodos para encontrar un HealthCondition por su respectivo typeHealthCondition")
    @ApiResponses({
            @ApiResponse(code = 200, message = "HealthConditions encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<HealthCondition>> findByTypeHealthCondition(@PathVariable("typeHealthCondition") TypeHealthCondition typeHealthCondition) {
        try {
            List<HealthCondition> healthConditions = healthConditionService.findByTypeHealthCondition(typeHealthCondition);
            return new ResponseEntity<List<HealthCondition>>(healthConditions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<HealthCondition>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
