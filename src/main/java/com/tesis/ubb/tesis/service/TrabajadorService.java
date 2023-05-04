package com.tesis.ubb.tesis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tesis.ubb.tesis.models.Trabajador;

@Service
public interface TrabajadorService {
    
	public List<Trabajador> findAll();
	
	public Trabajador save(Trabajador cliente);
	
	public void delete(Long id);
	
	public Trabajador findById(Long id);

}
