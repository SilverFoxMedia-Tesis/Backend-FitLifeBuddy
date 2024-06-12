package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.Nutritionist;
import com.fitLifeBuddy.Entity.Pacient;
import com.fitLifeBuddy.Entity.Person;
import com.fitLifeBuddy.Service.INutritionistService;
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

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/nutritionists")
@Api(tags = "Nutritionist", value = "Service Web RESTFul de Nutritionists")
public class NutritionistController {

    @Autowired
    private INutritionistService nutritionistService;

    @Autowired
    private IPersonService personService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Nutritionists", notes = "Método para listar a todos los nutritionists")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Nutritionists encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Nutritionist>> findAll() {
        try {
            List<Nutritionist> nutritionists = nutritionistService.getAll();
            return new ResponseEntity<>(nutritionists, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Nutritionist por Id", notes = "Método para encontrar un Nutritionist por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Nutritionist encontrado"),
            @ApiResponse(code = 404, message = "Nutritionist no encontrado"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<Nutritionist> findById(@PathVariable("id") Long id) {
        try {
            Optional<Nutritionist> nutritionist = nutritionistService.getById(id);
            if (!nutritionist.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(nutritionist.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("searchPacientsByIdNutritionist/{idNutritionist}")
    @ApiOperation(value = "Buscar Pacients por Nutritionist", notes = "Método para encontrar Pacients por su respectivo Nutricionist")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pacients encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Pacient>> findPacientsByIdNutritionist(@PathVariable("idNutritionist") Long idNutritionist) {
        try {
            List<Pacient> pacients = nutritionistService.findPacientsByIdNutritionist(idNutritionist);
            return new ResponseEntity<>(pacients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "{idPerson}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Nutritionist", notes = "Método que registra Nutritionists en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Nutritionist creado"),
            @ApiResponse(code = 404, message = "Nutritionist no creado")
    })
    public ResponseEntity<Nutritionist> insertNutritionist(@PathVariable("idPerson") Long idPerson,@Valid @RequestBody Nutritionist nutritionist) {
        try {
            Optional<Person> person = personService.getById(idPerson);
            if (person.isPresent()){
                nutritionist.setPerson(person.get());
                Nutritionist nutritionistNew = nutritionistService.save(nutritionist);
                return ResponseEntity.status(HttpStatus.CREATED).body(nutritionistNew);
            }else
                return new ResponseEntity<Nutritionist>(HttpStatus.FAILED_DEPENDENCY);

        } catch (Exception e) {
            return new ResponseEntity<Nutritionist>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de Nutritionist", notes = "Metodo que actualiza los datos de Nutritionist")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Nutritionist actualizados"),
            @ApiResponse(code = 404, message = "Nutritionist no encontrado")
    })
    public ResponseEntity<Nutritionist> updateNutritionist(
            @PathVariable("id") Long id, @Valid @RequestBody Nutritionist nutritionist) {
        try {
            Optional<Nutritionist> nutritionistUp = nutritionistService.getById(id);
            if (!nutritionistUp.isPresent())
                return new ResponseEntity<Nutritionist>(HttpStatus.NOT_FOUND);
            nutritionist.setIdNutritionist(id);
            nutritionistService.save(nutritionist);
            return new ResponseEntity<Nutritionist>(nutritionist, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Nutritionist>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de Nutritionist", notes = "Metodo que elimina los datos de Nutritionist")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Nutritionist eliminados"),
            @ApiResponse(code = 404, message = "Nutritionist no encontrado")
    })
    public ResponseEntity<Nutritionist> deleteNutritionist(@PathVariable("id") Long id) {
        try {
            Optional<Nutritionist> nutritionistDelete = nutritionistService.getById(id);
            if (!nutritionistDelete.isPresent())
                return new ResponseEntity<Nutritionist>(HttpStatus.NOT_FOUND);
            nutritionistService.delete(id);
            return new ResponseEntity<Nutritionist>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Nutritionist>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
