package com.app.pi.digitalbooking.repository;

import com.app.pi.digitalbooking.model.entity.Token;
import com.app.pi.digitalbooking.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);
    List<Token> findByUsuario(Usuario usuario);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tokens t " +
            "SET t.fechaConfirmacion = ?2 " +
            "WHERE t.token = ?1", nativeQuery = true)
    int updateFechaConfirmacion(String token, LocalDateTime fecha);

    @Query("""
            select t from Token t
            inner join Usuario u on t.usuario.idUsuario = u.idUsuario
            where u.idUsuario  = :idUsu and (t.expirado = false or t.revocado = false)
            """)
    List<Token> buscarTokenValidosPorUsuario(Integer idUsu);

}
