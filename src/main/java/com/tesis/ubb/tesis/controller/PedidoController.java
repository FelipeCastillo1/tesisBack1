package com.tesis.ubb.tesis.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tesis.ubb.tesis.models.EstadoPedido;
import com.tesis.ubb.tesis.models.ItemPedido;
import com.tesis.ubb.tesis.models.Pedido;
import com.tesis.ubb.tesis.models.PrecioCompra;
import com.tesis.ubb.tesis.models.PrecioVenta;
import com.tesis.ubb.tesis.models.Producto;
import com.tesis.ubb.tesis.security.service.UsuarioService;
import com.tesis.ubb.tesis.security.service.UsuarioServiceInterface;
import com.tesis.ubb.tesis.service.ClienteService;
import com.tesis.ubb.tesis.service.EstadoPedidoService;
import com.tesis.ubb.tesis.service.ItemPedidoService;
import com.tesis.ubb.tesis.service.PedidoService;
import com.tesis.ubb.tesis.service.PrecioCompraService;
import com.tesis.ubb.tesis.service.PrecioVentaService;
import com.tesis.ubb.tesis.service.ProductoService;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api")
public class PedidoController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private EstadoPedidoService estadoPedidoService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PrecioVentaService precioVentaService;

    @Autowired
    private PrecioCompraService precioCompraService;

    @Autowired
    private ItemPedidoService itemPedidoService;

    @Autowired
    private UsuarioServiceInterface usuarioService;

    // !Retorna todo la información del pedido.
    // ! Cliente + Pedido
    @GetMapping("pedido/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Pedido show(@PathVariable Long id) {
        return clienteService.findPedidoById(id);
    }

    @DeleteMapping("pedido/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        pedidoService.eliminacionLogica(id);
        // clienteService.deletePedido(id);
    }

    @GetMapping("pedido/filtrar-productos/{term}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Producto> filtraProductos(@PathVariable String term) {
        return clienteService.findProductoByNombre(term);
    }

    @PostMapping("/pedido")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Pedido create(@RequestBody Pedido pedido) {
        List<ItemPedido> productosEnElPedido = pedido.getItems();
        Integer UtilidadEnELpedido = 0;
        for (ItemPedido itemPedido : productosEnElPedido) {
            productoService.actualizastockPedienteVenta(itemPedido.getProducto().getId(), itemPedido.getCantidad());
            productoService.actualizaStock(itemPedido.getProducto().getId(), (itemPedido.getCantidad() * -1));
            // Calcula la utilidad
            UtilidadEnELpedido += ((itemPedido.getProducto().getUltimoPrecioVenta()
                    - itemPedido.getProducto().getUltimoPrecioCompra())
                    * itemPedido.getCantidad());
        }
        ;
        Long revision = (long) 1;
        pedido.setEstadoPedido(estadoPedidoService.findById(revision));
        pedido.setUtilidadPedido(UtilidadEnELpedido);
        return clienteService.savePedido(pedido);
    }

    @PostMapping("/pedidoVentaLocal")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Pedido createPedidoVentaLocal(@RequestBody Pedido pedido) {
        List<ItemPedido> productosEnElPedido = pedido.getItems();
        Integer UtilidadEnELpedido = 0;
        for (ItemPedido itemPedido : productosEnElPedido) {
            productoService.actualizastockPedienteVenta(itemPedido.getProducto().getId(), itemPedido.getCantidad());
            productoService.actualizaStock(itemPedido.getProducto().getId(), (itemPedido.getCantidad() * -1));
            // Calcula la utilidad
            UtilidadEnELpedido += ((itemPedido.getProducto().getUltimoPrecioVenta()
                    - itemPedido.getProducto().getUltimoPrecioCompra())
                    * itemPedido.getCantidad());
        }
        ;
        Long revision = (long) 7;
        pedido.setEstadoPedido(estadoPedidoService.findById(revision));
        pedido.setUtilidadPedido(UtilidadEnELpedido);
        return clienteService.savePedido(pedido);
    }
    @PostMapping("/crearMerma")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Pedido createMerma(@RequestBody Pedido pedido) {
        List<ItemPedido> productosEnElPedido = pedido.getItems();
        Integer UtilidadEnELpedido = 0;
        for (ItemPedido itemPedido : productosEnElPedido) {
            // productoService.actualizastockPedienteVenta(itemPedido.getProducto().getId(), itemPedido.getCantidad());
            productoService.actualizaStock(itemPedido.getProducto().getId(), (itemPedido.getCantidad() * -1));
            // Calcula la utilidad
            UtilidadEnELpedido += ((itemPedido.getProducto().getUltimoPrecioVenta()
                    - itemPedido.getProducto().getUltimoPrecioCompra())
                    * itemPedido.getCantidad());
        }
        ;
        Long revision = (long) 8;
        pedido.setEstadoPedido(estadoPedidoService.findById(revision));
        pedido.setUtilidadPedido(UtilidadEnELpedido);
        return clienteService.savePedido(pedido);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TRABAJADOR')")
    @GetMapping("/pedido")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Pedido> showAll() {
        List<Pedido> pedidos = pedidoService.findAll();
        return (List<Pedido>) pedidos;
    }

    @GetMapping("/pedido/estado/{id}")
    public List<Pedido> listarPedidosByEstado(@PathVariable Long id) {
        return pedidoService.findPedidoByEstado(id);
    }

    @GetMapping("/pedido/estado")
    public List<EstadoPedido> listarEstadosPedido() {
        return estadoPedidoService.soloEstadosValidos();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TRABAJADOR')")
    @GetMapping("/pedido/utilidad/diaria/{createAt}")
    // @ResponseStatus(code = HttpStatus.OK)
    public List<Pedido> findUtilidadDelDia(@PathVariable String createAt) {
        // System.out.println("La fecha llegada es "+ pedido.getCreateAt());
        // System.out.println("La fecha llegada es "+ pedido.getCreateAt());
        // System.out.println("La fecha llegada es "+ pedido.getCreateAt());

        // SimpleDateFormat formatter5=new SimpleDateFormat("yyyy/MM/dd");
        // Date date1=formatter5.parse(createAt);

        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd
        // HH:mm:ss z");
        // ZonedDateTime zonedDateTime = ZonedDateTime.parse("2015-05-05 10:15:30
        // America/Chile", formatter);
        String[] array = createAt.trim().split(" ");

        LocalDate d = LocalDate.parse(array[0]);
        java.sql.Date sqlDate = java.sql.Date.valueOf(d);

        return pedidoService.findUtilidadDelDia(sqlDate);
    }

    // @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TRABAJADOR')")
    // @GetMapping("/pedido/utilidad/diaria/")
    // // @ResponseStatus(code = HttpStatus.OK)
    // public List<Pedido> findUtilidadDelDia(@RequestBody Pedido pedido) {
    // System.out.println("La fecha llegada es "+ pedido.getCreateAt());
    // System.out.println("La fecha llegada es "+ pedido.getCreateAt());
    // System.out.println("La fecha llegada es "+ pedido.getCreateAt());
    // return pedidoService.findUtilidadDelDia(pedido.getCreateAt());
    // }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TRABAJADOR')")
    @GetMapping("/pedido/busquedaFecha/")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Pedido> pedidosPorFecha(@RequestBody String[] fechas) {
        String f1;
        String f2;
        // String[] test={"hola","chao"};
        String[] fechaFOrmatF1 = fechas[0].trim().split(" ");
        LocalDate lDF1 = LocalDate.parse(fechaFOrmatF1[0]);
        java.sql.Date sqlDateF1 = java.sql.Date.valueOf(lDF1);

        String[] fechaFOrmatF2 = fechas[1].trim().split(" ");
        LocalDate lDF2 = LocalDate.parse(fechaFOrmatF2[0]);
        java.sql.Date sqlDateF2 = java.sql.Date.valueOf(lDF2);
        return pedidoService.pedidosPorFechas(sqlDateF1, sqlDateF2);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TRABAJADOR')")
    @GetMapping("/pedido/busquedaFecha/{fechas}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Pedido> pedidosPorFecha(@PathVariable String fechas) {

        String[] fechaFOrmatF1 = fechas.trim().split(",");
        String[] fechaFOrmatFf1 = fechaFOrmatF1[0].trim().split(" ");
        String[] fechaFOrmatFf2 = fechaFOrmatF1[1].trim().split(" ");
        Date sqlDateF1 = null;
        Date sqlDateF2 = null;
        try {
            sqlDateF1 = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFOrmatFf1[0]);
            sqlDateF2 = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFOrmatFf2[0]);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pedidoService.pedidosPorFechas(sqlDateF1, sqlDateF2);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TRABAJADOR')")
    @PutMapping("/pedido/estado/mod/")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity modificarEstado(@RequestBody Pedido nuevo) {
        Pedido pedidoActual = pedidoService.findById(nuevo.getId());
        Long a = (long) 1/* Solicitado */,
                b = (long) 2 /* Armando pedido */,
                c = (long) 3 /* En camino */,
                d = (long) 4 /* Recibido por el cliente */,
                e = (long) 5 /* Facturado */,
                f = (long) 6 /* Pagado */,
                g = (long) 7 /* Venta local */,
                h = (long) 8 /* Merma */,
                i = (long) 9 /* Eliminado */;

        Integer stockOFantasmaOCantidadVenta = 0;
        Date fechaPedido = pedidoActual.getCreateAt();
        Long Idporducto;
        PrecioVenta venta;
        EstadoPedido es;

        Long idPedidoActual = pedidoActual.getEstadoPedido().getId();
        Long idEstadoNuevo = nuevo.getEstadoPedido().getId();

        Map<String, Object> response = new HashMap<>();
        // if (pedidoService.verificaMismoEstado(nuevo, idPedidoActual)) {
        // response.put("mensaje",
        // "Error al cambiar el estado, el estado no puede ser cambiado al mismo
        // estado.");
        // return new ResponseEntity<Map<String, Object>>(response,
        // HttpStatus.INTERNAL_SERVER_ERROR);
        // }

        // if (idPedidoActual == i) {

        // for (ItemPedido p : pedidoActual.getItems()) {
        // Idporducto = p.getProducto().getId();
        // Producto productoConsultado = productoService.findById(Idporducto);
        // Integer stockActualProdcuto = productoConsultado.getStock();
        // stockOFantasmaOCantidadVenta = p.getCantidad();
        // if (stockActualProdcuto < stockOFantasmaOCantidadVenta) {
        // response.put("mensaje", "Error al cambiar el estado, no hay suficiente stock
        // de "
        // .concat(productoConsultado.getNombreProducto().concat(".")));
        // return new ResponseEntity<Map<String, Object>>(response,
        // HttpStatus.INTERNAL_SERVER_ERROR);
        // }
        // }
        // stockOFantasmaOCantidadVenta = 0;
        // for (ItemPedido p : pedidoActual.getItems()) {
        // Idporducto = p.getProducto().getId();
        // stockOFantasmaOCantidadVenta = p.getCantidad();
        // productoService.actualizaStock(Idporducto, -stockOFantasmaOCantidadVenta);
        // if (idEstadoNuevo == a) {
        // productoService.moodificarFantasma(Idporducto, stockOFantasmaOCantidadVenta);
        // }
        // if (idEstadoNuevo == f || idEstadoNuevo == g) {
        // venta = precioVentaService.buscaDondeVa(fechaPedido, Idporducto);
        // precioVentaService.agregaCantidadVendidaARegistro(venta,
        // (stockOFantasmaOCantidadVenta));
        // }
        // }
        // }

        for (ItemPedido p : pedidoActual.getItems()) {
            Idporducto = p.getProducto().getId();
            stockOFantasmaOCantidadVenta = p.getCantidad();

            if (idPedidoActual == a || idPedidoActual == b || idPedidoActual == c) {
                if (!(idEstadoNuevo == a || idEstadoNuevo == b || idEstadoNuevo == c)) {
                    productoService.moodificarFantasma(Idporducto, -stockOFantasmaOCantidadVenta);
                }
                if (idEstadoNuevo == f || idEstadoNuevo == g) {
                    venta = precioVentaService.buscaDondeVa(fechaPedido, Idporducto);
                    precioVentaService.agregaCantidadVendidaARegistro(venta, stockOFantasmaOCantidadVenta);
                }
                if (idEstadoNuevo == i) {
                    productoService.actualizaStock(Idporducto, stockOFantasmaOCantidadVenta);
                }
            }
            if (idPedidoActual == d || idPedidoActual == e || idPedidoActual == h) {
                if (idEstadoNuevo == a || idEstadoNuevo == b || idEstadoNuevo == c) {
                    productoService.moodificarFantasma(Idporducto, stockOFantasmaOCantidadVenta);
                }
                if (idEstadoNuevo == f || idEstadoNuevo == g) {
                    venta = precioVentaService.buscaDondeVa(fechaPedido, Idporducto);
                    precioVentaService.agregaCantidadVendidaARegistro(venta, stockOFantasmaOCantidadVenta);
                }
                if (idEstadoNuevo == i) {
                    productoService.actualizaStock(Idporducto, stockOFantasmaOCantidadVenta);
                }
            }
            if (idPedidoActual == f || idPedidoActual == g) {
                if (!(idEstadoNuevo == f || idEstadoNuevo == g)) {
                    venta = precioVentaService.buscaDondeVa(fechaPedido, Idporducto);
                    precioVentaService.agregaCantidadVendidaARegistro(venta, (-stockOFantasmaOCantidadVenta));
                }
                if (idEstadoNuevo == a || idEstadoNuevo == b || idEstadoNuevo == c) {
                    productoService.moodificarFantasma(Idporducto, stockOFantasmaOCantidadVenta);
                }
                if (idEstadoNuevo == i) {
                    productoService.moodificarFantasma(Idporducto, stockOFantasmaOCantidadVenta);
                }
            }
            if (idPedidoActual == i) {
                if (!(idEstadoNuevo == i)) {
                    productoService.actualizaStock(Idporducto, -stockOFantasmaOCantidadVenta);
                }
                if (idEstadoNuevo == a || idEstadoNuevo == b || idEstadoNuevo == c) {
                    productoService.moodificarFantasma(Idporducto, stockOFantasmaOCantidadVenta);
                }
                if (idEstadoNuevo == f || idEstadoNuevo == g) {
                    venta = precioVentaService.buscaDondeVa(fechaPedido, Idporducto);
                    precioVentaService.agregaCantidadVendidaARegistro(venta, stockOFantasmaOCantidadVenta);
                }
            }

        }
        es = estadoPedidoService.findById(idEstadoNuevo);
        pedidoService.moodificarEstado(nuevo, es.getId());

        Pedido RegistrosFinal = pedidoService.findById(nuevo.getId());

        response.put("", "Se ha cambiado el estado a ".concat(es.getNombreEstado().concat(".")));
        response.put("cliente", RegistrosFinal);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TRABAJADOR')")
    @GetMapping("/pedido/dia/{fechas}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Pedido> pedidosAgrupadosPorDia(@PathVariable String fechas) {

        String[] fechaFOrmatF1 = fechas.trim().split(",");
        String[] fechaFOrmatFf1 = fechaFOrmatF1[0].trim().split(" ");
        String[] fechaFOrmatFf2 = fechaFOrmatF1[1].trim().split(" ");
        Date sqlDateF1 = null;
        Date sqlDateF2 = null;
        try {
            sqlDateF1 = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFOrmatFf1[0]);
            sqlDateF2 = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFOrmatFf2[0]);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pedidoService.agruparPordia(sqlDateF1, sqlDateF2);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TRABAJADOR')")
    @PutMapping("/pedido/checkUpdate/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> checkUpdate(@Valid @RequestBody Pedido pedido, BindingResult result,
            @PathVariable Long id) {

        return pedidoService.checkUpdate(pedido, result, id);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TRABAJADOR')")
    @PutMapping("/pedido/registrosUpdate/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> registrosUpdate(@Valid @RequestBody Pedido pedido, BindingResult result,
            @PathVariable Long id) {

        return pedidoService.registrosUpdate(pedido, result, id);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TRABAJADOR')")
    @PutMapping("/pedido/pedidoUpdate/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> pedidoUpdate(@Valid @RequestBody Pedido pedido, BindingResult result,
            @PathVariable Long id) {

        return pedidoService.pedidoUpdate(pedido, result, id);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TRABAJADOR')")
    @GetMapping("/pedido/utilidad2Fechas/{fechas}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Pedido> utilidadEntreDosFechas(@PathVariable String fechas) {

        String[] fechaFOrmatF1 = fechas.trim().split(",");
        String[] fechaFOrmatFf1 = fechaFOrmatF1[0].trim().split(" ");
        String[] fechaFOrmatFf2 = fechaFOrmatF1[1].trim().split(" ");
        Date sqlDateF1 = null;
        Date sqlDateF2 = null;
        try {
            sqlDateF1 = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFOrmatFf1[0]);
            sqlDateF2 = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFOrmatFf2[0]);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pedidoService.utilidadEntreDosFechas(sqlDateF1, sqlDateF2);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TRABAJADOR')")
    @GetMapping("/pedido/porfechas/{fechas}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Pedido> filtrarPedidosPorFechas(@PathVariable String fechas) {

        String[] fechaFOrmatF1 = fechas.trim().split(",");
        String[] fechaFOrmatFf1 = fechaFOrmatF1[0].trim().split(" ");
        String[] fechaFOrmatFf2 = fechaFOrmatF1[1].trim().split(" ");
        Date sqlDateF1 = null;
        Date sqlDateF2 = null;
        try {
            sqlDateF1 = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFOrmatFf1[0]);
            sqlDateF2 = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFOrmatFf2[0]);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pedidoService.pedidosPorFechas(sqlDateF1, sqlDateF2);

    }

    // !PAGINACION ALLPEDIDOS
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TRABAJADOR')")
    @GetMapping("/pedido/page/{page}")
    @ResponseStatus(code = HttpStatus.OK)
    public Page<Pedido> showAll(@PathVariable Integer page) {
        // return pedidoService.findAllPedidosValidos(PageRequest.of(page, 100));

        // PageRequest pageable = PageRequest.of(page, 100, Sort.by("id",Sort.Direction.ASC));

        return pedidoService.findAll(PageRequest.of(page, 100, Sort.by(Sort.Direction.DESC, "id")));
    }

}
