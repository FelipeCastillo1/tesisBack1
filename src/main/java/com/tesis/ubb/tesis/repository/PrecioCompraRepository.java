package com.tesis.ubb.tesis.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tesis.ubb.tesis.models.PrecioCompra;


import org.springframework.data.jpa.repository.Query;

public interface PrecioCompraRepository extends JpaRepository<PrecioCompra,Long>{
    
    @Query(value = "SELECT * from precio_compra where producto_id = ?1", nativeQuery = true)
    public List<PrecioCompra> findAllByIdDate(Long id);

    @Query(value = "SELECT * from precio_compra where producto_id = ?1 and fecha >= ?2 and fecha <= ?3", nativeQuery = true)
    public List<PrecioCompra> findByIdDate(Long id,Date f1,Date f2);

    @Query(value = "SELECT * from precio_venta where producto_id = ?1 and  fecha <= ?2 ORDER BY fecha DESC LIMIT 1;", nativeQuery = true)
    public PrecioCompra ubicaPosicion(Long f1, Date f2);

    @Query(value = "SELECT precio from precio_compra where producto_id = ?1 and  fecha <= ?2 ORDER BY fecha DESC LIMIT 1;", nativeQuery = true)
    public Integer precioPorUbicaPosicion(Long f1, Date f2);
}

