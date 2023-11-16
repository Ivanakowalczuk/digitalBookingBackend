package com.app.pi.digitalbooking.repository;

import com.app.pi.digitalbooking.model.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    Optional<Categoria> findCategoriaByCodigo(String codigo);
    List<Categoria> findAllByIndicadorHabilitadoIsTrue();
}
