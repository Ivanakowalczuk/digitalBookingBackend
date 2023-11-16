package com.app.pi.digitalbooking.controller;

import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.service.ImagenService;
import com.app.pi.digitalbooking.model.dto.ImagenDto;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/v1/imagen")
public class ImagenController {

    private final ImagenService service;

    @Autowired
    public ImagenController(ImagenService service){
        this.service = service;
    }

    @PostMapping("/buscarTodos")
    public ResponseEntity<?> buscarPaginado(@RequestBody PaginableDTO paginableDTO){
        return ResponseEntity.status(HttpStatus.OK).body(service.listar(paginableDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> burcarPorId(@PathVariable Integer id) throws RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorId(id).orElse(null));
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody ImagenDto imagenDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(imagenDto));
    }

    @PostMapping("/upload")
    public ResponseEntity<?> crearS3(@RequestParam("imagen") MultipartFile imagen) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardarImagenS3(imagen));
    }
    @PostMapping("/upload/bulk")
    public ResponseEntity<?> crearS3Bulk(@RequestParam("imagen") List<MultipartFile> imagen) {
        List<ImagenDto> lista = new ArrayList<>();
        imagen.forEach(i -> {
            try {
                lista.add(service.guardarImagenS3(i));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return ResponseEntity.status(HttpStatus.CREATED).body(lista);
    }

    @PutMapping
    public ResponseEntity<?> actualizar(@RequestBody ImagenDto imagenDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.actualizar(imagenDto));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/desactivar/{key}")
    public ResponseEntity<?> eliminar(@PathVariable String key) {
        service.eliminarImagenS3(key);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
