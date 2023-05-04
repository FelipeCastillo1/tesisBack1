package com.tesis.ubb.tesis.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tesis.ubb.tesis.security.enums.RolNombre;
import com.tesis.ubb.tesis.security.models.Rol;

public interface RolRepository extends JpaRepository<Rol, Long>{

    Optional<Rol> findByRolNombre(RolNombre rolNombre);
    
}
