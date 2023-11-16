package com.app.pi.digitalbooking.controller;

import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.dto.ValoracionDto;
import com.app.pi.digitalbooking.service.ValoracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/valoracion")
public class ValoracionController {

    private final ValoracionService service;
    @Autowired
    public ValoracionController(ValoracionService service) {
        this.service = service;
    }

    @PostMapping("/buscarTodos")
    public ResponseEntity<?> buscarPaginado(@RequestBody PaginableDTO paginableDTO){
        return ResponseEntity.status(HttpStatus.OK).body(service.listar(paginableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> burcarPorId(@PathVariable Integer id) throws RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody ValoracionDto valoracionDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(valoracionDto));
    }

    @PutMapping
    public ResponseEntity<?> actualizar(@RequestBody ValoracionDto valoracionDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.actualizar(valoracionDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/buscarPorIdProducto/{id}")
    public ResponseEntity<?> buscarPorIdProducto(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarTodosPorIdProducto(id));
    }

    @PostMapping("/buscarPorIdUsuario/{id}")
    public ResponseEntity<?> buscarPorIdUsuario(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarTodoPorIdUsuario(id));
    }



}
