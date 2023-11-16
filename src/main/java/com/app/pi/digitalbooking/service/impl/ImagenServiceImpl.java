package com.app.pi.digitalbooking.service.impl;


import com.app.pi.digitalbooking.model.entity.Imagen;
import com.app.pi.digitalbooking.repository.ImagenRepository;
import com.app.pi.digitalbooking.service.ImagenService;
import com.app.pi.digitalbooking.model.dto.ImagenDto;
import com.app.pi.digitalbooking.model.dto.PaginableDTO;
import com.app.pi.digitalbooking.util.mapper.MapperObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImagenServiceImpl implements ImagenService {

    private final ImagenRepository repository;
    private final S3ServiceImpl s3Service;
    private final MapperObject mapper;

    @Autowired
    public ImagenServiceImpl(ImagenRepository repository, S3ServiceImpl s3Service, MapperObject mapper) {
        this.repository = repository;
        this.s3Service = s3Service;
        this.mapper = mapper;
    }


    @Override
    public ImagenDto crear(ImagenDto imagenDto) {
        imagenDto.setIndicadorHabilitado(imagenDto.getIndicadorHabilitado() == null || imagenDto.getIndicadorHabilitado());
        Optional<ImagenDto> guardado = findImagenByKey(imagenDto.getKeyImagen());
        guardado.ifPresent(imagen -> imagenDto.setIdImagen(imagen.getIdImagen()));
        return mapper.map().convertValue(repository.save(mapper.map().convertValue(imagenDto, Imagen.class)), ImagenDto.class);
    }

    @Override
    public List<ImagenDto> listar(PaginableDTO pageable) {
        return Optional.of(repository.findAll(PageRequest.of(pageable.getPagina(), pageable.getCantidad())))
                .get()
                .get()
                .map(c -> mapper.map().convertValue(c, ImagenDto.class)).collect(Collectors.toList());
    }

    @Override
    public void eliminar(Integer id) {
        repository.findById(id).ifPresent(elemento -> {
            elemento.setIndicadorHabilitado(false);
            repository.save(elemento);
        });
    }

    @Override
    public ImagenDto actualizar(ImagenDto imagenDto) {
        return crear(imagenDto);
    }

    @Override
    public Optional<ImagenDto> buscarPorId(Integer id) {
        return Optional.of(Optional.of(mapper.map().convertValue(repository.findById(id)
                .orElse(new Imagen()), ImagenDto.class))).orElse(null);
    }

    @Override
    public Optional<ImagenDto> findImagenByKey(String key) {
        return Optional.ofNullable(mapper.map().convertValue(repository.findImagenByKeyImagen(key), ImagenDto.class));
    }

    @Override
    public ImagenDto guardarImagenS3(MultipartFile imagen) throws IOException {
        String key = s3Service.guardarImagen(imagen);
        ImagenDto imagenDto = ImagenDto.builder()
                .keyImagen(key)
                .urlImagen(s3Service.obtenerUrlS3(key)).build();
        return crear(imagenDto);
    }


    @Override
    public void eliminarImagenS3(String key) {
        s3Service.eliminarImagen(key);
        Optional<ImagenDto> imagenGuardada = findImagenByKey(key);
        imagenGuardada.ifPresent(imagen -> {
            imagen.setIndicadorHabilitado(false);
            actualizar(imagen);
        });
    }

}
