package com.proyecto.proyecto.dto;

import lombok.Data;

@Data
public class ConsultaDniResponse {
    private boolean success;
    private String dni;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombreCompleto;
    private String mensaje;

    public String getApellidosCompletos() {
        if (apellidoPaterno != null && apellidoMaterno != null) {
            return apellidoPaterno + " " + apellidoMaterno;
        }
        return "";
    }
}
