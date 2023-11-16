package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.dto.ProductoSedeDto;
import com.app.pi.digitalbooking.model.entity.ProductoSede;
import com.app.pi.digitalbooking.repository.ProductoSedeRepository;
import com.app.pi.digitalbooking.service.ProductoSedeService;
import com.app.pi.digitalbooking.util.mapper.MapperObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoSedeServiceImpl implements ProductoSedeService {

    private final ProductoSedeRepository repository;
    private final MapperObject mapper;

    @Override
    public ProductoSedeDto crear(ProductoSedeDto productoSedeDto) throws NombreProductoExisteExcepcion, RegistroExistenteException {
        productoSedeDto.setIndicadorHabilitado(productoSedeDto.getIndicadorHabilitado() == null || productoSedeDto.getIndicadorHabilitado());
        Optional<ProductoSedeDto> registroGuardado = Optional.ofNullable(buscarUnico(productoSedeDto.getIdProducto(), productoSedeDto.getIdSede()));
        registroGuardado.ifPresent(r -> productoSedeDto.setIdSedeProducto(r.getIdSedeProducto()));
        return mapper.map().convertValue(repository.save(mapper.map().convertValue(productoSedeDto, ProductoSede.class)), ProductoSedeDto.class);
    }

    @Override
    public List<ProductoSedeDto> listar(PaginableDTO pageable) {
        return Optional.of(repository.findAll(PageRequest.of(pageable.getPagina(), pageable.getCantidad())))
                .get()
                .get()
                .map(r -> mapper.map().convertValue(r, ProductoSedeDto.class)).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Integer id) {
        repository.findById(id).ifPresent(elemento -> {
            elemento.setIndicadorHabilitado(false);
            repository.save(elemento);
        });
    }

    @Override
    public ProductoSedeDto actualizar(ProductoSedeDto productoSedeDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        return crear(productoSedeDto);
    }

    @Override
    public Optional<ProductoSedeDto> buscarPorId(Integer id) throws RegistroNoEncontradoException {
        return Optional.of(Optional.of(mapper.map().convertValue(repository.findById(id)
                .orElse(new ProductoSede()), ProductoSedeDto.class))).orElse(null);
    }

    @Override
    public ProductoSedeDto buscarUnico(Integer idProducto, Integer idSede) {
        return repository.findByIdProductoAndIdSedeAndIndicadorHabilitadoIsTrue(idProducto, idSede)
                .map(r -> mapper.map().convertValue(r, ProductoSedeDto.class)).orElse(null);
    }
}
