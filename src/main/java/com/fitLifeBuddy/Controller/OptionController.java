package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.Option;
import com.fitLifeBuddy.Entity.Question;
import com.fitLifeBuddy.Service.IOptionService;
import com.fitLifeBuddy.Service.IQuestionService;
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
@RequestMapping("/api/options")
@Api(tags = "Option", value = "Service Web RESTFul de Options")
public class OptionController {

    @Autowired
    private IOptionService optionService;

    @Autowired
    private IQuestionService questionService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Options", notes = "Método para listar a todos los Options")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Options encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Option>> findAll() {
        try {
            List<Option> options = optionService.getAll();
            return new ResponseEntity<>(options, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Option por Id", notes = "Método para encontrar un Option por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Option encontrado o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Option>> findById(@PathVariable("id") Long id) {
        try {
            Optional<Option> option = optionService.getById(id);
            if (!option.isPresent()) {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            }
            List<Option> result = new ArrayList<>();
            result.add(option.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de Option", notes = "Metodo que actualiza los datos de Option")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Option actualizados"),
            @ApiResponse(code = 404, message = "Option no encontrado")
    })
    public ResponseEntity<Option> updateOption(
            @PathVariable("id") Long id, @Valid @RequestBody Option option) {
        try {
            Optional<Option> optionUp = optionService.getById(id);
            if (!optionUp.isPresent())
                return new ResponseEntity<Option>(HttpStatus.NOT_FOUND);
            option.setIdOption(id);
            optionService.save(option);
            return new ResponseEntity<Option>(option, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Option>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping(value = "{idQuestion}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Option", notes = "Método que registra Option en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Option creado"),
            @ApiResponse(code = 404, message = "Option no creado")
    })
    public ResponseEntity<Option> insertOption(@PathVariable("idQuestion") Long idQuestion ,@Valid @RequestBody Option option) {
        try {
            Optional<Question> question = questionService.getById(idQuestion);
            if (question.isPresent()){
                option.setQuestion(question.get());
                Option optionNew = optionService.save(option);
                return ResponseEntity.status(HttpStatus.CREATED).body(optionNew);
            }else
                return new ResponseEntity<Option>(HttpStatus.FAILED_DEPENDENCY);

        } catch (Exception e) {
            return new ResponseEntity<Option>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de Option", notes = "Metodo que elimina los datos de Option")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Option eliminados"),
            @ApiResponse(code = 404, message = "Option no encontrado")
    })
    public ResponseEntity<Option> deleteOption(@PathVariable("id") Long id) {
        try {
            Optional<Option> optionDelete = optionService.getById(id);
            if (!optionDelete.isPresent())
                return new ResponseEntity<Option>(HttpStatus.NOT_FOUND);
            optionService.delete(id);
            return new ResponseEntity<Option>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Option>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("searchByNameOption/{nameOption}")
    @ApiOperation(value = "Buscar Option por nameOption", notes = "Método para encontrar un Option por su respectivo nameOption")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Options encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Option>> findByNameOption(@PathVariable("nameOption") String nameOption) {
        try {
            List<Option> options = optionService.findByNameOption(nameOption);
            return new ResponseEntity<>(options, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
