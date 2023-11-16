package com.app.pi.digitalbooking.repository;

import com.app.pi.digitalbooking.model.entity.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Integer> {
}
