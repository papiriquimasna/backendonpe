package com.proyecto.proyecto.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CambiarRoleRequest {
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El rol es obligatorio")
    private String nuevoRole; // USUARIO, ADMINISTRADOR, SUPERADMINISTRADOR
}
