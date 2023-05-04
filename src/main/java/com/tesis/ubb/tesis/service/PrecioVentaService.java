package com.tesis.ubb.tesis.service;

import java.util.Date;
import java.util.List;

import com.tesis.ubb.tesis.models.PrecioVenta;
import com.tesis.ubb.tesis.models.Producto;

public interface PrecioVentaService {

    public List<PrecioVenta> findAll();  
	
	public PrecioVenta save(PrecioVenta precioVenta);
	
	public void delete(Long id);
	
	public PrecioVenta findById(Long id);
    
	public PrecioVenta saveByProducto(Producto producto);

	public PrecioVenta agregaCantidadVendidaARegistro(PrecioVenta venta,Integer agregar);

	public PrecioVenta buscaDondeVa(Date fechaPedido, Long Idporducto);

	public List<PrecioVenta> findlByIdDate(Date a,Date b, Long c);

	public Integer precioPorFecha(Date fechaPedido, Long Idporducto);


}
