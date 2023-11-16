package com.app.pi.digitalbooking.controller;

import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.service.CategoriaService;
import com.app.pi.digitalbooking.model.dto.CategoriaDto;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/categoria")
public class CategoriaController {

    private final CategoriaService service;

    @Autowired
    public CategoriaController(CategoriaService service){
        this.service = service;
    }

    @PostMapping("/buscarTodos")
    public ResponseEntity<?> buscarpaginado(@RequestBody PaginableDTO paginableDTO){
        return ResponseEntity.status(HttpStatus.OK).body(service.listar(paginableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) throws RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorId(id).orElse(null));
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CategoriaDto categoriaDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(categoriaDto));
    }

    @PutMapping
    public ResponseEntity<?> actualizar(@RequestBody CategoriaDto categoriaDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.actualizar(categoriaDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/activas")
    public ResponseEntity<?> devolverTodosConIndicadorHabilitado(){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarTodosPorIndicadorHabilitadoEsTrue());
    }
}
