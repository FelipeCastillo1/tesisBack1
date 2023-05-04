package com.tesis.ubb.tesis.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.FetchProfile.Item;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.http.HttpStatus;
import org.springframework.dao.DataAccessException;

import com.tesis.ubb.tesis.models.EstadoPedido;
import com.tesis.ubb.tesis.models.ItemPedido;
import com.tesis.ubb.tesis.models.Pedido;
import com.tesis.ubb.tesis.models.PrecioCompra;
import com.tesis.ubb.tesis.models.PrecioVenta;
import com.tesis.ubb.tesis.models.Producto;
import com.tesis.ubb.tesis.repository.EstadoPedidoRepository;
import com.tesis.ubb.tesis.repository.PedidoRepository;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ItemPedidoService itemPedidoService;

    @Autowired
    private PrecioVentaService precioVentaService;

    @Autowired
    private PrecioCompraService precioCompraService;

    @Override
    @Transactional(readOnly = true)
    public List<Pedido> findAll() {
        return (List<Pedido>) pedidoRepository.findAll();
    };

    @Override
    @Transactional
    public Pedido eliminacionLogica(Long id) {
        Pedido aEliminar = pedidoRepository.findById(id).orElse(null);
        Long idestado = (long) 9;
        EstadoPedido p = estadoPedidoRepository.findById(idestado).orElse(null);
        aEliminar.setEstadoPedido(p);
        pedidoRepository.save(aEliminar);
        return aEliminar;
    };

    @Override
    public List<Pedido> findPedidoByEstado(Long id) {
        return pedidoRepository.findAllEstadoById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoPedido> finEstadoPedidos() {
        return estadoPedidoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pedido> findUtilidadDelDia(Date fecha) {
        return pedidoRepository.findUtilidadDeHoy(fecha);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pedido> pedidosPorFechas(Date f1, Date f2) {
        return pedidoRepository.pedidosPorFechas(f1, f2);
    }

    @Override
    @Transactional
    public Pedido findById(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Pedido moodificarEstado(Pedido miPedido, Long NuevoEstado) {
        Pedido modificameEstado = pedidoRepository.findById(miPedido.getId()).orElse(null);
        EstadoPedido E = estadoPedidoRepository.findById(NuevoEstado).orElse(null);
        modificameEstado.setEstadoPedido(E);
        pedidoRepository.save(modificameEstado);
        return modificameEstado;
    };

    //DEPRECATED
    @Override
    @Transactional
    public boolean verificaMismoEstado(Pedido miPedido, Long estadoAntiguo) {
        if (miPedido.getEstadoPedido().getId() == estadoAntiguo) {
            return true;
        }
        return false;
    };

    @Override
    @Transactional
    public List<Pedido> agruparPordia(Date inicio, Date fin) {
        List<Pedido> pedidosENcontrados = pedidoRepository.pedidosPorFechasOrderASC(inicio, fin);
        List<Pedido> resultados = new ArrayList<>();

        Date actual = null;
        Integer count = 0;

        String pequeña = "1989-26-09";
        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        Date control = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        try {
            control = new SimpleDateFormat("yyyy-MM-dd").parse(pequeña);
        } catch (ParseException e) {

            e.printStackTrace();
        }

        for (Pedido pedido : pedidosENcontrados) {

            String strDate = dateFormat.format(pedido.getCreateAt());
            String[] fechaFOrmatF1 = strDate.trim().split(" ");

            try {
                actual = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFOrmatF1[0]);
            } catch (ParseException e) {

                e.printStackTrace();
            }

            System.out.println(actual);
            System.out.println(control);
            if (control.equals(actual)) {
                count += 1;
                resultados.get(resultados.size() - 1).setUtilidadPedido(count);
            } else {
                control = actual;
                count = 1;
                resultados.add(pedido);
                resultados.get(resultados.size() - 1).setUtilidadPedido(count);
            }
        }
        return resultados;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pedido> utilidadEntreDosFechas(Date f1, Date f2) {
        return pedidoRepository.UtilidadEntreDosFechas(f1, f2);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Pedido> findAll(Pageable pageable) {
        return pedidoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemPedido> revisaStockSuficiente(Pedido pedido) {

        List<ItemPedido> NuevoRegistro = new ArrayList<ItemPedido>();

        for (ItemPedido itemPedido : pedido.getItems()) {

            Long idProucto = itemPedido.getProducto().getId();

            Pedido pedidoActual = this.findById(pedido.getId());
            List<ItemPedido> almacenados = pedidoActual.getItems();

            Integer cantidadProductoActual = itemPedidoService.cantidadEnRegisto(itemPedido.getId());

            Integer cantidadProdcutoNuevo = itemPedido.getCantidad();

            if (cantidadProdcutoNuevo == 0) {
                NuevoRegistro.add(itemPedido);
            }

            Integer diferencia = (cantidadProductoActual) - (cantidadProdcutoNuevo);
            Producto p = productoService.findById(idProucto);
            Integer stock = p.getStock();
            Integer esViable = stock + diferencia;
            if (0 > esViable) {
                ItemPedido malo = new ItemPedido();
                malo.setCantidad(-1);
                malo.setProducto(p);
                NuevoRegistro.add(itemPedido);
                return NuevoRegistro;
            } else {
                Integer index = almacenados.indexOf(itemPedido);
                if (index != -1) {
                    NuevoRegistro.add(itemPedido);
                }

            }
        }

        return NuevoRegistro;
    }

    @Override
    @Transactional
    public Pedido actualizaProductosPorPedidoEditado(Pedido pedido) {

        Long idItemPedido;
        Long IdProducto;

        Integer diferencia;
        Pedido pedidoActual = this.findById(pedido.getId());

        for (ItemPedido itemPedido : pedido.getItems()) {
            idItemPedido = itemPedido.getId();

            IdProducto = itemPedido.getProducto().getId();
            Integer cantidadProductoActual = itemPedidoService.cantidadEnRegisto(itemPedido.getId());

            Integer cantidadProdcutoNuevo = itemPedido.getCantidad();

            diferencia = (cantidadProductoActual) - (cantidadProdcutoNuevo);

            // logica que quiero

            if (IdProducto != null && diferencia != null) {
                productoService.actualizastockPedienteVenta(IdProducto, -diferencia);
                productoService.actualizaStock(IdProducto, diferencia);
            }
        }

        if (pedido.getDescripcion() != null) {
            pedidoActual.setDescripcion(pedido.getDescripcion());
        }

        if (pedido.getObservacion() != null) {
            pedidoActual.setObservacion(pedido.getObservacion());
        }
        // pedidoActual.setItems(pedido.getItems());
        // pedidoRepository.save(pedido);
        return pedido;
    }
    

    @Override
    @Transactional
    public Integer calculaUtilidad(Pedido pedido) {
        Integer UtilidadEnELpedido = 0;
        Pedido pedidoActual = this.findById(pedido.getId());
        for (ItemPedido itemPedido : pedido.getItems()) {
            Long IdProducto = itemPedido.getProducto().getId();
            PrecioVenta precioVenta = precioVentaService.buscaDondeVa(pedidoActual.getCreateAt(), IdProducto);
            PrecioCompra precioCompra = precioCompraService.buscaDondeVa(pedidoActual.getCreateAt(), IdProducto);
            Integer precioV = precioVenta.getPrecio();
            Integer precioC = precioCompra.getPrecio();
            UtilidadEnELpedido += ((precioV - precioC) * itemPedido.getCantidad());
        }

        pedidoActual.setUtilidadPedido(UtilidadEnELpedido);
        pedidoRepository.save(pedidoActual);
        return UtilidadEnELpedido;
    }

    @Override
    @Transactional
    public List<ItemPedido> actualizaItemsPorPedidoEditado(Pedido pedido){
        Pedido p=this.findById(pedido.getId());
        p.setItems(pedido.getItems());
        pedidoRepository.save(p);
        return pedido.getItems();
    }


    @Override
    @Transactional
    public ResponseEntity<?> checkUpdate(Pedido pedido, BindingResult result,Long id){
        Pedido pedidoActual = this.findById(id);
        Pedido pedidoCheckActualizar = null;

        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            pedidoCheckActualizar = null;

            response.put("Error",  pedidoCheckActualizar);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if (pedidoActual == null) {
            response.put("El pedido ID: ".concat(id.toString().concat(" no existe en la base de datos")),pedidoActual);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        Long idEstadoActual = pedidoActual.getEstadoPedido().getId();
        if (idEstadoActual != 1) {
            pedidoActual=null;
            response.put("El pedido ID: ".concat(id.toString().concat(" no puede ser modificado.")),pedidoActual);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        List<ItemPedido> itemsAEliminar = this.revisaStockSuficiente(pedido);

        if (itemsAEliminar != null) {
            for (ItemPedido itemPedido : itemsAEliminar) {
                if (itemPedido.getCantidad() == -1) {
                    pedidoActual=null;
                    response.put("El producto: ".concat(
                            itemsAEliminar.get(0).getProducto().getNombreProducto().toString()
                                    .concat(" no tiene suficiente stock.")),pedidoActual);
                    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
                }
            }
        }
        if (itemsAEliminar == null) {
             itemPedidoService.deleteGrupo(itemsAEliminar);
        }
        pedidoCheckActualizar=pedido;
        response.put("", "El pedido ha sido actualizado con éxito");
        response.put("cliente", pedidoCheckActualizar);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<?> registrosUpdate(Pedido pedido, BindingResult result,Long id){
        Pedido pedidoActual = this.findById(id);
        Pedido pedidoAActualizar = null;
        Map<String, Object> response = new HashMap<>();
        try {
            this.actualizaProductosPorPedidoEditado(pedido);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el pedido");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        pedidoAActualizar=pedido;
        response.put("", "El pedido ha sido actualizado con éxito");
        response.put("cliente", pedidoAActualizar);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<?> pedidoUpdate(Pedido pedido, BindingResult result,Long id){
        Pedido pedidoActual = this.findById(id);
        Pedido pedidoUpdated = null;

        Map<String, Object> response = new HashMap<>();

        if (pedidoActual.getEstadoPedido().getId() != 1) {
            
            response.put("El pedido ID: ".concat(id.toString().concat(" no puede ser modificado.")),pedidoActual);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
  
        try {
           this.actualizaItemsPorPedidoEditado(pedido);
           pedidoActual.setUtilidadPedido(this.calculaUtilidad(pedido));
        if(pedido.getDescripcion()!=pedidoActual.getDescripcion()){
            pedidoActual.setDescripcion(pedido.getDescripcion());
        }
        if(pedido.getObservacion()!=pedidoActual.getObservacion()){
            pedidoActual.setObservacion(pedido.getObservacion());
        }
           //this.findById(pedido.getId()).setUtilidadPedido( this.calculaUtilidad(pedido));
           //this.actualizaItemsPorPedidoEditado(pedido);
           //pedidoRepository.save(pedidoActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el pedido");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //pedidoUpdated=pedidoActual;
        response.put("", "El pedido ha sido actualizado con éxito");
        response.put("cliente", pedidoUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<Pedido> findAllPedidosValidos(Pageable pageable) {
          
       return pedidoRepository.soloPedidosValidos(pageable);
    }
    
}