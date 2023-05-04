package com.tesis.ubb.tesis.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tesis.ubb.tesis.security.models.Usuario;

@Entity
@Table(name = "pedidos")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;
    private String observacion;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private EstadoPedido estadoPedido;

    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }

    @JsonIgnoreProperties(value = { "pedidos", "hibernateLazyInitializer", "handler" }, allowSetters = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pedido_id")
    private List<ItemPedido> items;

    private Integer utilidadPedido;

    public Double getTotal() {
        Double total = 0.00;
        for (ItemPedido item : items) {
            total += item.getImporte();
        }
        return total;
    }

    public Pedido() {
        this.items = new ArrayList<>();
    }

    public Pedido(Long id, String descripcion, String observacion, Date createAt, @NotNull EstadoPedido estadoPedido,
            Cliente cliente, Integer utilidadPedido) {
        this.id = id;
        this.descripcion = descripcion;
        this.observacion = observacion;
        this.createAt = createAt;
        this.estadoPedido = estadoPedido;
        this.cliente = cliente;
        this.utilidadPedido = utilidadPedido;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservacion() {
        return this.observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getCreateAt() {
        return this.createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    public Pedido id(Long id) {
        setId(id);
        return this;
    }

    public Pedido descripcion(String descripcion) {
        setDescripcion(descripcion);
        return this;
    }

    public Pedido observacion(String observacion) {
        setObservacion(observacion);
        return this;
    }

    public Pedido createAt(Date createAt) {
        setCreateAt(createAt);
        return this;
    }

    public Pedido cliente(Cliente cliente) {
        setCliente(cliente);
        return this;
    }

    public List<ItemPedido> getItems() {
        return this.items;
    }

    public void setItems(List<ItemPedido> items) {
        this.items = items;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public Integer getUtilidadPedido() {
        return utilidadPedido;
    }

    public void setUtilidadPedido(Integer utilidadPedido) {
        this.utilidadPedido = utilidadPedido;
    }

}
