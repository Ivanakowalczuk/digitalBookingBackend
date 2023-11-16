package com.app.pi.digitalbooking.controller;

import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.service.ProductoService;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.dto.ProductoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("api/v1/producto")
public class ProductoController {

    private final ProductoService service;

    @Autowired
    public ProductoController(ProductoService service){
        this.service = service;
    }

    @PostMapping("/buscarTodos")
    public ResponseEntity<?> buscarPaginado(@RequestBody PaginableDTO paginableDTO){
        return ResponseEntity.status(HttpStatus.OK).body(service.listar(paginableDTO));
    }

    @PostMapping("/paginable/categoria/{idCategoria}")
    public ResponseEntity<?> buscarPaginado(@PathVariable Integer idCategoria, @RequestBody PaginableDTO paginableDTO){
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorCategoriaPaginable(idCategoria, paginableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> burcarPorId(@PathVariable Integer id) throws RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorId(id).get());
    }


    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody ProductoDto productoDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(productoDto));
    }

    @PutMapping
    public ResponseEntity<?> actualizar(@RequestBody ProductoDto productoDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.actualizar(productoDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping("/aleatorio")
    public ResponseEntity<?> buscarProductosAleatorios(@RequestBody PaginableDTO paginableDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarAleatoriamente(paginableDTO));
    }

    @PostMapping("/sede/{id}")
    public ResponseEntity<?> buscarProductosPorSede(@PathVariable Integer id, @RequestBody PaginableDTO paginableDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorSede(paginableDTO, id));
    }

    @PostMapping("/ciudad/{id}")
    public ResponseEntity<?> buscarProductosPorCiudad(@PathVariable Integer id, @RequestBody PaginableDTO paginableDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorCiudad(paginableDTO, id));
    }

    @PostMapping("/inhabilitados")
    public ResponseEntity<?> listaInhabilitados(@RequestBody PaginableDTO paginableDTO){
        return ResponseEntity.status(HttpStatus.OK).body(service.listaInhabilitados(paginableDTO));
    }
}
