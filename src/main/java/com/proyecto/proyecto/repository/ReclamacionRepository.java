package com.proyecto.proyecto.repository;

import com.proyecto.proyecto.model.Reclamacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReclamacionRepository extends JpaRepository<Reclamacion, Long> {
    List<Reclamacion> findByUsuarioId(Long usuarioId);
}
