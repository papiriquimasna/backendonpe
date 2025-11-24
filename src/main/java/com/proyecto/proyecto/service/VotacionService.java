package com.proyecto.proyecto.service;

import com.proyecto.proyecto.model.Candidato;
import com.proyecto.proyecto.model.Usuario;
import com.proyecto.proyecto.model.Voto;
import com.proyecto.proyecto.repository.CandidatoRepository;
import com.proyecto.proyecto.repository.UsuarioRepository;
import com.proyecto.proyecto.repository.VotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VotacionService {

    private final VotoRepository votoRepository;
    private final CandidatoRepository candidatoRepository;
    private final UsuarioRepository usuarioRepository;

    public boolean yaVoto(String dni) {
        Usuario usuario = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return votoRepository.findByUsuarioIdAndCompletadoTrue(usuario.getId()).isPresent();
    }

    public List<Candidato> obtenerCandidatos(Candidato.TipoCandidato tipo) {
        return candidatoRepository.findByTipoAndActivoTrue(tipo);
    }

    @Transactional
    public String votarPresidente(String dni, Long candidatoId) {
        Usuario usuario = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (votoRepository.findByUsuarioIdAndCompletadoTrue(usuario.getId()).isPresent()) {
            throw new RuntimeException("Ya has votado anteriormente");
        }

        Candidato candidato = candidatoRepository.findById(candidatoId)
                .orElseThrow(() -> new RuntimeException("Candidato no encontrado"));

        if (candidato.getTipo() != Candidato.TipoCandidato.PRESIDENTE) {
            throw new RuntimeException("Este candidato no es para presidente");
        }

        // Buscar o crear voto en progreso
        Voto voto = votoRepository.findByUsuarioIdAndCompletadoFalse(usuario.getId())
                .orElse(new Voto());
        
        voto.setUsuario(usuario);
        voto.setCandidatoPresidente(candidato);
        
        votoRepository.save(voto);
        
        return "Voto por presidente registrado. Ahora elige tu alcalde.";
    }

    @Transactional
    public String votarAlcalde(String dni, Long candidatoId) {
        Usuario usuario = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Voto voto = votoRepository.findByUsuarioIdAndCompletadoFalse(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Primero debes votar por un presidente"));

        Candidato candidato = candidatoRepository.findById(candidatoId)
                .orElseThrow(() -> new RuntimeException("Candidato no encontrado"));

        if (candidato.getTipo() != Candidato.TipoCandidato.ALCALDE) {
            throw new RuntimeException("Este candidato no es para alcalde");
        }

        voto.setCandidatoAlcalde(candidato);
        voto.setCompletado(true);
        votoRepository.save(voto);

        // Incrementar votos
        voto.getCandidatoPresidente().setVotos(voto.getCandidatoPresidente().getVotos() + 1);
        candidato.setVotos(candidato.getVotos() + 1);
        candidatoRepository.save(voto.getCandidatoPresidente());
        candidatoRepository.save(candidato);

        return "¡Votación completada exitosamente! Gracias por participar.";
    }

    public Optional<Voto> obtenerVotoEnProgreso(String dni) {
        Usuario usuario = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return votoRepository.findByUsuarioIdAndCompletadoFalse(usuario.getId());
    }
}
