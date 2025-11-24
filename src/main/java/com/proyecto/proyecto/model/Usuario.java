package com.proyecto.proyecto.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombres;

    private String apellidos;

    @Column(nullable = false, unique = true, length = 8)
    private String dni;

    @Column(nullable = false, unique = true)
    private String correo;

    private String distrito;

    private String departamento;

    @Column(nullable = false, length = 60)
    private String pin;

    private String fotoPerfil;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USUARIO;

    private String codigoVerificacion;

    private LocalDateTime codigoExpiracion;

    @Column(nullable = false)
    private Boolean verificado = false;

    @Column(nullable = false)
    private Boolean activo = true;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    public enum Role {
        USUARIO, ADMINISTRADOR, SUPERADMINISTRADOR
    }
}
