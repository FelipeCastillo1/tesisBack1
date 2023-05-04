package com.tesis.ubb.tesis.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.tesis.ubb.tesis.enums.TiposProducto;

@Entity
@Table(name = "tipos_producto")
public class TipoProducto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idTipoProducto;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TiposProducto nombreTipo;

	public TipoProducto() {
	}

	public TipoProducto(@NotNull TiposProducto nombreTipo){
		this.nombreTipo = nombreTipo;
	}
	public Long getIdTipoProducto() {
		return this.idTipoProducto;
	}

	public void setIdTipoProducto(Long idTipoProducto) {
		this.idTipoProducto = idTipoProducto;
	}

	public TiposProducto getNombreTipo() {
		return this.nombreTipo;
	}

	public void setNombreTipo(TiposProducto nombreTipo) {
		this.nombreTipo = nombreTipo;
	}

}
