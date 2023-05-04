package com.tesis.ubb.tesis.security.controller;

import java.util.HashMap;
import java.util.HashSet;

import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.tesis.ubb.tesis.models.Cliente;
import com.tesis.ubb.tesis.security.dto.JwtDto;
import com.tesis.ubb.tesis.security.dto.LoginUsuario;
import com.tesis.ubb.tesis.security.dto.Mensaje;
import com.tesis.ubb.tesis.security.dto.NuevoUsuario;
import com.tesis.ubb.tesis.security.enums.RolNombre;
import com.tesis.ubb.tesis.security.jwt.JwtProvider;
import com.tesis.ubb.tesis.security.models.Rol;
import com.tesis.ubb.tesis.security.models.Usuario;
import com.tesis.ubb.tesis.security.service.RolService;
import com.tesis.ubb.tesis.security.service.UsuarioService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:4200" , "*"})
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Mensaje("campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
        }

        if (usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario())) {
            return new ResponseEntity<>(new Mensaje("nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);
        }

        if (usuarioService.existsByEmail(nuevoUsuario.getEmail())) {
            return new ResponseEntity<>(new Mensaje("ese email ya existe"), HttpStatus.BAD_REQUEST);
        }
        Cliente cliente = new Cliente(nuevoUsuario.getNombre(), nuevoUsuario.getApellido(), nuevoUsuario.getTelefono(),
                nuevoUsuario.getDireccion(), nuevoUsuario.getNombreEmpresa());
        Usuario usuario = new Usuario(nuevoUsuario.getNombreUsuario(),
                nuevoUsuario.getEmail(),
                passwordEncoder.encode(nuevoUsuario.getPassword()), cliente);

        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_CLIENTE).get());
        if (nuevoUsuario.getRoles().contains("admin")) {
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        }
        if (nuevoUsuario.getRoles().contains("trabajador")) {
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_TRABAJADOR).get());
        }
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        return new ResponseEntity<>(new Mensaje("usuario guardado"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {

        // Map<String, Object> response = new HashMap<>();

        // if (bindingResult.hasErrors()){
        // List<String> errors = bindingResult.getFieldErrors().stream().map(err -> {
        // return "El campo '" + err.getField() + "' " + err.getDefaultMessage();
        // }).collect(Collectors.toList());
        // response.put("errors", errors);
        // return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        // }
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        JwtDto jwtDto = new JwtDto(jwt);
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }

}
