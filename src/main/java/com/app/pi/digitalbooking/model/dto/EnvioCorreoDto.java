package com.app.pi.digitalbooking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnvioCorreoDto {
    private String destinatario;
    private String asunto;
    private String mensaje;
}
