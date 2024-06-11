package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.Person;
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
@RequestMapping("/api/persons")
@Api(tags = "Person", value = "Service Web RESTFul de Persons")
public class PersonController {
    @Autowired
    private IPersonService personService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Persons", notes = "Método para listar a todos los Persons")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Persons encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Person>> findAll() {
        try {
            List<Person> people = personService.getAll();
            return new ResponseEntity<>(people, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Person por Id", notes = "Método para encontrar un Person por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Person encontrado o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Person>> findById(@PathVariable("id") Long id) {
        try {
            Optional<Person> person = personService.getById(id);
            if (!person.isPresent()) {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
            }
            List<Person> result = new ArrayList<>();
            result.add(person.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("searchByEmailAddress/{emailAddress}")
    @ApiOperation(value = "Buscar Person por emailAddress", notes = "Método para encontrar un Person por su respectivo emailAddress")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Person encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Person>> findByEmailAddress(@PathVariable("emailAddress") String emailAddress) {
        try {
            List<Person> people = personService.findByEmailAddress(emailAddress);
            return new ResponseEntity<>(people, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("searchByFullName/{fullname}")
    @ApiOperation(value = "Buscar Person por fullname", notes = "Método para encontrar un Person por su respectivo fullname")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Person encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Person>> findByFullname(@PathVariable("fullname") String fullname) {
        try {
            List<Person> people = personService.findByFullname(fullname);
            return new ResponseEntity<>(people, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("searchByLastName/{lastname}")
    @ApiOperation(value = "Buscar Person por lastname", notes = "Método para encontrar un Person por su respectivo lastname")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Person encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Person>> findByLastname(@PathVariable("lastname") String lastname) {
        try {
            List<Person> people = personService.findByLastname(lastname);
            return new ResponseEntity<>(people, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de Person", notes = "Metodo que actualiza los datos de Person")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Person actualizados"),
            @ApiResponse(code = 404, message = "Person no encontrado")
    })
    public ResponseEntity<Person> updatePerson(
            @PathVariable("id") Long id, @Valid @RequestBody Person person) {
        try {
            Optional<Person> personUp = personService.getById(id);
            if (!personUp.isPresent())
                return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
            person.setIdPerson(id);
            personService.save(person);
            return new ResponseEntity<Person>(person, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Person>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Usuarios", notes = "Método que registra Usuarios en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuario creado"),
            @ApiResponse(code = 404, message = "Usuario no creado")
    })
    public ResponseEntity<Person> insertPerson(@Valid @RequestBody Person person) {
        try {
            Person personNew = personService.save(person);
            return ResponseEntity.status(HttpStatus.CREATED).body(personNew);
        } catch (Exception e) {
            return new ResponseEntity<Person>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de Usuario", notes = "Metodo que elimina los datos de Usuario")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Usuario eliminados"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    public ResponseEntity<Person> deletePerson(@PathVariable("id") Long id) {
        try {
            Optional<Person> personDelete = personService.getById(id);
            if (!personDelete.isPresent())
                return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
            personService.delete(id);
            return new ResponseEntity<Person>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Person>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
