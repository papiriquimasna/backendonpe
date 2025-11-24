package com.proyecto.proyecto.repository;

import com.proyecto.proyecto.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    Optional<Voto> findByUsuarioIdAndCompletadoTrue(Long usuarioId);
    Optional<Voto> findByUsuarioIdAndCompletadoFalse(Long usuarioId);
}
