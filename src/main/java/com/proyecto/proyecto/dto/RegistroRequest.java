package com.proyecto.proyecto.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegistroRequest {
    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 dígitos")
    private String dni;

    @NotBlank(message = "El dígito verificador es obligatorio")
    private String dniDigitoVerificador;

    private String nombres;
    
    private String apellidos;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe ser válido")
    private String correo;

    @NotBlank(message = "El departamento es obligatorio")
    private String departamento;

    @NotBlank(message = "El distrito es obligatorio")
    private String distrito;

    @NotBlank(message = "El PIN es obligatorio")
    @Pattern(regexp = "\\d{6}", message = "El PIN debe tener 6 dígitos")
    private String pin;
}
