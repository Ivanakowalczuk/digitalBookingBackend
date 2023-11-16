package com.app.pi.digitalbooking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Asset {
    private byte[] contenido;
    private String tipoContenido;
}
