package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.CiudadDto;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.entity.Ciudad;
import com.app.pi.digitalbooking.repository.CiudadRepository;
import com.app.pi.digitalbooking.service.CiudadService;
import com.app.pi.digitalbooking.util.mapper.MapperObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CiudadServiceImpl implements CiudadService {

    private final CiudadRepository repository;
    private final MapperObject mapper;

    @Override
    public CiudadDto crear(CiudadDto ciudadDto) throws NombreProductoExisteExcepcion, RegistroExistenteException {
        return mapper.map().convertValue(repository.save(mapper.map().convertValue(ciudadDto, Ciudad.class)), CiudadDto.class);
    }

    @Override
    public List<CiudadDto> listar(PaginableDTO pageable) {
        return Optional.of(repository.findAll(PageRequest.of(pageable.getPagina(), pageable.getCantidad())))
                .get()
                .get()
                .filter(Ciudad::getIndicadorHabilitado)
                .map(r -> mapper.map().convertValue(r, CiudadDto.class)).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Integer id) {
        repository.findById(id).ifPresent(elemento -> {
            elemento.setIndicadorHabilitado(false);
            repository.save(elemento);
        });
    }

    @Override
    public CiudadDto actualizar(CiudadDto ciudadDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        return crear(ciudadDto);
    }

    @Override
    public Optional<CiudadDto> buscarPorId(Integer id) throws RegistroNoEncontradoException {
        return Optional.of(Optional.of(mapper.map().convertValue(repository.findById(id)
                .orElse(new Ciudad()), CiudadDto.class))).orElse(null);
    }
}
