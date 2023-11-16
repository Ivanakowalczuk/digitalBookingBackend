package com.app.pi.digitalbooking.controller;

import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.dto.SedeDto;
import com.app.pi.digitalbooking.service.SedeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/sede")
public class SedeController {

    private final SedeService service;

    @PostMapping("/buscarTodos")
    public ResponseEntity<?> buscarPaginado(@RequestBody PaginableDTO paginableDTO){
        return ResponseEntity.status(HttpStatus.OK).body(service.listar(paginableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) throws RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorId(id).orElse(null));
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody SedeDto sedeDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(sedeDto));
    }

    @PutMapping
    public ResponseEntity<?> actualizar(@RequestBody SedeDto sedeDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.actualizar(sedeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/buscarUnico/{sede}/{idProvincia}/{idCiudad}")
    public ResponseEntity<?> buscarUnico(@PathVariable String sede, @PathVariable Integer idProvincia, @PathVariable Integer idCiudad) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarUnico(sede, idProvincia, idCiudad));
    }

    @PostMapping("/buscarPorCiudad/{idCiudad}")
    public ResponseEntity<?> buscarTodosPorIdCiudad(@PathVariable Integer idCiudad, @RequestBody PaginableDTO paginableDTO){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarTodosPorIdCiudad(idCiudad, paginableDTO));
    }

}
