package com.app.pi.digitalbooking.repository;

import com.app.pi.digitalbooking.model.entity.ProductosImagenes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductosImagenesRepository extends JpaRepository<ProductosImagenes, Integer> {
    @Query(value = "SELECT * FROM productosImagenes WHERE idProducto = ?1", nativeQuery = true)
    List<ProductosImagenes> findAllImagenesByIdProducto(Integer idProducto);

}
