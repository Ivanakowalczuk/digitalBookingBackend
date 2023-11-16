package com.app.pi.digitalbooking.service;

import com.app.pi.digitalbooking.model.dto.ImagenDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ImagenService extends CrudService<ImagenDto, Integer>{
    Optional<ImagenDto> findImagenByKey(String key);
    ImagenDto guardarImagenS3(MultipartFile imagen) throws IOException;
    void eliminarImagenS3(String key);
}
