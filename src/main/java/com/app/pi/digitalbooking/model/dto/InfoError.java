package com.app.pi.digitalbooking.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InfoError {
    private String mensaje;
    private int codigoEstado;

    public InfoError(String mensaje, int codigoEstado) {
        this.mensaje = mensaje;
        this.codigoEstado = codigoEstado;
    }
}
