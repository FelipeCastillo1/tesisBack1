package com.tesis.ubb.tesis.models;


import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "productos")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombreProducto;
    
    private String imagen;

    @NotNull
    @Min(value = 0)
    private Integer stock;

    
    private Integer stockPedienteVenta;

    @NotNull
    @Min(value = 0)
    private Integer ultimoPrecioCompra;

    @NotNull
    @Min(value = 0)
    private Integer ultimoPrecioVenta;
    private boolean visibilidad;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidad_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private UnidadMedida unidadMedida;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TipoProducto tipoProducto;

    public Producto() {
    }

    public Producto(Long id, String nombreProducto, String imagen, Integer stock,Integer stockPedienteVenta,  UnidadMedida unidadMedida,
            Integer ultimoPrecioCompra, Integer ultimoPrecioVenta, boolean visibilidad, TipoProducto tipoProducto) {
        this.id = id;
        this.nombreProducto = nombreProducto;
        this.imagen = imagen;
        this.stock = stock;
        this.stockPedienteVenta=stockPedienteVenta;
        this.unidadMedida = unidadMedida;
        this.ultimoPrecioCompra = ultimoPrecioCompra;
        this.ultimoPrecioVenta = ultimoPrecioVenta;
        this.visibilidad = visibilidad;
        this.tipoProducto = tipoProducto;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreProducto() {
        return this.nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getImagen() {
        return this.imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getStock() {
        return this.stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public UnidadMedida getUnidadMedida() {
        return this.unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Integer getUltimoPrecioCompra() {
        return this.ultimoPrecioCompra;
    }

    public void setUltimoPrecioCompra(Integer ultimoPrecioCompra) {
        this.ultimoPrecioCompra = ultimoPrecioCompra;
    }

    public Integer getUltimoPrecioVenta() {
        return this.ultimoPrecioVenta;
    }

    public void setUltimoPrecioVenta(Integer ultimoPrecioVenta) {
        this.ultimoPrecioVenta = ultimoPrecioVenta;
    }

    public boolean isVisibilidad() {
        return this.visibilidad;
    }

    public boolean getVisibilidad() {
        return this.visibilidad;
    }

    public void setVisibilidad(boolean visibilidad) {
        this.visibilidad = visibilidad;
    }

    public TipoProducto getTipoProducto() {
        return this.tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public Producto id(Long id) {
        setId(id);
        return this;
    }

    public Producto nombreProducto(String nombreProducto) {
        setNombreProducto(nombreProducto);
        return this;
    }

    public Producto imagen(String imagen) {
        setImagen(imagen);
        return this;
    }

    public Producto stock(Integer stock) {
        setStock(stock);
        return this;
    }
    
    public Integer getStockPedienteVenta() {
        return stockPedienteVenta;
    }

    public void setStockPedienteVenta(Integer stockPedienteVenta) {
        this.stockPedienteVenta = stockPedienteVenta;
    }

    public Producto unidadMedida(UnidadMedida unidadMedida) {
        setUnidadMedida(unidadMedida);
        return this;
    }

    public Producto ultimoPrecioCompra(Integer ultimoPrecioCompra) {
        setUltimoPrecioCompra(ultimoPrecioCompra);
        return this;
    }

    public Producto ultimoPrecioVenta(Integer ultimoPrecioVenta) {
        setUltimoPrecioVenta(ultimoPrecioVenta);
        return this;
    }

    public Producto visibilidad(boolean visibilidad) {
        setVisibilidad(visibilidad);
        return this;
    }

    public Producto tipoProducto(TipoProducto tipoProducto) {
        setTipoProducto(tipoProducto);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Producto)) {
            return false;
        }
        Producto producto = (Producto) o;
        return Objects.equals(id, producto.id) && Objects.equals(nombreProducto, producto.nombreProducto)
                && Objects.equals(imagen, producto.imagen) && Objects.equals(stock, producto.stock)
                && Objects.equals(unidadMedida, producto.unidadMedida)
                && Objects.equals(ultimoPrecioCompra, producto.ultimoPrecioCompra)
                && Objects.equals(ultimoPrecioVenta, producto.ultimoPrecioVenta) && visibilidad == producto.visibilidad
                && Objects.equals(tipoProducto, producto.tipoProducto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreProducto, imagen, stock, unidadMedida, ultimoPrecioCompra, ultimoPrecioVenta,
                visibilidad, tipoProducto);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", nombreProducto='" + getNombreProducto() + "'" +
                ", imagen='" + getImagen() + "'" +
                ", stock='" + getStock() + "'" +
                ", unidadMedida='" + getUnidadMedida() + "'" +
                ", ultimoPrecioCompra='" + getUltimoPrecioCompra() + "'" +
                ", ultimoPrecioVenta='" + getUltimoPrecioVenta() + "'" +
                ", visibilidad='" + isVisibilidad() + "'" +
                ", tipoProducto='" + getTipoProducto() + "'" +
                "}";
    }

}
