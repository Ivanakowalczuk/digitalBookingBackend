package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.dto.ProvinciaDto;
import com.app.pi.digitalbooking.model.entity.Provincia;
import com.app.pi.digitalbooking.repository.ProvinciaRepository;
import com.app.pi.digitalbooking.service.ProvinciaService;
import com.app.pi.digitalbooking.util.mapper.MapperObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProvinciaServiceImpl implements ProvinciaService {

    private final ProvinciaRepository repository;
    private final MapperObject mapper;
    @Override
    public ProvinciaDto crear(ProvinciaDto provinciaDto) throws NombreProductoExisteExcepcion, RegistroExistenteException {
        return mapper.map().convertValue(repository.save(mapper.map().convertValue(provinciaDto, Provincia.class)), ProvinciaDto.class);
    }

    @Override
    public List<ProvinciaDto> listar(PaginableDTO pageable) {
        return Optional.of(repository.findAll(PageRequest.of(pageable.getPagina(), pageable.getCantidad())))
                .get()
                .get()
                .map(r -> mapper.map().convertValue(r, ProvinciaDto.class)).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Integer id) {
        repository.findById(id).ifPresent(elemento -> {
            elemento.setIndicadorHabilitado(false);
            repository.save(elemento);
        });
    }

    @Override
    public ProvinciaDto actualizar(ProvinciaDto provinciaDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        return crear(provinciaDto);
    }

    @Override
    public Optional<ProvinciaDto> buscarPorId(Integer id) throws RegistroNoEncontradoException {
        return Optional.of(Optional.of(mapper.map().convertValue(repository.findById(id)
                .orElse(new Provincia()), ProvinciaDto.class))).orElse(null);
    }
}
