package com.proyecto.proyecto.controller;

import com.proyecto.proyecto.dto.ConsultaDniResponse;
import com.proyecto.proyecto.dto.MessageResponse;
import com.proyecto.proyecto.service.ReniecService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reniec")
@RequiredArgsConstructor
public class ReniecController {

    private final ReniecService reniecService;

    @GetMapping("/consultar-dni/{dni}")
    public ResponseEntity<?> consultarDni(@PathVariable String dni) {
        try {
            ConsultaDniResponse response = reniecService.consultarDniPublico(dni);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error al consultar DNI: " + e.getMessage()));
        }
    }

    @GetMapping("/validar-dni/{dni}")
    public ResponseEntity<?> validarDni(@PathVariable String dni) {
        try {
            if (dni == null || !dni.matches("\\d{8}")) {
                return ResponseEntity.badRequest().body(new MessageResponse("DNI inválido. Debe tener 8 dígitos."));
            }
            
            ConsultaDniResponse response = reniecService.consultarDniPublico(dni);
            
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse(response.getMensaje()));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error al validar DNI: " + e.getMessage()));
        }
    }
}
