package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.Feedback;
import com.fitLifeBuddy.Entity.Option;
import com.fitLifeBuddy.Entity.Question;
import com.fitLifeBuddy.Service.IFeedbackService;
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
@RequestMapping("/api/questions")
@Api(tags = "Question", value = "Service Web RESTFul de Questions")
public class QuestionController {
    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IFeedbackService feedbackService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Questions", notes = "Método para listar a todos los Questions")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Questions encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Question>> findAll() {
        try {
            List<Question> questions = questionService.getAll();
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Question por Id", notes = "Método para encontrar un Question por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Question encontrado o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Question>> findById(@PathVariable("id") Long id) {
        try {
            Optional<Question> question = questionService.getById(id);
            if (!question.isPresent()) {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            }
            List<Question> result = new ArrayList<>();
            result.add(question.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de Question", notes = "Metodo que actualiza los datos de Question")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Question actualizados"),
            @ApiResponse(code = 404, message = "Question no encontrado")
    })
    public ResponseEntity<Question> updateQuestion(
            @PathVariable("id") Long id, @Valid @RequestBody Question question) {
        try {
            Optional<Question> questionUp = questionService.getById(id);
            if (!questionUp.isPresent())
                return new ResponseEntity<Question>(HttpStatus.NOT_FOUND);
            question.setIdQuestion(id);
            questionService.save(question);
            return new ResponseEntity<Question>(question, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Question>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping(value = "{idFeedback}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Question", notes = "Método que registra Question en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Question creado"),
            @ApiResponse(code = 404, message = "Question no creado")
    })
    public ResponseEntity<Question> insertQuestion(@PathVariable("idFeedback") Long idFeedback ,@Valid @RequestBody Question question) {
        try {
            Optional<Feedback> feedback = feedbackService.getById(idFeedback);
            if (feedback.isPresent()){
                question.setFeedback(feedback.get());
                Question questionNew = questionService.save(question);
                return ResponseEntity.status(HttpStatus.CREATED).body(questionNew);
            }else
                return new ResponseEntity<Question>(HttpStatus.FAILED_DEPENDENCY);

        } catch (Exception e) {
            return new ResponseEntity<Question>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de Question", notes = "Metodo que elimina los datos de Question")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Question eliminados"),
            @ApiResponse(code = 404, message = "Question no encontrado")
    })
    public ResponseEntity<Question> deleteQuestion(@PathVariable("id") Long id) {
        try {
            Optional<Question> questionDelete = questionService.getById(id);
            if (!questionDelete.isPresent())
                return new ResponseEntity<Question>(HttpStatus.NOT_FOUND);
            questionService.delete(id);
            return new ResponseEntity<Question>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Question>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("searchByNameQuestion/{nameQuestion}")
    @ApiOperation(value = "Buscar Question por nameQuestion", notes = "Método para encontrar un Question por su respectivo nameQuestion")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Question encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Question>> findByNameQuestion(@PathVariable("nameQuestion") String nameQuestion) {
        try {
            List<Question> questions = questionService.findByNameQuestion(nameQuestion);
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("searchOptionsByIdQuestion/{idQuestion}")
    @ApiOperation(value = "Buscar Options por Question", notes = "Método para encontrar Options por su respectivo Question")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Options encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Option>> findOptionsByIdQuestion(@PathVariable("idQuestion") Long idQuestion) {
        try {
            List<Option> options = questionService.findOptionsByIdQuestion(idQuestion);
            return new ResponseEntity<>(options, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
