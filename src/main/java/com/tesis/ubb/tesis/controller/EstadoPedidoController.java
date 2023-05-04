package com.tesis.ubb.tesis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tesis.ubb.tesis.models.EstadoPedido;
import com.tesis.ubb.tesis.service.EstadoPedidoService;

@CrossOrigin(origins = {"http://localhost:4200" , "*"})
@RestController
@RequestMapping("/api")
public class EstadoPedidoController {
    
    
    @Autowired
    EstadoPedidoService estadoPedidoService;
    
    @GetMapping("/estadopedido")
    public List<EstadoPedido> index(){
        return estadoPedidoService.findAll();
    }

    @GetMapping("/estadopedido/{id}")
    public EstadoPedido findById(@PathVariable Long id){
        return estadoPedidoService.findById(id);
    }
}
