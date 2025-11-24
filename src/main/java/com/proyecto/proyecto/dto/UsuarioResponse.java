package com.proyecto.proyecto.dto;

import com.proyecto.proyecto.model.Usuario;
import lombok.Data;

@Data
public class UsuarioResponse {
    private Long id;
    private String nombres;
    private String apellidos;
    private String dni;
    private String correo;
    private String distrito;
    private String departamento;
    private String fotoPerfil;
    private String role;

    public static UsuarioResponse fromUsuario(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setNombres(usuario.getNombres());
        response.setApellidos(usuario.getApellidos());
        response.setDni(usuario.getDni());
        response.setCorreo(usuario.getCorreo());
        response.setDistrito(usuario.getDistrito());
        response.setDepartamento(usuario.getDepartamento());
        response.setFotoPerfil(usuario.getFotoPerfil());
        response.setRole(usuario.getRole().name());
        return response;
    }
}
