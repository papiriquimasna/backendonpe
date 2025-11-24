package com.proyecto.proyecto.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "votos")
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "candidato_presidente_id")
    private Candidato candidatoPresidente;

    @ManyToOne
    @JoinColumn(name = "candidato_alcalde_id")
    private Candidato candidatoAlcalde;

    private LocalDateTime fechaVoto = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean completado = false;
}
