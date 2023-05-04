package com.tesis.ubb.tesis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tesis.ubb.tesis.models.TipoProducto;
import com.tesis.ubb.tesis.service.TipoProductoService;

@CrossOrigin(origins = {"http://localhost:4200" , "*"})
@RestController
@RequestMapping("/api")
public class TipoProductoController {

    @Autowired
    TipoProductoService tipoProductoService;
    
    @GetMapping("/tipoproducto")
    public List<TipoProducto> index(){
        return tipoProductoService.findAll();
    }
}

