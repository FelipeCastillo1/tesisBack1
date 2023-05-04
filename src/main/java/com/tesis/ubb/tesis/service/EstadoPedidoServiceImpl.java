package com.tesis.ubb.tesis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tesis.ubb.tesis.models.EstadoPedido;
import com.tesis.ubb.tesis.repository.EstadoPedidoRepository;

@Service
public class EstadoPedidoServiceImpl implements EstadoPedidoService{

    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EstadoPedido> findAll() {
        return (List<EstadoPedido>) estadoPedidoRepository.findAll();
    }

    @Override
    public EstadoPedido findById(Long id) {
        return estadoPedidoRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return estadoPedidoRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoPedido> soloEstadosValidos(){
        return (List<EstadoPedido>) estadoPedidoRepository.soloEstadosValidos();
    }

}