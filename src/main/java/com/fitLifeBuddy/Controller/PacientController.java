package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.*;
import com.fitLifeBuddy.Service.INutritionistService;
import com.fitLifeBuddy.Service.IPacientService;
import com.fitLifeBuddy.Service.IPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/pacients")
@Api(tags = "Pacient", value = "Service Web RESTFul de Pacients")
public class PacientController {

    private static final Logger logger = LoggerFactory.getLogger(PacientController.class);

    @Autowired
    private IPacientService pacientService;

    @Autowired
    private IPersonService personService;

    @Autowired
    private INutritionistService nutritionistService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Pacients", notes = "Método para listar a todos los Pacients")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pacients encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Pacient>> findAll() {
        try {
            List<Pacient> pacients = pacientService.getAll();
            return new ResponseEntity<>(pacients, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Pacient por Id", notes = "Método para encontrar un Pacient por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pacient encontrado"),
            @ApiResponse(code = 404, message = "Pacient no encontrado"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<Pacient> findById(@PathVariable("id") Long id) {
        try {
            Optional<Pacient> pacient = pacientService.getById(id);
            if (!pacient.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(pacient.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{idPerson}/{idNutritionist}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Pacients", notes = "Método que registra Pacients en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pacient creado"),
            @ApiResponse(code = 404, message = "Pacient no creado")
    })
    public ResponseEntity<Pacient> insertPacient(@PathVariable("idPerson") Long idPerson, @PathVariable("idNutritionist") Long idNutritionist, @Valid @RequestBody Pacient pacient ) {
        try {
            Optional<Person> person = personService.getById(idPerson);
            Optional<Nutritionist> nutritionist = nutritionistService.getById(idNutritionist);
            if (person.isPresent() && nutritionist.isPresent()) {
                pacient.setPerson(person.get());
                pacient.setNutritionist(nutritionist.get());
                Pacient pacientNew = pacientService.save(pacient);
                return ResponseEntity.status(HttpStatus.CREATED).body(pacientNew);
            } else
                return new ResponseEntity<Pacient>(HttpStatus.FAILED_DEPENDENCY);
        } catch (Exception e) {
            return new ResponseEntity<Pacient>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de Pacient", notes = "Metodo que actualiza los datos de Pacient")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Pacient actualizados"),
            @ApiResponse(code = 404, message = "Pacient no encontrado")
    })
    public ResponseEntity<Pacient> updatePacient(@PathVariable("id") Long id, @Valid @RequestBody Pacient pacient) {
        try {
            Optional<Pacient> pacientUp = pacientService.getById(id);
            if (!pacientUp.isPresent())
                return new ResponseEntity<Pacient>(HttpStatus.NOT_FOUND);
            pacient.setIdPacient(id);
            pacientService.save(pacient);
            return new ResponseEntity<Pacient>(pacient, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Pacient>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de Pacient", notes = "Metodo que elimina los datos de Pacient")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Pacient eliminados"),
            @ApiResponse(code = 404, message = "Pacient no encontrado")
    })
    public ResponseEntity<Pacient> deletePacient(@PathVariable("id") Long id) {
        try {
            Optional<Pacient> pacientDelete = pacientService.getById(id);
            if (!pacientDelete.isPresent())
                return new ResponseEntity<Pacient>(HttpStatus.NOT_FOUND);
            pacientService.delete(id);
            return new ResponseEntity<Pacient>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Pacient>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("searchPacientHistoryByIdPacient/{idPacient}")
    @ApiOperation(value = "Buscar PacientHistory por Pacient", notes = "Método para encontrar PacientHistory por su respectivo Pacient")
    @ApiResponses({
            @ApiResponse(code = 200, message = "PacientHistory encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<PacientHistory>> findPacientHistoryByIdPacient(@PathVariable("idPacient") Long idPacient) {
        try {
            List<PacientHistory> pacientHistories = pacientService.findPacientHistoryByIdPacient(idPacient);
            return new ResponseEntity<>(pacientHistories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("searchPlanByIdPacient/{idPacient}")
    @ApiOperation(value = "Buscar Plan por Pacient", notes = "Método para encontrar Plan por su respectivo Pacient")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Plan encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Plan>> findPlanByIdPacient(@PathVariable("idPacient") Long idPacient) {
        try {
            List<Plan> plans = pacientService.findPlanByIdPacient(idPacient);
            return new ResponseEntity<>(plans, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("searchByBirthDate/{birthDate}")
    @ApiOperation(value = "Buscar Pacient por birthDate", notes = "Método para encontrar un Pacient por su respectivo birthDate")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pacients encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Pacient>> find(@PathVariable("birthDate") Date birthDate) {
        try {
            List<Pacient> pacients = pacientService.find(birthDate);
            return new ResponseEntity<>(pacients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("searchHealthConditionByIdPacient/{idPacient}")
    @ApiOperation(value = "Buscar HealthCondition por idPacient", notes = "Método para encontrar HealthCondition por su respectivo idPacient")
    @ApiResponses({
            @ApiResponse(code = 200, message = "HealthConditions encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<HealthCondition>> findHealthConditionsByIdPacient(@PathVariable("idPacient") Long idPacient) {
        try {
            List<HealthCondition> healthConditions = pacientService.findHealthConditionsByIdPacient(idPacient);
            return new ResponseEntity<>(healthConditions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("searchFoodConditionByIdPacient/{idPacient}")
    @ApiOperation(value = "Buscar FoodCondition por idPacient", notes = "Método para encontrar FoodCondition por su respectivo idPacient")
    @ApiResponses({
            @ApiResponse(code = 200, message = "FoodCondition encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<FoodCondition>> findFoodConditionsByIdPacient(@PathVariable("idPacient") Long idPacient) {
        try {
            List<FoodCondition> foodConditions = pacientService.findFoodConditionsByIdPacient(idPacient);
            return new ResponseEntity<>(foodConditions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
