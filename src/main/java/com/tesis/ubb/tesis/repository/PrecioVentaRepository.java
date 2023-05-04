package com.tesis.ubb.tesis.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tesis.ubb.tesis.models.PrecioVenta;

public interface PrecioVentaRepository extends JpaRepository<PrecioVenta, Long>{
    
     @Query(value = "SELECT * from precio_venta where producto_id = ?1 and  fecha <= ?2 ORDER BY fecha asc LIMIT 1;", nativeQuery = true)
     public PrecioVenta ubicaPosicion(Long f1, Date f2);

     @Query(value = "SELECT * from precio_venta where producto_id = ?1 and fecha >= ?2 and fecha <= ?3", nativeQuery = true)
     public List<PrecioVenta> findByIdDate(Long id,Date f1,Date f2);

     @Query(value = "SELECT precio from precio_venta where producto_id = ?1 and  fecha <= ?2 ORDER BY fecha DESC LIMIT 1;", nativeQuery = true)
     public Integer precioPorUbicaPosicion(Long f1, Date f2);

     

}
