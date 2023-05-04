package com.tesis.ubb.tesis.models;

import java.io.Serializable;
import java.util.regex.Matcher;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.Pattern;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.tesis.ubb.tesis.security.models.Usuario;

@Entity
@Table(name = "trabajadores")
public class Trabajador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(nullable = false,unique = true)
    @Pattern(regexp = "^[0-9]{7,8}[-][0-9kK]$", message = "El Rut ingresado no es válido, debe ser sin puntos y con guion")
    private String rut;

    @NotEmpty
    @Column(nullable = false)
    private String nombre;

    @NotEmpty
    @Column(nullable = false)
    private String apellido;

    @NotEmpty
    @Column(nullable = false,unique = true)
    @Pattern(regexp = "^\\+56(9)\\d{8}$", message = "El número de teléfono ingresado no es válido, debe agregar +569")
    private String telefono;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Trabajador() {
    }

    public Trabajador(Long id, String rut, String nombre, String apellido, String telefono) {
        this.id = id;
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    public Trabajador(String rut, String nombre, String apellido, String telefono, Usuario usuario) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.usuario = usuario;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRut() {
        return this.rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Trabajador id(Long id) {
        setId(id);
        return this;
    }

    public Trabajador rut(String rut) {
        setRut(rut);
        return this;
    }

    public Trabajador nombre(String nombre) {
        setNombre(nombre);
        return this;
    }

    public Trabajador apellido(String apellido) {
        setApellido(apellido);
        return this;
    }

    public Trabajador telefono(String telefono) {
        setTelefono(telefono);
        return this;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
