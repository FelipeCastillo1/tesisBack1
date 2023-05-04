package com.tesis.ubb.tesis.service;

import java.util.List;

import com.tesis.ubb.tesis.models.EstadoPedido;

public interface EstadoPedidoService {
    
    public List<EstadoPedido> findAll();

	public EstadoPedido findById(Long id);

	public boolean existsById(Long id);

	public List<EstadoPedido> soloEstadosValidos();

}
