package com.proyecto.proyecto.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reclamaciones")
public class Reclamacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String asunto;

    @Column(nullable = false, length = 2000)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoReclamacion tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoReclamacion estado = EstadoReclamacion.PENDIENTE;

    private String evidencia; // Ruta de la imagen (opcional)

    private String respuesta;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    private LocalDateTime fechaRespuesta;

    public enum TipoReclamacion {
        QUEJA, RECLAMO, SUGERENCIA
    }

    public enum EstadoReclamacion {
        PENDIENTE, EN_PROCESO, RESUELTO, CERRADO
    }
}
