package com.app.pi.digitalbooking.repository;

import com.app.pi.digitalbooking.model.entity.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Integer> {
    Imagen findImagenByKeyImagen(String key);
}
