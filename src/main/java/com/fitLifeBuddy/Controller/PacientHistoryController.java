package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.HealthCondition;
import com.fitLifeBuddy.Entity.Pacient;
import com.fitLifeBuddy.Entity.PacientHistory;
import com.fitLifeBuddy.Service.IPacientHistoryService;
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
@RequestMapping("/api/pacientHistories")
@Api(tags = "PacientHistory", value = "Service Web RESTFul de PacientHistories")
public class PacientHistoryController {
    @Autowired
    private IPacientHistoryService pacientHistoryService;

    @Autowired
    private IPacientService pacientService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar PacientHistories", notes = "Método para listar a todos los PacientHistories")
    @ApiResponses({
            @ApiResponse(code = 200, message = "PacientHistories encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<PacientHistory>> findAll(){
        try {
            List<PacientHistory> pacientHistories = pacientHistoryService.getAll();
            return new ResponseEntity<List<PacientHistory>>(pacientHistories, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<List<PacientHistory>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar PacientHistory por Id", notes = "Método para encontrar un PacientHistory por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "PacientHistory encontrado"),
            @ApiResponse(code = 404, message = "PacientHistory no encontrado"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<PacientHistory> findById(@PathVariable("id") Long id) {
        try {
            Optional<PacientHistory> pacientHistory = pacientHistoryService.getById(id);
            if (!pacientHistory.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(pacientHistory.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de PacientHistory", notes = "Metodo que actualiza los datos de PacientHistory")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de PacientHistory actualizados"),
            @ApiResponse(code = 404, message = "PacientHistory no encontrado")
    })
    public ResponseEntity<PacientHistory> updatePacientHistory(
            @PathVariable("id") Long id, @Valid @RequestBody PacientHistory pacientHistory) {
        try {
            Optional<PacientHistory> pacientHistoryUp = pacientHistoryService.getById(id);
            if (!pacientHistoryUp.isPresent())
                return new ResponseEntity<PacientHistory>(HttpStatus.NOT_FOUND);
            pacientHistory.setIdPacientHistory(id);
            pacientHistoryService.save(pacientHistory);
            return new ResponseEntity<PacientHistory>(pacientHistory, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<PacientHistory>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping(value = "{idPacient}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de PacientHistory", notes = "Método que registra PacientHistories en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "PacientHistory creado"),
            @ApiResponse(code = 404, message = "PacientHistory no creado")
    })
    public ResponseEntity<PacientHistory> insertPacientHistory(@PathVariable("idPacient") Long idPacient ,@Valid @RequestBody PacientHistory pacientHistory) {
        try {
            Optional<Pacient> pacient = pacientService.getById(idPacient);
            if (pacient.isPresent()){
                pacientHistory.setPacient(pacient.get());
                PacientHistory pacientHistoryNew = pacientHistoryService.save(pacientHistory);
                return ResponseEntity.status(HttpStatus.CREATED).body(pacientHistoryNew);
            }else
                return new ResponseEntity<PacientHistory>(HttpStatus.FAILED_DEPENDENCY);

        } catch (Exception e) {
            return new ResponseEntity<PacientHistory>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de PacientHistory", notes = "Metodo que elimina los datos de PacientHistory")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de PacientHistory eliminados"),
            @ApiResponse(code = 404, message = "PacientHistory no encontrado")
    })
    public ResponseEntity<PacientHistory> deletePacientHistory(@PathVariable("id") Long id) {
        try {
            Optional<PacientHistory> pacientHistoryDelete = pacientHistoryService.getById(id);
            if (!pacientHistoryDelete.isPresent())
                return new ResponseEntity<PacientHistory>(HttpStatus.NOT_FOUND);
            pacientHistoryService.delete(id);
            return new ResponseEntity<PacientHistory>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<PacientHistory>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/latest/{pacientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar la última historia del paciente por ID", notes = "Método para encontrar la última historia del paciente por su ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Última historia del paciente encontrada o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<PacientHistory>> findLatestByPacientId(@PathVariable Long pacientId) {
        try {
            Optional<PacientHistory> pacientHistory = pacientHistoryService.findLatestByPacientId(pacientId);
            if (pacientHistory.isPresent()) {
                List<PacientHistory> result = new ArrayList<>();
                result.add(pacientHistory.get());
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
