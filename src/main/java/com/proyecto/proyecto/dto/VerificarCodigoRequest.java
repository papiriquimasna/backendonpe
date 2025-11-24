package com.proyecto.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerificarCodigoRequest {
    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    @NotBlank(message = "El código es obligatorio")
    private String codigo;
}
