package com.app.pi.digitalbooking.repository;

import com.app.pi.digitalbooking.model.entity.Sede;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SedeRepository extends JpaRepository<Sede, Integer> {
    Optional<Sede> findBySedeAndIdProvinciaAndIdCiudadAndIndicadorHabilitadoIsTrue(String sede, Integer idProvincia, Integer idCiudad);
    List<Sede> findAllByIdCiudad(Integer idCiudad, Pageable pageable);
    List<Sede> findByIdCiudadAndIndicadorHabilitadoIsTrue(Integer idCiudad);

}
