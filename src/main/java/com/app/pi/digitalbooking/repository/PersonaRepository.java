package com.app.pi.digitalbooking.repository;

import com.app.pi.digitalbooking.model.entity.Persona;
import com.app.pi.digitalbooking.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    Optional<Persona> findByCorreo(String correo);
    Optional<Persona> findByIdPersona(Integer id);

}
