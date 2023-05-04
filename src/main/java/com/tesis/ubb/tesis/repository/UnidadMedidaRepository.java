package com.tesis.ubb.tesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tesis.ubb.tesis.models.UnidadMedida;


public interface UnidadMedidaRepository extends JpaRepository<UnidadMedida, Long> {

    public boolean existsById(Long id);
}
