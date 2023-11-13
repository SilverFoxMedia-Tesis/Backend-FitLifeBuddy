package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.Daily;
import com.fitLifeBuddy.Entity.Feedback;
import com.fitLifeBuddy.Entity.Question;
import com.fitLifeBuddy.Service.IDailyService;
import com.fitLifeBuddy.Service.IFeedbackService;
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
@RequestMapping("/api/feedbacks")
@Api(tags = "Feedback", value = "Service Web RESTFul de Feedbacks")
public class FeedbackController {

    @Autowired
    private IFeedbackService feedbackService;

    @Autowired
    private IDailyService dailyService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Feedbacks", notes = "Metodo para listar a todos los Feedbacks")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Feedbacks encontrados"),
            @ApiResponse(code = 404, message = "Feedbacks no encontrados")
    })
    public ResponseEntity<List<Feedback>> findAll(){
        try {
            List<Feedback> feedbacks = feedbackService.getAll();
            if (feedbacks.size() > 0)
                return new ResponseEntity<List<Feedback>>(feedbacks, HttpStatus.OK);
            else
                return new ResponseEntity<List<Feedback>>(HttpStatus.NOT_FOUND);
        } catch (Exception ex){
            return new ResponseEntity<List<Feedback>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Feedback por Id", notes = "Métodos para encontrar un Feedback por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Feedback encontrado"),
            @ApiResponse(code = 404, message = "Feedback no encontrado")
    })
    public ResponseEntity<Feedback> findById(@PathVariable("id") Long id) {
        try {
            Optional<Feedback> feedback = feedbackService.getById(id);
            if (!feedback.isPresent())
                return new ResponseEntity<Feedback>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Feedback>(feedback.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Feedback>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de Feedback", notes = "Metodo que actualiza los datos de Feedback")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Feedback actualizados"),
            @ApiResponse(code = 404, message = "Feedback no encontrado")
    })
    public ResponseEntity<Feedback> updateFeedback(
            @PathVariable("id") Long id, @Valid @RequestBody Feedback feedback) {
        try {
            Optional<Feedback> feedbackUp = feedbackService.getById(id);
            if (!feedbackUp.isPresent())
                return new ResponseEntity<Feedback>(HttpStatus.NOT_FOUND);
            feedback.setIdFeedback(id);
            feedbackService.save(feedback);
            return new ResponseEntity<Feedback>(feedback, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Feedback>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping(value = "{idDaily}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Feedback", notes = "Método que registra Feedback en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Feedback creado"),
            @ApiResponse(code = 404, message = "Feedback no creado")
    })
    public ResponseEntity<Feedback> insertFeedback(@PathVariable("idDaily") Long idDaily ,@Valid @RequestBody Feedback feedback) {
        try {
            Optional<Daily> daily = dailyService.getById(idDaily);
            if (daily.isPresent()){
                feedback.setDaily(daily.get());
                Feedback feedbackNew = feedbackService.save(feedback);
                return ResponseEntity.status(HttpStatus.CREATED).body(feedbackNew);
            }else
                return new ResponseEntity<Feedback>(HttpStatus.FAILED_DEPENDENCY);

        } catch (Exception e) {
            return new ResponseEntity<Feedback>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de Feedback", notes = "Metodo que elimina los datos de Feedback")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Feedback eliminados"),
            @ApiResponse(code = 404, message = "Feedback no encontrado")
    })
    public ResponseEntity<Feedback> deleteFeedback(@PathVariable("id") Long id) {
        try {
            Optional<Feedback> feedbackDelete = feedbackService.getById(id);
            if (!feedbackDelete.isPresent())
                return new ResponseEntity<Feedback>(HttpStatus.NOT_FOUND);
            feedbackService.delete(id);
            return new ResponseEntity<Feedback>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Feedback>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("searchQuestionsByIdFeedback/{idFeedback}")
    @ApiOperation(value = "Buscar Question por Feedback", notes = "Métodos para encontrar Questions por su respectivo Feedback")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Question encontrados"),
            @ApiResponse(code = 404, message = "Question no encontrados")
    })
    public ResponseEntity<List<Question>> findQuestionsByIdFeedback(@PathVariable("idFeedback") Long idFeedback) {
        try {
            List<Question> questions = feedbackService.findQuestionsByIdFeedback(idFeedback);
            if (questions.size() > 0)
                return new ResponseEntity<List<Question>>(questions, HttpStatus.OK);
            else
                return new ResponseEntity<List<Question>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<Question>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
