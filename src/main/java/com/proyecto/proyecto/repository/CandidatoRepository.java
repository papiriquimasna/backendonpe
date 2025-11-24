package com.proyecto.proyecto.repository;

import com.proyecto.proyecto.model.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
    List<Candidato> findByTipoAndActivoTrue(Candidato.TipoCandidato tipo);
}
