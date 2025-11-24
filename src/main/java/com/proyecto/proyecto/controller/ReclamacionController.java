package com.proyecto.proyecto.controller;

import com.proyecto.proyecto.dto.MessageResponse;
import com.proyecto.proyecto.dto.ReclamacionRequest;
import com.proyecto.proyecto.model.Reclamacion;
import com.proyecto.proyecto.service.ReclamacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reclamaciones")
@RequiredArgsConstructor
public class ReclamacionController {

    private final ReclamacionService reclamacionService;

    @PostMapping
    public ResponseEntity<?> crearReclamacion(
            @Valid @RequestBody ReclamacionRequest request,
            Authentication authentication) {
        try {
            String dni = authentication.getName();
            Reclamacion reclamacion = reclamacionService.crearReclamacion(request, dni, null);
            return ResponseEntity.ok(reclamacion);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> crearReclamacionConEvidencia(
            @RequestParam("asunto") String asunto,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("tipo") String tipo,
            @RequestParam(value = "evidencia", required = false) org.springframework.web.multipart.MultipartFile evidencia,
            Authentication authentication) {
        try {
            String dni = authentication.getName();
            ReclamacionRequest request = new ReclamacionRequest();
            request.setAsunto(asunto);
            request.setDescripcion(descripcion);
            request.setTipo(tipo);
            
            Reclamacion reclamacion = reclamacionService.crearReclamacion(request, dni, evidencia);
            return ResponseEntity.ok(reclamacion);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/mis-reclamaciones")
    public ResponseEntity<?> obtenerMisReclamaciones(Authentication authentication) {
        try {
            String dni = authentication.getName();
            List<Reclamacion> reclamaciones = reclamacionService.obtenerMisReclamaciones(dni);
            return ResponseEntity.ok(reclamaciones);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/todas")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERADMINISTRADOR')")
    public ResponseEntity<?> obtenerTodasReclamaciones() {
        try {
            List<Reclamacion> reclamaciones = reclamacionService.obtenerTodasReclamaciones();
            return ResponseEntity.ok(reclamaciones);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
