package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.ProductoDto;
import com.app.pi.digitalbooking.model.dto.ReservaDto;
import com.app.pi.digitalbooking.service.IResumible;
import com.app.pi.digitalbooking.service.ProductoService;
import com.app.pi.digitalbooking.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResumirInfoReservaImpl implements IResumible<ReservaDto> {

    private final ReservaService reservaService;
    private final ProductoService productoService;

    @Override
    public ReservaDto resumirInformacion(Integer id) throws RegistroNoEncontradoException {

        Optional<ReservaDto> optReserva = reservaService.buscarPorId(id);
        ProductoDto producto;
        ReservaDto reserva = null;
        if(optReserva.isPresent()){
            reserva = optReserva.get();
            producto = optReserva.map(reservaDto -> {
                try {
                    return productoService.buscarPorId(reservaDto.getIdProducto()).orElse(null);
                } catch (RegistroNoEncontradoException e) {
                    throw new RuntimeException(e);
                }
            }).orElse(null);

            reserva.setProducto(producto);
        }
        return reserva;
    }
}
