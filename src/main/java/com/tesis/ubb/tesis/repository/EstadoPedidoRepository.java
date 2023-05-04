package com.tesis.ubb.tesis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tesis.ubb.tesis.models.EstadoPedido;

public interface EstadoPedidoRepository extends JpaRepository<EstadoPedido,Long>{
    
       

    @Query(value = "select * from estado_pedido where id != 8 and id != 9", nativeQuery = true)
    public List<EstadoPedido> soloEstadosValidos();

}
