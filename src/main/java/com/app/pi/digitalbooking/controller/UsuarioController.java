package com.app.pi.digitalbooking.controller;

import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.dto.RegistroDto;
import com.app.pi.digitalbooking.model.dto.UsuarioDto;
import com.app.pi.digitalbooking.service.GestionUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/usuario")
public class UsuarioController {

    private final GestionUsuariosService service;

    @Autowired
    public UsuarioController(GestionUsuariosService service) {
        this.service = service;
    }

    @PostMapping("/buscarTodos")
    public ResponseEntity<?> buscarpaginado(@RequestBody PaginableDTO paginableDTO){
        return ResponseEntity.status(HttpStatus.OK).body(service.listar(paginableDTO));
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody RegistroDto registroDto) throws RegistroExistenteException, NombreProductoExisteExcepcion, RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.OK).body(service.crear(registroDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) throws RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorId(id).orElse(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody RegistroDto registroDto) throws RegistroNoEncontradoException { /* , NombreProductsExisteExcepcion { */
        return ResponseEntity.status(HttpStatus.OK).body(service.actualizar(id,registroDto));
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<?> buscarPorCorreo(@PathVariable String correo) throws RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarUsuarioPorCorreo(correo));
    }
}
