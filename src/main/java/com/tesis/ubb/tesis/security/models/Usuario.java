package com.tesis.ubb.tesis.security.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tesis.ubb.tesis.models.Cliente;
import com.tesis.ubb.tesis.models.Pedido;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable{

    private static final Long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String nombreUsuario;
    @Email
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    private String password;

    private String tokenPasword;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>();

    public Usuario() {
        // this.pedidos = new ArrayList<>();
    }

    public Usuario(@NotNull String nombreUsuario, @NotNull String email,
            @NotNull String password) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
    }


    public Usuario(String nombreUsuario, String email, String password, Cliente cliente) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.cliente = cliente;
        
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    public String getTokenPasword() {
        return this.tokenPasword;
    }

    public void setTokenPasword(String tokenPasword) {
        this.tokenPasword = tokenPasword;
    }
    // public List<Pedido> getPedidos() {
    //     return this.pedidos;
    // }

    // public void setPedidos(List<Pedido> pedidos) {
    //     this.pedidos = pedidos;
    // }


    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}