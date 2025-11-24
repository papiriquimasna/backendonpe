package com.proyecto.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReclamacionRequest {
    @NotBlank(message = "El asunto es obligatorio")
    private String asunto;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "El tipo es obligatorio")
    private String tipo; // QUEJA, RECLAMO, SUGERENCIA
}
