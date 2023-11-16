package com.app.pi.digitalbooking.controller;

import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.CiudadDto;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.service.CiudadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/ciudad")
public class CiudadController {

    private final CiudadService service;

    @PostMapping("/buscarTodos")
    public ResponseEntity<?> buscarPaginado(@RequestBody PaginableDTO paginableDTO){
        return ResponseEntity.status(HttpStatus.OK).body(service.listar(paginableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) throws RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorId(id).orElse(null));
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CiudadDto ciudadDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(ciudadDto));
    }

    @PutMapping
    public ResponseEntity<?> actualizar(@RequestBody CiudadDto ciudadDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.actualizar(ciudadDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
