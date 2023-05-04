package com.tesis.ubb.tesis.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tesis.ubb.tesis.models.TipoProducto;
import com.tesis.ubb.tesis.repository.TipoProductoRepository;

@Service
public class TipoProductoServiceImpl implements TipoProductoService {

    @Autowired
    private TipoProductoRepository tipoProductoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TipoProducto> findAll() {
        return (List<TipoProducto>) tipoProductoRepository.findAll();

    }

    @Override
    public TipoProducto findById(Long id) {
        return tipoProductoRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return tipoProductoRepository.existsById(id);
    }
}