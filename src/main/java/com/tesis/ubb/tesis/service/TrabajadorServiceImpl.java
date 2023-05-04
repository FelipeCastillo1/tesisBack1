package com.tesis.ubb.tesis.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tesis.ubb.tesis.models.Trabajador;
import com.tesis.ubb.tesis.repository.TrabajadorRepository;

@Service
public class TrabajadorServiceImpl implements TrabajadorService{

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    @Override
	@Transactional(readOnly = true)
	public List<Trabajador> findAll() {
		return (List<Trabajador>) trabajadorRepository.findAll();
	}

	@Override
	@Transactional
	public Trabajador save(Trabajador empleado) {
		return trabajadorRepository.save(empleado);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		trabajadorRepository.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public Trabajador findById(Long id) {
		return trabajadorRepository.findById(id).orElse(null);
	}

}
