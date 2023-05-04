package com.tesis.ubb.tesis.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tesis.ubb.tesis.models.PrecioCompra;
import com.tesis.ubb.tesis.models.Producto;

public interface PrecioCompraService {

    public List<PrecioCompra> findAll();

    public Page<PrecioCompra> findAll(Pageable pageable);
	
	public PrecioCompra save(PrecioCompra precioCompra);
	
	public void delete(Long id);
	
	public PrecioCompra findById(Long id);

	public PrecioCompra saveByProducto(Producto producto);
    
	public List<PrecioCompra> findAllByIdDate(Producto producto);

	public List<PrecioCompra> findlByIdDate(Date a,Date b, Long c);

	public PrecioCompra buscaDondeVa(Date fechaPedido, Long Idporducto);

	public Integer precioPorFecha(Date fechaPedido, Long Idporducto);
	
}
