package com.tesis.ubb.tesis.service;

import java.util.List;

import com.tesis.ubb.tesis.models.TipoProducto;

public interface TipoProductoService {

	public List<TipoProducto> findAll();

	public TipoProducto findById(Long id);

	public boolean existsById(Long id);
}

