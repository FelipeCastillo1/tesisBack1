package com.tesis.ubb.tesis.security.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tesis.ubb.tesis.models.Pedido;
import com.tesis.ubb.tesis.models.Producto;
import com.tesis.ubb.tesis.repository.PedidoRepository;
import com.tesis.ubb.tesis.repository.ProductoRepository;
import com.tesis.ubb.tesis.security.models.Usuario;
import com.tesis.ubb.tesis.security.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
	private PedidoRepository pedidoRepository;

    @Autowired
	private ProductoRepository productoRepository;

    public Optional<Usuario> getByNombreUsuario(String nombreUsuario){
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public Optional<Usuario> getByNombreUsuarioOrEmail(String nombreOrEmail){
        return usuarioRepository.findByNombreUsuarioOrEmail(nombreOrEmail,nombreOrEmail);
    }

    public Optional<Usuario> getByTokenPasword(String tokenPasword){
        return usuarioRepository.findByTokenPasword(tokenPasword);
    }

    public boolean existsByNombreUsuario(String nombreUsuario){
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public void save(Usuario usuario){
        usuarioRepository.save(usuario);
    }
  
	public void delete(Long id) {
		usuarioRepository.deleteById(id);

	}

    public Usuario findById(Long id) {
       return usuarioRepository.findById(id).orElse(null);
    }


	public Pedido savePedido(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}

 
	public void deletePedido(Long id) {
		pedidoRepository.deleteById(id);
	}

	public List<Producto> findProductoByNombre(String nombre) {
		return productoRepository.findProductoByNombre(nombre);
	}

   
    public Pedido findPedidoById(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return (List<Usuario>) usuarioRepository.findAll();
    }
}