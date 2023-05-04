package com.tesis.ubb.tesis.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
@Table(name = "precio_venta")
public class PrecioVenta {

    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull(message = "El precio no puede ser vacio.")
    @Min(1)
    @Column(nullable = false)
    private Integer precio;

    @NotNull(message = "La cantidad no puede ser vacia.")
    @Min(-1)
    @Column(nullable = true)
    private Integer cantidad;

    //@Basic
    @NotNull(message = "Debe ingresar la fecha.")
    @Column(name="fecha",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date fechaVenta;

    @NotNull(message = "Indique que tipo de producto ha comprado.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Producto producto;

    public PrecioVenta() {
    }

    public PrecioVenta(Long id, @NotNull(message = "El precio no puede ser vacio.") @Min(1) Integer precio,
            @NotNull(message = "La cantidad no puede ser vacia.") @Min(0) Integer cantidad, Date fechaVenta,
            @NotNull(message = "Indique que tipo de producto ha comprado.") Producto producto) {
        this.id = id;
        this.precio = precio;
        this.cantidad = cantidad;
        this.fechaVenta = fechaVenta;
        this.producto = producto;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public java.util.Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(java.util.Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

   


    
}
