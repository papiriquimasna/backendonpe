package com.proyecto.proyecto.controller;

import com.proyecto.proyecto.dto.CambiarRoleRequest;
import com.proyecto.proyecto.dto.MessageResponse;
import com.proyecto.proyecto.dto.UsuarioResponse;
import com.proyecto.proyecto.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/perfil")
    public ResponseEntity<?> obtenerPerfil(Authentication authentication) {
        try {
            String dni = authentication.getName();
            UsuarioResponse response = usuarioService.obtenerPerfil(dni);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/foto-perfil")
    public ResponseEntity<?> cambiarFotoPerfil(
            @RequestParam("archivo") MultipartFile archivo,
            Authentication authentication) {
        try {
            String dni = authentication.getName();
            String mensaje = usuarioService.cambiarFotoPerfil(dni, archivo);
            return ResponseEntity.ok(new MessageResponse(mensaje));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/cambiar-role")
    @PreAuthorize("hasRole('SUPERADMINISTRADOR')")
    public ResponseEntity<?> cambiarRole(
            @Valid @RequestBody CambiarRoleRequest request,
            Authentication authentication) {
        try {
            String dni = authentication.getName();
            String mensaje = usuarioService.cambiarRole(request, dni);
            return ResponseEntity.ok(new MessageResponse(mensaje));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
