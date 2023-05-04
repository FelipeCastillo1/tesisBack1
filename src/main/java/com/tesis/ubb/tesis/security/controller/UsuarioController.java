package com.tesis.ubb.tesis.security.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tesis.ubb.tesis.security.models.Usuario;
import com.tesis.ubb.tesis.security.repository.UsuarioRepository;
import com.tesis.ubb.tesis.security.service.UsuarioServiceInterface;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200" , "*"})
public class UsuarioController {

    @Autowired
    UsuarioServiceInterface usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TRABAJADOR')")
    @GetMapping("/usuario")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Usuario> index() {
        return usuarioService.findAll();
    }

    // @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TRABAJADOR') hasRole('ROLE_CLIENTE')")
    @GetMapping(value = "usuario/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> showUsuarioById(@PathVariable Long id) {

        Usuario usuario = null;
        Map<String, Object> response = new HashMap<>();
        try {
            usuario = usuarioService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (usuario == null) {
            response.put("mensaje", "El usuario ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);

    }

    @GetMapping(value = "usuario/filtrar/{term}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> showUsuarioByUsername(@PathVariable String term){
        Usuario usuario = null;
        Map<String, Object> response = new HashMap<>();
        try {
            usuario = usuarioRepository.existByNombreUsuario(term);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (usuario == null) {
            response.put("mensaje", "El usuario no existe en la base de datos" );
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }

}
