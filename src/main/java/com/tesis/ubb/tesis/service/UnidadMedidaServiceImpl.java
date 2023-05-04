package com.tesis.ubb.tesis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tesis.ubb.tesis.models.UnidadMedida;
import com.tesis.ubb.tesis.repository.UnidadMedidaRepository;

@Service
public class UnidadMedidaServiceImpl implements UnidadMedidaService {

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @Override
    @Transactional
    public List<UnidadMedida> findAll() {
        return (List<UnidadMedida>) unidadMedidaRepository.findAll();
    }

}
