package com.proyecto.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String tipo = "Bearer";
    private UsuarioResponse usuario;

    public LoginResponse(String token, UsuarioResponse usuario) {
        this.token = token;
        this.usuario = usuario;
    }
}
