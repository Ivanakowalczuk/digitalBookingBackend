package com.app.pi.digitalbooking.repository;

import com.app.pi.digitalbooking.model.entity.Producto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    Producto findByNombreAndIndicadorHabilitadoIsTrue(String nombre);
    List<Producto> findAllByIdCategoria(Integer idCategoria, Pageable pageable);
    @Query(value = "SELECT * FROM productos WHERE indicadorHabilitado is true ORDER BY RAND() LIMIT ?1", nativeQuery = true)
    List<Producto> findRandom(int cantidad);

}
