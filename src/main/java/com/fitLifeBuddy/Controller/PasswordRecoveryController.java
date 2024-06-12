package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.Person;
import com.fitLifeBuddy.Service.IPersonService;
import com.fitLifeBuddy.Util.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/password")
@Api(tags = "Password Recovery", value = "Service Web RESTFul de Password Recovery")
public class PasswordRecoveryController {
    @Autowired
    private IPersonService personService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/recover")
    @ApiOperation(value = "Recuperar Contraseña", notes = "Método para recuperar la contraseña olvidada")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Correo de recuperación enviado"),
            @ApiResponse(code = 404, message = "Correo no encontrado"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    public ResponseEntity<String> recoverPassword(@RequestParam("email") String email) {
        try {
            Optional<Person> person = personService.findPersonByEmailAddress(email);
            if (person.isPresent()) {
                String name = person.get().getFullname();
                String password = person.get().getPassword();
                String message = "<p>Hola " + name + ",</p>" +
                        "<p>Recibimos una solicitud para recuperar tu contraseña. Aquí tienes tu contraseña:</p>" +
                        "<p><strong>Tu contraseña es: " + password + "</strong></p>" +
                        "<p>Si no solicitaste este correo, por favor ignóralo.</p>" +
                        "<p>Saludos,<br/>El equipo de FitLifeBuddy</p>";
                emailService.sendEmail(email, "Recuperación de Contraseña", message);
                return new ResponseEntity<>("Correo de recuperación enviado", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Correo no encontrado", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
