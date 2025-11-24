package com.proyecto.proyecto.repository;

import com.proyecto.proyecto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByDni(String dni);
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByDni(String dni);
    boolean existsByCorreo(String correo);
}
