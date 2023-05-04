package com.tesis.ubb.tesis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tesis.ubb.tesis.models.Producto;
import com.tesis.ubb.tesis.models.TipoProducto;
import com.tesis.ubb.tesis.models.UnidadMedida;
import com.tesis.ubb.tesis.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return (List<Producto>) productoRepository.findAll();
    }

    @Override
    @Transactional
    public Page<Producto> findAll(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productoRepository.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoProducto> findAllTipos() {
        return productoRepository.findAllTipos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UnidadMedida> findAllUnidades() {
        return productoRepository.findAllUnidades();
    }

    @Override
    public List<Producto> findAllTipoById(Long id) {
        return productoRepository.findAllTipoById(id);
    }

    @Override
    @Transactional
    public Producto actualizaStock(Long id, Integer stock) {
        Producto p = productoRepository.findById(id).orElse(null);
        Integer valor = p.getStock() + stock;
        p.setStock(valor);
        return p;
    }

    @Override
    @Transactional
    public Producto actualizaPrecioCompra(Long id, Integer precioCompra) {
        Producto p = productoRepository.findById(id).orElse(null);
        p.setUltimoPrecioCompra(precioCompra);
        return p;
    }

    @Override
    @Transactional
    public Producto actualizaPrecioVenta(Long id, Integer precioVenta) {
        Producto p = productoRepository.findById(id).orElse(null);
        p.setUltimoPrecioVenta(precioVenta);
        return p;
    }

    @Override
    @Transactional
    public Producto actualizastockPedienteVenta(Long id, Integer stockParaVender) {
        Producto p = productoRepository.findById(id).orElse(null);
        Integer stockNuevo=(p.getStockPedienteVenta() +stockParaVender);
        p.setStockPedienteVenta(stockNuevo);
        return p;
    }

    @Override
    @Transactional
    public Producto moodificarFantasma(Long idProducto,Integer fantasma){
        Producto modificame = productoRepository.findById(idProducto).orElse(null);
        Integer valorFnatas= modificame.getStockPedienteVenta() + fantasma;
        modificame.setStockPedienteVenta(valorFnatas);
        return modificame;
    }
}