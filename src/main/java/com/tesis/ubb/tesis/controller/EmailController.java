package com.tesis.ubb.tesis.controller;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tesis.ubb.tesis.models.Email.ChangePassword;
import com.tesis.ubb.tesis.models.Email.EmailValues;
import com.tesis.ubb.tesis.security.dto.Mensaje;
import com.tesis.ubb.tesis.security.models.Usuario;
import com.tesis.ubb.tesis.security.service.UsuarioService;
import com.tesis.ubb.tesis.service.EmailService;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = {"http://localhost:4200" , "*"})
public class EmailController {

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Autowired
    EmailService emailService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static final String subject = "Cambio de Contraseña";

    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmailTemplate(@RequestBody EmailValues emailValues) {
        Optional<Usuario> usuarioOpt = usuarioService.getByNombreUsuarioOrEmail(emailValues.getMailTo());
        if (!usuarioOpt.isPresent()) {
            return new ResponseEntity<>(new Mensaje("No existe ese correo o nombre de usuario en nuestro sistema"),
                    HttpStatus.NOT_FOUND);
        }

        Usuario usuario = usuarioOpt.get();
        emailValues.setMailFrom(mailFrom);
        emailValues.setMailTo(usuario.getEmail());
        emailValues.setSubject(subject);
        emailValues.setUserName(usuario.getNombreUsuario());
        UUID uuid = UUID.randomUUID();
        String tokenPassword = uuid.toString();
        emailValues.setTokenPassword(tokenPassword);
        usuario.setTokenPasword(tokenPassword);
        usuarioService.save(usuario);
        emailService.SendEmail(emailValues);
        return new ResponseEntity<>(new Mensaje("Te hemos enviado un correo"), HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePassword password, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        if(!password.getPassword().equals(password.getConfirmPassword()))
            return new ResponseEntity(new Mensaje("Las contraseñas no coinciden"), HttpStatus.BAD_REQUEST);

        Optional<Usuario> usuarioOpt = usuarioService.getByTokenPasword(password.getTokenPassword());
        if (!usuarioOpt.isPresent()) {
            return new ResponseEntity<>(new Mensaje("No existe ningún usuario con esas credenciales"),
                    HttpStatus.NOT_FOUND);
        }
        Usuario usuario = usuarioOpt.get();
        String newPassword = passwordEncoder.encode(password.getPassword());
        usuario.setPassword(newPassword);
        usuario.setTokenPasword(null);
        usuarioService.save(usuario);
        return new ResponseEntity<>(new Mensaje("Contraseña actualizada"),HttpStatus.OK);
    }
}

