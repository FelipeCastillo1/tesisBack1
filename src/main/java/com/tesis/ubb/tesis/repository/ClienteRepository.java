package com.tesis.ubb.tesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tesis.ubb.tesis.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
