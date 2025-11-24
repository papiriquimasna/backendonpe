package com.proyecto.proyecto.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ChatResponse {
    private Long id;
    private String mensaje;
    private String respuesta;
    private LocalDateTime fechaCreacion;
    private List<String> sugerencias;

    public ChatResponse(Long id, String mensaje, String respuesta, LocalDateTime fechaCreacion, List<String> sugerencias) {
        this.id = id;
        this.mensaje = mensaje;
        this.respuesta = respuesta;
        this.fechaCreacion = fechaCreacion;
        this.sugerencias = sugerencias;
    }
}
