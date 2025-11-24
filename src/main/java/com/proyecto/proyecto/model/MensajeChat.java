package com.proyecto.proyecto.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "mensajes_chat")
public class MensajeChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 1000)
    private String mensaje;

    @Column(nullable = false, length = 1000)
    private String respuesta;

    private LocalDateTime fechaCreacion = LocalDateTime.now();
}
