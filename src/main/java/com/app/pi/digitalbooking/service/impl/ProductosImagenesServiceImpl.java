package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.excepciones.MensajesException;
import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.ImagenDto;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.dto.ProductosImagenesDto;
import com.app.pi.digitalbooking.model.entity.ProductosImagenes;
import com.app.pi.digitalbooking.repository.ProductosImagenesRepository;
import com.app.pi.digitalbooking.service.ProductosImagenesService;
import com.app.pi.digitalbooking.util.mapper.MapperObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductosImagenesServiceImpl implements ProductosImagenesService {

    private final ProductosImagenesRepository repository;
    private final MapperObject mapper;

    @Autowired
    public ProductosImagenesServiceImpl(ProductosImagenesRepository repository, MapperObject mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ProductosImagenesDto crear(ProductosImagenesDto productosImagenesDto) throws NombreProductoExisteExcepcion {
       if(productosImagenesDto.getIdProducto() == null){
           throw new NombreProductoExisteExcepcion(MensajesException.REGISTRO_NO_ENCONTRADO.getMensaje());
       }else{
           return mapper.map().convertValue(repository.save(mapper.map().
                   convertValue(productosImagenesDto, ProductosImagenes.class)), ProductosImagenesDto.class) ;
       }
    }

    @Override
    public List<ProductosImagenesDto> listar(PaginableDTO pageable) {
        return Optional.of(repository.findAll(PageRequest.of(pageable.getPagina(), pageable.getCantidad())))
                .get()
                .get()
                .map(c -> mapper.map().convertValue(c, ProductosImagenesDto.class)).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Integer id) {
        repository.findById(id).ifPresent(elemento -> {
            elemento.getImagen().setIndicadorHabilitado(false);
            repository.save(elemento);
        });
    }

    @Override
    public ProductosImagenesDto actualizar(ProductosImagenesDto productosImagenesDto) throws NombreProductoExisteExcepcion {
        return crear(productosImagenesDto);
    }

    @Override
    public Optional<ProductosImagenesDto> buscarPorId(Integer id) throws RegistroNoEncontradoException {
        return Optional.ofNullable(repository.findById(id)
                .map(productoImagen -> mapper.map().convertValue(productoImagen, ProductosImagenesDto.class))
                .orElseThrow(() -> new RegistroNoEncontradoException(MensajesException.REGISTRO_NO_ENCONTRADO.getMensaje())));

    }

    @Override
    public List<ImagenDto> buscarTodasLasImagenesPorIdProducto(Integer idProducto) {
        return repository.findAllImagenesByIdProducto(idProducto)
                .stream().map(i -> mapper.map().convertValue(i.getImagen(), ImagenDto.class))
                .collect(Collectors.toList());
    }
}
