package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.dto.SedeDto;
import com.app.pi.digitalbooking.model.entity.Sede;
import com.app.pi.digitalbooking.repository.SedeRepository;
import com.app.pi.digitalbooking.service.SedeService;
import com.app.pi.digitalbooking.util.mapper.MapperObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SedeServiceImpl implements SedeService {

    private final SedeRepository repository;
    private final MapperObject mapper;

    @Override
    public SedeDto crear(SedeDto sedeDto) throws NombreProductoExisteExcepcion, RegistroExistenteException {
        sedeDto.setIndicadorHabilitado(sedeDto.getIndicadorHabilitado() == null || sedeDto.getIndicadorHabilitado());
        Optional<SedeDto> registroGuardado = Optional.ofNullable(buscarUnico(sedeDto.getSede(), sedeDto.getIdProvincia(), sedeDto.getIdCiudad()));
        registroGuardado.ifPresent(r -> sedeDto.setIdSede(r.getIdSede()));
        return mapper.map().convertValue(repository.save(mapper.map().convertValue(sedeDto, Sede.class)), SedeDto.class);
    }

    @Override
    public List<SedeDto> listar(PaginableDTO pageable) {
        return Optional.of(repository.findAll(PageRequest.of(pageable.getPagina(), pageable.getCantidad())))
                .get()
                .get()
                .filter(Sede::getIndicadorHabilitado)
                .map(r -> mapper.map().convertValue(r, SedeDto.class)).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Integer id) {
        repository.findById(id).ifPresent(elemento -> {
            elemento.setIndicadorHabilitado(false);
            repository.save(elemento);
        });
    }

    @Override
    public SedeDto actualizar(SedeDto sedeDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        return crear(sedeDto);
    }

    @Override
    public Optional<SedeDto> buscarPorId(Integer id) throws RegistroNoEncontradoException {
        return Optional.of(Optional.of(mapper.map().convertValue(repository.findById(id)
                .orElse(new Sede()), SedeDto.class))).orElse(null);
    }

    @Override
    public SedeDto buscarUnico(String sede, Integer idProvincia, Integer idCiudad) {
        return repository.findBySedeAndIdProvinciaAndIdCiudadAndIndicadorHabilitadoIsTrue(sede, idProvincia, idCiudad)
                .map(r -> mapper.map().convertValue(r, SedeDto.class)).orElse(null);
    }

    @Override
    public List<SedeDto> buscarTodosPorIdCiudad(Integer idCiudad, PaginableDTO paginableDTO) {
        return repository.findAllByIdCiudad(idCiudad,PageRequest.of(paginableDTO.getPagina(), paginableDTO.getCantidad()))
                .stream().map(s -> mapper.map().convertValue(s, SedeDto.class))
                .sorted(Comparator.comparing(SedeDto::getIdSede).reversed())
                .collect(Collectors.toList());
    }
}
