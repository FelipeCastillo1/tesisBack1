package com.tesis.ubb.tesis.security.service;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;

import com.tesis.ubb.tesis.models.Cliente;
import com.tesis.ubb.tesis.models.Pedido;
import com.tesis.ubb.tesis.models.Producto;
import com.tesis.ubb.tesis.security.models.Usuario;

public interface UsuarioServiceInterface {

    public List<Usuario> findAll();

    public Usuario save(Usuario usuario);

    public void delete(Long id);

    public Usuario findById(Long id);

    public Pedido findPedidoById(Long id);

    public Pedido savePedido(Pedido pedido);

    public void deletePedido(Long id);

    public List<Producto> findProductoByNombre(String nombre);
    
}
