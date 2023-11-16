package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.excepciones.MensajesException;
import com.app.pi.digitalbooking.excepciones.NombreProductoExisteExcepcion;
import com.app.pi.digitalbooking.excepciones.RegistroExistenteException;
import com.app.pi.digitalbooking.excepciones.RegistroNoEncontradoException;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.model.dto.ValoracionDto;
import com.app.pi.digitalbooking.model.entity.Producto;
import com.app.pi.digitalbooking.model.entity.Valoracion;
import com.app.pi.digitalbooking.repository.ProductoRepository;
import com.app.pi.digitalbooking.repository.ValoracionRepository;
import com.app.pi.digitalbooking.service.ValoracionService;
import com.app.pi.digitalbooking.util.mapper.MapperObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ValoracionServiceImpl implements ValoracionService {

    private final ValoracionRepository repository;
    private final MapperObject mapper;
    private final ProductoRepository productoRepository;

    @Autowired
    public ValoracionServiceImpl(ValoracionRepository repository, MapperObject mapper, ProductoRepository productoRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.productoRepository = productoRepository;
    }


    @Override
    public ValoracionDto crear(ValoracionDto valoracionDto) throws NombreProductoExisteExcepcion, RegistroExistenteException {
        if(valoracionDto.getValoracion() == null || valoracionDto.getIdProducto() == null ||
                valoracionDto.getIdUsuario() == null ){
            throw  new NombreProductoExisteExcepcion(MensajesException.CAMPOS_INVALIDOS.getMensaje());
        }
        Optional<ValoracionDto> valoracion = buscarUnico(valoracionDto.getIdProducto(), valoracionDto.getIdUsuario());
        if (valoracion.isPresent()) {
            throw new RegistroExistenteException("Ya le ha dado una valoracion al producto");
        }
            valoracionDto = mapper.map().convertValue(repository.save(mapper.map().
                    convertValue(valoracionDto, Valoracion.class)),ValoracionDto.class);

            sacarPromedio(valoracionDto.getIdProducto());

            return valoracionDto;
    }

    public void sacarPromedio (Integer idProducto) {
        List<Valoracion> lista = repository.findAllByIdProducto(idProducto);

        int cantidad = repository.findAllByIdProducto(idProducto).size();
        double sumaValoraciones = lista.stream().mapToDouble(Valoracion::getValoracion).sum();
        double promedio = sumaValoraciones / cantidad ;

        Optional<Producto> producto = productoRepository.findById(idProducto);
        producto.ifPresent(p -> p.setPromedio(promedio));
        productoRepository.save(mapper.map().convertValue(producto.get(), Producto.class));


    }

    @Override
    public List<ValoracionDto> listar(PaginableDTO pageable) {
        return Optional.of(repository.findAll(PageRequest.of(pageable.getPagina(), pageable.getCantidad())))
                .get().get().map(v -> mapper.map().convertValue(v, ValoracionDto.class)).toList();
        /* .collect(Collectors.toList());*/
    }

    @Override
    public void eliminar(Integer id) {
        Optional<Valoracion> valoracionAEliminar = repository.findById(id);
        if (valoracionAEliminar.isPresent()) {
            Integer idProducto = valoracionAEliminar.get().getIdProducto();
            repository.deleteById(valoracionAEliminar.get().getIdValoracion());
            sacarPromedio(idProducto);
        }
    }

    @Override
    public ValoracionDto actualizar(ValoracionDto valoracionDto) throws NombreProductoExisteExcepcion, RegistroExistenteException, RegistroNoEncontradoException {
        Optional<ValoracionDto> valoracion =  buscarUnico(valoracionDto.getIdProducto(), valoracionDto.getIdUsuario());
        if(valoracion.isEmpty()){
            throw  new NombreProductoExisteExcepcion(MensajesException.REGISTRO_NO_ENCONTRADO.getMensaje());
        }
        valoracionDto = mapper.map().
                convertValue(repository.save(mapper.map().
                convertValue(valoracion.get(), Valoracion.class)), ValoracionDto.class);
        sacarPromedio(valoracion.get().getIdProducto());
        return valoracionDto;

    }

    @Override
    public Optional<ValoracionDto> buscarPorId(Integer id) throws RegistroNoEncontradoException {
        return Optional.ofNullable(repository.findById(id)
                .map(valoracion -> mapper.map().convertValue(valoracion, ValoracionDto.class))
                .orElseThrow(() -> new RegistroNoEncontradoException(MensajesException.REGISTRO_NO_ENCONTRADO.getMensaje())));
    }

    @Override
    public List<ValoracionDto> buscarTodosPorIdProducto(Integer idProduct) {
        return repository.findAllByIdProducto(idProduct).stream().map(v-> mapper.map().convertValue
                (v, ValoracionDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ValoracionDto> buscarTodoPorIdUsuario(Integer idUsuario) {
        return repository.findAllByIdUsuario(idUsuario).stream().map(v-> mapper.map().convertValue
                (v, ValoracionDto.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<ValoracionDto> buscarUnico(Integer idProduct, Integer idUsuario) {
        return Optional.ofNullable(mapper.map().
                convertValue(repository.findByIdProductoAndIdUsuario(idProduct, idUsuario), ValoracionDto.class));
    }


}
