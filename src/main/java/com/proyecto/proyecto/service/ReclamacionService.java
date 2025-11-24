package com.proyecto.proyecto.service;

import com.proyecto.proyecto.dto.ReclamacionRequest;
import com.proyecto.proyecto.model.Reclamacion;
import com.proyecto.proyecto.model.Usuario;
import com.proyecto.proyecto.repository.ReclamacionRepository;
import com.proyecto.proyecto.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReclamacionService {

    private final ReclamacionRepository reclamacionRepository;
    private final UsuarioRepository usuarioRepository;

    public Reclamacion crearReclamacion(ReclamacionRequest request, String dni, MultipartFile evidencia) {
        Usuario usuario = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Reclamacion reclamacion = new Reclamacion();
        reclamacion.setUsuario(usuario);
        reclamacion.setAsunto(request.getAsunto());
        reclamacion.setDescripcion(request.getDescripcion());
        
        try {
            reclamacion.setTipo(Reclamacion.TipoReclamacion.valueOf(request.getTipo()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo de reclamación inválido");
        }

        // Guardar evidencia si se proporciona
        if (evidencia != null && !evidencia.isEmpty()) {
            try {
                String nombreArchivo = UUID.randomUUID().toString() + "_" + evidencia.getOriginalFilename();
                Path rutaArchivo = Paths.get("uploads/reclamaciones", nombreArchivo);
                Files.createDirectories(rutaArchivo.getParent());
                Files.write(rutaArchivo, evidencia.getBytes());
                reclamacion.setEvidencia(nombreArchivo);
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar la evidencia: " + e.getMessage());
            }
        }

        return reclamacionRepository.save(reclamacion);
    }

    public List<Reclamacion> obtenerMisReclamaciones(String dni) {
        Usuario usuario = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return reclamacionRepository.findByUsuarioId(usuario.getId());
    }

    public List<Reclamacion> obtenerTodasReclamaciones() {
        return reclamacionRepository.findAll();
    }
}
