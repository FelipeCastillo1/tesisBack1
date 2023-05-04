package com.tesis.ubb.tesis.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tesis.ubb.tesis.security.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    Optional<Usuario> findByNombreUsuarioOrEmail(String nombreUsuario, String email);

    Optional<Usuario> findByTokenPasword(String tokenPasword);

    boolean existsByNombreUsuario(String nombreUsuario);

    boolean existsByEmail(String email);

    @Query(value = "SELECT * from usuario u where u.nombre_usuario = ?1", nativeQuery = true)
    Usuario existByNombreUsuario(String nombreUsuario);

}
