package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.RolDto;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.entity.Rol;
import com.app.pi.digitalbooking.repository.RolRepository;
import com.app.pi.digitalbooking.service.RolService;
import com.app.pi.digitalbooking.util.mapper.MapperObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository repository;
    private final MapperObject mapper;

    @Autowired
    public RolServiceImpl(RolRepository repository, MapperObject mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public RolDto crear(RolDto rolDto) throws RegistroExistenteException {

        RolDto rol = buscarPorCodigo(rolDto.getCodigo());
        if(rol != null){
            throw  new RegistroExistenteException("El rol ya existe");
        }
        return mapper.map().convertValue(repository.save(mapper.map().convertValue(rolDto, Rol.class)), RolDto.class);
    }

    @Override
    public List<RolDto> listar(PaginableDTO pageable) {
        return Optional.of(repository.findAll(PageRequest.of(pageable.getPagina(), pageable.getCantidad())))
                .get()
                .get()
                .map(r -> mapper.map().convertValue(r, RolDto.class)).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Integer id) {
        repository.findById(id).ifPresent(elemento -> {
            elemento.setIndicadorHabilitado(false);
            repository.save(elemento);
        });
    }

    @Override
    public RolDto actualizar(RolDto rolDto) throws  RegistroNoEncontradoException {
        RolDto rol = buscarPorCodigo(rolDto.getCodigo());
        if(rol != null){
            return mapper.map().convertValue(repository.save(mapper.map().convertValue(rolDto, Rol.class)), RolDto.class);
        }
        throw  new RegistroNoEncontradoException("El rol no existe");

    }

    @Override
    public Optional<RolDto> buscarPorId(Integer id) {
        return Optional.of(Optional.of(mapper.map().convertValue(repository.findById(id)
                .orElse(new Rol()), RolDto.class))).orElse(null);
    }

    @Override
    public RolDto buscarPorCodigo(String codigo) {
        Optional<Rol> rol = repository.findByCodigo(codigo);
        return mapper.map().convertValue(rol.orElse(null), RolDto.class);
    }

}
