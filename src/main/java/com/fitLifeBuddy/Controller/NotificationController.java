package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.Notification;
import com.fitLifeBuddy.Entity.Person;
import com.fitLifeBuddy.Service.INotificationService;
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
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/notifications")
@Api(tags = "Notification", value = "Service Web RESTFul de Notifications")
public class NotificationController {

    @Autowired
    private INotificationService notificationService;

    @Autowired
    private IPersonService personService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Notifications", notes = "Método para listar a todos los Notifications")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Notifications encontrados o lista vacía"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Notification>> findAll() {
        try {
            List<Notification> notifications = notificationService.getAll();
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Notification por Id", notes = "Método para encontrar un Notification por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Notification encontrado"),
            @ApiResponse(code = 404, message = "Notification no encontrado"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<Notification> findById(@PathVariable("id") Long id) {
        try {
            Optional<Notification> notification = notificationService.getById(id);
            if (!notification.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(notification.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/byPerson/{idPerson}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Notifications por Person", notes = "Método para encontrar Notifications por el ID de la persona")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Notifications encontrados"),
            @ApiResponse(code = 404, message = "Notifications no encontrados"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<List<Notification>> findByPersonId(@PathVariable("idPerson") Long idPerson) {
        try {
            List<Notification> notifications = notificationService.findByPersonId(idPerson);
            if (notifications.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/{idPerson}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registrar Notification", notes = "Método para registrar una nueva Notification")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Notification creada"),
            @ApiResponse(code = 404, message = "Notification no creada")
    })
    public ResponseEntity<Notification> insertNotification(@PathVariable("idPerson") Long idPerson, @Valid @RequestBody Notification notification) {
        try {
            Optional<Person> personOptional = personService.getById(idPerson);
            if (!personOptional.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Person person = personOptional.get();
            notification.setPerson(person);
            Notification newNotification = notificationService.save(notification);
            return ResponseEntity.status(HttpStatus.CREATED).body(newNotification);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualizar Notification", notes = "Método para actualizar los datos de una Notification")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Notification actualizados"),
            @ApiResponse(code = 404, message = "Notification no encontrada")
    })
    public ResponseEntity<Notification> updateNotification(
            @PathVariable("id") Long id, @Valid @RequestBody Notification notification) {
        try {
            Optional<Notification> notificationUp = notificationService.getById(id);
            if (!notificationUp.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            notification.setIdNotification(id);
            notificationService.save(notification);
            return new ResponseEntity<>(notification, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminar Notification", notes = "Método para eliminar los datos de una Notification")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Notification eliminados"),
            @ApiResponse(code = 404, message = "Notification no encontrada")
    })
    public ResponseEntity<Notification> deleteNotification(@PathVariable("id") Long id) {
        try {
            Optional<Notification> notificationDelete = notificationService.getById(id);
            if (!notificationDelete.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            notificationService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
