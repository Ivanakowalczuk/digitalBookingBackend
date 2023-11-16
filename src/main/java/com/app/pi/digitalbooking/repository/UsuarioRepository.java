package com.app.pi.digitalbooking.repository;

import com.app.pi.digitalbooking.model.entity.Persona;
import com.app.pi.digitalbooking.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByIdUsuario(Integer id);
    Optional<Usuario> findByUsuario(String usuario);
    Optional<Usuario> findByPersona(Persona persona);
    Optional<Usuario> findByPersona_Correo(String correo);
}
