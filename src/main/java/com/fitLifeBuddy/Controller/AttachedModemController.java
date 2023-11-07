package com.fitLifeBuddy.Controller;


import com.fitLifeBuddy.Entity.*;
import com.fitLifeBuddy.Service.IAttachedModemService;
import com.fitLifeBuddy.Service.IClientService;
import com.fitLifeBuddy.Service.IModemService;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/attachedModems")
@Api(tags = "AttachedModem",value = "Service Web RESTFul de AttachedModem")
public class AttachedModemController {

    @Autowired
    private IAttachedModemService attachedModemService;

    @Autowired
    private IClientService clientService;

    @Autowired
    private IModemService modemService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar AttachedModems",notes = "Métodos para listar a todos los attachedModems")
    @ApiResponses({
            @ApiResponse(code = 201, message = "AttachedModems encontrados"),
            @ApiResponse(code = 404, message = "AttachedModems no encontrados")
    })
    public ResponseEntity<List<AttachedModem>> findAll(){
        try {
            List<AttachedModem> attachedModems = attachedModemService.getAll();
            //if (attachedModems.size() > 0)
            return new ResponseEntity<List<AttachedModem>>(attachedModems, HttpStatus.OK);
            //else
            // return new ResponseEntity<List<AttachedModem>>(HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            return new ResponseEntity<List<AttachedModem>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar AttachedModem por Id", notes = "Métodos para encontrar un AttachedModem por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "AttachedModem encontrado"),
            @ApiResponse(code = 404, message = "AttachedModem no encontrado")
    })
    public ResponseEntity<AttachedModem> findById(@PathVariable("id") Long id) {
        try {
            Optional<AttachedModem> attachedModem = attachedModemService.getById(id);
            if (!attachedModem.isPresent())
                return new ResponseEntity<AttachedModem>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<AttachedModem>(attachedModem.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<AttachedModem>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "searchClientCode/{clientCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar AttachedModem por ClientCode", notes = "Métodos para encontrar un AttachedModem por su respectivo clientCode")
    @ApiResponses({
            @ApiResponse(code = 201, message = "AttachedModem encontrado"),
            @ApiResponse(code = 404, message = "AttachedModem no encontrado")
    })
    public ResponseEntity<AttachedModem> findByClientCode(@PathVariable("clientCode") Long clientCode) {
        try {
            AttachedModem attachedModem = attachedModemService.findByClientCode(clientCode);
            if (attachedModem == null)
                return new ResponseEntity<AttachedModem>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<AttachedModem>(attachedModem, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<AttachedModem>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @GetMapping("searchByPlan/{plan}")
    @ApiOperation(value = "Buscar AttachedModem por plan", notes = "Métodos para encontrar un AttachedModem por su respectivo plan")
    @ApiResponses({
            @ApiResponse(code = 201, message = "AttachedModem encontrados"),
            @ApiResponse(code = 404, message = "AttachedModem no encontrados")
    })
    public ResponseEntity<List<AttachedModem>> findByPlan(@PathVariable("plan") String plan) {
        try {
            List<AttachedModem> attachedModems = attachedModemService.findByPlan(plan);
            if (attachedModems.size() > 0)
                return new ResponseEntity<List<AttachedModem>>(attachedModems, HttpStatus.OK);
            else
                return new ResponseEntity<List<AttachedModem>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<AttachedModem>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping(value = "/searchByDate", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar AttachedModem entre fechas", notes = "Método para listar un AttachedModem entre fechas")
    @ApiResponses({
            @ApiResponse(code = 201, message = "AttachedModem encontrados"),
            @ApiResponse(code = 404, message = "AttachedModem no encontrados")
    })
    public ResponseEntity<List<AttachedModem>> findAttachedModemByDate(@RequestParam("establishmentDate") String establishmentString) {
        try {
            Date establishmentDate = ParseDate(establishmentString);
            List<AttachedModem> attachedModems = attachedModemService.find(establishmentDate);
            if (attachedModems.size() > 0)
                return new ResponseEntity<List<AttachedModem>>(attachedModems, HttpStatus.OK);
            else
                return new ResponseEntity<List<AttachedModem>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<AttachedModem>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static Date ParseDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date result = null;
        try {
            result = format.parse(date);
        } catch (Exception e) {

        }
        return result;
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de un AttachedModem", notes = "Método que registra un AttachedModem")
    @ApiResponses({
            @ApiResponse(code = 201, message = "AttachedModem creado"),
            @ApiResponse(code = 404, message = "AttachedModem no creado")
    })
    public ResponseEntity<AttachedModem> insertAttachedModemClient(@PathVariable("id") Long id, @PathVariable("id2") Long id2, @Valid @RequestBody AttachedModem attachedModem) {
        try {
            Optional<Client> client = clientService.getById(id);
            Optional<Modem> modem =modemService.getById(id2);
            if (client.isPresent() && modem.isPresent()) {
                attachedModem.setClient(client.get());
                attachedModem.setModem(modem.get());
                AttachedModem attachedModemNew= attachedModemService.save(attachedModem);
                return ResponseEntity.status(HttpStatus.CREATED).body(attachedModemNew);
            } else
                return new ResponseEntity<AttachedModem>(HttpStatus.FAILED_DEPENDENCY);
        } catch (Exception e) {
            return new ResponseEntity<AttachedModem>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de AttachedModem", notes = "Metodo que actualiza los datos de AttachedModem")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Datos de AttachedModem actualizados"),
            @ApiResponse(code = 404, message = "AttachedModem no encontrado")
    })
    public ResponseEntity<AttachedModem> updateAttachedModem(@PathVariable("id") Long id, @Valid @RequestBody AttachedModem attachedModem) {
        try {
            Optional<AttachedModem> attachedModemOld = attachedModemService.getById(id);
            if (!attachedModemOld.isPresent())
                return new ResponseEntity<AttachedModem>(HttpStatus.NOT_FOUND);
            else {
                attachedModem.setId(id);
                attachedModemService.save(attachedModem);
                return new ResponseEntity<AttachedModem>(attachedModem, HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<AttachedModem>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de AttachedModem por Id", notes = "Metodo que elimina los datos de un AttachedModem")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Datos de AttachedModem eliminados"),
            @ApiResponse(code = 404, message = "AttachedModem no encontrado")
    })
    public ResponseEntity<AttachedModem> deleteAttachedModem(@PathVariable("id") Long id) {
        try {
            Optional<AttachedModem> AttachedModemDeleted = attachedModemService.getById(id);
            if (AttachedModemDeleted.isPresent()) {
                attachedModemService.delete(id);
                return new ResponseEntity<AttachedModem>(HttpStatus.OK);
            } else
                return new ResponseEntity<AttachedModem>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<AttachedModem>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
