package com.app.pi.digitalbooking.controller;

import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.dto.PeticionFechasDto;
import com.app.pi.digitalbooking.model.dto.ReservaDto;
import com.app.pi.digitalbooking.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/reserva")
public class ReservaController {

    private final ReservaService service;

    @PostMapping("/buscarTodos")
    public ResponseEntity<?> buscarPaginado(@RequestBody PaginableDTO paginableDTO){
        return ResponseEntity.status(HttpStatus.OK).body(service.listar(paginableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) throws RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorId(id).orElse(null));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> buscarPorIdusuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorIdUsuario(idUsuario));
    }
    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<?> buscarPorIdProducto(@PathVariable Integer idProducto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorIdProducto(idProducto));
    }
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ReservaDto reservaDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(reservaDto));
    }

    @PutMapping
    public ResponseEntity<?> actualizar(@RequestBody ReservaDto reservaDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.actualizar(reservaDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/fechas/inhabilitadas")
    public ResponseEntity<?> buscarFechasInhabilitadas(@RequestBody PeticionFechasDto peticionFechasDto){
        return ResponseEntity.status(HttpStatus.OK).body(service.fechasInhabilitadas(peticionFechasDto));
    }

}
