package com.proyecto.proyecto.controller;

import com.proyecto.proyecto.dto.ChatRequest;
import com.proyecto.proyecto.dto.MessageResponse;
import com.proyecto.proyecto.model.MensajeChat;
import com.proyecto.proyecto.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/mensaje")
    public ResponseEntity<?> enviarMensaje(
            @Valid @RequestBody ChatRequest request,
            Authentication authentication) {
        try {
            String dni = authentication.getName();
            MensajeChat mensaje = chatService.enviarMensaje(request, dni);
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/historial")
    public ResponseEntity<?> obtenerHistorial(Authentication authentication) {
        try {
            String dni = authentication.getName();
            List<MensajeChat> historial = chatService.obtenerHistorial(dni);
            return ResponseEntity.ok(historial);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/sugerencias")
    public ResponseEntity<?> obtenerSugerencias(Authentication authentication) {
        try {
            String dni = authentication.getName();
            List<String> sugerencias = chatService.obtenerSugerencias(dni);
            return ResponseEntity.ok(sugerencias);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
