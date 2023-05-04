package com.tesis.ubb.tesis.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import com.tesis.ubb.tesis.models.Trabajador;
import com.tesis.ubb.tesis.security.dto.Mensaje;
import com.tesis.ubb.tesis.security.dto.NuevoTrabajador;
import com.tesis.ubb.tesis.security.enums.RolNombre;
import com.tesis.ubb.tesis.security.models.Rol;
import com.tesis.ubb.tesis.security.models.Usuario;
import com.tesis.ubb.tesis.security.service.RolService;
import com.tesis.ubb.tesis.security.service.UsuarioService;
import com.tesis.ubb.tesis.service.TrabajadorService;

import net.bytebuddy.asm.Advice.Return;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api")
public class TrabajadorController {

    @Autowired
    TrabajadorService trabajadorService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/empleado")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Trabajador> index() {
        return trabajadorService.findAll();
    }

    @GetMapping(value = "empleado/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> show(@PathVariable Long id) {

        Trabajador trabajador = null;
        Map<String, Object> response = new HashMap<>();
        try {
            trabajador = trabajadorService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (trabajador == null) {
            response.put("mensaje", "El empleado ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Trabajador>(trabajador, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/empleado")
    public ResponseEntity<?> create(@Valid @RequestBody NuevoTrabajador nuevoTrabajador, BindingResult bindingResult) {

        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors().stream().map(err -> {
                return err.getField() + "' " + err.getDefaultMessage();
            }).collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

        }

        if (usuarioService.existsByNombreUsuario(nuevoTrabajador.getNombreUsuario())) {
            return new ResponseEntity<>(new Mensaje("nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
        }

        if (usuarioService.existsByEmail(nuevoTrabajador.getEmail())) {
            return new ResponseEntity<>(new Mensaje("ese email ya existe"), HttpStatus.BAD_REQUEST);
        }
        Usuario usuario = new Usuario(nuevoTrabajador.getNombreUsuario(),
                nuevoTrabajador.getEmail(),
                passwordEncoder.encode(nuevoTrabajador.getPassword()));
        Trabajador trabajador = new Trabajador(nuevoTrabajador.getRut(), nuevoTrabajador.getNombre(),
                nuevoTrabajador.getApellido(), nuevoTrabajador.getTelefono(), usuario);

        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_TRABAJADOR).get());
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        trabajadorService.save(trabajador);
        return new ResponseEntity<>(new Mensaje("trabajador guardado"), HttpStatus.CREATED);
    }

    @PutMapping("empleado/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Trabajador empleado, BindingResult result,
            @PathVariable Long id) {

        Trabajador empleadoActual = trabajadorService.findById(id);
        Trabajador empleadoUpdated = null;

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(err -> {
                return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
            }).collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

        }

        if (empleadoActual == null) {
            response.put("mensaje", "El empleado ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

            empleadoActual.setNombre(empleado.getNombre());
            empleadoActual.setApellido(empleado.getApellido());
            empleadoActual.setTelefono(empleado.getTelefono());
            empleadoActual.setRut(empleado.getRut());

            empleadoUpdated = trabajadorService.save(empleadoActual);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el empleado");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("", "El empleado ha sido actualizado con éxito");
        response.put("empleado", empleadoUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

    @DeleteMapping("empleado/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();

        try {

            Trabajador trabajador = trabajadorService.findById(id);
            trabajadorService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar al empleado");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Empleado eliminado con éxito");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
