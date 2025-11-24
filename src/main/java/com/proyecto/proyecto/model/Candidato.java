package com.proyecto.proyecto.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "candidatos")
public class Candidato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String partido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCandidato tipo;

    private String propuestas;

    @Column(nullable = false)
    private Boolean activo = true;

    private Integer votos = 0;

    public enum TipoCandidato {
        PRESIDENTE, ALCALDE
    }
}
