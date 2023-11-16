package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.model.dto.ProductosImagenesDto;
import com.app.pi.digitalbooking.model.dto.ProductoSedeDto;
import com.app.pi.digitalbooking.model.entity.Producto;
import com.app.pi.digitalbooking.excepciones.MensajesException;
import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.entity.ProductoSede;
import com.app.pi.digitalbooking.model.entity.Sede;
import com.app.pi.digitalbooking.repository.ProductoRepository;
import com.app.pi.digitalbooking.repository.ProductoSedeRepository;
import com.app.pi.digitalbooking.repository.SedeRepository;
import com.app.pi.digitalbooking.service.ProductoService;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.dto.ProductoDto;
import com.app.pi.digitalbooking.service.ProductosImagenesService;
import com.app.pi.digitalbooking.service.ProductoSedeService;
import com.app.pi.digitalbooking.util.mapper.MapperObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository repository;
    private final ProductosImagenesService serviceProductosImagenes;
    private final ProductoSedeService serviceSedeProducto;

    private final MapperObject mapper;
    private final ProductoSedeRepository productoSedeRepository;
    private final SedeRepository sedeRepository;


    @Override
    public ProductoDto crear(ProductoDto productoDto) throws NombreProductoExisteExcepcion {

        productoDto.setIndicadorHabilitado(productoDto.getIndicadorHabilitado() == null || productoDto.getIndicadorHabilitado());
        Optional<ProductoDto> producto = buscarPorNombre(productoDto.getNombre());
        if (producto.isPresent()) {
            throw new NombreProductoExisteExcepcion(MensajesException.NOMBRE_EXISTENTE.getMensaje());
        }

        productoDto = mapper.map().convertValue(repository.save(mapper.map().convertValue(productoDto, Producto.class)), ProductoDto.class);

        ProductoDto finalProductoDto = productoDto;
        productoDto.getListasIdsImagen()
                .stream()
                .map(id -> {
                    try {
                        return serviceProductosImagenes.crear(ProductosImagenesDto.builder()
                                .idProducto(finalProductoDto.getIdProducto())
                                .idImagen(id)
                                .build());
                    } catch (NombreProductoExisteExcepcion | RegistroExistenteException |
                             RegistroNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();

        productoDto.getListaIdsSedes()
                .stream()
                .map(id -> {
                    try {
                        return serviceSedeProducto.crear(ProductoSedeDto.builder()
                                .idProducto(finalProductoDto.getIdProducto())
                                .idSede(id)
                                .build());
                    } catch (NombreProductoExisteExcepcion | RegistroExistenteException | RegistroNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();

        return productoDto;
    }

    @Override
    public List<ProductoDto> listar(PaginableDTO pageable) {
        return Optional.of(repository.findAll(PageRequest.of(pageable.getPagina(), pageable.getCantidad()))
                        .stream()
                        .filter(Producto::getIndicadorHabilitado))
                .get()
                .map(c -> mapper.map().convertValue(c, ProductoDto.class)).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Integer id) {
        repository.findById(id).ifPresent(elemento -> {
            elemento.setIndicadorHabilitado(false);
            repository.save(elemento);
        });
    }

    @Override
    public ProductoDto actualizar(ProductoDto productoDto) throws NombreProductoExisteExcepcion {
        return crear(productoDto);
    }

    @Override
    public Optional<ProductoDto> buscarPorId(Integer id) throws RegistroNoEncontradoException {
        return Optional.ofNullable(repository.findById(id)
                .map(producto -> mapper.map().convertValue(producto, ProductoDto.class))
                .orElseThrow(() -> new RegistroNoEncontradoException(MensajesException.REGISTRO_NO_ENCONTRADO.getMensaje())));
    }

    @Override
    public Optional<ProductoDto> buscarPorNombre(String nombre) {
        return Optional.ofNullable(mapper.map().convertValue(repository.findByNombreAndIndicadorHabilitadoIsTrue(nombre), ProductoDto.class));
    }

    @Override
    public List<ProductoDto> buscarPorCategoriaPaginable(Integer idCategoria, PaginableDTO paginableDTO) {
        return repository.findAllByIdCategoria(idCategoria, PageRequest.of(paginableDTO.getPagina(), paginableDTO.getCantidad()))
                .stream()
                .map(productos -> mapper.map().convertValue(productos, ProductoDto.class))
                .sorted(Comparator.comparing(ProductoDto::getIdProducto).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoDto> buscarAleatoriamente(PaginableDTO paginableDTO) {
        return repository.findRandom(paginableDTO.getCantidad())
                .stream()
                .map(productos -> mapper.map().convertValue(productos, ProductoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoDto> buscarPorSede(PaginableDTO paginableDTO, Integer idSede) {
        List<ProductoSede> productoSedes = productoSedeRepository.findByIdSedeAndIndicadorHabilitadoIsTrue(idSede);
        List<ProductoDto> productoDtos = new ArrayList<>();
        productoSedes.forEach(productoSede -> repository.findById(productoSede.getIdProducto()).ifPresent(producto -> productoDtos.add(mapper.map().convertValue(producto, ProductoDto.class))));
        return productoDtos;
    }

    @Override
    public List<ProductoDto> buscarPorCiudad(PaginableDTO paginableDTO, Integer idCiudad) {
        List<Sede> sedes = sedeRepository.findByIdCiudadAndIndicadorHabilitadoIsTrue(idCiudad);
        List<ProductoDto> productoDtos = new ArrayList<>();

        sedes.forEach(sede -> {
            if (Objects.equals(sede.getIdCiudad(), idCiudad)) {
                List<ProductoSede> productoSedes = productoSedeRepository.findByIdSedeAndIndicadorHabilitadoIsTrue(sede.getIdSede());
                productoSedes.forEach(productoSede -> repository.findById(productoSede.getIdProducto()).ifPresent(producto -> productoDtos.add(mapper.map().convertValue(producto, ProductoDto.class))));
            }
        });

        return productoDtos;
    }

    @Override
    public  List<ProductoDto> listaInhabilitados(PaginableDTO paginableDTO){
        return Optional.of(repository.findAll(PageRequest.of(paginableDTO.getPagina(), paginableDTO.getCantidad()))
                        .stream()
                        .filter(p -> !p.getIndicadorHabilitado()))
                .get()
                .map(c -> mapper.map().convertValue(c, ProductoDto.class)).collect(Collectors.toList());
    }

}
