package com.app.pi.digitalbooking.service.impl;

import com.app.pi.digitalbooking.model.entity.Categoria;
import com.app.pi.digitalbooking.repository.CategoriaRepository;
import com.app.pi.digitalbooking.service.CategoriaService;
import com.app.pi.digitalbooking.model.dto.CategoriaDto;
import com.app.pi.digitalbooking.model.dto.CategoriaParcialDto;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.util.mapper.MapperObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository repository;
    private final MapperObject mapper;

    @Autowired
    public CategoriaServiceImpl(CategoriaRepository repository, MapperObject mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public CategoriaDto crear(CategoriaDto categoriaDto) {

        categoriaDto.setFechaCreacion(categoriaDto.getFechaCreacion() == null ? LocalDateTime.now() : categoriaDto.getFechaCreacion());
        categoriaDto.setIndicadorHabilitado(categoriaDto.getIndicadorHabilitado() == null || categoriaDto.getIndicadorHabilitado());

        CategoriaDto categoria = buscarPorCodigo(categoriaDto.getCodigo());
        if(categoria != null){
            categoriaDto.setIdCategoria(categoria.getIdCategoria());
        }
        return mapper.map().convertValue(repository.save(mapper.map().convertValue(categoriaDto, Categoria.class)), CategoriaDto.class);
    }

    @Override
    public List<CategoriaDto> listar(PaginableDTO pageable) {
        return Optional.of(repository.findAll(PageRequest.of(pageable.getPagina(), pageable.getCantidad())))
                .get()
                .get()
                .map(c -> mapper.map().convertValue(c, CategoriaDto.class)).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Integer id) {
        repository.findById(id).ifPresent(elemento -> {
            elemento.setIndicadorHabilitado(false);
            repository.save(elemento);
        });
    }

    @Override
    public CategoriaDto actualizar(CategoriaDto categoriaDto) {
        return crear(categoriaDto);
    }

    @Override
    public Optional<CategoriaDto> buscarPorId(Integer id) {
        return Optional.of(Optional.of(mapper.map().convertValue(repository.findById(id)
                .orElse(new Categoria()), CategoriaDto.class))).orElse(null);
    }

    @Override
    public CategoriaDto buscarPorCodigo(String codigo) {
        Optional<Categoria> categoria = repository.findCategoriaByCodigo(codigo);
        return mapper.map().convertValue(categoria.orElse(null), CategoriaDto.class);
    }

    @Override
    public List<CategoriaParcialDto> buscarTodosPorIndicadorHabilitadoEsTrue() {
        return repository.findAllByIndicadorHabilitadoIsTrue()
                .stream().map(c -> mapper.map().convertValue(c, CategoriaParcialDto.class))
                .collect(Collectors.toList());
    }

}
