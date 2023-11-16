package com.app.pi.digitalbooking.repository;

import com.app.pi.digitalbooking.model.entity.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValoracionRepository extends JpaRepository<Valoracion, Integer> {
    List<Valoracion> findAllByIdProducto(Integer idProduct);
    List<Valoracion> findAllByIdUsuario(Integer idUsuario);
    Valoracion findByIdProductoAndIdUsuario (Integer idProduct, Integer idUsuario);
}

