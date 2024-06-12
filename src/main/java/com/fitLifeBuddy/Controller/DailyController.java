package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.*;
import com.fitLifeBuddy.Entity.DTO.DailyDTO;
import com.fitLifeBuddy.Entity.Enum.Frecuently;
import com.fitLifeBuddy.Entity.Enum.Status;
import com.fitLifeBuddy.Service.IDailyService;
import com.fitLifeBuddy.Service.IPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/dailies")
@Api(tags = "Daily" ,value = "Service Web RESTFul de Dailies")
public class DailyController {

    private static final Logger logger = LoggerFactory.getLogger(DailyController.class);

    @Autowired
    private IDailyService dailyService;

    @Autowired
    private IPlanService planService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Dailies", notes = "Método para listar a todos los Dailies")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Dailies encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Daily>> findAll(){
        try {
            List<Daily> dailies = dailyService.getAll();
            return new ResponseEntity<List<Daily>>(dailies, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<List<Daily>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Daily por Id", notes = "Método para encontrar un Daily por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Daily encontrado"),
            @ApiResponse(code = 404, message = "Daily no encontrado"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<Daily> findById(@PathVariable("id") Long id) {
        try {
            Optional<Daily> daily = dailyService.getById(id);
            if (!daily.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(daily.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de Daily", notes = "Metodo que actualiza los datos de Daily")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Daily actualizados"),
            @ApiResponse(code = 404, message = "Daily no encontrado")
    })
    public ResponseEntity<Daily> updateDaily(
            @PathVariable("id") Long id, @Valid @RequestBody Daily daily) {
        try {
            Optional<Daily> dailyUp = dailyService.getById(id);
            if (!dailyUp.isPresent())
                return new ResponseEntity<Daily>(HttpStatus.NOT_FOUND);
            daily.setIdDaily(id);
            dailyService.save(daily);
            return new ResponseEntity<Daily>(daily, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Daily>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping(value = "{idPlan}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Daily", notes = "Método que registra Dailies en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Daily creado"),
            @ApiResponse(code = 404, message = "Daily no creado")
    })
    public ResponseEntity<Daily> insertDaily(@PathVariable("idPlan") Long idPlan ,@Valid @RequestBody DailyDTO dailyDTO) {
        try {
            Optional<Plan> plan = planService.getById(idPlan);
            if (plan.isPresent()){
                Daily daily = new Daily();
                daily.setDate((dailyDTO.getDate()));
                daily.setDateNumber(dailyDTO.getDateNumber());
                daily.setPlan(plan.get());
                Daily dailyNew = dailyService.save(daily);
                return ResponseEntity.status(HttpStatus.CREATED).body(dailyNew);
            }else
                return new ResponseEntity<Daily>(HttpStatus.FAILED_DEPENDENCY);

        } catch (Exception e) {
            return new ResponseEntity<Daily>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de Daily", notes = "Metodo que elimina los datos de Daily")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Daily eliminados"),
            @ApiResponse(code = 404, message = "Daily no encontrado")
    })
    public ResponseEntity<Daily> deleteDaily(@PathVariable("id") Long id) {
        try {
            Optional<Daily> dailyDelete = dailyService.getById(id);
            if (!dailyDelete.isPresent())
                return new ResponseEntity<Daily>(HttpStatus.NOT_FOUND);
            dailyService.delete(id);
            return new ResponseEntity<Daily>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Daily>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchByDateAndPatient/{date}/{idPacient}")
    @ApiOperation(value = "Buscar Daily por fecha y ID de paciente", notes = "Métodos para encontrar un Daily por fecha y el ID del paciente asociado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Dailies encontrados o no existen Dailies para esta fecha e ID de paciente"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Daily>> findByDateAndPatientId(
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
            @PathVariable("idPacient") Long idPacient) {
        try {
            List<Daily> dailies = dailyService.findByDateAndPatientId(date, idPacient);
            return new ResponseEntity<>(dailies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("searchByDate/{date}")
    @ApiOperation(value = "Buscar Daily por date", notes = "Métodos para encontrar un Daily por su respectivo date")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Dailies encontrados o no existen Dailies para esta fecha"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Daily>> findByDate(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        try {
            List<Daily> dailies = dailyService.findByDate(date);
            return new ResponseEntity<>(dailies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("searchMealsByIdDaily/{idDaily}")
    @ApiOperation(value = "Buscar Meals por Daily", notes = "Métodos para encontrar un Meal por su respectivo Daily")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Meals encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Meal>> findMealsByIdDaily(@PathVariable("idDaily") Long idDaily) {
        try {
            List<Meal> meals = dailyService.findMealsByIdDaily(idDaily);
            return new ResponseEntity<List<Meal>>(meals, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<Meal>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("searchRoutinesByIdDaily/{idDaily}")
    @ApiOperation(value = "Buscar Routines por Daily", notes = "Métodos para encontrar un Routine por su respectivo Daily")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Routines encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Routine>> findRoutinesByIdDaily(@PathVariable("idDaily") Long idDaily) {
        try {
            List<Routine> routines = dailyService.findRoutinesByIdDaily(idDaily);
            return new ResponseEntity<List<Routine>>(routines, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<Routine>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/completeToday/{idPacient}")
    @ApiOperation(value = "Completar todos los Dailies de Hoy para un paciente", notes = "Marca todos los Dailies de hoy para un paciente especificado como completados si su estado es UNFILLED")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Dailies completados"),
            @ApiResponse(code = 404, message = "No hay Dailies UNFILLED para hoy para este paciente"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<?> completeTodayDaily(@PathVariable("idPacient") Long idPacient) {
        try {
            Date today = new Date();
            List<Daily> dailiesToday = dailyService.findDailyByDateAndStatusUnfilled(today, idPacient);

            if (dailiesToday.isEmpty()) {
                return new ResponseEntity<>("No hay Dailies UNFILLED para hoy para el paciente especificado.", HttpStatus.NOT_FOUND);
            }

            boolean allCompleted = true;
            for (Daily daily : dailiesToday) {
                daily.setStatus(Status.COMPLETED);
                dailyService.save(daily);

                // Comprueba si este es el último Daily basado en la frecuencia del plan
                if (daily.getDateNumber() == getNumberOfDaysBasedOnFrequency(daily.getPlan().getFrecuently())) {
                    allCompleted = false;
                }
            }

            // Si todos los Dailies están completos, cambia el estado del plan a COMPLETED
            if (allCompleted) {
                Plan plan = dailiesToday.get(0).getPlan();
                plan.setStatus(Status.COMPLETED);
                planService.save(plan);
            }

            return new ResponseEntity<>(dailiesToday, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error al completar los Dailies: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private int getNumberOfDaysBasedOnFrequency(Frecuently frequency) {
        switch (frequency) {
            case MONTLY: return 30;
            case BIMONTLY: return 60;
            case QUARTERLY: return 90;
            default: return 0;
        }
    }

    @GetMapping("/confirm/{idDaily}")
    @ApiOperation(value = "Confirmar Daily", notes = "Método para confirmar un Daily y cambiar su estado a COMPLETED")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Daily confirmado"),
            @ApiResponse(code = 404, message = "Daily no encontrado"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<String> confirmDaily(@PathVariable("idDaily") Long idDaily) {
        try {
            Optional<Daily> dailyOptional = dailyService.getById(idDaily);
            if (!dailyOptional.isPresent()) {
                return new ResponseEntity<>("Daily no encontrado", HttpStatus.NOT_FOUND);
            }

            Daily daily = dailyOptional.get();
            daily.setStatus(Status.COMPLETED);
            dailyService.save(daily);

            return new ResponseEntity<>("Daily confirmado", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
