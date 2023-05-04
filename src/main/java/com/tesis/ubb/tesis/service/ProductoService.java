package com.tesis.ubb.tesis.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tesis.ubb.tesis.models.Pedido;
import com.tesis.ubb.tesis.models.Producto;
import com.tesis.ubb.tesis.models.TipoProducto;
import com.tesis.ubb.tesis.models.UnidadMedida;

public interface ProductoService {

    public List<Producto> findAll();

    public Page<Producto>findAll(Pageable pageable);

    public Producto save(Producto producto);

    public void delete(Long id);

    public Producto findById(Long id);

    public List<TipoProducto> findAllTipos();

    public List<UnidadMedida> findAllUnidades();

    public List<Producto> findAllTipoById(Long id);

    public Producto actualizaStock(Long id,Integer stock);

    public Producto actualizaPrecioVenta(Long id ,Integer stock);
    
    public Producto actualizaPrecioCompra(Long id ,Integer precioCompra);

    public Producto actualizastockPedienteVenta(Long id, Integer precioVenta);

    public Producto moodificarFantasma(Long id,Integer fantasma);
}
