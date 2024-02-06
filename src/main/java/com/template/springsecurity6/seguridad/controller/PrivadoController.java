package com.template.springsecurity6.seguridad.controller;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kgalarza
 */
@RestController
@RequestMapping("/privado/v1")
@Slf4j
public class PrivadoController {

    @GetMapping("/otros")
    public ResponseEntity<?> getMensaje() {
        log.info("Obteniendo el mensaje");

        var auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Datos del Usuario: {}", auth.getPrincipal());
        log.info("Datos de los Roles {}", auth.getAuthorities());
        log.info("Esta autenticado {}", auth.isAuthenticated());

        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("contenido", "Hola Mundo a todos");
        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/admin/soloadmin")
    public ResponseEntity<?> getMensajeAdmin() {

        var auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Datos del Usuario: {}", auth.getPrincipal());
        log.info("Datos de los Permisos {}", auth.getAuthorities());
        log.info("Esta autenticado {}", auth.isAuthenticated());

        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("contenido", "Hola Mundo a los Administradores");
        return ResponseEntity.ok(mensaje);
    }
}
