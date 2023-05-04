package com.tesis.ubb.tesis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;


import com.tesis.ubb.tesis.models.Trabajador;

public interface TrabajadorRepository extends JpaRepository<Trabajador,Long> {


}
