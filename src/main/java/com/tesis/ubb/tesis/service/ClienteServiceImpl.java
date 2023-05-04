package com.tesis.ubb.tesis.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tesis.ubb.tesis.models.Cliente;
import com.tesis.ubb.tesis.models.Pedido;
import com.tesis.ubb.tesis.models.Producto;
import com.tesis.ubb.tesis.repository.ClienteRepository;
import com.tesis.ubb.tesis.repository.PedidoRepository;
import com.tesis.ubb.tesis.repository.ProductoRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ProductoRepository productoRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteRepository.findAll();
	}

	@Override
	@Transactional
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteRepository.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		return clienteRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Pedido findPedidoById(Long id) {
		return pedidoRepository.findById(id).orElse(null);
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



}