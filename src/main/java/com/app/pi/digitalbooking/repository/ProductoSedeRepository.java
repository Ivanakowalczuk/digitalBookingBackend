package com.app.pi.digitalbooking.repository;

import com.app.pi.digitalbooking.model.entity.ProductoSede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoSedeRepository extends JpaRepository<ProductoSede, Integer> {
    Optional<ProductoSede> findByIdProductoAndIdSedeAndIndicadorHabilitadoIsTrue(Integer idProducto, Integer idSede);
    List<ProductoSede> findByIdSedeAndIndicadorHabilitadoIsTrue(Integer idSede);

}
