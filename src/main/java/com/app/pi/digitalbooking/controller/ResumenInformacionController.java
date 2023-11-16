package com.app.pi.digitalbooking.controller;

import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.ReservaDto;
import com.app.pi.digitalbooking.service.IResumible;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/resume")
@RequiredArgsConstructor
public class ResumenInformacionController {

    private final IResumible<ReservaDto> resumibleReserva;
    @GetMapping("/reserva/{idReserva}")
    ResponseEntity<?> resumenReserva(@PathVariable Integer idReserva) throws RegistroNoEncontradoException {
        return ResponseEntity.ok(resumibleReserva.resumirInformacion(idReserva));
    }
}
