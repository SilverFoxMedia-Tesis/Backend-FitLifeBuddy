package com.fitLifeBuddy.Controller;


import com.fitLifeBuddy.Entity.Modem;
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
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/modems")
@Api(tags = "Modem",value = "Service Web RESTFul de Modems")
public class ModemController {

    @Autowired
    private IModemService modemService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Modems",notes = "Métodos para listar a todos los modems")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Modems encontrados"),
            @ApiResponse(code = 404, message = "Modems no encontrados")
    })
    public ResponseEntity<List<Modem>> findAll(){
        try {
            List<Modem> modems = modemService.getAll();
            //if (modems.size() > 0)
            return new ResponseEntity<List<Modem>>(modems, HttpStatus.OK);
            //else
            // return new ResponseEntity<List<Modem>>(HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            return new ResponseEntity<List<Modem>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Modem por Id", notes = "Métodos para encontrar un Modem por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Modem encontrado"),
            @ApiResponse(code = 404, message = "Modem no encontrado")
    })
    public ResponseEntity<Modem> findById(@PathVariable("id") Long id) {
        try {
            Optional<Modem> modem = modemService.getById(id);
            if (!modem.isPresent())
                return new ResponseEntity<Modem>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Modem>(modem.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Modem>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "searchByPonSn/{ponSn}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Modem por PonSn", notes = "Métodos para encontrar un Modem por su respectivo ponSn")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Modem encontrado"),
            @ApiResponse(code = 404, message = "Modem no encontrado")
    })
    public ResponseEntity<Modem> findByPonSn(@PathVariable("ponSn") String ponSn) {
        try {
            Modem modem = modemService.findByPonSn(ponSn);
            if (modem == null)
                return new ResponseEntity<Modem>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Modem>(modem, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Modem>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @GetMapping("searchBySupplier/{supplier}")
    @ApiOperation(value = "Buscar Modem por supplier", notes = "Métodos para encontrar un Modem por su respectivo supplier")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Modem encontrados"),
            @ApiResponse(code = 404, message = "Modem no encontrados")
    })
    public ResponseEntity<List<Modem>> findBySupplier(@PathVariable("supplier") String supplier) {
        try {
            List<Modem> modems = modemService.findBySupplier(supplier);
            if (modems.size() > 0)
                return new ResponseEntity<List<Modem>>(modems, HttpStatus.OK);
            else
                return new ResponseEntity<List<Modem>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<Modem>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("searchByPon/{pon}")
    @ApiOperation(value = "Buscar Modem por pon", notes = "Métodos para encontrar un Modem por su respectivo pon")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Modem encontrados"),
            @ApiResponse(code = 404, message = "Modem no encontrados")
    })
    public ResponseEntity<List<Modem>> findByPon(@PathVariable("pon") Long pon) {
        try {
            List<Modem> modems = modemService.findByPon(pon);
            if (modems.size() > 0)
                return new ResponseEntity<List<Modem>>(modems, HttpStatus.OK);
            else
                return new ResponseEntity<List<Modem>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<Modem>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Modem", notes = "Método que registra Modems en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Modem creado"),
            @ApiResponse(code = 404, message = "Modem no creado")
    })
    public ResponseEntity<Modem> insertModem(@Valid @RequestBody Modem modem) {
        try {
            Modem modemNew = modemService.save(modem);
            return ResponseEntity.status(HttpStatus.CREATED).body(modemNew);
        } catch (Exception e) {
            return new ResponseEntity<Modem>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de Modem", notes = "Metodo que actualiza los datos de Modem")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Modem actualizados"),
            @ApiResponse(code = 404, message = "Modem no encontrado")
    })
    public ResponseEntity<Modem> updateModem(
            @PathVariable("id") Long id, @Valid @RequestBody Modem modem) {
        try {
            Optional<Modem> modemUp = modemService.getById(id);
            if (!modemUp.isPresent())
                return new ResponseEntity<Modem>(HttpStatus.NOT_FOUND);
            modem.setId(id);
            modemService.save(modem);
            return new ResponseEntity<Modem>(modem, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Modem>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de Modem", notes = "Metodo que elimina los datos de Modem")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Modem eliminados"),
            @ApiResponse(code = 404, message = "Modem no encontrado")
    })
    public ResponseEntity<Modem> deleteModem(@PathVariable("id") Long id) {
        try {
            Optional<Modem> modemDelete = modemService.getById(id);
            if (!modemDelete.isPresent())
                return new ResponseEntity<Modem>(HttpStatus.NOT_FOUND);
            modemService.delete(id);
            return new ResponseEntity<Modem>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Modem>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
