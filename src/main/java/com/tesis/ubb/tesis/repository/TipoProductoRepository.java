package com.tesis.ubb.tesis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tesis.ubb.tesis.models.TipoProducto;

public interface TipoProductoRepository extends JpaRepository<TipoProducto,Long>{

    public boolean existsById(Long id);
 
}
