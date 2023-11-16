package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.excepciones.MensajesException;
import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.FechasRespuestasDto;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.dto.PeticionFechasDto;
import com.app.pi.digitalbooking.model.dto.ReservaDto;
import com.app.pi.digitalbooking.model.entity.Reserva;
import com.app.pi.digitalbooking.repository.ReservaRepository;
import com.app.pi.digitalbooking.service.ReservaService;
import com.app.pi.digitalbooking.util.mapper.MapperObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository repository;
    private final MapperObject mapper;

    @Override
    public ReservaDto crear(ReservaDto reservaDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        if (buscarUnico(reservaDto.getIdProducto(), reservaDto.getIdUsuario(), reservaDto.getFechaInicioReserva(), reservaDto.getFechaFinReserva()).isPresent())
            throw new RegistroExistenteException(MensajesException.REGISTRO_EXISTENTE.getMensaje());

        return mapper.map().convertValue(repository.save(mapper.map().convertValue(reservaDto, Reserva.class)), ReservaDto.class);
    }

    @Override
    public List<ReservaDto> listar(PaginableDTO pageable) {
        return Optional.of(repository.findAll(PageRequest.of(pageable.getPagina(), pageable.getCantidad())))
                .get()
                .get()
                .map(r -> mapper.map().convertValue(r, ReservaDto.class)).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public ReservaDto actualizar(ReservaDto reservaDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        return crear(reservaDto);
    }

    @Override
    public Optional<ReservaDto> buscarPorId(Integer id) throws RegistroNoEncontradoException {
        return Optional.of(Optional.of(mapper.map().convertValue(repository.findById(id)
                .orElse(new Reserva()), ReservaDto.class))).orElse(null);
    }

    @Override
    public Optional<ReservaDto> buscarUnico(Integer idProducto, Integer idUsuario, LocalDate fehaInicio, LocalDate fechaFin) throws RegistroNoEncontradoException {
        return Optional.ofNullable(mapper.map().convertValue(
                repository.findByIdProductoAndIdUsuarioAndFechaInicioReservaAndFechaFinReserva(idProducto, idUsuario, fehaInicio, fechaFin), ReservaDto.class));
    }

    @Override
    public List<Reserva> buscarPorIdUsuario(Integer idUsuario) {
        return repository.findAllByIdUsuario(idUsuario);
    }

    @Override
    public List<ReservaDto> buscarPorIdProducto(Integer idProducto) {
        List<Reserva> reservas = repository.findAllByIdProducto(idProducto);
        List<ReservaDto> reservasDto = new ArrayList<>();
         reservas.forEach(r -> reservasDto.add(mapper.map().convertValue(r, ReservaDto.class)));
         return reservasDto;
    }
    
    public FechasRespuestasDto fechasInhabilitadas(PeticionFechasDto requestDto) {
        List<String> fechasListado = new ArrayList<>();

        repository.findByIdProductoAndFechaInicioReservaIsGreaterThanEqualAndFechaFinReservaLessThanEqual(
                        requestDto.getIdProducto(), requestDto.getFechaDesde(), requestDto.getFechaHasta()
                )
                .forEach(reserva -> fechasListado.addAll(concatenarFechas(
                        reserva.getFechaInicioReserva(), reserva.getFechaFinReserva()
                )));

        return FechasRespuestasDto.builder()
                .rangoFechas(fechasListado)
                .build();
    }

    private List<String> concatenarFechas(LocalDate desde, LocalDate hasta) {
        List<String> listaDeFechas = new ArrayList<>();
        long diferenciaDias = DAYS.between(desde, hasta);
        for (int i = 0; i <= diferenciaDias; i++) {
            listaDeFechas.add(String.valueOf(desde.plusDays(i)));
        }
        return listaDeFechas;
    }
}
