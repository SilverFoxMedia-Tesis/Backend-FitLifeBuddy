package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.Nutritionist;
import com.fitLifeBuddy.Entity.Pacient;
import com.fitLifeBuddy.Service.INutritionistService;
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
@RequestMapping("/api/nutritionists")
@Api(tags = "Nutritionist", value = "Service Web RESTFul de Nutritionists")
public class NutritionistController {

    @Autowired
    private INutritionistService nutritionistService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Nutritionists",notes = "Métodos para listar a todos los nutritionists")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Nutritionists encontrados"),
            @ApiResponse(code = 404, message = "Nutritionists no encontrados")
    })
    public ResponseEntity<List<Nutritionist>> findAll(){
        try {
            List<Nutritionist> nutritionists = nutritionistService.getAll();
            if (nutritionists.size() > 0)
                return new ResponseEntity<List<Nutritionist>>(nutritionists, HttpStatus.OK);
            else
                return new ResponseEntity<List<Nutritionist>>(HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            return new ResponseEntity<List<Nutritionist>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Nutritionist por Id", notes = "Métodos para encontrar un Nutritionist por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Nutritionist encontrado"),
            @ApiResponse(code = 404, message = "Nutritionist no encontrado")
    })
    public ResponseEntity<Nutritionist> findById(@PathVariable("id") Long id) {
        try {
            Optional<Nutritionist> nutritionist = nutritionistService.getById(id);
            if (!nutritionist.isPresent())
                return new ResponseEntity<Nutritionist>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Nutritionist>(nutritionist.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Nutritionist>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("searchPacientsByIdNutritionist/{idNutritionist}")
    @ApiOperation(value = "Buscar Pacients por Nutritionist", notes = "Métodos para encontrar Pacients por su respectivo Nutricionist")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pacients encontrados"),
            @ApiResponse(code = 404, message = "Pacients no encontrados")
    })
    public ResponseEntity<List<Pacient>> findPacientsByIdNutritionist(@PathVariable("idNutritionist") Long idNutritionist) {
        try {
            List<Pacient> pacients = nutritionistService.findPacientsByIdNutritionist(idNutritionist);
            if (pacients.size() > 0)
                return new ResponseEntity<List<Pacient>>(pacients, HttpStatus.OK);
            else
                return new ResponseEntity<List<Pacient>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<Pacient>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Nutritionist", notes = "Método que registra Nutritionists en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Nutritionist creado"),
            @ApiResponse(code = 404, message = "Nutritionist no creado")
    })
    public ResponseEntity<Nutritionist> insertNutritionist(@Valid @RequestBody Nutritionist nutritionist) {
        try {
            Nutritionist nutritionistNew = nutritionistService.save(nutritionist);
            return ResponseEntity.status(HttpStatus.CREATED).body(nutritionistNew);
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
