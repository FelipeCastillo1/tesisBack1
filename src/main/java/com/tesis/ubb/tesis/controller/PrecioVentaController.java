package com.tesis.ubb.tesis.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tesis.ubb.tesis.models.PrecioVenta;
import com.tesis.ubb.tesis.models.Producto;
import com.tesis.ubb.tesis.service.PrecioVentaService;
import com.tesis.ubb.tesis.service.ProductoService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;

@CrossOrigin(origins = {"http://localhost:4200" , "*"})
@RestController
@RequestMapping("/api")
public class PrecioVentaController {

    @Autowired
    PrecioVentaService precioVentaService;

    @Autowired
    ProductoService productoService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TRABAJADOR')")
    @PostMapping("/precioVenta")
    public ResponseEntity<?> create(@Valid @RequestBody PrecioVenta precioVenta, BindingResult result) {

        PrecioVenta precioVentaNew = null;
        Map<String, Object> response = new HashMap<>();

        Producto NoMenoACOmpra=productoService.findById(precioVenta.getProducto().getId());

        if(NoMenoACOmpra.getUltimoPrecioCompra()>=precioVenta.getPrecio()){
            response.put("mensaje", "Error al registrar un nuevo precio de venta, el precio de venta debe ser mayor al precio de compra.");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(err -> {
                return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
            }).collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

        }
        try {
            precioVentaNew = precioVentaService.save(precioVenta);
            productoService.actualizaPrecioVenta(precioVenta.getProducto().getId(),precioVenta.getPrecio());
            precioVentaNew.setProducto(productoService.findById(precioVenta.getProducto().getId()));
            
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al registrar un nuevo precio de venta de productos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("", "El nuevo precio de venta de productos ha sido agregado con éxito");
        response.put("Registro: ", precioVentaNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TRABAJADOR')")
    @GetMapping("/precioVenta")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PrecioVenta> index() {
        return precioVentaService.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TRABAJADOR')")
    @GetMapping("precioVenta/busquedaFecha/{fechas}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PrecioVenta> pedidosPorFecha(@PathVariable String fechas) {

        String[] fechaFOrmatF1 = fechas.trim().split(",");
        String[] fechaFOrmatFf1= fechaFOrmatF1[0].trim().split(" ");
        String[] fechaFOrmatFf2= fechaFOrmatF1[1].trim().split(" ");
        Long id=  Long.parseLong(fechaFOrmatF1[2]);
        Date sqlDateF1=null;
        Date sqlDateF2=null;
        try {
            sqlDateF1=new SimpleDateFormat("yyyy-MM-dd").parse(fechaFOrmatFf1[0]);
            sqlDateF2=new SimpleDateFormat("yyyy-MM-dd").parse(fechaFOrmatFf2[0]);
        } catch (ParseException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        }  
        return precioVentaService.findlByIdDate(sqlDateF1,sqlDateF2,id);
    }   
    
}
