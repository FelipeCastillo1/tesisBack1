package com.tesis.ubb.tesis.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.tesis.ubb.tesis.models.EstadoPedido;
import com.tesis.ubb.tesis.models.ItemPedido;
import com.tesis.ubb.tesis.models.Pedido;

import org.springframework.validation.BindingResult;

public interface PedidoService {

    public List<Pedido> findAll();

    public Page<Pedido> findAll(Pageable pageable);

    public Pedido eliminacionLogica(Long id);

    public List<EstadoPedido> finEstadoPedidos();

    public Pedido findById(Long id);

    public List<Pedido> findPedidoByEstado(Long id);

    public List<Pedido> findUtilidadDelDia(Date fecha);

    public List<Pedido> pedidosPorFechas(Date f1, Date f2);

    public Pedido moodificarEstado(Pedido miPedido,Long NuevoEstado);

    public boolean verificaMismoEstado(Pedido miPedido,Long NuevoEstado);

    public List<Pedido> agruparPordia(Date inicio, Date fin);

    public List<Pedido> utilidadEntreDosFechas(Date f1, Date f2);

    public List<ItemPedido> revisaStockSuficiente(Pedido pedido);

    public Pedido actualizaProductosPorPedidoEditado(Pedido pedido);

    public Integer calculaUtilidad(Pedido pedido);

    public  List<ItemPedido> actualizaItemsPorPedidoEditado(Pedido pedido);

    // public ResponseEntity<?> gestionaEdicion(Pedido pedido, BindingResult result,Long id);

    public ResponseEntity<?> checkUpdate(Pedido pedido, BindingResult result,Long id);

    public ResponseEntity<?> registrosUpdate(Pedido pedido, BindingResult result,Long id);

    public ResponseEntity<?> pedidoUpdate(Pedido pedido, BindingResult result,Long id);

    public Page<Pedido> findAllPedidosValidos(Pageable pageable);
    

    // public  Pedido actualizarPedido(Pedido pedido);


    // public Pedido modificarEstado (Long id); //cambia el estado y manda el stock
    // fantas a precioVenta
    // public Pedido modificarEstado (Long id); // modifica un pedido, por lo
    // general es modificar la cantidad de productos, eso cambia la cantidad de
    // utiliadd y el precio total

}
