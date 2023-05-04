package com.tesis.ubb.tesis.repository;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.tesis.ubb.tesis.models.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido,Long>{
   
    @Query(value = "SELECT DISTINCT * from pedidos where estado_id = ?1", nativeQuery = true)
    public List<Pedido> findAllEstadoById(Long id);

    
    @Query(value = "select * from pedidos where create_at >= ?1 and (estado_id=6 or estado_id=7)", nativeQuery = true)
    public List<Pedido> findUtilidadDeHoy(Date fecha);

    @Query(value = "select * from pedidos where create_at >= ?1 and create_at <= ?2 and estado_id!=8 and estado_id!=9", nativeQuery = true)
    public List<Pedido> pedidosPorFechas(Date f1,Date f2);

    @Query(value = "select * from pedidos where create_at >= ?1 and create_at <= ?2 ORDER BY create_at ASC;", nativeQuery = true)
    public List<Pedido> pedidosPorFechasOrderASC(Date f1,Date f2);

    @Query(value = "SELECT DATE_FORMAT(create_at, '%Y-%m-%d') AS the_date, COUNT(*) AS count FROM pedidos WHERE create_at BETWEEN DATE_FORMAT('?1', '%Y-%m-%d') AND DATE_FORMAT('?2', '%Y-%m-%d') GROUP BY the_date;", nativeQuery = true)
    public List<Pedido> pedidosAgrupadosPordia(Date f1,Date f2);


    @Query(value = "select * from pedidos where create_at >= ?1 and create_at <= ?2 and (estado_id=6 or estado_id=7)ORDER BY create_at ASC", nativeQuery = true)
    public List<Pedido> UtilidadEntreDosFechas(Date inicio,Date fin);

    @Query(value = "select * from pedidos where estado_id != 8 and estado_id != 9", nativeQuery = true)
    public Page<Pedido> soloPedidosValidos(Pageable pageable);

    
  
}
