package com.tesis.ubb.tesis.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tesis.ubb.tesis.models.Cliente;
import com.tesis.ubb.tesis.models.Pedido;
import com.tesis.ubb.tesis.models.Producto;

public interface ClienteService {

    public List<Cliente> findAll();

    public Page<Cliente> findAll(Pageable pageable);

    public Cliente save(Cliente cliente);

    public void delete(Long id);

    public Cliente findById(Long id);

    public Pedido findPedidoById(Long id);

    public Pedido savePedido(Pedido pedido);

    public void deletePedido(Long id);

    public List<Producto> findProductoByNombre(String nombre);


}
