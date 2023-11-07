package com.fitLifeBuddy.Controller;

import com.fitLifeBuddy.Entity.Client;
import com.fitLifeBuddy.Service.IClientService;
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
@RequestMapping("/api/clients")
@Api(tags = "Client",value = "Service Web RESTFul de Clients")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Listar Clients",notes = "Métodos para listar a todos los clients")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Clients encontrados"),
            @ApiResponse(code = 404, message = "Clients no encontrados")
    })
    public ResponseEntity<List<Client>> findAll(){
        try {
            List<Client> clients = clientService.getAll();
            //if (clients.size() > 0)
            return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
            //else
            // return new ResponseEntity<List<Client>>(HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            return new ResponseEntity<List<Client>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Client por Id", notes = "Métodos para encontrar un Client por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Client encontrado"),
            @ApiResponse(code = 404, message = "Client no encontrado")
    })
    public ResponseEntity<Client> findById(@PathVariable("id") Long id) {
        try {
            Optional<Client> client = clientService.getById(id);
            if (!client.isPresent())
                return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Client>(client.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "searchByNumberId/{numberId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Client por NumberId", notes = "Métodos para encontrar un Client por su respectivo numberId")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Client encontrado"),
            @ApiResponse(code = 404, message = "Client no encontrado")
    })
    public ResponseEntity<Client> findByNumberId(@PathVariable("numberId") Long numberId) {
        try {
            Client client = clientService.findByNumberId(numberId);
            if (client == null)
                return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Client>(client, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @GetMapping("searchByLastName/{lastName}")
    @ApiOperation(value = "Buscar Client por lastname", notes = "Métodos para encontrar un Client por su respectivo lastname")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Client encontrados"),
            @ApiResponse(code = 404, message = "Client no encontrados")
    })
    public ResponseEntity<List<Client>> findByLastName(@PathVariable("lastName") String lastName) {
        try {
            List<Client> clients = clientService.findByLastName(lastName);
            if (clients.size() > 0)
                return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
            else
                return new ResponseEntity<List<Client>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<Client>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("searchByName/{name}")
    @ApiOperation(value = "Buscar Client por name", notes = "Métodos para encontrar un Client por su respectivo name")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Client encontrados"),
            @ApiResponse(code = 404, message = "Client no encontrados")
    })
    public ResponseEntity<List<Client>> findByFirstName(@PathVariable("name") String name) {
        try {
            List<Client> clients = clientService.findByName(name);
            if (clients.size() > 0)
                return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
            else
                return new ResponseEntity<List<Client>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<Client>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping(value = "searchByNameAndLastName/{name}/{lastName}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Client por name y lastName", notes = "Métodos para encontrar un Client por su respectivo name y lastName")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Client encontrados"),
            @ApiResponse(code = 404, message = "Client no encontrados")
    })
    public ResponseEntity<List<Client>> findByNameAndLastName(
            @PathVariable("name") String name, @PathVariable("lastName") String lastName
    ) {
        try {
            List<Client> clients = clientService.findByNameAndLastName(name, lastName);
            if (clients.size() > 0)
                return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
            else
                return new ResponseEntity<List<Client>>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<List<Client>>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Client", notes = "Método que registra Clients en BD")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Client creado"),
            @ApiResponse(code = 404, message = "Client no creado")
    })
    public ResponseEntity<Client> insertClient(@Valid @RequestBody Client client) {
        try {
            Client clientNew = clientService.save(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(clientNew);
        } catch (Exception e) {
            return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualización de datos de Client", notes = "Metodo que actualiza los datos de Client")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Client actualizados"),
            @ApiResponse(code = 404, message = "Client no encontrado")
    })
    public ResponseEntity<Client> updateClient(
            @PathVariable("id") Long id, @Valid @RequestBody Client client) {
        try {
            Optional<Client> clientUp = clientService.getById(id);
            if (!clientUp.isPresent())
                return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
            client.setId(id);
            clientService.save(client);
            return new ResponseEntity<Client>(client, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminación de Client", notes = "Metodo que elimina los datos de Client")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Datos de Client eliminados"),
            @ApiResponse(code = 404, message = "Client no encontrado")
    })
    public ResponseEntity<Client> deleteCustomer(@PathVariable("id") Long id) {
        try {
            Optional<Client> clientDelete = clientService.getById(id);
            if (!clientDelete.isPresent())
                return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
            clientService.delete(id);
            return new ResponseEntity<Client>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
