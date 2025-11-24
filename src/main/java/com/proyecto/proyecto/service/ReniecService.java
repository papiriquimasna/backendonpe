package com.proyecto.proyecto.service;

import com.proyecto.proyecto.dto.ConsultaDniResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class ReniecService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // API principal para consulta DNI
    private static final String API_APISPERU = "https://dniruc.apisperu.com/api/v1/dni/";
    
    // Token desde application.properties
    @org.springframework.beans.factory.annotation.Value("${reniec.api.token:}")
    private String API_TOKEN;

    public ConsultaDniResponse consultarDni(String dni) {
        ConsultaDniResponse response = new ConsultaDniResponse();
        
        try {
            if (dni == null || !dni.matches("\\d{8}")) {
                response.setSuccess(false);
                response.setMensaje("DNI inválido. Debe tener 8 dígitos.");
                return response;
            }

            // Intentar con múltiples APIs
            response = intentarConsultarDni(dni);
            
            if (!response.isSuccess()) {
                response.setSuccess(false);
                response.setMensaje("No se pudieron obtener los datos del DNI. Por favor, ingresa tus datos manualmente.");
            }

        } catch (Exception e) {
            response.setSuccess(false);
            response.setMensaje("Error al consultar DNI: " + e.getMessage());
            System.err.println("Error consultando DNI: " + e.getMessage());
        }

        return response;
    }

    private ConsultaDniResponse intentarConsultarDni(String dni) {
        // Intentar con dniruc.apisperu.com con token como query parameter
        if (API_TOKEN != null && !API_TOKEN.isEmpty()) {
            try {
                // El token va en la URL como parámetro, no en el header
                String url = API_APISPERU + dni + "?token=" + API_TOKEN;
                System.out.println("🔍 Consultando DNI: " + API_APISPERU + dni + "?token=***");

                ResponseEntity<String> apiResponse = restTemplate.getForEntity(url, String.class);

                System.out.println("📡 Respuesta API: " + apiResponse.getBody());

                if (apiResponse.getStatusCode() == HttpStatus.OK) {
                    JsonNode jsonNode = objectMapper.readTree(apiResponse.getBody());
                    
                    if (jsonNode.has("nombres") && !jsonNode.get("nombres").isNull()) {
                        ConsultaDniResponse response = new ConsultaDniResponse();
                        response.setSuccess(true);
                        response.setDni(dni);
                        response.setNombres(jsonNode.get("nombres").asText());
                        response.setApellidoPaterno(jsonNode.get("apellidoPaterno").asText());
                        response.setApellidoMaterno(jsonNode.get("apellidoMaterno").asText());
                        response.setNombreCompleto(
                            jsonNode.get("nombres").asText() + " " +
                            jsonNode.get("apellidoPaterno").asText() + " " +
                            jsonNode.get("apellidoMaterno").asText()
                        );
                        response.setMensaje("Datos encontrados exitosamente");
                        
                        System.out.println("✅ DNI consultado exitosamente: " + dni);
                        return response;
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ API falló: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("⚠️ No hay token configurado. Configura reniec.api.token en application.properties");
        }

        // Si la API falla
        ConsultaDniResponse response = new ConsultaDniResponse();
        response.setSuccess(false);
        response.setDni(dni);
        response.setMensaje("No se pudieron obtener los datos del DNI. Por favor, ingresa tus datos manualmente.");
        return response;
    }

    // Método público que usa la misma lógica
    public ConsultaDniResponse consultarDniPublico(String dni) {
        return consultarDni(dni);
    }
}
