package com.proyecto.proyecto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "El DNI es obligatorio")
    private String dni;

    @NotBlank(message = "El PIN es obligatorio")
    private String pin;
}
