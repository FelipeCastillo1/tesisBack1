package com.tesis.ubb.tesis.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tesis.ubb.tesis.models.Cliente;
import com.tesis.ubb.tesis.models.Pedido;
import com.tesis.ubb.tesis.models.Producto;
import com.tesis.ubb.tesis.repository.PedidoRepository;
import com.tesis.ubb.tesis.repository.ProductoRepository;
import com.tesis.ubb.tesis.security.models.Usuario;
import com.tesis.ubb.tesis.security.repository.UsuarioRepository;

@Service
public class UsuarioServiceInterfaceImplements implements UsuarioServiceInterface{

    @Autowired
	private UsuarioRepository usuarioRepository;

    @Autowired
	private PedidoRepository pedidoRepository;

    @Autowired
	private ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @Override
	@Transactional
	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

    @Override
	@Transactional
	public void delete(Long id) {
		usuarioRepository.deleteById(id);

	}

    @Override
    public Usuario findById(Long id) {
       return usuarioRepository.findById(id).orElse(null);
    }

    @Override
	@Transactional
	public Pedido savePedido(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}

    @Override
	@Transactional
	public void deletePedido(Long id) {
		pedidoRepository.deleteById(id);
	}

	@Override
	@Transactional
	public List<Producto> findProductoByNombre(String nombre) {
		return productoRepository.findProductoByNombre(nombre);
	}

    @Override
    public Pedido findPedidoById(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }
    
}
