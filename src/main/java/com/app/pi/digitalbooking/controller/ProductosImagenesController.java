package com.app.pi.digitalbooking.controller;

import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.dto.ProductosImagenesDto;
import com.app.pi.digitalbooking.service.ProductosImagenesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/productosImagenes")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:80", "http://localhost","*"})
public class ProductosImagenesController {
    public final ProductosImagenesService service;

    @Autowired
    public ProductosImagenesController(ProductosImagenesService service) {
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
    public ResponseEntity<?> crear(@RequestBody ProductosImagenesDto productosImagenesDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(productosImagenesDto));
    }
    @PutMapping
    public ResponseEntity<?> actualizar(@RequestBody ProductosImagenesDto productosImagenesDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.actualizar(productosImagenesDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/buscarImagenes/{idProducto}")
    public ResponseEntity<?> buscarImagenesConIdProducto(@PathVariable Integer idProducto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarTodasLasImagenesPorIdProducto(idProducto));
    }
}
