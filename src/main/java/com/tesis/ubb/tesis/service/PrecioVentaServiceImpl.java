package com.tesis.ubb.tesis.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tesis.ubb.tesis.models.PrecioVenta;
import com.tesis.ubb.tesis.models.Producto;
import com.tesis.ubb.tesis.repository.PrecioVentaRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrecioVentaServiceImpl implements PrecioVentaService {

    @Autowired
    private PrecioVentaRepository precioVentaRepository;

    @Override
    @Transactional
    public PrecioVenta save(PrecioVenta precioVenta) {
        return precioVentaRepository.save(precioVenta);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        precioVentaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrecioVenta> findAll() {
        return (List<PrecioVenta>) precioVentaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public PrecioVenta findById(Long id) {
        return precioVentaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public PrecioVenta saveByProducto(Producto producto) {
        PrecioVenta precioVenta = new PrecioVenta();
        Integer cero = 0;
        Date fecha = new Date();
        precioVenta.setCantidad(cero);
        precioVenta.setFechaVenta(fecha);
        precioVenta.setPrecio(producto.getUltimoPrecioVenta());
        precioVenta.setProducto(producto);
        return precioVentaRepository.save(precioVenta);
    };

    @Override
    @Transactional
    public PrecioVenta agregaCantidadVendidaARegistro(PrecioVenta venta, Integer agregarCantidadVenta) {
        long id = venta.getId();
        PrecioVenta modificame = precioVentaRepository.findById(id).orElse(null);
        Integer valorNuevo = modificame.getCantidad() + agregarCantidadVenta;
        modificame.setCantidad(valorNuevo);
        precioVentaRepository.save(modificame);
        return modificame;
    }

    @Override
    @Transactional
    public PrecioVenta buscaDondeVa(Date fechaPedido, Long Idporducto) {
        return precioVentaRepository.ubicaPosicion(Idporducto, fechaPedido);
    }

    @Override
    @Transactional
    public List<PrecioVenta> findlByIdDate(Date a,Date b, Long c){
        return precioVentaRepository.findByIdDate(c, a, b);
    }

    @Override
    @Transactional
    public Integer precioPorFecha(Date fechaPedido, Long Idporducto) {
        return precioVentaRepository.precioPorUbicaPosicion(Idporducto, fechaPedido);
    }
}
