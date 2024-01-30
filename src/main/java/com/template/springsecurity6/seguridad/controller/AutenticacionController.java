/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.template.springsecurity6.seguridad.controller;

import com.template.springsecurity6.seguridad.model.RequestUsuario;
import com.template.springsecurity6.seguridad.seguridad.JwtUtilService;
import com.template.springsecurity6.seguridad.seguridad.TokenGenerado;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kgalarza
 */
@RestController
@RequestMapping("/publico/v1")
@Slf4j
public class AutenticacionController {

    @Autowired
    UserDetailsService usuarioDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @GetMapping("/test")
    public ResponseEntity<?> getMensaje() {

        var auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Datos del Usuario: {}", auth.getPrincipal());
        log.info("Datos de los Roles {}", auth.getAuthorities());
        log.info("Esta autenticado {}", auth.isAuthenticated());

        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("saludo", "Hola Mundo");
        return ResponseEntity.ok(mensaje);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenGenerado> authenticate(@RequestBody RequestUsuario authenticationReq) {
        log.info("Autenticando al usuario {}", authenticationReq.getUsuario());

        try {
            System.out.println("Antes del authenticationManager");
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authenticationReq.getUsuario(),
//                            authenticationReq.getClave()));

            //validar usuario y contraseña con datos de properties
            if (!authenticationReq.getClave().equals("1234567890")) {
                //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            System.out.println("Antes del usuarioDetailsService");
            final UserDetails userDetails = usuarioDetailsService.loadUserByUsername(
                    authenticationReq.getUsuario());

            System.out.println("Antes del jwtUtilService");
            final String jwt = jwtUtilService.generateToken(userDetails);

            System.out.println("Responder");
            return ResponseEntity.ok(new TokenGenerado(jwt));
        } catch (Exception e) {
            log.info("Error al autenticar", e);
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).build();
        }
    }

}
